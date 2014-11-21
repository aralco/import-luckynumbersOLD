'use strict';

/* Services */

var URL = {};
URL.host = "http://localhost";

luckynumbersApp.factory('Register', function ($resource) {
        return $resource('app/rest/register', {}, {
        });
    });

luckynumbersApp.factory('Activate', function ($resource) {
        return $resource('app/rest/activate', {}, {
            'get': { method: 'GET', params: {}, isArray: false}
        });
    });

luckynumbersApp.factory('Password', function ($resource) {
        return $resource('app/rest/account/change_password', {}, {
        });
    });

luckynumbersApp.factory('Sessions', function ($resource) {
        return $resource('app/rest/account/sessions/:series', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });

luckynumbersApp.factory('Account', function ($resource) {
        return $resource(URL.host + ':7001/import-luckynumbers/luckynumbers/sysutils/loggeduser', {}, {
        });
    });

luckynumbersApp.factory('Jobs', function ($resource) {
        return $resource(URL.host + ':7001/import-luckynumbers/luckynumbers/monitor', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });

luckynumbersApp.factory('MetricsService',function ($http) {
            return {
            get: function() {
                var promise = $http.get('metrics/metrics').then(function(response){
                    return response.data;
                });
                return promise;
            }
        };
    });

luckynumbersApp.factory('ThreadDumpService', function ($http) {
        return {
            dump: function() {
                var promise = $http.get('dump').then(function(response){
                    return response.data;
                });
                return promise;
            }
        };
    });

luckynumbersApp.factory('HealthCheckService', function ($rootScope, $http) {
        return {
            check: function() {
                var promise = $http.get('health').then(function(response){
                    return response.data;
                });
                return promise;
            }
        };
    });

luckynumbersApp.factory('LogsService', function ($resource) {
        return $resource('app/rest/logs', {}, {
            'findAll': { method: 'GET', isArray: true},
            'changeLevel':  { method: 'PUT'}
        });
    });

luckynumbersApp.factory('AuditsService', function ($http) {
        return {
            findAll: function() {
                var promise = $http.get('app/rest/audits/all').then(function (response) {
                    return response.data;
                });
                return promise;
            },
            findByDates: function(fromDate, toDate) {
                var promise = $http.get('app/rest/audits/byDates', {params: {fromDate: fromDate, toDate: toDate}}).then(function (response) {
                    return response.data;
                });
                return promise;
            }
        }
    });

luckynumbersApp.factory('Session', function () {
        this.create = function (login, userId, userRoles) {
            this.login = login;
            this.userId = userId;
            this.userRoles = userRoles;
        };
        this.invalidate = function () {
            this.login = null;
            this.userId = null;
            this.userRoles = null;
        };
        return this;
    });

luckynumbersApp.factory('AuthenticationSharedService', function ($rootScope, $http, authService, Session, Account, Base64Service, AccessToken) {
        return {
            login: function (param) {
                var data = "username=" + param.username + "&password=" + param.password + "&grant_type=password&scope=app&client_secret=botigo&client_id=tigobo";
                $http.post(URL.host + ':7001/import-luckynumbers/oauth/token', data, {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "Accept": "application/json",
                        "access_type" : "offline"
                    },
                    ignoreAuthModule: 'ignoreAuthModule'
                }).success(function (data, status, headers, config) {
                    httpHeaders.common['Authorization'] = 'Bearer ' + data.access_token;
                    AccessToken.set(data);

                    Account.get(function(data) {
                       Session.create(data.name, data.id, data.role);
                       $rootScope.account = Session;
                        authService.loginConfirmed(data);
                    });
                }).error(function (data, status, headers, config) {
                    $rootScope.authenticated = false;
                    Session.invalidate();
                    AccessToken.remove();
                    delete httpHeaders.common['Authorization'];

                    $rootScope.errorMessage=data.errors[0];
                    $rootScope.authenticationError = true;
                    $rootScope.$broadcast('event:auth-loginRequired', data);

                });
            },
            valid: function (authorizedRoles) {
                if(AccessToken.get() !== null) {
                    httpHeaders.common['Authorization'] = 'Bearer ' + AccessToken.get();
                }

                $http({
                        method: 'GET',
                        url: URL.host + ':7001/import-luckynumbers/luckynumbers/config/city',
                        data: {'key1':'value1', 'key2':'value2'},
                        headers: {"Content-Type": "application/json"},
                        ignoreAuthModule: 'ignoreAuthModule'

                }).success(function (data, status, headers, config) {
                    if (!Session.login || AccessToken.get() != undefined) {
                        if (AccessToken.get() == undefined || AccessToken.expired()) {
                            $rootScope.$broadcast("event:auth-loginRequired");
                            return;
                        }
                        Account.get(function(data) {
                            Session.create(data.name, data.id, data.role);
                            $rootScope.account = Session;
                            if (!$rootScope.isAuthorized(authorizedRoles)) {
                                // user is not allowed
                               $rootScope.$broadcast("event:auth-notAuthorized");
                            } else {
                                $rootScope.$broadcast("event:auth-loginConfirmed");
                            }
                        });
                    }else{
                        if (!$rootScope.isAuthorized(authorizedRoles)) {
                                // user is not allowed
                                $rootScope.$broadcast("event:auth-notAuthorized");
                        } else {
                                $rootScope.$broadcast("event:auth-loginConfirmed");
                        }
                    }
                }).error(function (data, status, headers, config) {
                    if (!$rootScope.isAuthorized(authorizedRoles)) {
                        $rootScope.$broadcast('event:auth-loginRequired', data);
                    }
                });
            },
            isAuthorized: function (authorizedRoles) {
                if (!angular.isArray(authorizedRoles)) {
                    if (authorizedRoles == '*') {
                        return true;
                    }

                    authorizedRoles = [authorizedRoles];
                }

                var isAuthorized = false;
                angular.forEach(authorizedRoles, function(authorizedRole) {
                    var authorized = (!!Session.login &&
                        Session.userRoles.indexOf(authorizedRole) !== -1);

                    if (authorized || authorizedRole == '*') {
                        isAuthorized = true;
                    }
                });

                return isAuthorized;
            },
            logout: function () {
                $rootScope.authenticationError = false;
                $rootScope.authenticated = false;
                $rootScope.account = null;
                AccessToken.remove();

                $http.get(URL.host + ':7001/import-luckynumbers/logout');
                Session.invalidate();
                delete httpHeaders.common['Authorization'];
                authService.loginCancelled();
            }
        };
    });
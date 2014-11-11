
'use strict';

/* App Module */
var httpHeaders;


var luckynumbersApp = angular.module('luckynumbersApp', ['ngRoute', 'route-segment', 'view-segment', 'http-auth-interceptor', 'ngResource', 'ngCookies', 'luckynumbersAppUtils']);


luckynumbersApp.config(function ($routeSegmentProvider, $httpProvider, USER_ROLES) {
    $routeSegmentProvider.options.autoLoadTemplates = true;
 
    $routeSegmentProvider
        .when('/', 'mprogramaciones')
        .when('/mprogramaciones', 'mprogramaciones')
        .when('/nprogramacion', 'nprogramacion')
        .when('/login', 'login')
 
        .segment('mprogramaciones',{
            default: true,
            templateUrl: 'templates/misprogramaciones.html',
            controller: 'MisProgramacionesController',
        })
        .segment('nprogramacion', {
            templateUrl: 'templates/nuevaprogramacion.html',
            controller: 'NuevaProgramacionController',
        })
         .segment('modal', {
            templateUrl: 'templates/modal.html',
            controller: 'DirectivesController',
        })
         .segment('login', {
            templateUrl: 'templates/login.html',
            controller: 'LoginController',
        })

httpHeaders = $httpProvider.defaults.headers;

})

.run(function($rootScope, $location, $http, AuthenticationSharedService, Session, USER_ROLES) {

                var pagesA = {};
                pagesA["mprogramaciones"] = {
                        authorizedRoles: [USER_ROLES.user]
                    };
                pagesA["nprogramacion"] = {
                        authorizedRoles: [USER_ROLES.user]
                    };
                pagesA["login"] = {
                        authorizedRoles: [USER_ROLES.all]
                    };

                $rootScope.authenticated = false;
                $rootScope.$on('$routeChangeStart', function (event, next) {
                    $rootScope.isAuthorized = AuthenticationSharedService.isAuthorized;
                    $rootScope.userRoles = USER_ROLES;

                    if (next.segment) {
                        AuthenticationSharedService.valid(pagesA[next.segment].authorizedRoles);
                    } else {
                       // AuthenticationSharedService.valid(pagesA[next.segment].authorizedRoles);
                    }

                });

                // Call when the the client is confirmed
                $rootScope.$on('event:auth-loginConfirmed', function(data) {
                    $rootScope.authenticated = true;
                    if ($location.path() === "/login") {
                        var search = $location.search();
                        if (search.redirect !== undefined) {
                            $location.path(search.redirect).search('redirect', null).replace();
                        } else {
                            $location.path('/').replace();
                        }
                    }
                });

                // Call when the 401 response is returned by the server
                $rootScope.$on('event:auth-loginRequired', function(rejection) {
                    console.log("Invalidate!");
                    Session.invalidate();
                    $rootScope.authenticated = false;
                    if ($location.path() !== "" && $location.path() !== "/register" &&
                            $location.path() !== "/activate" && $location.path() !== "/login") {
                        var redirect = $location.path();
                        $location.path('/login').search('redirect', redirect).replace();
                    }
                });

                // Call when the 403 response is returned by the server
                $rootScope.$on('event:auth-notAuthorized', function(rejection) {
                    $rootScope.errorMessage = 'errors.403';
                    $location.path('/error').replace();
                });

                // Call when the user logs out
                $rootScope.$on('event:auth-loginCancelled', function() {
                    $location.path('');
                });
        });

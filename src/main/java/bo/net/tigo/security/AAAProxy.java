package bo.net.tigo.security;

/**
 * Created by aralco on 10/29/14.
 */
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

public class AAAProxy {

    private Proxy proxy;
    private RestTemplate template;

    public AAAProxy() {
        proxy = new Proxy(Type.HTTP, new InetSocketAddress(
                "proxy.abc.net", 3001));

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        requestFactory.setProxy(proxy);

        template = new RestTemplate(requestFactory);
    }

    public boolean isValidUser(String user, String password) {

//        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//        map.add("user", user);
//        map.add("password", password);
//        System.out.println("Datos-> usr:"+user+", passwd:"+password);
//        HttpEntity<String> response = template.postForEntity(
//                "https://authentication.local/auth", map,
//                String.class);
//
//        HttpHeaders headers = response.getHeaders();
//
//        List<String> cookies = headers.get("Set-Cookie");
//
//        for (String cookie : cookies) {
//            if (cookie.indexOf("Auth")!=-1) {
//                System.out.println("Datos-> :cookie.indexOf(\"Auth\")!=-1");
//                return true;
//            }
//        }
//
//        return false;
        System.out.println("#### AAAProxy:isValidUser:"+user);
        return true;
    }

}
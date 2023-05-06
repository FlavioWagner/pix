package br.com.flaviowagner.projetos.pix.psp;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

public interface IPSP {

    String getAuthentication();
    Map<String,String> getQueryString();
    RestTemplate getRestTemplate();

    String getOauthHost();
    String getHost();
}

package br.com.flaviowagner.projetos.pix.psp;

import br.com.flaviowagner.projetos.pix.annotations.PSP;
import br.com.flaviowagner.projetos.pix.utils.PSPUtil;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@PSP(codigoBanco = "001",nomeBanco = "Banco do Brasil")
public class BB implements IPSP {

    @Override
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Override
    public String getOauthHost() {
        return PSPUtil.isProducao() ? "https://oauth.bb.com.br" : "https://oauth.hm.bb.com.br";
    }

    @Override
    public String getHost() {
        return PSPUtil.isProducao() ? "https://api.hm.bb.com.br/pix/v1" : "https://api.hm.bb.com.br/pix/v1";
    }

    @Override
    public String getAuthentication() {
        return "Basic " + Base64.getEncoder().encodeToString((getClientId() + ":" + getClientSecret()).getBytes());
    }

    private static String getApplicationKey(){
        return PSPUtil.isProducao() ? "coloque aqui sua applicationKey de produção" : "coloque aqui sua applicationKey de ambiente Sandbox";
    }

    private static String getClientId(){
        return PSPUtil.isProducao() ? "coloque aqui o clientId de produção" : "coloque aqui o clientId de ambiente Sandbox";
    }

    private static String getClientSecret(){
        return PSPUtil.isProducao() ? "coloque aqui o clientSecret de produção" : "coloque aqui o clientSecret de ambiente Sandbox";
    }

    public Map<String,String> getQueryString(){
        Map<String, String> header = new HashMap<>();
        header.put("gw-dev-app-key",getApplicationKey());
        return header;
    }

}

package br.com.flaviowagner.projetos.pix.service;

import br.com.flaviowagner.projetos.pix.psp.IPSP;
import br.com.flaviowagner.projetos.pix.request.RequestPIX;
import br.com.flaviowagner.projetos.pix.response.ResponsePix;
import br.com.flaviowagner.projetos.pix.response.ResponseToken;
import br.com.flaviowagner.projetos.pix.utils.PSPUtil;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;


@Service
public class PixService {
    private IPSP getPSP(String psp) {
        try {
            var className = PSPUtil.listarPSPs().stream().filter(x -> x.getCodigoBanco().equals(psp)).collect(Collectors.toList()).get(0).getClassName();
            Class<?> clazz = Class.forName(className);
            IPSP obj = (IPSP) clazz.newInstance();
            return obj;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    public ResponseToken getToken(String psp) {

            IPSP pspInfo = getPSP(psp);

            String url = pspInfo.getOauthHost() + "/oauth/token?grant_type=client_credentials&scope=cob.write+cob.read+webhook.read+webhook.write";
            RestTemplate restTemplate = pspInfo.getRestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Authorization", pspInfo.getAuthentication());

            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
            ResponseToken response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseToken.class).getBody();
            return response;
    }

    public ResponsePix consultar(String psp, String txid) {

        var token = getToken(psp);

        IPSP pspInfo = getPSP(psp);
        RestTemplate restTemplate = pspInfo.getRestTemplate();
        String url = pspInfo.getHost() + "/cob/" + txid;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token.getAccessToken());

        if ((getPSP(psp).getQueryString() != null) && (pspInfo.getQueryString().size() > 0)) {
            url += "?";
            for (Map.Entry<String, String> entry : pspInfo.getQueryString().entrySet()) {
                url += entry.getKey() + "=" + entry.getValue();
            }
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponsePix response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, ResponsePix.class).getBody();

        return response;
    }

    public ResponsePix criar(String psp, String txid, RequestPIX requestPIX) {

        var token = getToken(psp);

        IPSP pspInfo = getPSP(psp);
        RestTemplate restTemplate = pspInfo.getRestTemplate();
        String url = pspInfo.getHost() + "/cob/" + txid;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token.getAccessToken());

        if ((getPSP(psp).getQueryString() != null) && (pspInfo.getQueryString().size() > 0)) {
            url += "?";
            for (Map.Entry<String, String> entry : pspInfo.getQueryString().entrySet()) {
                url += entry.getKey() + "=" + entry.getValue();
            }
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(requestPIX.toJson(), headers);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ResponsePix.class).getBody();
    }

    private boolean PSPisValid(String psp) {
        return !PSPUtil.listarPSPs().stream().filter(x -> x.getCodigoBanco().equals(psp)).collect(Collectors.toList()).isEmpty();
    }

    public ResponseEntity<?> recuperarPSP(String psp) {
        return ResponseEntity.ok(PSPUtil.listarPSPs().stream().filter(x -> x.getCodigoBanco().equals(psp)).collect(Collectors.toList()).get(0));
    }
}
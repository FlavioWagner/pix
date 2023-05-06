package br.com.flaviowagner.projetos.pix.psp;

import br.com.flaviowagner.projetos.pix.annotations.PSP;
import br.com.flaviowagner.projetos.pix.utils.PSPUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.FileReader;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Map;

@PSP(codigoBanco = "748",nomeBanco = "Sicredi")
public class Sicredi implements IPSP {

    @Override
    public String getAuthentication() {
        return "Basic " + Base64.getEncoder().encodeToString((getClientId() + ":" + getClientSecret()).getBytes());
    }

    @Override
    public Map<String, String> getQueryString() {
        return null;
    }

    @Override
    public RestTemplate getRestTemplate() {
        return new RestTemplate(getRequestFactory());
    }

    @Override
    public String getOauthHost() {
        return PSPUtil.isProducao() ? "https://api-pix.sicredi.com.br/" : "https://api-pix-h.sicredi.com.br/";
    }

    @Override
    public String getHost() {
        return PSPUtil.isProducao() ? "https://api-pix.sicredi.com.br/api/v2" : "https://api-pix-h.sicredi.com.br/api/v2";
    }

    private static String getCertificadoPath(){
        return PSPUtil.isProducao() ? "path certificado.pem de produção" : "path certificado.pem de ambiente Sandbox";
    }

    private static String getCertificadoKeyPath(){
        return PSPUtil.isProducao() ? "path chave.key de produção" : "path chave.key de ambiente Sandbox";
    }

    private static String getClientId(){
        return PSPUtil.isProducao() ? "coloque aqui o clientId de produção" : "coloque aqui o clientId de ambiente Sandbox";
    }

    private static String getClientSecret(){
        return PSPUtil.isProducao() ? "coloque aqui o clientSecret de produção" : "coloque aqui o clientSecret de ambiente Sandbox";
    }

    private HttpComponentsClientHttpRequestFactory getRequestFactory() {
        try {
            var httpclient = gerarHttpClient();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpclient);

            return requestFactory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CloseableHttpClient gerarHttpClient() throws Exception {
        // Carrega a chave privada do arquivo .key
        PEMParser pemParser = new PEMParser(new FileReader(getCertificadoKeyPath()));
        PEMKeyPair pemKeyPair = (PEMKeyPair) pemParser.readObject();

        // Converte o PEMKeyPair para um objeto PrivateKey
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKey privateKey = converter.getPrivateKey(pemKeyPair.getPrivateKeyInfo());

        // Carrega o certificado do arquivo .pem
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(new FileInputStream(getCertificadoPath()));

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, null);
        keyStore.setKeyEntry("chave", privateKey, null, new X509Certificate[]{cert});

        // cria um objeto SSLContext e carrega o material de chave e o material de confiança
        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, null)
                .loadTrustMaterial(keyStore, (x509Certificates, s) -> true)
                .build();

        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setSSLContext(sslContext);

        return builder.build();
    }
}

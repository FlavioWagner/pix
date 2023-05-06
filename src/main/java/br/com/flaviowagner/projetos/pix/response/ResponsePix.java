package br.com.flaviowagner.projetos.pix.response;

import br.com.flaviowagner.projetos.pix.model.*;
import br.com.flaviowagner.projetos.pix.request.RequestPIX;
import br.com.flaviowagner.projetos.pix.utils.QRCodeGenerator;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePix {
    @JsonProperty("calendario")
    private Calendario calendario;
    @JsonProperty("txid")
    private String txid;
    @JsonProperty("revisao")
    private Integer revisao;
    @JsonProperty("loc")
    private Loc loc;
    @JsonProperty("location")
    private String location;
    @JsonProperty("status")
    private String status;
    @JsonProperty("devedor")
    private Devedor devedor;
    @JsonProperty("valor")
    private Valor valor;
    @JsonProperty("chave")
    private String chave;
    @JsonProperty("solicitacaoPagador")
    private String solicitacaoPagador;
    @JsonProperty("pix")
    private List<Pix> pix;
    @JsonProperty("infoAdicionais")
    private List<InformacaoAdicional> infoAdicionais;
    @JsonProperty("pixCopiaECola")
    @JsonAlias("textoImagemQRcode")
    private String pixCopiaECola;

    @JsonProperty("qrCode")
    public byte[] getQrCode(){
        return pixCopiaECola == null ? null : QRCodeGenerator.generate(pixCopiaECola);
    }

    public String toJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString((ResponsePix)this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
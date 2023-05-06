package br.com.flaviowagner.projetos.pix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pix {
    @JsonProperty("endToEndId")
    private String endToEndId;
    @JsonProperty("txid")
    private String txid;
    @JsonProperty("valor")
    private String valor;
    @JsonProperty("horario")
    private String horario;
    @JsonProperty("infoPagador")
    private String infoPagador;
    @JsonProperty("devolucoes")
    private List<Devolucao> devolucoes;
}

package br.com.flaviowagner.projetos.pix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaqueTroco {
    @JsonProperty("valor")
    private String valor;
    @JsonProperty("modalidadeAlteracao")
    private Integer modalidadeAlteracao;
    @JsonProperty("modalidadeAgente")
    private String modalidadeAgente;
    @JsonProperty("prestadorDoServicoDeSaque")
    private String prestadorDoServicoDeSaque;
}





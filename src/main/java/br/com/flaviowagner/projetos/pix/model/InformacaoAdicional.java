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
public class InformacaoAdicional {
    @JsonProperty("nome")
    public String nome;
    @JsonProperty("valor")
    public String valor;
}

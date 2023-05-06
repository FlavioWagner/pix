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
public class Devolucao {
    @JsonProperty("id")
    private String id;
    @JsonProperty("rtrId")
    private String rtrId;
    @JsonProperty("valor")
    private String valor;
    @JsonProperty("horario")
    private Horario horario;
    @JsonProperty("status")
    private String status;
}

package br.com.flaviowagner.projetos.pix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Loc {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("location")
    private String location;
    @JsonProperty("tipoCob")
    private String tipoCob;
    @JsonProperty("criacao")
    private LocalDateTime criacao;
}

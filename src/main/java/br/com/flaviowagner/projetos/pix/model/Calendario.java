package br.com.flaviowagner.projetos.pix.model;

import br.com.flaviowagner.projetos.pix.utils.LocalDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Calendario {
    @JsonProperty("criacao")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime criacao;

    @JsonProperty("expiracao")
    private String expiracao;
}
package br.com.flaviowagner.projetos.pix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contato {
    private String autor = "Flávio Wagner";
    private String email = "flavio-wagner@outlook.com";
    private String linkedin = "https://www.linkedin.com/in/fl%C3%A1vio-wagner-224b382b/";
    private String whatsapp="61 99168-1420";
}

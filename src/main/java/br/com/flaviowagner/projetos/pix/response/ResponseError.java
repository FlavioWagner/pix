package br.com.flaviowagner.projetos.pix.response;

import br.com.flaviowagner.projetos.pix.model.Violacoes;
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
public class ResponseError {
    @JsonProperty("type")
    public String type;
    @JsonProperty("title")
    public String title;
    @JsonProperty("status")
    public Integer status;
    @JsonProperty("detail")
    public String detail;
    @JsonProperty("violacoes")
    public List<Violacoes> violacoes;
}

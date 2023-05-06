package br.com.flaviowagner.projetos.pix.exceptionHandler;

import br.com.flaviowagner.projetos.pix.response.ResponseError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class PIXExceptionHandler extends Throwable {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ResponseError> errorHandle(HttpClientErrorException e) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            var error = objectMapper.readValue(e.getMessage(), ResponseError.class);
            return new ResponseEntity<>(error,e.getStatusCode());
        }catch(Exception ex){
            ResponseError error = new ResponseError();
            error.setTitle("Requisição Inválida");
            error.setStatus(e.getStatusCode().value());
            error.setDetail(ex.getMessage());

            return new ResponseEntity<>(error,e.getStatusCode());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> errorHandle(Exception e) {
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseError error = new ResponseError();
        error.setTitle("Requisição Inválida");
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setDetail(e.getMessage());

        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<ResponseError> errorHandle(IndexOutOfBoundsException e) {
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseError error = new ResponseError();
        error.setTitle("Requisição Inválida");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setDetail("PSP informado não cadastrado.");

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}

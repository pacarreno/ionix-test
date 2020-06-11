package cl.ionix.testtecnico.domain;

import lombok.Data;

@Data
public class ResultApiCall {

    int responseCode;
    String description;
    Long elapsedTime;
    ResultApiCallDetail result;

}

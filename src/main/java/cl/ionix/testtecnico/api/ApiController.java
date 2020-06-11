package cl.ionix.testtecnico.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.ionix.testtecnico.domain.ResultApiCall;
import cl.ionix.testtecnico.services.ApiCallService;

@RestController
public class ApiController {
 
    @Autowired
    ApiCallService apiCallService;

    /**
     * Permite llamar a la api externa retornando la cantidad de registros que esta entrega y el tiempo que demora la misma.
     */
    @PostMapping(path = "/apicall" , consumes = "application/json" )
    public ResponseEntity<ResultApiCall> create(@RequestParam String param) throws Exception {
        ResultApiCall result = apiCallService.callExternalApi(param);
        return ResponseEntity.ok( result );   
    }
}
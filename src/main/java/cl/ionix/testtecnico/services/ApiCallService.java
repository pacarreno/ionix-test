package cl.ionix.testtecnico.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.ionix.testtecnico.domain.ResultApiCall;
import cl.ionix.testtecnico.domain.ResultApiCallDetail;
import cl.ionix.testtecnico.utils.Encryptor;
import cl.ionix.testtecnico.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ApiCallService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    Encryptor encryptor;

    final String urlCall = "https://sandbox.ionix.cl/test-tecnico/search?rut={rut}";

    /**
     * Permite llamar una api externa para recuperar un listado de items
     * 
     * @param param parametro de busqueda
     * @return retorna la cantidad
     */
    public ResultApiCall callExternalApi(String param) {

        long timeElapsed = 0;

        // construct params to call external Api
        Map<String, String> params = null;
        try {
            params = constructParameters(param);
        } catch (Exception e) {
            log.error("Error al contruir los parametros con el campo entregado {}",param,e);
        }
         
        // get current time 
        long started = Utils.startCounting();
        // do the actual call to external service
        ResultExternalApi resultExternalCall = null;
        try{
            resultExternalCall = doExternalCall(urlCall,params);
        }catch(Exception e ){
            log.error("Error al llamar la api externa con el parametro {}",param,e);
        }finally {
            // calculate the elapsed time 
            timeElapsed = Utils.calculateTimeElapsed(started);
        }

        //construct result
        ResultApiCall result = constructResult(resultExternalCall,timeElapsed);

		return result;
    }
    
    /**
     * Construye la respuesta a enviar luego de ejecutar el requerimiento
     * @param resultExternalCall
     * @param timeElapsed
     * @return
     */
    private ResultApiCall constructResult(ResultExternalApi resultExternalCall, long timeElapsed) {
        ResultApiCall result = new ResultApiCall();
        result.setElapsedTime(timeElapsed);
        if( resultExternalCall != null ){
            List items = (List)resultExternalCall.result.get("items");
            result.setDescription("OK");
            if( items != null ){
                ResultApiCallDetail detail = new ResultApiCallDetail();
                detail.setRegisterCount( ((List)resultExternalCall.result.get("items")).size());
                result.setResult(detail);
            }
        } else {
            result.setDescription("NOK");
        }
        return result;
    }

    /**
     * Permite realizar el llamada a la API externa con los parametros entregados
     * 
     * @param urlToCall
     * @param params
     * @return Retorna lo devuelto por el servicio
     */
    private ResultExternalApi doExternalCall(String urlToCall, Map<String, String> params) {
        
        if( params == null ) 
            return null;
        
        return restTemplate.getForObject(urlCall, ResultExternalApi.class, params);
    }

    /**
     * Permite construir el Map de parametros necesarios para llamar la Api externa
     * 
     * @param param
     * @return
     * @throws Exception
     */
    private Map<String, String> constructParameters(String param) throws Exception {
        Map<String,String> params = new HashMap<String, String>();
        //encripta el parametro
        String encryptedParam = encryptor.encrypt(param);
        params.put("rut", encryptedParam );
        return params;
    }

    /**
     * Clase utilitaria para recuperar la información del servicio externo
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultExternalApi {
        int responseCode;
        String description;
        Map result;
        
    }

}

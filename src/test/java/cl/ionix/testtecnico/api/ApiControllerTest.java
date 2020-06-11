package cl.ionix.testtecnico.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import cl.ionix.testtecnico.domain.ResultApiCall;
import cl.ionix.testtecnico.services.ApiCallService;
import cl.ionix.testtecnico.utils.Encryptor;
import cl.ionix.testtecnico.utils.TestUtil;

@WebMvcTest(controllers = { ApiController.class })
@AutoConfigureWebClient
public class ApiControllerTest {
    
    @MockBean
    ApiCallService apiService;
    @Spy
    RestTemplate restTemplate;
    @Spy
    Encryptor encryptor;


    @Autowired
    private MockMvc mockMvc;

    /**
     * Si se llama la api sin parametros deberia dar un error de request
     * @throws Exception
     */
    @Test
    public void debe_dar_error_sin_parametros() throws Exception {
        
        //WHEN
        mockMvc.perform(post("/apicall")
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        //THEN
        .andExpect(status().isBadRequest());
    }

    /**
     * Si se llama con parametros validos, debe retornar resultado esperado
     * @throws Exception
     */
    @Test
    public void debe_dar_resultado_valido() throws Exception {
        //GIVEN 
        String param = "1-9";
        ResultApiCall resultApiCall = TestUtil.createApliCallResult();

        when(apiService.callExternalApi(param))
        .thenReturn(resultApiCall);

        //WHEN
        mockMvc.perform(post("/apicall?param="+param)
        .contentType(MediaType.APPLICATION_JSON_VALUE))

        //THEN
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.description").value("OK"))
        .andExpect(jsonPath("$.responseCode").value(0))
        .andExpect(jsonPath("$.elapsedTime").exists())
        .andExpect(jsonPath("$.result").exists())
        .andExpect(jsonPath("$.result.registerCount").value(3));
    }
}
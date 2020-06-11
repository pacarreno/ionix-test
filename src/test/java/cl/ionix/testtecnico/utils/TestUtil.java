package cl.ionix.testtecnico.utils;

import static org.mockito.Mockito.description;

import cl.ionix.testtecnico.domain.ResultApiCall;
import cl.ionix.testtecnico.domain.ResultApiCallDetail;
import cl.ionix.testtecnico.domain.User;

/**
 * Clase utilitaria que permite generar objetos para pruebas
 */
public class TestUtil {

	public static User createUser() {

		User user = new User();
        user.setId(1L);
        user.setEmail("usuario@ionix.cl");
        user.setName("Nombre");
        user.setUsername("username");
        user.setPhone("5554433");
        return user;
	}

	public static ResultApiCall createApliCallResult() {
        ResultApiCall resultApiCall = new ResultApiCall();
        resultApiCall.setDescription("OK");
        resultApiCall.setResponseCode(0);
        resultApiCall.setElapsedTime(50l);
        ResultApiCallDetail detail = new ResultApiCallDetail();
        detail.setRegisterCount(3);
        resultApiCall.setResult(detail);
		return resultApiCall;
	}

}

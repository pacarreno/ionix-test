package cl.ionix.testtecnico.api;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import cl.ionix.testtecnico.domain.User;
import cl.ionix.testtecnico.services.UserService;
import cl.ionix.testtecnico.utils.TestUtil;

@WebMvcTest(controllers = { UsersController.class })
@AutoConfigureWebClient
public class UsersControllerTest {

    @MockBean
    UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    /**
     * Test que verifica que la capa de exposición retorna elementos correctament cuando existen
     * @throws Exception
     */
    @Test
    public void debe_retornar_usuarios_cuando_existen() throws Exception {

        //GIVEN
        User user = TestUtil.createUser();
        List<User> users = new ArrayList<User>();
        users.add(user);
        Page<User> usersPage = new PageImpl<User>(users);

        when(userService.findAll(anyInt(),anyInt()))
        .thenReturn(usersPage);

        //WHEN
        mockMvc.perform(get("/users"))
        //THEN
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(1))
        .andExpect(jsonPath("$.content[0].name").value(user.getName()))
        .andExpect(jsonPath("$.content[0].username").value(user.getUsername()))
        .andExpect(jsonPath("$.content[0].email").value(user.getEmail()))
        .andExpect(jsonPath("$.content[0].phone").value(user.getPhone()));

    }

    /**
     * Test que verifica que la capa de exposición retorna elementos correctament cuando existen
     * @throws Exception
     */
    @Test
    public void debe_retornar_vacio_cuando_no_existen_usuarios() throws Exception {

        //GIVEN
        when(userService.findAll(anyInt(),anyInt()))
        .thenReturn(new PageImpl<User>(new ArrayList<User>()));

        //WHEN
        mockMvc.perform(get("/users"))
        //THEN
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(0));

    }

    /**
     * Test que verifica que la capa de exposición retorna elementos correctament cuando existen un usuario con el email dado
     * @throws Exception
     */
    @Test
    public void debe_retornar_usuario_por_email_cuando_existe() throws Exception {

        //GIVEN
        User user = TestUtil.createUser();

        when(userService.search(user.getEmail()))
        .thenReturn(Optional.of( user ));

        //WHEN
        mockMvc.perform(get("/users/"+user.getEmail()))
        //THEN
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(user.getName()))
        .andExpect(jsonPath("$.username").value(user.getUsername()))
        .andExpect(jsonPath("$.email").value(user.getEmail()))
        .andExpect(jsonPath("$.phone").value(user.getPhone()));

    }

        /**
     * Test que verifica que la capa de exposición no retorna elemento cuando el usuario no existe
     * @throws Exception
     */
    @Test
    public void NO_debe_retornar_usuario_por_email_cuando_NO_existe() throws Exception {

        String email = "a@b.com";
        //GIVEN
        when(userService.search(email))
        .thenReturn(Optional.empty() );

        //WHEN
        mockMvc.perform(get("/users/"+email))
        //THEN
        .andExpect(status().isNotFound());

    }

    /**
     * Test que verifica la creación del usuario
     */
    @Test
    public void crear_usuario_no_existente() throws Exception {

        //GIVEN
        User user = TestUtil.createUser();

        when(userService.save(user))
        .thenReturn( user );

        //GIVEN
        when(userService.save(user))
        .thenReturn(user);

        //WHEN
        mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(user))
        )
        //THEN
        .andExpect(status().isCreated());
    }
}
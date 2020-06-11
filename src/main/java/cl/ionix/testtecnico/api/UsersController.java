package cl.ionix.testtecnico.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.ionix.testtecnico.domain.User;
import cl.ionix.testtecnico.services.UserService;

/**
 * Controlador que tiene la logica de manejo de la entidad de usuarios
 */
@RestController
public class UsersController {

    @Autowired
    UserService userService;

    /**
     * Obtiene la lista de usuarios en la base
     * @param page
     * @param size
     * @return
     */
    @GetMapping(path = "/users" )
    public ResponseEntity<Page<User>> list(@RequestParam(defaultValue = "0") int page, 
                                           @RequestParam(defaultValue = "20") int size){
        Page<User> result = userService.findAll(page, size);
        return ResponseEntity.ok( result );
        
    }

    /**
     * Permite realizar busquedas de usuario por email
     * @param email
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/users/{email}" )
    public ResponseEntity<User> search(@PathVariable String email) throws Exception {
        Optional<User> result = userService.search(email);
        if( !result.isPresent() )
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok( result.get() );   
    }


    /**
     * Permite crear nuevo usuario en la base
     * TODO agregar validacion de existencia por email
     * @param user
     * @return
     */
    @PostMapping(path = "/users" , consumes = "application/json" )
    public ResponseEntity<User> create(@RequestBody User user){
        User userBD = userService.save(user);
        return new ResponseEntity<User>( userBD ,HttpStatus.CREATED);
    }

    
}
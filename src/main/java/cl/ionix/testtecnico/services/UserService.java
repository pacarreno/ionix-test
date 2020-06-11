package cl.ionix.testtecnico.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cl.ionix.testtecnico.domain.User;
import cl.ionix.testtecnico.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * Permite obtener la lista de usuarios o los todos los usuarios con el email dado
     * @param page numero de la pagina
     * @param size tamaño de la pagina
     * @return retorna una pagina de usuarios
     */
	public Page<User> findAll(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
	}

    /**
     * Permite guardar un nuevo usuario
     * @param user el objeto usuario a crear en la base
     * @return retorna el usuario creado
     */
	public User save(User user) {
		return userRepository.save(user);
	}

    /**
     * Permite buscar un usuario por su email
     * @param email Email a buscar
     * @return Usuario con el email entregado
     */
	public Optional<User> search(String email) {
		return userRepository.findByEmail(email);
	}

}

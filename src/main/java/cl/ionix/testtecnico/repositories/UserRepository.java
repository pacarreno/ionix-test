package cl.ionix.testtecnico.repositories;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cl.ionix.testtecnico.domain.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long>{

    Optional<User> findByEmail(String email);

}

package cl.ionix.testtecnico.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    Long id;
    String name;
    String username;
    String email;
    String phone;
}

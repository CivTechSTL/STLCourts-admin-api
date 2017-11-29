package svc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import svc.models.Users;

@Component
public interface UserJpaRespository extends JpaRepository<Users, Long>{

    Users findByName(String name);
}

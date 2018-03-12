package svc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import svc.models.User;


@Component
public interface UserRepository extends CrudRepository<User, Long>{
	User findByEmail(String email);
}

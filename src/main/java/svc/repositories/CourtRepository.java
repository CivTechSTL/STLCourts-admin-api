package svc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import svc.models.Court;


@Component
public interface CourtRepository extends CrudRepository<Court, Long>{

}

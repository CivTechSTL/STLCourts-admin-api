package svc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import svc.models.Judge;


@Component
public interface JudgeRepository extends CrudRepository<Judge, Long>{

}

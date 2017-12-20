package svc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import svc.models.Judge;


@Component
public interface JudgeJpaRepository extends JpaRepository<Judge, Long>{

}

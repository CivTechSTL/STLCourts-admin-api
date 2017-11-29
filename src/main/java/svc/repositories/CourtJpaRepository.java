package svc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import svc.models.Court;


@Component
public interface CourtJpaRepository extends JpaRepository<Court, Long>{

}

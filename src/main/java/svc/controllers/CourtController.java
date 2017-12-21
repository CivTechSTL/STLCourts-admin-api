package svc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import svc.models.Court;
import svc.repositories.CourtJpaRepository;


@RestController
@EnableAutoConfiguration
public class CourtController {
	
	@Autowired
	private CourtJpaRepository courtJpaRepository;
	
	@GetMapping(value = "courts")
	public List<Court> findAll(){
		return courtJpaRepository.findAll();
	}
	
	@GetMapping(value = "courts/{id}")
	public Court getOne(@PathVariable final Long id){
		return courtJpaRepository.findOne(id);
		 
	}
	
	@PostMapping(value = "courts")
	public Court load(@RequestBody final Court court){
		return courtJpaRepository.save(court);
	}
	
	@DeleteMapping(value = "courts/{id}")
	public void delete(@PathVariable final Long id){
		courtJpaRepository.delete(id);
	}
	
}



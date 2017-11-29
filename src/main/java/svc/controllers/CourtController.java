package svc.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.managers.*;
import svc.models.Court;
import svc.repositories.CourtJpaRepository;


@RestController
@EnableAutoConfiguration
public class CourtController {
	
	@Autowired
	private CourtJpaRepository courtJpaRepository;
	
	@GetMapping(value = "courts/all")
	public List<Court> findAll(){
		return courtJpaRepository.findAll();
	}
	
	@GetMapping(value = "courts/{id}")
	public Court getOne(@PathVariable final Long id){
		Court c = courtJpaRepository.getOne(id);
		c.getCourt_id();
		return c;
	}
	
	@PostMapping(value = "/load")
	public Court load(@RequestBody final Court court){
		Court savedCourt = courtJpaRepository.save(court);
		return courtJpaRepository.getOne(savedCourt.getCourt_id());
	}
	
	/*@Inject
	CourtManager courtManager;
	
	@Inject
	HashUtil hashUtil;
	
	@ResponseBody
	@PutMapping("/courts/{id}")
	Court updateCourt(@RequestBody Court court) {
		courtManager.updateCourt(court);
		return court;
	}
	
	@ResponseBody
	@PostMapping("/courts")
	Court createCourt(@RequestBody Court court){
		return courtManager.createCourt(court);
		
	}
	
	@ResponseBody
	@DeleteMapping("/courts/{id}")
	void deleteCourt(@PathVariable("id") String idString) {
		long id = hashUtil.decode(Court.class,idString);
		courtManager.deleteCourt(id);
	}
	*/
	
}



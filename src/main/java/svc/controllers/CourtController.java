package svc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import svc.exceptions.NotFoundException;
import svc.models.Court;
import svc.repositories.CourtRepository;


@RestController
public class CourtController {
	
	@Autowired
	private CourtRepository courtRepository;
	
	@GetMapping(value = "courts")
	public List<Court> findAll(){
		return (List<Court>) courtRepository.findAll();
	}
	
	@GetMapping(value = "courts/{id}")
	public Optional<Court> getOne(@PathVariable final Long id) throws NotFoundException{
		Optional<Court> optionalCourt = courtRepository.findById(id);
		if (!optionalCourt.isPresent()){
			throw new NotFoundException("Court Not Found");
		}
		return optionalCourt;
	}
	
	@PostMapping(value = "courts")
	public Court load(@RequestBody final Court court){
		return courtRepository.save(court);
	}
	
	@DeleteMapping(value = "courts/{id}")
	public void delete(@PathVariable final Long id){
		courtRepository.deleteById(id);
	}
	
}



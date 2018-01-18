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

import svc.models.Judge;
import svc.repositories.JudgeRepository;


@RestController
@EnableAutoConfiguration
public class JudgeController {
	
	@Autowired
	private JudgeRepository judgeRepository;
	
	@GetMapping(value = "judges")
	public List<Judge> findAll(){
		return (List<Judge>) judgeRepository.findAll();
	}
	
	@GetMapping(value = "judges/{id}")
	public Judge getOne(@PathVariable final Long id){
		return judgeRepository.findOne(id);
	}
	
	@PostMapping(value = "judges")
	public Judge load(@RequestBody final Judge judge){
		return judgeRepository.save(judge);
	}
	
	@DeleteMapping(value = "judges/{id}")
	public void delete(@PathVariable final Long id){
		judgeRepository.delete(id);
	}
}



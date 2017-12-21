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
import svc.repositories.JudgeJpaRepository;


@RestController
@EnableAutoConfiguration
public class JudgeController {
	
	@Autowired
	private JudgeJpaRepository judgeJpaRepository;
	
	@GetMapping(value = "judges")
	public List<Judge> findAll(){
		return judgeJpaRepository.findAll();
	}
	
	@GetMapping(value = "judges/{id}")
	public Judge getOne(@PathVariable final Long id){
		return judgeJpaRepository.findOne(id);
	}
	
	@PostMapping(value = "judges")
	public Judge load(@RequestBody final Judge judge){
		return judgeJpaRepository.save(judge);
	}
	
	@DeleteMapping(value = "judges/{id}")
	public void delete(@PathVariable final Long id){
		judgeJpaRepository.delete(id);
	}
	
}



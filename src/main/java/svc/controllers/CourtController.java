package svc.controllers;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.managers.*;
import svc.security.HashUtil;

import com.stlcourts.common.models.Court;


@RestController
@EnableAutoConfiguration
public class CourtController {	
	@Inject
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
	
}



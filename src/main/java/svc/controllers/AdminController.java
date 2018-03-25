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
public class AdminController {
	
	@GetMapping(value = "/admin/test")
	public String test(){
		return "Reached Test";
	}	
}



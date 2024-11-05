package com.bmdb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.db.CreditRepo;
import com.bmdb.model.Credit;
import com.bmdb.model.CreditDTO;

@CrossOrigin
@RestController
@RequestMapping("/api/credits")
public class CreditController {

	@Autowired
	private CreditRepo creditRepo;

	@GetMapping("/dto")
	public List<CreditDTO> getAllCreditDTOs() {
		List<CreditDTO> creditDTOs = new ArrayList<>();
		List<Credit> credits = creditRepo.findAll();
		for (Credit c: credits) {
			CreditDTO cdto = new CreditDTO(c.getId(), c.getMovie().getId(), 
					c.getActor().getId(), c.getRole());
			creditDTOs.add(cdto);
		}
		return creditDTOs;
	}

	@GetMapping("/")
	public List<Credit> getAllCredits() {
		return creditRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Credit getCreditById(@PathVariable int id) {
		Optional<Credit> c = creditRepo.findById(id);
		// TODO Add null check for valid id
		return c.get();
	}

	@PostMapping("")
	public Credit addCredit(@RequestBody Credit credit) {
		// TODO Check for existence by credit.getId() before save?
		return creditRepo.save(credit);
	}

	@PutMapping("/{id}")
	public Credit updateCredit(@PathVariable int id, @RequestBody Credit credit) {
		Credit c = null;
		if (id != credit.getId()) {
			System.err.println("Credit id does not match path id.");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request: Credit id mismatch vs URL.");
		} else if (!creditRepo.existsById(id)) {
			System.err.println("Credit does not exist for id: " + id);
			// TODO Return error to front end.
		} else {
			c = creditRepo.save(credit);
		}
		return c;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)	
	public void deleteCredit(@PathVariable int id) {
		if (creditRepo.existsById(id)) {
			creditRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Credit not found for id: "+id);
		}
	}
	
	// new requirement: Movie-Credits: return all credits for a movie
	@GetMapping("/movie-credits/{movieId}")
	public List<Credit> getCreditsForMovie(@PathVariable int movieId) {
		//return creditRepo.findBy(null, null);
		return creditRepo.findByMovieId(movieId);
	}
	
	// new requirement: Actor-Filmography: return all credits for an actor
	@GetMapping("/actor-credits/{actorId}")
	public List<Credit> getCreditsForActor(@PathVariable int actorId) {
		return creditRepo.findByActorId(actorId);
	}
	
	
	
	
	

}

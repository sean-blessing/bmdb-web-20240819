package com.bmdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.db.MovieRepo;
import com.bmdb.model.Movie;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
	@Autowired
	private MovieRepo movieRepo;
	
	@GetMapping("/")
	public List<Movie> getAllMovies() {
		return movieRepo.findAll();
	}
	
	// getById  - "/api/movies/{id}
	//			- return: Movie 
	@GetMapping("/{id}")
	public Optional<Movie> getMovieById(@PathVariable int id) {
		// check if movie exists for id
		// if yes, return movie
		// if no, return NotFound
		Optional<Movie> m = movieRepo.findById(id);
		if (m.isPresent()) {
			return m;
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Movie not found for id: "+id);
		}
	}
	
	// post 	- "/api/movies/" (movie will be in the RequestBody)
	//			- return: Movie
	@PostMapping("")
	public Movie addMovie(@RequestBody Movie movie) {
		return movieRepo.save(movie);
	}
	
	// put 		- "/api/movies/{id} (movie passed in RB)
	//			- return: NoContent()
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putMovie(@PathVariable int id, @RequestBody Movie movie) {
		// check id passed vs id in instance
		// - BadRequest if not exist
		if (id != movie.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie id mismatch vs URL.");
		}
		// if movie exists, update, else not found
		else if (movieRepo.existsById(movie.getId())) {
			movieRepo.save(movie);
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Movie not found for id: "+id);
		}
	}
	
	
	// delete 	- "/api/movies/{id}
	//			- return: NoContent()
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)	
	public void deleteMovie(@PathVariable int id) {
		// check existence, then remove movie
		// return NotFound if not exist
		if (movieRepo.existsById(id)) {
			movieRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Movie not found for id: "+id);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

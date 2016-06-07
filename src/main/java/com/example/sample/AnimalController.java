package com.example.sample;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mhyeon.lee on 2016. 6. 7..
 */
@RestController
public class AnimalController {

	@Autowired
	private AnimalRepository repository;

	@RequestMapping("/animals")
	public Resources<?> findAll() {
		List<Animal> animals = repository.findAll();
		List<Resource<Animal>> animalList = new ArrayList<>();
		for(Animal animal : animals) {
			Resource<Animal> resource = new Resource<Animal>(animal, new Link("/animals/" + animal.getId()).withSelfRel());
			animalList.add(resource);
		}
		return new Resources<>(animalList);
	}
}

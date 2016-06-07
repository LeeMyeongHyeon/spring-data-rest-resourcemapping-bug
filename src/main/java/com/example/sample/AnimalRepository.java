package com.example.sample;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mhyeon.lee on 2016. 6. 7..
 */
public interface AnimalRepository extends JpaRepository<Animal, Long> {
	List<Animal> findAll();
}

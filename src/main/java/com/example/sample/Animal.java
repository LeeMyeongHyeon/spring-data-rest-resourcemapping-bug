package com.example.sample;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.hateoas.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by mhyeon.lee on 2016. 6. 7..
 */
@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@AllArgsConstructor
public abstract class Animal {

	@EmbeddedId
	private Id id;
	private String name;
	private String message;

	@Embeddable
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Id implements Serializable {
		private Long id;
	}

	@Entity
	@Getter
	@NoArgsConstructor
	@Relation(value = "animal", collectionRelation = "animals")
	public static class Dog extends Animal {
		public Dog(Id id, String name, String message) {
			super(id, name, message);
		}
	}

	@Entity
	@Getter
	@NoArgsConstructor
	@Relation(value = "animal", collectionRelation = "animals")
	public static class Cat extends Animal {
		public Cat(Id id, String name, String message) {
			super(id, name, message);
		}
	}
}

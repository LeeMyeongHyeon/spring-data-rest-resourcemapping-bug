package com.example.sample;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.SpringDataRestRelproviderBugApplication;
import com.example.sample.Animal.Cat;
import com.example.sample.Animal.Dog;
import com.example.sample.Animal.Id;

/**
 * Created by mhyeon.lee on 2016. 6. 7..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataRestRelproviderBugApplication.class)
@WebAppConfiguration
public class IntegrationTest {
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private AnimalRepository repository;

	@Before
	public void setUp() {
		repository.save(new Dog(new Id(1L), "dog", "hi"));
		repository.save(new Cat(new Id(2L), "cat", "hi2"));
	}


	@Test
	public void test() throws Exception {
		MockMvc mvc = MockMvcBuilders.webAppContextSetup(wac).build();

		mvc.perform(MockMvcRequestBuilders.get("/animals"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$._embedded.animals[0].name").value("dog"))
				.andExpect(jsonPath("$._embedded.animals[0].message").value("hi"))
				.andExpect(jsonPath("$._embedded.animals[0].name").value("cat"))
				.andExpect(jsonPath("$._embedded.animals[0].message").value("hi2"));

		mvc.perform(MockMvcRequestBuilders.get("/animals"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$._embedded.cats[0].name").value("cat"))
				.andExpect(jsonPath("$._embedded.cats[0].message").value("hi2"))
				.andExpect(jsonPath("$._embedded.dogs[1].name").value("dog"))
				.andExpect(jsonPath("$._embedded.dogs[1].message").value("hi"));
	}
}

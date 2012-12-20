package com.nwm.coauthor.service.endpoint;

import java.io.IOException;

import org.junit.BeforeClass;
import com.nwm.coauthor.service.collection.Person;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;

public class TestSetup {
	@BeforeClass
	public static void beforeClass() throws IOException{
		MongoTemplate mongoTemplate = new MongoTemplate(new Mongo("localhost", 27017), "coauthor", new UserCredentials("", ""));
		
		mongoTemplate.getDb().dropDatabase();

		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Zhuang");

		mongoTemplate.insert(person);
	}
}

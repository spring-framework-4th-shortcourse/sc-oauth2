package org.sc.oauth2.resource.server;

import org.sc.oauth2.resource.server.model.User;
import org.sc.oauth2.resource.server.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	private UserRepository userRepo;
	
	public App(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		
		User user = new User("admin", "admin@gmail.com", "21101994", "Kandal");
		userRepo.save(user);
	}
}

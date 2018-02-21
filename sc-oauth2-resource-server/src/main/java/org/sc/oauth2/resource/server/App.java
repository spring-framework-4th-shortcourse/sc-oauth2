package org.sc.oauth2.resource.server;

import java.util.Arrays;

import org.sc.oauth2.resource.server.model.Article;
import org.sc.oauth2.resource.server.repository.ArticleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	private ArticleRepository articleRepo;

	public App(ArticleRepository articleRepo) {
		this.articleRepo = articleRepo;
	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		Arrays.asList(
				"Why Quitters Can Become Winners Too", 
				"The Lost Art of Criticism",
				"Is Planning a Kind of Guessing?").forEach(title -> {
					articleRepo.save(new Article(title));
				});
	}
}

package libraymanage.practice.librarypracticeapp;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import libraymanage.practice.librarypracticeapp.Entity.Book;
import libraymanage.practice.librarypracticeapp.Entity.Checkout;
import libraymanage.practice.librarypracticeapp.Entity.History;
import libraymanage.practice.librarypracticeapp.Entity.Message;
import libraymanage.practice.librarypracticeapp.Entity.Review;
import libraymanage.practice.librarypracticeapp.Entity.Role;
import libraymanage.practice.librarypracticeapp.Entity.Users;
import libraymanage.practice.librarypracticeapp.Repository.BookRepos;
import libraymanage.practice.librarypracticeapp.Repository.CheckoutRepos;
import libraymanage.practice.librarypracticeapp.Repository.HistoryRepos;
import libraymanage.practice.librarypracticeapp.Repository.MessageRepos;
import libraymanage.practice.librarypracticeapp.Repository.ReviewRepos;
import libraymanage.practice.librarypracticeapp.Repository.UserRepos;

@SpringBootApplication
public class LibraryPracticeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryPracticeAppApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepos userRepos, BookRepos bookRepos, CheckoutRepos checkoutRepos, HistoryRepos historyRepos, MessageRepos messageRepos, ReviewRepos reviewRepos) {
		return args -> {
			Users admin = new Users("quan_admin@gmail.com", new BCryptPasswordEncoder().encode("123456"), "quan_admin");
			admin.setRole(Role.ADMIN);
			Users user1 = new Users("quan@gmail.com", new BCryptPasswordEncoder().encode("123456"), "quan");
			user1.setRole(Role.USER);
			Users user2 = new Users("quan1@gmail.com", new BCryptPasswordEncoder().encode("123456"), "quan1");
			user2.setRole(Role.USER);
			userRepos.save(admin);
			userRepos.save(user2);
			userRepos.save(user1);
			
			Book book1 = new Book("react programming", "Brad Traversy", "react, redux, reducer, hook", 20, 20, "technology");
			Book book2 = new Book("java programming", "Big lurvy", "java core and spring boot", 20, 20, "technology");
			Book book3 = new Book("c# programming", "Mosh", "c# core and asp.net", 20, 20, "technology");
			bookRepos.save(book1);
			bookRepos.save(book2);
			bookRepos.save(book3);

			Checkout checkout1 = new Checkout(LocalDateTime.of(2022, 10, 12, 10, 55), LocalDateTime.of(2022, 12, 8, 14, 20), book1, user2);
			Checkout checkout2 = new Checkout(LocalDateTime.of(2021, 10, 12, 10, 55), LocalDateTime.of(2022, 10, 31, 14, 20), book2, user1);
			Checkout checkout3 = new Checkout(LocalDateTime.of(2022, 6, 10, 10, 55), LocalDateTime.of(2022, 8, 31, 14, 20), book3, user1);
			Checkout checkout4 = new Checkout(LocalDateTime.of(2022, 7, 10, 10, 55), LocalDateTime.of(2022, 8, 31, 14, 20), book3, user2);
			checkoutRepos.save(checkout1);
			//checkout
			checkoutRepos.save(checkout2);
			checkoutRepos.save(checkout3);
			checkoutRepos.save(checkout4);

			History history1 = new History(LocalDateTime.of(2021, 8, 12, 10, 55), LocalDateTime.of(2021, 12, 31, 14, 20), book1, user2);
			History history2 = new History(LocalDateTime.of(2019, 4, 12, 10, 55), LocalDateTime.of(2021, 12, 31, 14, 20), book2, user1);
			History history3 = new History(LocalDateTime.of(2020, 8, 12, 10, 55), LocalDateTime.of(2021, 3, 12, 14, 20), book2, user2);
			History history4 = new History(LocalDateTime.of(2016, 4, 12, 10, 55), LocalDateTime.of(2017, 12, 31, 14, 20), book3, user2);
			historyRepos.save(history1);
			historyRepos.save(history2);
			historyRepos.save(history3);
			historyRepos.save(history4);


			Review review1 = new Review(3, "it is good", book1, user2, LocalDateTime.of(2017, 6, 26, 16, 22));
			Review review2 = new Review(5, "it is helpful", book2, user2, LocalDateTime.of(2018, 9, 26, 16, 22));
			Review review3 = new Review(2, "it is abstract", book3, user1, LocalDateTime.of(2020, 12, 26, 16, 22));
			Review review4 = new Review(4, "it is very knowledgeable", book2, user1, LocalDateTime.of(2019, 9, 5, 16, 22));

			reviewRepos.save(review1);
			reviewRepos.save(review2);
			reviewRepos.save(review3);
			reviewRepos.save(review4);
 
			Message message1 = new Message("data structure", user1, "i would like to search a book about agorithm", LocalDateTime.now());
			messageRepos.save(message1);
			message1.updateAnswer("it is not available now", admin, LocalDateTime.now().plusDays(3));
			messageRepos.save(message1);

			Message message2 = new Message("spring security", user2, "i would like to search a spring security book", LocalDateTime.now());
			messageRepos.save(message2);
			// message2.updateAnswer("it is already in the shelf 5", user1, LocalDateTime.now().plusDays(7));
			// messageRepos.save(message2);
			// Message message3 = new Message("react native", user1, "i would like to search a book about mobile programming", LocalDateTime.now());
			// messageRepos.save(message3);
		};
	}

}

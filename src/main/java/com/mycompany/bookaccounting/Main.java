package com.mycompany.bookaccounting;

import com.mycompany.bookaccounting.entity.Author;
import com.mycompany.bookaccounting.entity.Book;
import com.mycompany.bookaccounting.entity.Client;
import com.mycompany.bookaccounting.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import com.mycompany.bookaccounting.repository.BookRepository;
import com.mycompany.bookaccounting.repository.ClientRepository;
import com.mycompany.bookaccounting.repository.OrderRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication(scanBasePackages = {"com.mycompany.bookaccounting"})
public class Main {
    
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    
    @Bean
    public CommandLineRunner initRepos(BookRepository bookRepository,
            ClientRepository clientRepository, OrderRepository orderRepository) {
        return (args) -> {
            Set<Author> authors = new HashSet<>();
            for (int i = 1; i <= 5; i++) {
                Author a = new Author("author " + i, 1900 + i);
                authors.add(a);
            }
            
            Set<Book> books = new HashSet<>();
            for (int i = 1; i <= 5; i++) {
                Book b = new Book("book " + i, 2000 + i, "anotation " + i);
                b.setAuthors(authors.stream().limit(i).collect(Collectors.toSet()));
                books.add(b);
            }
            bookRepository.saveAll(books);
            
            Client c1 = new Client("vasya", "11111111111");
            Client c2 = new Client("petya", "22222222222");
            Client c3 = new Client("masha", "22222222222");
            
            clientRepository.saveAll(Arrays.asList(c1, c2, c3));
            
            List<Order> orders = new ArrayList<>();
            
            Order o1 = new Order(c1, new HashSet<>(bookRepository.findAll().subList(0, 2)));
            o1.setExecutionDate(LocalDate.now());
            
            Order o2 = new Order(c1, new HashSet<>(bookRepository.findAll().subList(3, 4)));
            Order o3 = new Order(c2, new HashSet<>(bookRepository.findAll().subList(2, 4)));
            o3.setExecutionDate(LocalDate.of(1234, 1, 1));
            Order o4 = new Order(c2, new HashSet<>(bookRepository.findAll().subList(1, 5)));
            
            orderRepository.saveAll(Arrays.asList(o1, o2, o3, o4));                  
        };
    }
    
}

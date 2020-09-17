package com.gabrielnwogu.elevatorSystem.bootstrap;

import com.gabrielnwogu.elevatorSystem.domains.*;
import com.gabrielnwogu.elevatorSystem.repositories.*;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private AdminRepository adminRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserRepository userRepository;

    private ClientRepository clientRepository;

    private OrderRepository orderRepository;

    private ProductRepository productRepository;

    public BootstrapData(
            AdminRepository adminRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserRepository userRepository,
            ClientRepository clientRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository
    ) {
        this.adminRepository = adminRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        String encodedPassword = bCryptPasswordEncoder.encode("12345");

        if (adminRepository.count() < 1) {

            User user = new User("admin", encodedPassword, "admin");
            userRepository.save(user);

            Admin admin = new Admin(user);
            adminRepository.save(admin);
        }

        User clientUser = userRepository.findByUsername("demo_client");

        if (clientUser == null) {
            clientUser = new User("demo_client", encodedPassword, "client");
            userRepository.save(clientUser);

            Client client = new Client();

            client.setUser(clientUser);
            client.setEmail("info@thelession.com");
            client.setContactDetails("09088797765");
            client.setCompanyName("The Lession");
            client.setAddress("23, Broadway Street, Victoria Island");
            client.setName("Gabriel Nwogu");

            clientRepository.save(client);

            Order order = new Order();
            order.setAddress("28, Brookline Avenue, Victoria Island");
            order.setLocation("France");
            order.setPaymentType("Bank Transfer");
            order.setCost(1500);
            order.setType("Hatchback");
            order.setStatus("completed");
            order.setClient(client);

            orderRepository.save(order);
            order.setNumber(order.getOrderId());
            orderRepository.save(order);

            Order order2 = new Order();
            order2.setAddress("28, Chevy, London");
            order2.setLocation("London");
            order2.setPaymentType("Cheque");
            order2.setCost(2700);
            order2.setType("Crossover");
            order2.setStatus("completed");
            order2.setClient(client);

            orderRepository.save(order2);
            order2.setNumber(order2.getOrderId());
            orderRepository.save(order2);

            Order order3 = new Order();
            order3.setAddress("Carlington Close, New Zealand");
            order3.setLocation("New Zealand");
            order3.setPaymentType("Cheque");
            order3.setCost(5200);
            order3.setType("Crossover");
            order3.setStatus("completed");
            order3.setClient(client);

            orderRepository.save(order3);
            order3.setNumber(order3.getOrderId());
            orderRepository.save(order3);

            Order order4 = new Order();
            order4.setAddress("Fickle Street, China Town");
            order4.setLocation("Festac");
            order4.setPaymentType("Cheque");
            order4.setCost(3850);
            order4.setType("Sequence");
            order4.setStatus("completed");
            order4.setClient(client);

            orderRepository.save(order4);
            order4.setNumber(order4.getOrderId());
            orderRepository.save(order4);

            Order order5 = new Order();
            order5.setAddress("41, Gabriel Nwogu, Street, Abule");
            order5.setLocation("Ijoko");
            order5.setPaymentType("Direct Debit");
            order5.setCost(9850);
            order5.setType("Sequence");
            order5.setStatus("completed");
            order5.setClient(client);

            orderRepository.save(order5);
            order5.setNumber(order5.getOrderId());
            orderRepository.save(order5);

            Order order6 = new Order();
            order6.setAddress("Simplicity is Extreme");
            order6.setLocation("Abuja");
            order6.setPaymentType("Bank Transfer");
            order6.setCost(2850);
            order6.setType("Hatchback");
            order6.setStatus("completed");
            order6.setClient(client);

            orderRepository.save(order6);
            order6.setNumber(order6.getOrderId());
            orderRepository.save(order6);
        }

        if (productRepository.count() < 1) {
            Faker faker = new Faker();

            for (int i = 0; i < 6; i++) {
                Product product = new Product();
                product.setName(faker.commerce().productName());
                product.setDescription(faker.company().bs());
                product.setCost(Integer.valueOf(faker.number().digits(5)));

                productRepository.save(product);
            }
        }

    }
}

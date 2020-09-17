package com.gabrielnwogu.elevatorSystem.controllers;

import com.gabrielnwogu.elevatorSystem.domains.Complaint;
import com.gabrielnwogu.elevatorSystem.domains.Feedback;
import com.gabrielnwogu.elevatorSystem.domains.Order;
import com.gabrielnwogu.elevatorSystem.domains.User;
import com.gabrielnwogu.elevatorSystem.dto.ComplaintDto;
import com.gabrielnwogu.elevatorSystem.dto.FeedbackDto;
import com.gabrielnwogu.elevatorSystem.dto.PlaceOrderDto;
import com.gabrielnwogu.elevatorSystem.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;

@Controller
public class WebsiteController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    ComplaintRepository complaintRepository;

    @Autowired
    ProductRepository productRepository;

    @ModelAttribute("order")
    public PlaceOrderDto placeOrderDto() {
        return new PlaceOrderDto();
    }

    @ModelAttribute("feedback")
    public FeedbackDto feedbackDto() {
        return new FeedbackDto();
    }

    @ModelAttribute("complaint")
    public ComplaintDto complaintDto() {
        return new ComplaintDto();
    }

    @GetMapping("/")
    public String home()
    {
        return "index";
    }

    @GetMapping("client/dashboard")
    public String dashboard(Model model, Principal principal)
    {
        String username = principal.getName();
        User user  = userRepository.findByUsername(username);

        if (user.getClient() == null) {
            return "redirect:/client/login";
        }

        model.addAttribute("orders", user.getClient().getOrders());
        return "client_dashboard";
    }

    @PostMapping("/client/order")
    public String placeOrder(@ModelAttribute("order") @Valid PlaceOrderDto placeOrderDto, Principal principal) {

        String username = principal.getName();
        HashMap<String, Integer> priceMap = new HashMap<>();
        priceMap.put("hatchback", 1500);
        priceMap.put("sequence", 2000);
        priceMap.put("crossover", 2670);

        String type = placeOrderDto.getType();
        String paymentType = placeOrderDto.getPaymentType();
        Integer price = priceMap.getOrDefault(type, null);

        if (price == null) {
            price = 1500;
            type = "hatchback";
        }

        if (paymentType == null || paymentType == "") {
            paymentType = "Bank Transfer";
        }

        User user  = userRepository.findByUsername(username);
        Order order = new Order();
        order.setAddress(placeOrderDto.getAddress());
        order.setLocation(placeOrderDto.getLocation());
        order.setPaymentType(paymentType);
        order.setCost(price);
        order.setType(type);
        order.setStatus("pending");
        order.setClient(user.getClient());

        orderRepository.save(order);
        order.setNumber(order.getOrderId());
        orderRepository.save(order);

        return "redirect:/client/dashboard?success";
    }

    @GetMapping("client/feedback")
    public String feedback(Model model, Principal principal)
    {
        String username = principal.getName();
        User user  = userRepository.findByUsername(username);

        if (user.getClient() == null) {
            return "redirect:/client/login";
        }

        model.addAttribute("feedbacks", user.getClient().getFeedbacks());
        return "client_feedback";
    }

    @PostMapping("/client/feedback")
    public String submitFeedback(@ModelAttribute("feedback") @Valid FeedbackDto feedbackDto, Principal principal) {

        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        String level = feedbackDto.getLevel();

        if (level == null || level == "") {
            level = "Good";
        }

        Feedback feedback = new Feedback();
        feedback.setClient(user.getClient());
        feedback.setEmail(user.getClient().getEmail());
        feedback.setName(user.getClient().getName());
        feedback.setDescription(feedbackDto.getDescription());
        feedback.setImprovement(feedbackDto.getImprovement());
        feedback.setLevel(level);
        feedback.setProblem(feedbackDto.getProblem());

        feedbackRepository.save(feedback);

        return "redirect:/client/feedback?success";
    }

    @GetMapping("/client/complaint")
    public String complaint(Model model, Principal principal)
    {
        String username = principal.getName();
        User user  = userRepository.findByUsername(username);

        if (user.getClient() == null) {
            return "redirect:/client/login";
        }

        model.addAttribute("complaints", user.getClient().getComplaints());
        model.addAttribute("orders", user.getClient().getOrders());

        return "client_complaint";
    }

    @PostMapping("/client/complaint")
    public String submitComplaint(@ModelAttribute("complaint") @Valid ComplaintDto complaintDto, Principal principal)
    {
        String username = principal.getName();
        User user  = userRepository.findByUsername(username);

        String orderNumber = complaintDto.getOrderNumber();

        if (orderNumber == null || orderNumber == "") {
            return "redirect:/client/complaint?error=Order Number is Required";
        }

        Order order = orderRepository.findByNumber(orderNumber);

        if (order == null) {
            return "redirect:/client/complaint?error=Order Number is not valid";
        }

        Complaint complaint = new Complaint();

        complaint.setClient(user.getClient());
        complaint.setOrderNumber(orderNumber);
        complaint.setProblem(complaintDto.getProblem());

        complaintRepository.save(complaint);

        return "redirect:/client/complaint?success";
    }

    @GetMapping("/client/project")
    public String projects(Model model)
    {
        model.addAttribute("projects", orderRepository.findByStatus("completed"));
        return "client_project";
    }

    @GetMapping("/client/product")
    public String products(Model model)
    {
        model.addAttribute("products", productRepository.findAll());
        return "client_product";
    }

    @GetMapping("/client/contact")
    public String contact()
    {
        return "client_contact";
    }

    @GetMapping("/client/about")
    public String about()
    {
        return "client_about";
    }
}

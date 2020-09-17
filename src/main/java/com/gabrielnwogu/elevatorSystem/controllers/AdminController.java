package com.gabrielnwogu.elevatorSystem.controllers;

import com.gabrielnwogu.elevatorSystem.domains.Order;
import com.gabrielnwogu.elevatorSystem.domains.User;
import com.gabrielnwogu.elevatorSystem.repositories.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ComplaintRepository complaintRepository;

    @GetMapping("/admin/dashboard")
    public String viewAdminDashboard(Model model, Principal principal)
    {
        String username = principal.getName();
        User user  = userRepository.findByUsername(username);

        if (user.getClient() != null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("clients", clientRepository.findAll());
        return "admin_dashboard";
    }

    @GetMapping("/admin/order")
    public String viewOrders(Model model, Principal principal)
    {
        String username = principal.getName();
        User user  = userRepository.findByUsername(username);

        if (user.getClient() != null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("orders", orderRepository.findAll());

        return "admin_order";
    }

    @GetMapping("/admin/feedback")
    public String viewFeedbacks(Model model, Principal principal)
    {
        String username = principal.getName();
        User user  = userRepository.findByUsername(username);

        if (user.getClient() != null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("feedbacks", feedbackRepository.findAll());

        return "admin_feedback";
    }

    @GetMapping("/admin/complaint")
    public String viewComplaints(Model model, Principal principal)
    {
        String username = principal.getName();
        User user  = userRepository.findByUsername(username);

        if (user.getClient() != null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("complaints", complaintRepository.findAll());

        return "admin_complaint";
    }

    @GetMapping("/admin/order/status")
    public String markStatus(@RequestParam String id, Principal principal)
    {
        String username = principal.getName();
        User user  = userRepository.findByUsername(username);

        if (user.getClient() != null) {
            return "redirect:/admin/login";
        }

        Optional<Order> order = orderRepository.findById(Long.parseLong(id));

        if (order.isPresent()) {
            String status = order.get().getStatus();
            String updateStatus = status;

            switch (status) {
                case "pending":
                    updateStatus = "processing";
                    break;
                case "processing":
                    updateStatus = "completed";
                    break;
                case "completed":
                    updateStatus = "pending";
                    break;
            }

            order.get().setStatus(updateStatus);
            orderRepository.save(order.get());
        }

        return "redirect:/admin/order";
    }
}

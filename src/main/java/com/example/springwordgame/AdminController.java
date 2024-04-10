package com.example.springwordgame;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    private String passwordEncoder;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin_login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Admin admin, HttpSession session, Model model) {
        Admin authenticatedAdmin = adminService.findByEmail(admin.getEmail());
        if (authenticatedAdmin != null && authenticatedAdmin.getPassword().equals(admin.getPassword())) {
            session.setAttribute("admin", authenticatedAdmin);
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "admin_login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin != null) {
            // Load words from the database and pass them to the dashboard view
            // You may want to implement this logic in the WordService
            // List<Word> words = wordService.getAllWords();
            // model.addAttribute("words", words);
            return "admin_dashboard";
        } else {
            return "redirect:/admin/login";
        }
    }
}






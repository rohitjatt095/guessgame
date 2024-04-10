package com.example.springwordgame;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class UserController {
    private final UserRepository userRepository;
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/form")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        userRepository.findById(id).ifPresent(user -> model.addAttribute("user", user));
        return "form";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users/list";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String
            password, HttpSession session, Model model) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "index";
        }
    }



    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "dashboard";
        } else {
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

}

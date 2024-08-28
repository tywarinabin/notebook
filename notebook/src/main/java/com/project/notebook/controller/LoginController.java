package com.project.notebook.controller;

import com.project.notebook.model.User;
import com.project.notebook.repository.RoleRepository;
import com.project.notebook.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;

@Controller
public class LoginController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User()); // Ensure User is the correct class
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
        String password = user.getPassword();
        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(bCryptPasswordEncoder.encode(password)); // Use PasswordEncoder

        // Remove role assignment
        user.setRoles(new HashSet<>()); // Ensure no roles are set

        userRepository.save(user);

        // Perform login after registration
        request.login(user.getEmail().toLowerCase(), password);

        return "redirect:/";
    }
}

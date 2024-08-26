package com.project.notebook.controller;

import com.project.notebook.model.Note;
import com.project.notebook.model.User;
import com.project.notebook.repository.NoteRepository;
import com.project.notebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;


    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login"; // Ensure this matches your Thymeleaf template name
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user) {
        // Handle login logic here (e.g., authenticate the user)
        return "redirect:/"; // Redirect to home page after successful login
    }
    public String indexPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            StringBuilder str = new StringBuilder();
            for(char c:userDetails.getUsername().toCharArray()){
                if(c=='@') break;
                else str.append(c);
            }
            str.setCharAt(0,Character.toUpperCase(str.charAt(0)));
            String name = String.valueOf(str);
            model.addAttribute("username", name);
        } else {
            model.addAttribute("username", "Guest");
        }
        return "index"; // Thymeleaf template
    }
}


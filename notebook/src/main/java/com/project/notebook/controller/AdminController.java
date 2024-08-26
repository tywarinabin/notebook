package com.project.notebook.controller;

import com.project.notebook.model.Note;
import com.project.notebook.model.User;
import com.project.notebook.repository.NoteRepository;
import com.project.notebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;
    @GetMapping("/admin")
    public String goToAdminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }
    @GetMapping("/admin/notes")
    public String getAllNotes(Model model) {
        List<Note> notes = noteRepository.findAll();
        model.addAttribute("notes", notes);
        return "noteList";
    }
}

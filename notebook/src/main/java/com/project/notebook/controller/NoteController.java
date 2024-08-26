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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    // Display create note form
    @GetMapping("/createnewnote")
    public String showCreateNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "createNote"; // Return the Thymeleaf template name for the form
    }

    // Handle form submission
    @PostMapping("/createnewnote")
    public String createNote(@ModelAttribute("note") Note note,
                             @AuthenticationPrincipal UserDetails userDetails) {
        // Get the logged-in user
        String email = userDetails.getUsername();
        User user = userRepository.findUserByEmail(email).get();

        // Associate the note with the logged-in user
        note.setUser(user);

        // Save the note
        noteRepository.save(note);

        return "redirect:/notes"; // Redirect to the notes list after creation
    }
    @GetMapping("/notes")
    public String getUserNotes(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Get the logged-in user's email
        String email = userDetails.getUsername();
        User user = userRepository.findUserByEmail(email).get();

        // Retrieve all notes for the logged-in user
        List<Note> notes = noteRepository.findByUserId(user.getId());

        // Add the notes to the model to display in the Thymeleaf template
        model.addAttribute("notes", notes);

        return "notes"; // Return the Thymeleaf template name for displaying the notes
    }
}

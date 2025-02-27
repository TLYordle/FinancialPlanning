package com.group_2.FinancialPlanning.controllers;

import com.group_2.FinancialPlanning.entities.Term;
import com.group_2.FinancialPlanning.entities.User;
import com.group_2.FinancialPlanning.services.TermService;
import com.group_2.FinancialPlanning.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/terms")
public class TermController {
    @Autowired
    private TermService termService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Term> getAllTerms() {
        return termService.getAllTerms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Term> getTermById(@PathVariable Integer id) {
        Optional<Term> term = termService.getTermById(id);
        return term.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Tìm Term theo tên
    @GetMapping("/search")
    public ResponseEntity<Term> findByTermNameAndStatus(@RequestParam String name, @RequestParam Term.Status status) {
        Term term = termService.findByTermNameAndStatus(name, status);
        return term != null ? ResponseEntity.ok(term) : ResponseEntity.notFound().build();
    }

    //Tìm Terms theo trạng thái
    @GetMapping("/status/{status}")
    public List<Term> getTermsByStatus(@PathVariable Term.Status status) {
        return termService.getTermsByStatus(status);
    }

    //Tìm Terms theo loại thời gian (duration)
    @GetMapping("/duration/{duration}")
    public List<Term> getTermsByDuration(@PathVariable Term.Duration duration) {
        return termService.getTermsByDuration(duration);
    }


    @PostMapping("/{userId}")
    public ResponseEntity<?> createTerm(@RequestBody Term term, @PathVariable Integer userId ) {
        // Kiểm tra xem User có tồn tại không
        Optional<User> user = userService.getUserById(Long.valueOf(userId));
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found!");
        }

        if (termService.findByTermNameAndStatus(term.getTermName(), term.getStatus()) != null) {
            return ResponseEntity.badRequest().body("This term name with status already exist!");
        }

        // Gán User vào Term
        term.setCreatedBy(user.get());

        Term savedTerm = termService.createTerm(term);
        return ResponseEntity.ok(savedTerm);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTerm(@PathVariable Integer id, @RequestBody Term termDetails) {
        Term checkExistingTerm = termService.findByTermNameAndStatus(termDetails.getTermName(), termDetails.getStatus());
        if (checkExistingTerm != null && !Objects.equals(checkExistingTerm.getTermId(), id)) {
            return ResponseEntity.badRequest().body("This term name with status already exist!");
        }
        return ResponseEntity.ok(termService.updateTerm(id, termDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerm(@PathVariable Integer id) {
        termService.deleteTerm(id);
        return ResponseEntity.noContent().build();
    }
}

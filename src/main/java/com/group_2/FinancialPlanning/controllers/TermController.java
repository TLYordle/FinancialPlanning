package com.group_2.FinancialPlanning.controllers;

import com.group_2.FinancialPlanning.entities.Term;
import com.group_2.FinancialPlanning.services.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/terms")
public class TermController {
    @Autowired
    private TermService termService;

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
    public ResponseEntity<Term> getTermsByTermName(@RequestParam String name) {
        Term term = termService.getTermsByTermName(name);
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


    @PostMapping
    public Term createTerm(@RequestBody Term term) {
        return termService.createTerm(term);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Term> updateTerm(@PathVariable Integer id, @RequestBody Term termDetails) {
        return ResponseEntity.ok(termService.updateTerm(id, termDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerm(@PathVariable Integer id) {
        termService.deleteTerm(id);
        return ResponseEntity.noContent().build();
    }
}

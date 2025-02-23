package com.group_2.FinancialPlanning.controller;

import com.group_2.FinancialPlanning.entity.Term;
import com.group_2.FinancialPlanning.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/terms")
public class TermController {

    @Autowired
    private TermService termService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT', 'STAFF')")
    public List<Term> getAllTerms() {
        return termService.getAllTerms();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT', 'STAFF')")
    public ResponseEntity<Term> getTermById(@PathVariable Integer id) {
        Optional<Term> term = termService.getTermById(id);
        return term.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Term createTerm(@RequestBody Term term) {
        return termService.createTerm(term);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<Term> updateTerm(@PathVariable Integer id, @RequestBody Term termDetails) {
        Term updatedTerm = termService.updateTerm(id, termDetails);
        return ResponseEntity.ok(updatedTerm);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTerm(@PathVariable Integer id) {
        termService.deleteTerm(id);
        return ResponseEntity.noContent().build();
    }
}
package com.group_2.FinancialPlanning.services;

import com.group_2.FinancialPlanning.entities.Term;
import com.group_2.FinancialPlanning.repositories.TermsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TermService {
    @Autowired
    private TermsRepository termRepository;

    public List<Term> getAllTerms() {
        return termRepository.findAll();
    }

    public Optional<Term> getTermById(Integer id) {
        return termRepository.findById(id);
    }

    public Term getTermsByTermName(String termName) {
        return termRepository.findByTermName(termName);
    }

    public List<Term> getTermsByStatus(Term.Status status) {
        return termRepository.findByStatus(status);
    }

    public List<Term> getTermsByDuration(Term.Duration duration) {
        return termRepository.findByDuration(duration);
    }

    public Term createTerm(Term term) {
        return termRepository.save(term);
    }

    public Term updateTerm(Integer id, Term termDetails) {
        Term term = termRepository.findById(id).orElseThrow(() -> new RuntimeException("Term not found"));
        term.setTermName(termDetails.getTermName());
        term.setDuration(termDetails.getDuration());
        term.setStartDate(termDetails.getStartDate());
        term.setEndDate(termDetails.getEndDate());
        term.setPlanDueDate(termDetails.getPlanDueDate());
        term.setReportDueDate(termDetails.getReportDueDate());
        term.setStatus(termDetails.getStatus());
        return termRepository.save(term);
    }

    public void deleteTerm(Integer id) {
        termRepository.deleteById(id);
    }
}

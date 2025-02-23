package com.group_2.FinancialPlanning.service;

import com.group_2.FinancialPlanning.entity.Term;
import com.group_2.FinancialPlanning.repository.TermRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Service
public class TermService {

    @Autowired
    private TermRepository termRepository;

    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT', 'STAFF')")
    public List<Term> getAllTerms() {
        return termRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT', 'STAFF')")
    public Optional<Term> getTermById(Integer id) {
        return termRepository.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Term createTerm(Term term) {
        return termRepository.save(term);
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTerm(Integer id) {
        termRepository.deleteById(id);
    }


}

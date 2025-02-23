package com.group_2.FinancialPlanning.controllerThymeleaf;

import com.group_2.FinancialPlanning.entity.Term;
import com.group_2.FinancialPlanning.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/termsHtml")  // Định nghĩa đường dẫn của Thymeleaf
public class TermControllerThymeleaf {

    @Autowired
    private TermService termService;

    @GetMapping
    public String showTerms(Model model) {
        List<Term> terms = termService.getAllTerms();  // Lấy danh sách terms từ Service
        model.addAttribute("terms", terms);  // Đưa dữ liệu vào Model
        return "listTerm";  // Trả về template Thymeleaf (listTerm.html)
    }
}


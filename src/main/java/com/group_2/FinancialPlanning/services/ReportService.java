package com.group_2.FinancialPlanning.services;

import com.group_2.FinancialPlanning.entities.MonthlyReport;
import com.group_2.FinancialPlanning.entities.MonthlyReportDetails;
import com.group_2.FinancialPlanning.entities.Term;
import com.group_2.FinancialPlanning.entities.User;
import com.group_2.FinancialPlanning.repositories.MonthlyReportDetailsRepository;
import com.group_2.FinancialPlanning.repositories.MonthlyReportRepository;
import com.group_2.FinancialPlanning.repositories.TermsRepository;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

@Service
public class ReportService {

    private final Map<String, List<MonthlyReportDetails>> temporaryReportData = new HashMap<>();
    private final MonthlyReportRepository monthlyReportRepository;
    private final MonthlyReportDetailsRepository monthlyReportDetailsRepository;
    private final TermsRepository termsRepository;
    private final UserService userService;

    public List<MonthlyReport> getAllReports() {
        return monthlyReportRepository.findAll();
    }

    @Autowired
    public ReportService(MonthlyReportRepository monthlyReportRepository,
                         MonthlyReportDetailsRepository monthlyReportDetailsRepository,
                         TermsRepository termsRepository, UserService userService) {
        this.monthlyReportRepository = monthlyReportRepository;
        this.monthlyReportDetailsRepository = monthlyReportDetailsRepository;
        this.termsRepository = termsRepository;
        this.userService = userService;
    }

    public void processExcelFile(MultipartFile file, String term, String month) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalStateException("No file uploaded or file is empty");
        }

        String contentType = file.getContentType();
        if (!"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType)) {
            throw new IllegalStateException("Only .xlsx files are allowed");
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            List<MonthlyReportDetails> reportDetails = new ArrayList<>();
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua header

                MonthlyReportDetails detail = new MonthlyReportDetails();
                detail.setExpense(getCellValue(row.getCell(0)));
                detail.setCostType(getCellValue(row.getCell(1)));
                detail.setUnitPrice(parseBigDecimal(getCellValue(row.getCell(2))));
                detail.setAmount(parseInteger(row.getCell(3)));
                detail.setProjectName(getCellValue(row.getCell(4)));
                detail.setNote(getCellValue(row.getCell(5)));
                reportDetails.add(detail);
            }
            temporaryReportData.put(month, reportDetails);
        } catch (IOException e) {
            throw new IOException("Error reading Excel file: " + e.getMessage(), e);
        }
    }


    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            return new BigDecimal(value.trim().replace(",", ""));
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    private Integer parseInteger(Cell cell) {
        if (cell == null) return 0;
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Integer.parseInt(cell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }


    @Transactional
    public void deleteMonthlyReport(Integer reportId) {
        monthlyReportRepository.deleteById(reportId);
    }

    public MonthlyReport getReportById(Integer reportId) {
        return monthlyReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with ID: " + reportId));
    }


    @Transactional
    public MonthlyReport saveMonthlyReport(MonthlyReport monthlyReport) {
        if (monthlyReport.getTerm() == null || monthlyReport.getTerm().getTermId() == null) {
            throw new IllegalArgumentException("Term is required and must have a valid termId");
        }
        if (monthlyReport.getUser() == null || monthlyReport.getUser().getUser_id() == null) {
            throw new IllegalArgumentException("User is required and must have a valid userId");
        }

        // Kiểm tra termId tồn tại
        Term term = termsRepository.findById(monthlyReport.getTerm().getTermId())
                .orElseThrow(() -> new IllegalArgumentException("Term not found with ID: " + monthlyReport.getTerm().getTermId()));

        // Kiểm tra userId tồn tại
        User user = userService.getUserById(Long.valueOf(monthlyReport.getUser().getUser_id()))
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + monthlyReport.getUser().getUser_id()));

        monthlyReport.setTerm(term);
        monthlyReport.setUser(user);

        // Đặt reportDate nếu chưa có
        if (monthlyReport.getReportDate() == null) {
            monthlyReport.setReportDate(new java.sql.Date(System.currentTimeMillis()));
        }

        try {
            MonthlyReport savedReport = monthlyReportRepository.save(monthlyReport);

            // Lưu các chi tiết (MonthlyReportDetails) nếu có trong temporaryReportData
            List<MonthlyReportDetails> details = temporaryReportData.get(monthlyReport.getMonthName());
            if (details != null && !details.isEmpty()) {
                for (MonthlyReportDetails detail : details) {
                    detail.setReportId(savedReport.getReportId());
                    monthlyReportDetailsRepository.save(detail);
                }
                temporaryReportData.remove(monthlyReport.getMonthName());
            }

            return savedReport;
        } catch (Exception e) {
            throw new RuntimeException("Error saving monthly report: " + e.getMessage(), e);
        }
    }
}
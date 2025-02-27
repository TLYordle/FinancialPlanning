-- Insert into users
INSERT INTO users (full_name, email, password, address, birthday, phonenumber, position, department, role)
VALUES
('Nguyễn Thanh An', 'rubikcuty29@gmail.com', '123456', '123 Main St', '1990-05-15', '123456789', 'Manager', 'IT', 'ADMIN'),
('Trần Văn Lâm', 'tranvanlam@gmail.com', '123456', '456 Elm St', '1985-07-20', '987654321', 'Accountant', 'Accounting', 'ACCOUNTANT'),
('Lê Khánh Duy', 'lekhanhduy@gmail.com', '123456', '789 Oak St', '1992-03-10', '456123789', 'HR Specialist', 'HR', 'STAFF'),
('Lê Văn Lâm', 'levanlam@gmail.com', '123456', '321 Pine St', '1988-08-25', '789654123', 'Marketing Lead', 'Marketing', 'STAFF'),
('Trần Đức Bo', 'tranducbo@gmail.com', '123456', '654 Birch St', '1995-12-05', '159357456', 'Finance Analyst', 'Finance', 'ACCOUNTANT');

-- Insert into terms
INSERT INTO terms (term_name, duration, start_date, end_date, plan_due_date, report_due_date, status, created_by)
VALUES
('Q1 2024', 'QUARTERLY', '2024-01-01', '2024-03-31', '2024-03-15', '2024-04-01', 'NEW', 1),
('Q2 2024', 'QUARTERLY', '2024-04-01', '2024-06-30', '2024-06-15', '2024-07-01', 'NEW', 4),
('Q3 2024', 'QUARTERLY', '2024-07-01', '2024-09-30', '2024-09-15', '2024-10-01', 'NEW', 2),
('Q4 2024', 'QUARTERLY', '2024-10-01', '2024-12-31', '2024-12-15', '2025-01-01', 'NEW', 3),
('Q1 2025', 'QUARTERLY', '2025-01-01', '2025-03-31', '2025-03-15', '2025-04-01', 'NEW', 1);


-- Insert into financial_plans
INSERT INTO financial_plans (id_unique, plan_name, term_id, uploaded_by)
VALUES
(101, 'IT Budget Plan', 1, 1),
(102, 'HR Salary Planning', 2, 3),
(103, 'Marketing Campaign Budget', 3, 4),
(104, 'Finance Forecast Plan', 4, 5),
(105, 'New Product Development Budget', 5, 2);

-- Insert into financial_plan_details
INSERT INTO financial_plan_details (expense, cost_type, unit_price, amount, project_name, note, status_expense, plan_id)
VALUES
('Software Licenses', 'Operational', 1000.00, 10, 'IT Infrastructure', 'Annual license renewal', 'NEW', 1),
('Employee Training', 'HR Development', 500.00, 5, 'HR Improvement', 'Quarterly training sessions', 'NEW', 2),
('Ad Campaigns', 'Marketing', 2000.00, 3, 'Brand Awareness', 'Digital advertising expenses', 'NEW', 3),
('Financial Audit', 'Compliance', 1500.00, 2, 'Annual Reports', 'End of year financial review', 'NEW', 4),
('R&D Investment', 'Development', 3000.00, 2, 'New Product Development', 'Research and development for new product', 'NEW', 5);

-- Insert into monthly_report
INSERT INTO monthly_report (report_name, month_name, term_id, user_id, status)
VALUES
('January IT Report', 'January', 1, 1, 'NEW'),
('February HR Report', 'February', 2, 3, 'NEW'),
('March Marketing Report', 'March', 3, 4, 'NEW'),
('April Finance Report', 'April', 4, 5, 'NEW'),
('May R&D Report', 'May', 5, 2, 'NEW');

-- Insert into monthly_report_details
INSERT INTO monthly_report_details (expense, cost_type, unit_price, amount, project_name, note, report_id, total_expense, total_user)
VALUES
('Cloud Hosting', 'Infrastructure', 200.00, 15, 'IT Operations', 'Monthly cloud service bill', 1, 3000.00, 5),
('HR Workshops', 'HR Training', 300.00, 4, 'HR Development', 'Employee engagement programs', 2, 1200.00, 10),
('SEO Optimization', 'Marketing', 700.00, 3, 'Digital Strategy', 'Search engine marketing', 3, 2100.00, 6),
('Financial Consulting', 'Audit', 1800.00, 2, 'Compliance', 'Annual financial audit', 4, 3600.00, 4),
('Prototype Development', 'R&D', 5000.00, 1, 'New Product Development', 'Initial prototype costs', 5, 5000.00, 3),
('Server Maintenance', 'IT', 250.00, 8, 'Infrastructure Support', 'Monthly server upkeep', 1, 2000.00, 4),
('Recruitment Ads', 'HR', 500.00, 3, 'Hiring Campaign', 'Job posting expenses', 2, 1500.00, 6),
('Social Media Ads', 'Marketing', 1200.00, 2, 'Brand Engagement', 'Sponsored social media ads', 3, 2400.00, 5),
('Tax Consultancy', 'Finance', 3000.00, 1, 'Tax Planning', 'Consulting for tax optimization', 4, 3000.00, 2),
('Patent Filing', 'R&D', 7000.00, 1, 'Innovation Protection', 'Filing for product patents', 5, 7000.00, 1);

-- Insert into annual_expense_reports
INSERT INTO annual_expense_reports (year, status, monthly_report_details_id, user_id)
VALUES
(2024, 'submitted', 1, 1),
(2024, 'draft', 2, 3),
(2024, 'approved', 3, 4),
(2024, 'rejected', 4, 5);

-- Insert into annual_expense_reports_detail
INSERT INTO annual_expense_reports_detail (cost_type, user_id)
VALUES
('IT Equipment', 1),
('HR Salary', 3),
('Marketing Strategy', 4),
('Financial Risk Management', 5);
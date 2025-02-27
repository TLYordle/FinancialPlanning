USE financial_planning;
-- Insert sample data into users table
INSERT INTO users (full_name, email, password, address, birthday, phonenumber, position, department, role)
VALUES 
('John Doe', 'johndoe@example.com', 'password123', '123 Main St', '1985-05-15', '1234567890', 'Manager', 'Finance', 'ADMIN'),
('Jane Smith', 'janesmith@example.com', 'password123', '456 Elm St', '1990-07-20', '0987654321', 'Accountant', 'Finance', 'ACCOUNTANT'),
('Alice Johnson', 'alicejohnson@example.com', 'password123', '789 Oak St', '1992-03-10', '1122334455', 'Analyst', 'Finance', 'STAFF'),
('Bob Brown', 'bobbrown@example.com', 'password123', '321 Pine St', '1988-11-30', '2233445566', 'Director', 'Finance', 'ADMIN'),
('Charlie Black', 'charlieblack@example.com', 'password123', '654 Maple St', '1995-09-05', '3344556677', 'Intern', 'Finance', 'STAFF');

-- Insert sample data into terms table
INSERT INTO terms (term_name, duration, start_date, end_date, plan_due_date, report_due_date, status, created_by)
VALUES 
('Q1 2025', 'QUARTERLY', '2025-01-01', '2025-03-31', '2025-03-15', '2025-03-31', 'NEW', 1),
('Q2 2025', 'QUARTERLY', '2025-04-01', '2025-06-30', '2025-06-15', '2025-06-30', 'NEW', 2),
('H1 2025', 'HALFYEAR', '2025-01-01', '2025-06-30', '2025-06-15', '2025-06-30', 'NEW', 3);

-- Insert sample data into financial_plans table
INSERT INTO financial_plans (id_unique, plan_name, term_id, uploaded_by)
VALUES 
(1, '2025 Financial Plan A', 1, 1),
(2, '2025 Financial Plan B', 2, 2),
(3, '2025 Financial Plan C', 3, 3);

-- Insert sample data into financial_plan_details table
INSERT INTO financial_plan_details (expense, cost_type, unit_price, amount, status_expense, plan_id)
VALUES 
('Office Supplies', 'Operational', 100.00, 10, 'NEW', 1),
('Travel Expenses', 'Operational', 500.00, 5, 'NEW', 1),
('Marketing', 'Advertising', 300.00, 2, 'NEW', 2),
('Software Licenses', 'Operational', 200.00, 15, 'NEW', 3);

-- Insert sample data into monthly_report table
INSERT INTO monthly_report (report_name, month_name, term_id, user_id, status)
VALUES 
('January Report', 'January', 1, 1, 'NEW'),
('February Report', 'February', 1, 2, 'NEW');

-- Insert sample data into annual_expense_reports table
INSERT INTO annual_expense_reports (year, status, user_id, monthly_report_details_id)
VALUES 
(2025, 'draft', 1, 1),
(2025, 'submitted', 2, 2);
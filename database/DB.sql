DROP DATABASE financial_planning;
CREATE DATABASE financial_planning;
USE financial_planning;
-- Bảng users
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
	address VARCHAR(255) DEFAULT '',
	birthday date,
    phonenumber VARCHAR(255) DEFAULT '',
    position VARCHAR(255) DEFAULT '',
    department VARCHAR(255) DEFAULT '',
    role ENUM('ADMIN', 'STAFF', 'ACCOUNTANT') NOT NULL,
    is_active TINYINT(1) DEFAULT 1,
    created_at datetime default CURRENT_TIMESTAMP,
    updated_at datetime default CURRENT_TIMESTAMP
);

-- Bảng terms
CREATE TABLE terms (
    term_id INT AUTO_INCREMENT PRIMARY KEY,
    term_name VARCHAR(255) NOT NULL,
    duration ENUM('MONTHLY', 'QUARTERLY', 'HALFYEAR') NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    plan_due_date DATE NOT NULL,
    report_due_date DATE NOT NULL,
    status ENUM('NEW', 'INPROGRESS', 'CLOSED') DEFAULT 'NEW',
    created_by INT,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- Bảng financial_plans
CREATE TABLE financial_plans (
    plan_id INT AUTO_INCREMENT PRIMARY KEY,
    id_unique int,
    plan_name VARCHAR(255) DEFAULT '',
    term_id INT,
    uploaded_by INT,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('NEW', 'WAITING FOR APPROVAL', 'APPROVED', 'CLOSED') DEFAULT 'NEW',
    version int DEFAULT 1,
    FOREIGN KEY (term_id) REFERENCES terms(term_id),
    FOREIGN KEY (uploaded_by) REFERENCES users(user_id)
);


-- Bảng financial_plan_details
CREATE TABLE financial_plan_details (
    detail_id INT AUTO_INCREMENT PRIMARY KEY,
    expense VARCHAR(255) NOT NULL,
    cost_type VARCHAR(255) NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    amount INT NOT NULL,
    total DECIMAL(15,2) GENERATED ALWAYS AS (unit_price * amount) STORED,
    project_name VARCHAR(255),
    note VARCHAR(6000),
    status_expense ENUM('NEW', 'WAITING FOR APPROVAL', 'APPROVED') NOT NULL,
    plan_id int,
    FOREIGN KEY (plan_id) REFERENCES financial_plans(plan_id)
);

-- Bảng monthly_reports
CREATE TABLE monthly_report (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    report_name VARCHAR(100) NOT NULL,
    month_name VARCHAR(20) NOT NULL,
    term_id int,
    user_id int,
    status ENUM('NEW', 'CLOSED') NOT NULL,
    report_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    version int DEFAULT 1,
    foreign key (user_id) REFERENCES users(user_id),
    FOREIGN KEY (term_id) REFERENCES terms(term_id)
);

-- Bảng monthly_report_details 
CREATE TABLE monthly_report_details (
    monthly_report_details_id INT AUTO_INCREMENT PRIMARY KEY,
    expense VARCHAR(255) NOT NULL,
    cost_type VARCHAR(255) NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    amount INT NOT NULL,
    total DECIMAL(15,2) GENERATED ALWAYS AS (unit_price * amount) STORED,
    project_name VARCHAR(255),
    note VARCHAR(6000),
    report_id int,
    total_expense DECIMAL(20,2),
    total_user int,
    FOREIGN KEY (report_id) REFERENCES monthly_report(report_id)
);

-- Bảng annual_expense_reports
CREATE TABLE annual_expense_reports (
    aex_report_id INT AUTO_INCREMENT PRIMARY KEY,
    year INT NOT NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('draft', 'submitted', 'approved', 'rejected') NOT NULL,
    monthly_report_details_id int,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (monthly_report_details_id) REFERENCES monthly_report_details(monthly_report_details_id)
);

CREATE TABLE annual_expense_reports_detail(
	aex_report_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    cost_type VARCHAR(6000),
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

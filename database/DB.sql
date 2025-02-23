CREATE DATABASE financial_planning;
USE financial_planning;

-- Bảng users
CREATE TABLE users (
    user_id VARCHAR(255) PRIMARY KEY DEFAULT (UUID()),
    full_name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'staff', 'accountant') NOT NULL,
    is_active TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bảng terms
CREATE TABLE terms (
    term_id INT AUTO_INCREMENT PRIMARY KEY,
    term_name VARCHAR(255) NOT NULL,
    duration ENUM('monthly', 'quarterly', 'yearly') NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    plan_due_date DATE NOT NULL,
    report_due_date DATE NOT NULL,
    status ENUM('active', 'inactive', 'archived') NOT NULL,
    created_by VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- Bảng financial_plans
CREATE TABLE financial_plans (
    plan_id INT AUTO_INCREMENT PRIMARY KEY,
    plan_name VARCHAR(255) NOT NULL,
    term_id INT,
    uploaded_by VARCHAR(255),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('draft', 'submitted', 'approved', 'rejected') NOT NULL,
    version INT DEFAULT 1,
    FOREIGN KEY (term_id) REFERENCES terms(term_id),
    FOREIGN KEY (uploaded_by) REFERENCES users(user_id)
);

-- Bảng financial_plan_details
CREATE TABLE financial_plan_details (
    detail_id INT AUTO_INCREMENT PRIMARY KEY,
    plan_id INT,
    expense_name VARCHAR(255) NOT NULL,
    cost_type VARCHAR(255) NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    amount INT NOT NULL,
    total DECIMAL(15,2) GENERATED ALWAYS AS (unit_price * amount) STORED,
    project_name VARCHAR(255),
    regulations TEXT,
    FOREIGN KEY (plan_id) REFERENCES financial_plans(plan_id)
);

-- Bảng expense_approvals
CREATE TABLE expense_approvals (
    approval_id INT AUTO_INCREMENT PRIMARY KEY,
    detail_id INT,
    approved_by VARCHAR(255),
    approval_status ENUM('pending', 'approved', 'rejected') NOT NULL,
    approval_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (detail_id) REFERENCES financial_plan_details(detail_id),
    FOREIGN KEY (approved_by) REFERENCES users(user_id)
);

-- Bảng monthly_expense_reports
CREATE TABLE monthly_expense_reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    term_id INT,
    uploaded_by VARCHAR(255),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('draft', 'submitted', 'approved', 'rejected') NOT NULL,
    FOREIGN KEY (term_id) REFERENCES terms(term_id),
    FOREIGN KEY (uploaded_by) REFERENCES users(user_id)
);

-- Bảng annual_expense_reports
CREATE TABLE annual_expense_reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    year INT NOT NULL,
    uploaded_by VARCHAR(255),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('draft', 'submitted', 'approved', 'rejected') NOT NULL,
    FOREIGN KEY (uploaded_by) REFERENCES users(user_id)
);

-- Bảng plan_versions
CREATE TABLE plan_versions (
    version_id INT AUTO_INCREMENT PRIMARY KEY,
    plan_id INT,
    version INT NOT NULL,
    uploaded_by VARCHAR(255),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    file_path VARCHAR(255) NOT NULL,
    FOREIGN KEY (plan_id) REFERENCES financial_plans(plan_id),
    FOREIGN KEY (uploaded_by) REFERENCES users(user_id)
);

-- Bảng user_sessions
CREATE TABLE user_sessions (
    session_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255),
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    logout_time TIMESTAMP NULL,
    session_token VARCHAR(255) UNIQUE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
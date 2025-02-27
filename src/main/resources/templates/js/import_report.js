<script>
    // Hàm logout
    function logout() {
        alert("Logged out successfully!");
    }

    // Hàm quay lại trang trước
    function goBack() {
        window.location.href = 'import_report.html';
    }

    // Hàm submit báo cáo
    function submitReport() {
        const reportData = {
            term: document.getElementById('termValue').textContent.trim(),
            month: document.getElementById('monthValue').textContent.trim(),
            department: document.getElementById('departmentValue').textContent.trim(),
            fileName: document.getElementById('fileValue').textContent.trim()
        };

        // Lấy dữ liệu từ bảng chi tiết
        const tableRows = document.querySelectorAll("#tableBody tr");
        let reportDetails = [];

        tableRows.forEach(row => {
            const cells = row.querySelectorAll("td");
            if (cells.length >= 9) { // Đảm bảo có đủ cột (Expense, Cost Type, Unit Price, Amount, Total, Project Name, Notes)
                const detail = {
                    expense: cells[0].textContent.trim() === 'NULL' ? null : cells[0].textContent.trim(),
                    costType: cells[1].textContent.trim() === 'NULL' ? null : cells[1].textContent.trim(),
                    unitPrice: parseFloat(cells[2].textContent.replace(/,/g, '')) || 0, // Xóa dấu phẩy và parse thành số
                    amount: parseInt(cells[4].textContent) || 0, // Giả sử cột Amount là cột thứ 5
                    total: parseFloat(cells[5].textContent.replace(/,/g, '')) || 0, // Xóa dấu phẩy và parse thành số
                    projectName: cells[6].textContent.trim() === 'NULL' ? null : cells[6].textContent.trim(),
                    note: cells[7].textContent.trim() === 'NULL' ? null : cells[7].textContent.trim()
                };
                reportDetails.push(detail);
            }
        });

        // Lấy reportId từ URL nếu có (giả sử báo cáo đã tồn tại)
        const urlParams = new URLSearchParams(window.location.search);
        const reportId = urlParams.get('reportId');

        // Tạo object để gửi lên server
        const payload = {
            reportName: `Monthly Report - ${reportData.term} - ${reportData.month}`, // Tạo tên báo cáo
            termId: getTermIdFromName(reportData.term), // Hàm chuyển tên term sang termId (cần định nghĩa)
            monthName: reportData.month,
            userId: localStorage.getItem('user_id'), // Giả sử có user_id trong localStorage
            department: reportData.department,
            status: "SUBMITTED", // Giả sử trạng thái mặc định là SUBMITTED
            reportDate: new Date().toISOString().split('T')[0], // Ngày hiện tại
            version: 1, // Version ban đầu, có thể tăng dần nếu đã tồn tại
            fileName: reportData.fileName
        };

        // Gửi dữ liệu tổng hợp (Monthly Report) lên server
        fetch(`http://localhost:8080/api/reports${reportId ? `/${reportId}` : ''}`, {
            method: reportId ? "PUT" : "POST", // Sử dụng PUT nếu có reportId, POST nếu mới
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem('authToken')
            },
            body: JSON.stringify(payload)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const savedReportId = reportId || data.reportId; // Lấy reportId từ response nếu là POST

            // Gửi dữ liệu chi tiết (Monthly Report Details) lên server
            reportDetails.forEach(detail => {
                detail.reportId = savedReportId; // Gán reportId cho mỗi chi tiết
                // Tính lại total nếu cần (giả sử server sẽ xử lý)
                if (detail.unitPrice && detail.amount) {
                    detail.total = detail.unitPrice.multiply(BigDecimal.valueOf(detail.amount)); // Cần import BigDecimal nếu dùng JavaScript math
                }
            });

            return fetch(`http://localhost:8080/api/report_details/import`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + localStorage.getItem('authToken')
                },
                body: JSON.stringify(reportDetails)
            });
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            alert("Báo cáo và chi tiết đã được lưu thành công!");
            window.location.href = 'monthly_report_staff.html'; // Chuyển hướng sau khi submit
        })
        .catch(error => {
            console.error("Lỗi khi lưu báo cáo:", error);
            alert("Có lỗi xảy ra khi lưu báo cáo: " + error.message);
        });
    }

    // Hàm chuyển tên Term sang TermId (giả sử)
    function getTermIdFromName(termName) {
        // Logic để ánh xạ tên Term (ví dụ: "Q3 2024") sang TermId
        // Giả sử bạn có danh sách terms trong một biến global hoặc từ API
        const terms = [
            { termId: 1, termName: "Q1 2024" },
            { termId: 2, termName: "Q2 2024" },
            { termId: 3, termName: "Q3 2024" },
            { termId: 4, termName: "Q4 2024" }
        ];
        const term = terms.find(t => t.termName === termName);
        return term ? term.termId : null;
    }

    // Xử lý sự kiện cho nút Back
    const backButton = document.querySelector('.back-btn');
    if (backButton) {
        backButton.addEventListener('click', function(event) {
            event.preventDefault(); // Ngăn form submit mặc định
            window.location.href = 'import_report.html';
        });
    }

    document.addEventListener('DOMContentLoaded', function() {
        const fileName = localStorage.getItem('importedFileName');
        if (fileName) document.getElementById('fileValue').textContent = fileName;

        const fileData = localStorage.getItem('excelData');
        if (fileData) {
            const jsonData = JSON.parse(fileData);
            renderTable(jsonData);
        }

        // Lấy giá trị từ localStorage
        const savedTerm = localStorage.getItem('selectedTerm');
        const savedMonth = localStorage.getItem('selectedMonth');

        // Hiển thị giá trị trong HTML
        if (savedTerm) {
            document.getElementById('termValue').textContent = savedTerm;
            console.log('Loaded term from localStorage:', savedTerm);
        } else {
            document.getElementById('termValue').textContent = 'None';
        }

        if (savedMonth) {
            document.getElementById('monthValue').textContent = savedMonth;
            console.log('Loaded month from localStorage:', savedMonth);
        } else {
            document.getElementById('monthValue').textContent = 'None';
        }
    });

    function renderTable(data) {
        const tableHead = document.getElementById("tableHead");
        const tableBody = document.getElementById("tableBody");

        if (!data || data.length === 0) {
            tableHead.innerHTML = '';
            tableBody.innerHTML = '<tr><td colspan="9" class="p-2 text-gray-500">Không có dữ liệu trong file.</td></tr>';
            return;
        }

        // Tạo tiêu đề bảng
        const headers = Object.keys(data[0]);
        tableHead.innerHTML = "<tr>" + headers.map(col => `<th class="p-2 border bg-gray-300">${col}</th>`).join("") + "</tr>";

        // Tạo nội dung bảng, thay undefined hoặc trống bằng "NULL"
        tableBody.innerHTML = data.map(row =>
            `<tr class="table-row">` +
            headers.map((col, index) => {
                let value = row[col] === undefined || row[col] === '' ? 'NULL' : row[col];
                // Định dạng số cho cột 3 (Unit Price) và cột 5 (Total)
                if ((index === 2 || index === 4) && value !== 'NULL') {
                    value = Number(value).toLocaleString('en-US'); // Thêm dấu phẩy
                }
                return `<td class="p-2 border">${value}</td>`;
            }).join("") +
            `</tr>`
        ).join("");
    }

    async function fetchTerms(url = "http://localhost:8080/api/terms") {
        try {
            let userIdLocalStorage = localStorage.getItem("user_id");
            let userResponse = await fetch(`http://localhost:8080/api/users/${userIdLocalStorage}`);
            let user = await userResponse.json();
            let response = await fetch(url);
            let data = await response.json();
            return user.role;
        } catch (error) {
            console.error("Error fetching terms:", error);
        }
    }

    // Hàm tự chạy (Immediately Invoked Async Function Expression - IIFE)
    (async function() {
        let role = await fetchTerms();

        if (role !== 'ADMIN') {
            let homePage = document.querySelector('.homePage');
            let userPage = document.querySelector('.userPage');
            homePage.href = '../user/home.html';
            userPage.style.display = 'none';
        }
    })();

    var fullName = localStorage.getItem("full_name")
    var role = localStorage.getItem("role")
    var span = document.getElementById("full_name")
    var spanRole = document.getElementById("role")
    span.textContent = fullName
    spanRole.textContent = role
</script>
<script src="../js/import_report.js"></script>
// lấy Dữ liệu của term
document.addEventListener("DOMContentLoaded", function () {
    fetchTermData();
});

let allterm = [];
function fetchTermData(){
    fetch("http://localhost:8080/api/terms",{
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((res) => res.json())
        .then((data) => {
            console.log("Dữ liệu API trả về:", data);

            if (Array.isArray(data)) {
                allterm = data;  // Lưu dữ liệu vào biến toàn cục
                optiontermname(allterm); // Tự động điền tên vào term search
            } else {
                console.error("Lỗi lấy dữ liệu: Dữ liệu API không đúng định dạng");
            }
        })
        .catch((error) => console.error("Lỗi kết nối API:", error));
}
function optiontermname(terms) {
    const optionterms = document.getElementById("term");
    const uniquetermname = [...new Set(terms.map(term => term.termName))];

    uniquetermname.forEach(name => {
        const option = document.createElement("option");
        option.value = name;
        option.textContent = name;
        optionterms.appendChild(option);
    });
}

// importplans1.js

// Hàm xử lý import file Excel
function handleFile(event) {
    const file = event.target.files[0];
    if (!file) {
        alert("Please select a file.");
        return;
    }

    const reader = new FileReader();
    reader.onload = function(e) {
        const data = new Uint8Array(e.target.result);
        const workbook = XLSX.read(data, { type: 'array' });

        // Giả sử bạn muốn lấy dữ liệu từ sheet đầu tiên
        const firstSheetName = workbook.SheetNames[0];
        const worksheet = workbook.Sheets[firstSheetName];

        // Chuyển đổi sheet thành JSON
        const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 });

        // Lưu dữ liệu vào localStorage để sử dụng ở step 2
        localStorage.setItem('excelData', JSON.stringify(jsonData));

        // Chuyển hướng đến step 2
        window.location.href = './plan_import_step_2.html'; // Đảm bảo đường dẫn chính xác
    };

    reader.readAsArrayBuffer(file);
}

// Gán sự kiện cho input file
document.getElementById('fileinput').addEventListener('change', handleFile);

// Hàm chuyển hướng đến step 2
function goToDisplay() {
    const term = document.getElementById('term').value;
    if (!term) {
        alert("Please select a term.");
        return;
    }

    // Thực hiện hành động khác nếu cần trước khi chuyển hướng
    window.location.href = './plan_import_step_2.html'; // Đảm bảo đường dẫn chính xác
}
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

function logout() {
    alert("Logout successful!");
}

const importButton = document.querySelector('.import-btn');
if (importButton) {
    importButton.addEventListener('click', function(event) {
        event.preventDefault();
        const fileDisplay = document.getElementById('fileDisplay');
        if (fileDisplay && fileDisplay.textContent !== 'Attach a file') {
            localStorage.setItem('importedFileName', fileDisplay.textContent);
            window.location.href = 'plan_import_step_2.html';
        } else {
            alert("Vui lòng chọn một file trước khi import!");
        }
    });
}

const backButton = document.querySelector('.back-btn');
if (backButton) {
    backButton.addEventListener('click', function(event) {
        event.preventDefault();
        window.location.href = 'Plan_list.html';
    });
}

function handleFileSelect(event) {
    const file = event.target.files[0];
    const fileDisplay = document.getElementById('fileDisplay');

    if (file) {
        const validTypes = ['application/vnd.ms-excel', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'];
        if (!validTypes.includes(file.type)) {
            alert("Vui lòng chọn file .xls hoặc .xlsx!");
            event.target.value = '';
            fileDisplay.textContent = 'Attach a file';
            return;
        }
        if (file.size > 500 * 1024 * 1024) {
            alert("File quá lớn! Dung lượng tối đa là 500MB.");
            event.target.value = '';
            fileDisplay.textContent = 'Attach a file';
            return;
        }

        fileDisplay.textContent = file.name;
        localStorage.setItem('importedFileName', file.name);

        const reader = new FileReader();
        reader.readAsBinaryString(file);
        reader.onload = function (e) {
            const data = e.target.result;
            const workbook = XLSX.read(data, { type: 'binary' });
            const sheetName = workbook.SheetNames[0];
            const sheetData = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName]);
            localStorage.setItem('excelData', JSON.stringify(sheetData));
            console.log("Dữ liệu Excel:", sheetData);
        };
    }
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

(async function() {
    let role = await fetchTerms();

    if (role !== 'ADMIN') {
        let homePage = document.querySelector('.homePage');
        let userPage = document.querySelector('.userPage');
        homePage.href = '../user/home.html';
        userPage.style.display = 'none';
    }
})();

document.addEventListener("DOMContentLoaded", function () {
    const termSelect = document.querySelector('#termSelect');
    const monthSelect = document.querySelector('#monthSelect');

    if (termSelect) {
        termSelect.addEventListener('change', function (e) {
            const selectedValue = e.target.value;
            if (selectedValue) {
                localStorage.setItem('selectedTerm', selectedValue);
            } else {
                localStorage.removeItem('selectedTerm');
            }
            if (monthSelect.value) {
                localStorage.setItem('selectedMonth', monthSelect.value);
            }
        });

        const savedTerm = localStorage.getItem('selectedTerm');
        if (savedTerm) {
            termSelect.value = savedTerm;
        }
    }

    if (monthSelect) {
        monthSelect.addEventListener('change', function (e) {
            const selectedValue = e.target.value;
            if (selectedValue) {
                localStorage.setItem('selectedMonth', selectedValue);
            } else {
                localStorage.removeItem('selectedMonth');
            }
            if (termSelect.value) {
                localStorage.setItem('selectedTerm', termSelect.value);
            }
        });

        const savedMonth = localStorage.getItem('selectedMonth');
        if (savedMonth) {
            monthSelect.value = savedMonth;
        }
    }
});

var fullName = localStorage.getItem("full_name");
var role = localStorage.getItem("role");
document.getElementById("full_name").textContent = fullName;
document.getElementById("role").textContent = role;
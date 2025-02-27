function logout() {
    alert("Logged out successfully!");
}

function goBack() {
    window.location.href = 'plan_import_Step_1.html';
}

function submitReport() {
    alert("Report submitted successfully!");
}

document.addEventListener('DOMContentLoaded', function () {
    const fileName = localStorage.getItem('importedFileName');
    if (fileName) document.getElementById('fileValue').textContent = fileName;

    const fileData = localStorage.getItem('excelData');
    if (fileData) {
        const jsonData = JSON.parse(fileData);
        renderTable(jsonData);
    }

    const savedTerm = localStorage.getItem('selectedTerm');

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

    var fullName = localStorage.getItem("full_name");
    var role = localStorage.getItem("role");
    document.getElementById("full_name").textContent = fullName;
    document.getElementById("role").textContent = role;

    const backButton = document.querySelector('.back-btn');
    if (backButton) {
        backButton.addEventListener('click', function(event) {
            event.preventDefault(); // Prevent default form submission
            window.location.href = 'plan_import_Step_1.html';
        });
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

    const headers = Object.keys(data[0]);
    tableHead.innerHTML = "<tr>" + headers.map(col => `<th class="p-2 border bg-gray-300">${col}</th>`).join("") + "</tr>";

    tableBody.innerHTML = data.map(row =>
        `<tr class="table-row">` +
        headers.map((col, index) => {
            let value = row[col] === undefined || row[col] === '' ? 'NULL' : row[col];
            if ((index === 2 || index === 4) && value !== 'NULL') {
                value = Number(value).toLocaleString('en-US'); // Format numbers
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

// Immediately Invoked Async Function Expression (IIFE)
(async function() {
    let role = await fetchTerms();
    if (role !== 'ADMIN') {
        document.querySelector('.homePage').href = '../user/home.html';
        document.querySelector('.userPage').style.display = 'none';
    }
})();
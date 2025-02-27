// Hàm logout
function logout() {
    alert("Đã đăng xuất thành công!");
    localStorage.removeItem('authToken');
    window.location.href = '../user/login.html'; // Chuyển hướng về trang login
}

const urlParams = new URLSearchParams(window.location.search);
const planId = urlParams.get('planId');
if(planId){
    fetch(`http://localhost:8080/api/financialplans/${planId}`)
        .then(response => response.json())
        .then(plan => {
            document.getElementById("PlanName").innerText = plan.planName;
            document.getElementById("TermName").innerText = plan.termId.termName;
            document.getElementById("Department").innerText = plan.uploadedBy.department;
            document.getElementById("Status").innerText = plan.status;
            document.getElementById("username").innerText = plan.uploadedBy.fullName;
            document.getElementById("dueDate").innerText = plan.termId.planDueDate;
            document.getElementById("version").innerText = plan.version;

        })
        .catch(error => console.error("Lỗi khi tải dữ liệu user:", error));
}

document.addEventListener("DOMContentLoaded", function () {
    fetchPlandetailsData();
});
let allPlandetails = []; // Lưu danh sách plandetail lấy từ API
function fetchPlandetailsData() {
    fetch("http://localhost:8080/api/financialplansdetail/view", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((res) => res.json())
        .then((data) => {
            console.log("Dữ liệu API trả về:", data);

            if (Array.isArray(data)) {
                allPlandetails = data.filter(plan => plan.financialPlan.planId == planId)  // Lưu dữ liệu vào biến toàn cục
                displayPlandetail(allPlandetails); // Hiển thị danh sách ban đầu
                const totalSum = calculateTotal(allPlandetails);
                displayTotal(totalSum);
            } else {
                console.error("Lỗi lấy dữ liệu: Dữ liệu API không đúng định dạng");
            }
        })
        .catch((error) => console.error("Lỗi kết nối API:", error));
}
function calculateTotal(planDetails) {
    return planDetails.reduce((sum, detail) => sum + parseFloat(detail.total), 0).toFixed(0);
}
function displayTotal(totalSum) {
    const totalElement = document.getElementById('total-sum');
    totalElement.innerText = `${formatCurrency(totalSum)}`;
}
function formatCurrency(value) {
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.') + ' VND';
}
function displayPlandetail(plans) {
    const tableBody = document.querySelector("#PlansTabledetail tbody");
    tableBody.innerHTML = ""; // Xóa dữ liệu cũ

    const role = localStorage.getItem("role"); // Lấy role từ localStorage

    if (plans.length === 0) {
        const emptyRow = document.createElement("tr");
        emptyRow.innerHTML = `
                <td colspan="7" class="text-center py-4 text-gray-500">
                    Không có dữ liệu nào để hiển thị.
                </td>
            `;
        tableBody.appendChild(emptyRow);
        return; // Không làm gì thêm nếu không có data
    }

    plans.forEach((plan, index) => {
        const row = document.createElement("tr");


        row.innerHTML = `
            <td class="py-2 px-4 border-b text-center">${index + 1}</td>
            <td class="py-2 px-4 border-b text-center">${plan.expense}</td>
            <td class="py-2 px-4 border-b text-center">${plan.costType}</td>
            <td class="py-2 px-4 border-b text-center">${plan.unitPrice}</td>
            <td class="py-2 px-4 border-b text-center">${plan.amount}</td>
            <td class="py-2 px-4 border-b text-center">${plan.total}</td>
            <td class="py-2 px-4 border-b text-center">${plan.projectName || "N/A"}</td>
            <td class="py-2 px-4 border-b text-center">${plan.note || "N/A"}</td>
            <td class="py-2 px-4 border-b text-center">${plan.statusExpense}</td>
        `;

        tableBody.appendChild(row);
    });
}
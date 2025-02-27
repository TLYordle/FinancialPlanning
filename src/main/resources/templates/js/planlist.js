document.addEventListener("DOMContentLoaded", function () {
    fetchPlansData();
});
let allPlans = [];
function fetchPlansData() {
    fetch("http://localhost:8080/api/financialplans/list", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((res) => res.json())
        .then((data) => {
            console.log("Dữ liệu API trả về:", data);

            if (Array.isArray(data)) {
                allPlans = data.filter((plan) => plan.isdeleted === false);  // Lưu dữ liệu vào biến toàn cục
                displayPlans(allPlans); // Hiển thị danh sách ban đầu
                optiontermname(allPlans); // Tự động điền tên vào term search
            } else {
                console.error("Lỗi lấy dữ liệu: Dữ liệu API không đúng định dạng");
            }
        })
        .catch((error) => console.error("Lỗi kết nối API:", error));
}
function optiontermname(plans){
    const optionterms = document.getElementById("optionterm");
    const uniquetermname = [...new Set(plans.map(plan => plan.termId.termName))];

    uniquetermname.forEach(name => {
        const option = document.createElement("option");
        option.value = name;
        option.textContent = name
        optionterms.appendChild(option);
    });
}

function displayPlans(plans) {
    const tableBody = document.querySelector("#PlansTable tbody");
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

        let deleteButtonHTML = "";
        if (role === "STAFF" || role === "ADMIN") {
            deleteButtonHTML = `
                <button class="text-red-500 ml-2" onclick="deletePlan('${plan.planId}')">
                    <i class="fas fa-trash"></i>
                </button>`;
        }

        row.innerHTML = `
            <td class="py-2 px-4 border-b text-center">${index + 1}</td>
            <td class="py-2 px-4 border-b text-center">${plan.planName || "N/A"}</td>
            <td class="py-2 px-4 border-b text-center">${plan.termId.termName}</td>
            <td class="py-2 px-4 border-b text-center">${plan.uploadedBy.department}</td>
            <td class="py-2 px-4 border-b text-center">${plan.status}</td>
            <td class="py-2 px-4 border-b text-center">${plan.version}</td>
            <td class="py-2 px-4 border-b text-center">
                <button class="text-blue-500 ml-2" onclick="viewPlanDetails('${plan.planId}')">
                    <i class="fas fa-eye"></i>
                </button>
                ${deleteButtonHTML} <!-- Nút xoá chỉ hiện nếu là STAFF -->
            </td>
        `;

        tableBody.appendChild(row);
    });
}

// Giả sử hàm này xử lý khi xoá plan (gọi API xoá chẳng hạn)
function deletePlan(planId) {
    if (!confirm("Bạn có chắc chắn muốn xoá kế hoạch này không?")) {
        return; // Người dùng bấm Cancel thì không làm gì cả
    }

    fetch(`http://localhost:8080/api/financialplans/${planId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                alert("Xóa kế hoạch thành công!");
                fetchPlansData(); // Load lại danh sách sau khi xoá
            } else {
                alert("Xóa thất bại. Vui lòng thử lại!");
            }
        })
        .catch((error) => {
            console.error("Lỗi khi gọi API xóa:", error);
            alert("Có lỗi khi kết nối đến server.");
        });
}


// Hàm này có thể dùng để mở chi tiết plan
function viewPlan(planId) {
    // Chuyển hướng hoặc show modal gì đó
    console.log(`Xem chi tiết plan với ID: ${planId}`);
}


function viewPlanDetails(planId) {
    window.location.href = `plandetail.html?planId=${planId}`;
}


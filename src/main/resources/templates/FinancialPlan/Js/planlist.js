document.addEventListener("DOMContentLoaded", function () {
    fetchPlansData();
});
let allPlans = []; // Lưu danh sách user lấy từ API
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
                allPlans = data;  // Lưu dữ liệu vào biến toàn cục
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
    plans.forEach((plan, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
      <td class="py-2 px-4 border-b text-center">${index + 1}</td>
      <td class="py-2 px-4 border-b text-center">${plan.planName || "N/A"}</td>
      <td class="py-2 px-4 border-b text-center">${plan.termId.termName}</td>
      <td class="py-2 px-4 border-b text-center">${plan.uploadedBy.department}</td>
      <td class="py-2 px-4 border-b text-center">${plan.status}</td>
      <td class="py-2 px-4 border-b text-center">${plan.version}</td>
      <td class="py-2 px-4 border-b text-center">
        <button class="text-blue-500 ml-2" onclick="">
            <i class="fas fa-eye"></i>
        </button>
        <button class="text-yellow-500 ml-2" onclick="">
            <i class="fas fa-edit"></i>
        </button>
        <button class="text-red-500 ml-2" onclick="">
            <i class="fas fa-trash"></i>
        </button>
      </td>
    `;
        tableBody.appendChild(row);
    });
}

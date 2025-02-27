document.addEventListener("DOMContentLoaded", function () {
  fetchReportData();
});

let allReports = [];

function fetchReportData() {
  fetch("http://localhost:8080/api/reports", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`HTTP error! status: ${res.status}`);
      }
      return res.json();
    })
    .then((data) => {
      console.log("Dữ liệu API trả về:", data);

      if (Array.isArray(data)) {
        allReports = data;
        displayReports(allReports);
      } else {
        console.error("Lỗi lấy dữ liệu: Dữ liệu API không đúng định dạng");
      }
    })
    .catch((error) => console.error("Lỗi kết nối API:", error));
}

function displayReports(reports) {
  const tableBody = document.querySelector("#reportTable tbody");
    if (!tableBody) {
      console.error("Không tìm thấy tbody trong #reportTable");
      return;
    }
    tableBody.innerHTML = "";

  reports.forEach((rp, index) => {
    const row = document.createElement("tr");

    row.innerHTML = `
      <td class="py-2 px-4 border-b">${index + 1}</td>
      <td class="py-2 px-4 border-b">${rp.reportName || "N/A"}</td>
      <td class="py-2 px-4 border-b">${rp.monthName}</td>
      <td class="py-2 px-4 border-b">${rp.monthName}</td>
      <td class="py-2 px-4 border-b">${rp.monthName}</td>
      <td class="py-2 px-4 border-b">${rp.status}</td>
      <td class="py-2 px-4 border-b">${rp.version}</td>
      <td class="py-2 px-4 border-b">
        <button class="text-blue-500 ml-2" onclick="viewReport(${rp.reportId})">
                    <i class="fas fa-eye"></i>
                  </button>
                  <button class="text-red-500 ml-2" onclick="deleteReport(${rp.reportId})">
                    <i class="fas fa-trash"></i>
                  </button>
      </td>
    `;
    tableBody.appendChild(row);
  });
}

function deleteReport(reportId) {
  if (confirm("Bạn có chắc chắn muốn xóa báo cáo này?")) {
    fetch(`http://localhost:8080/api/reports/${reportId}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (response.ok) {
          alert("Báo cáo đã được xóa thành công!");
          fetchUserData();
        } else {
          throw new Error("Không thể xóa báo cáo. Vui lòng thử lại.");
        }
      })
      .catch((error) => {
        console.error("Lỗi khi xóa báo cáo:", error);
        alert("Có lỗi xảy ra khi xóa báo cáo. Vui lòng kiểm tra console để biết chi tiết.");
      });
  }
}

document.addEventListener("DOMContentLoaded", function () {
  fetchReportData();
});

let allReports = [];

async function fetchReportData() {
  try {
    const res = await fetch("http://localhost:8080/api/reports", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    }
    const data = await res.json();
    console.log("Dữ liệu MonthlyReport:", data);

    if (Array.isArray(data)) {
      allReports = data;
      displayReports(allReports);
    } else {
      console.error("Lỗi lấy dữ liệu: Dữ liệu API không đúng định dạng");
    }
  } catch (error) {
    console.error("Lỗi kết nối API:", error);
  }
}

function displayReports(reports) {
  const tableBody = document.querySelector("#reportTable tbody");
  if (!tableBody) {
    console.error("Không tìm thấy tbody trong #reportTable");
    return;
  }
  tableBody.innerHTML = "";

  reports.forEach((rp, index) => {
    // Lấy termName từ term, nếu không có thì hiển thị "N/A"
    const termName = rp.term && rp.term.termName ? rp.term.termName : "N/A";
    // Lấy department từ user, nếu không có thì hiển thị "N/A"
    const department = rp.user && rp.user.department ? rp.user.department : "N/A";

    const row = document.createElement("tr");
    row.innerHTML = `
      <td class="py-2 px-4 border-b">${index + 1}</td>
      <td class="py-2 px-4 border-b">${rp.reportName || "N/A"}</td>
      <td class="py-2 px-4 border-b">${rp.monthName || "N/A"}</td>
      <td class="py-2 px-4 border-b">${termName}</td>
      <td class="py-2 px-4 border-b">${department}</td>
      <td class="py-2 px-4 border-b">${rp.status || "N/A"}</td>
      <td class="py-2 px-4 border-b">${rp.version || "N/A"}</td>
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
          fetchReportData(); // Cập nhật lại danh sách
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

function viewReport(reportId) {
  // Chuyển hướng đến trang report_details.html với reportId trong URL
    window.location.href = `report_details.html?reportId=${reportId}`;
}
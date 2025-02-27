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
    console.log("Dữ liệu API trả về:", data);

    if (Array.isArray(data)) {
      allReports = data;
      // Lấy thông tin Term và User cho từng báo cáo
      await Promise.all(
        allReports.map(async (report) => {
          report.term = await fetchTerm(report.termId);
          report.user = await fetchUser(report.userId);
        })
      );
      displayReports(allReports);
    } else {
      console.error("Lỗi lấy dữ liệu: Dữ liệu API không đúng định dạng");
    }
  } catch (error) {
    console.error("Lỗi kết nối API:", error);
  }
}

// Hàm lấy thông tin Term theo termId
async function fetchTerm(termId) {
  if (!termId || isNaN(termId)) {
    console.error("termId không hợp lệ:", termId);
    return { termName: "N/A" };
  }
  try {
    const res = await fetch(`http://localhost:8080/terms/${termId}`, { // Sửa thành /terms
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    if (!res.ok) {
      if (res.status === 400) {
        console.error("Lỗi 400 khi lấy Term với ID:", termId);
      } else if (res.status === 404) {
        console.error("Term không tồn tại với ID:", termId);
      }
      throw new Error(`Lỗi khi lấy Term: ${res.status}`);
    }
    return await res.json();
  } catch (error) {
    console.error("Lỗi khi lấy Term:", error);
    return { termName: "N/A" }; // Trả về giá trị mặc định
  }
}
// Hàm lấy thông tin User theo userId
async function fetchUser(userId) {
  try {
    const res = await fetch(`http://localhost:8080/api/users/${userId}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    if (!res.ok) throw new Error(`Không thể lấy User với ID ${userId}`);
    return await res.json();
  } catch (error) {
    console.error("Lỗi khi lấy User:", error);
    return { department: "N/A" }; // Trả về giá trị mặc định nếu lỗi
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
    const row = document.createElement("tr");
    row.innerHTML = `
      <td class="py-2 px-4 border-b">${index + 1}</td>
      <td class="py-2 px-4 border-b">${rp.reportName || "N/A"}</td>
      <td class="py-2 px-4 border-b">${rp.monthName || "N/A"}</td>
      <td class="py-2 px-4 border-b">${rp.term ? rp.term.termName : "N/A"}</td>
      <td class="py-2 px-4 border-b">${rp.user ? rp.user.department : "N/A"}</td>
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
          fetchReportData();
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

// Hàm viewReport (chưa được định nghĩa trong mã gốc, thêm placeholder)
function viewReport(reportId) {
  console.log(`Xem báo cáo với ID: ${reportId}`);
  // Có thể thêm logic để chuyển hướng hoặc hiển thị chi tiết báo cáo
}
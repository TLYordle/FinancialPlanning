document.addEventListener("DOMContentLoaded", function () {
  fetchUserData();
});

function fetchUserData() {
  fetch("http://localhost:8080/api/users", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((res) => res.json())
    .then((data) => {
      console.log("Dữ liệu API trả về:", data);

      if (Array.isArray(data)) {
        displayUsers(data); // Gọi trực tiếp vì API trả về mảng user
      } else {
        console.error("Lỗi lấy dữ liệu: Dữ liệu API không đúng định dạng");
      }
    })
    .catch((error) => console.error("Lỗi kết nối API:", error));
}

function displayUsers(users) {
  const tableBody = document.querySelector("#userTable tbody");
  tableBody.innerHTML = ""; // Xóa dữ liệu cũ

  users.forEach((user, index) => {
    const row = document.createElement("tr");

    row.innerHTML = `
      <td class="py-2 px-4 border-b">${index + 1}</td>
      <td class="py-2 px-4 border-b">${user.fullName || "N/A"}</td>
      <td class="py-2 px-4 border-b">${user.email}</td>
      <td class="py-2 px-4 border-b">${user.department}</td>
      <td class="py-2 px-4 border-b">${user.position}</td>
      <td class="py-2 px-4 border-b">
        <button class="text-blue-500 ml-2" onclick="viewUserDetails('${user.user_id}')">
            <i class="fas fa-eye"></i>
        </button>
        <button class="text-yellow-500 ml-2" onclick="redirectToEditUser('${user.user_id}')">
            <i class="fas fa-edit"></i>
        </button>
        <button class="text-red-500 ml-2" onclick="confirmDeleteUser('${user.user_id}')">
            <i class="fas fa-trash"></i>
        </button>
      </td>
    `;

    tableBody.appendChild(row);
  });
}

function redirectToEditUser(userId) {
    // Điều hướng sang trang edit_user.html với user_id trên URL
    window.location.href = `edit_user.html?user_id=${userId}`;
}

function ToEditUser() {
    const userId = document.getElementById("detailUserId").value; // Lấy user_id từ input hidden

    if (userId) {
        window.location.href = `edit_user.html?user_id=${userId}`; // Điều hướng kèm user_id
    } else {
        alert("Không tìm thấy ID người dùng!");
    }
}



// Xác nhận xóa user
function confirmDeleteUser(userId) {
  const confirmDelete = confirm("Bạn chắc chắn muốn xóa user này?");
  if (confirmDelete) {
    deleteUser(userId);
  }
}

// Gửi yêu cầu xóa user
async function deleteUser(userId) {
  const response = await fetch(`http://localhost:8080/api/users/${userId}`, {
    method: "DELETE",
    headers: {},
  });

  const data = await response.json();
  console.log("Response:", data);

  if (response.ok) {
    alert("Xóa user thành công!");
    location.reload(); // Tải lại trang sau khi xóa
  } else {
    alert("Lỗi khi xóa user!");
  }
}

async function viewUserDetails(userId) {
  try {
    // Gọi API để lấy thông tin chi tiết user
    const response = await fetch(`http://localhost:8080/api/users/${userId}`);
    const user = await response.json();

    if (!user || response.status !== 200) {
      alert("Không tìm thấy thông tin người dùng!");
      return;
    }

    // Hiển thị thông tin user vào modal
    document.getElementById("detailUserId").value = user.user_id || "";
    document.getElementById("detailFullName").innerText = user.fullName || "N/A";
    document.getElementById("detailEmail").innerText = user.email || "N/A";
    document.getElementById("detailPhoneNumber").innerText = user.phoneNumber || "N/A";
    document.getElementById("detailAddress").innerText = user.address || "N/A";
    document.getElementById("detailBirthday").innerText = user.birthday || "N/A";
    document.getElementById("detailDepartment").innerText = user.department || "N/A";
    document.getElementById("detailPosition").innerText = user.position || "N/A";
    document.getElementById("detailRole").innerText = user.role || "N/A";
    document.getElementById("detailStatus").innerText = user.isActive ? "Active" : "Inactive";

    // Mở modal hiển thị thông tin
    document.getElementById("userDetailModal").classList.remove("hidden");
  } catch (error) {
    console.error("Lỗi khi lấy thông tin user:", error);
    alert("Lỗi khi tải dữ liệu!");
  }
}

// Hàm đóng modal
function closeUserDetailModal() {
  document.getElementById("userDetailModal").classList.add("hidden");
}


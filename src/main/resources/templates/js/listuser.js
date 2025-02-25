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


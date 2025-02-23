document.addEventListener("DOMContentLoaded", function () {
  const token = localStorage.getItem("token");
  if (!token) {
    alert("Bạn cần đăng nhập!");
    window.location.href = "login.html"; // Chuyển hướng về trang đăng nhập nếu chưa có token
    return;
  }

  fetchUserData(token);
});

function fetchUserData(token) {
  fetch("http://localhost:8080/api/users/active", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  })
    .then((res) => res.json())
    .then((data) => {
        console.log(data);
      if (data.code === 200) {
        displayUsers(data.result);
      } else {
        console.error("Lỗi lấy dữ liệu:", data.message);
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
      <td class="py-2 px-4 border-b">${user.full_name || "N/A"}</td>
      <td class="py-2 px-4 border-b">${user.email}</td>
      <td class="py-2 px-4 border-b">${user.roles.length > 0 ? user.roles[0].description : "N/A"}</td>
      <td class="py-2 px-4 border-b">${user.createdAt ? new Date(user.createdAt).toLocaleString() : "N/A"}</td>
      <td class="py-2 px-4 border-b">
        <button class="text-yellow-500 ml-2" onclick="showEditForm('${user.id}', '${user.full_name}', '${user.roles[0]?.name}')">
            <i class="fas fa-edit"></i>
        </button>
        <button class="text-red-500 ml-2" onclick="confirmDeleteUser('${user.id}')">
            <i class="fas fa-trash"></i>
        </button>
      </td>
    `;

    tableBody.appendChild(row);
  });
}

// Hiển thị modal chỉnh sửa user
function showEditForm(userId, fullName, role) {
    document.getElementById("editUserId").value = userId;
    document.getElementById("editFullName").value = fullName;
    document.getElementById("editRole").value = role;
    document.getElementById("editUserModal").classList.remove("hidden");
}

// Đóng modal
function closeEditModal() {
    document.getElementById("editUserModal").classList.add("hidden");
}
function renderUserTable(users) {
    const userTableBody = document.getElementById("userTable").getElementsByTagName("tbody")[0];
    userTableBody.innerHTML = "";

    users.forEach((user) => {
        const row = `
            <tr>
                <td class="py-2 px-4 border-b">${user.id}</td>
                <td class="py-2 px-4 border-b">${user.full_name}</td>
                <td class="py-2 px-4 border-b">${user.email}</td>
                <td class="py-2 px-4 border-b">${user.roles[0]?.name || "N/A"}</td>
                <td class="py-2 px-4 border-b">${new Date(user.createdAt).toLocaleString()}</td>
                <td class="py-2 px-4 border-b">
                    <button class="text-yellow-500 ml-2" onclick="showEditForm('${user.id}', '${user.full_name}', '${user.roles[0]?.name || ""}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="text-red-500 ml-2" onclick="confirmDeleteUser('${user.id}')">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `;
        userTableBody.innerHTML += row;
    });
}
document.getElementById("editUserForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const token = localStorage.getItem("token");
    if (!token) {
        alert("Bạn cần đăng nhập!");
        return;
    }

    const userId = document.getElementById("editUserId").value;
    const fullName = document.getElementById("editFullName").value;
    const role = document.getElementById("editRole").value;

    try {
        const response = await fetch(`http://localhost:8080/api/users/${userId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify({
                full_name: fullName,
                roles: [role],
            }),
        });

        const data = await response.json();
        if (data.code === 200) {
            alert("Cập nhật user thành công!");
            closeEditModal();
            fetchUserData(); // Cập nhật lại bảng user
            location.reload(); // Tải lại trang
        } else {
            alert("Lỗi: " + data.message);
        }
    } catch (error) {
        console.error("Lỗi kết nối API:", error);
    }
});

function confirmDeleteUser(userId) {
  const confirmDelete = confirm("Bạn chắc chắn muốn xóa user này?");
  if (confirmDelete) {
    deleteUser(userId);
  }
}

async function deleteUser(userId) {
  const token = localStorage.getItem("token");

  const response = await fetch(`http://localhost:8080/api/users/${userId}`, {
    method: "DELETE",
    headers: {
      "Authorization": `Bearer ${token}`
    }
  });
  const data = await response.json(); // Lấy dữ liệu phản hồi từ API
  console.log("Response:", data); // Kiểm tra phản hồi từ API

  if (response.ok) {
    alert("Xóa user thành công!");
    location.reload(); // Tải lại trang sau khi xóa
  } else {
    alert("Lỗi khi xóa user!");
  }
}



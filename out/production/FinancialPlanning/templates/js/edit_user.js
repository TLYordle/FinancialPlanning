// Lấy user_id từ URL
const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get("user_id");

// Nếu userId tồn tại thì gọi API để lấy thông tin user
if (userId) {
  fetch(`http://localhost:8080/api/users/${userId}`)
    .then(response => response.json())
    .then(user => {
      document.getElementById("editUserId").value = user.user_id;
      document.getElementById("editFullName").value = user.fullName;
      document.getElementById("editEmail").value = user.email;
      document.getElementById("editPhoneNumber").value = user.phoneNumber;
      document.getElementById("editAddress").value = user.address;
      document.getElementById("editDOB").value = user.birthday;
      document.getElementById("editDepartment").value = user.department;
      document.getElementById("editPosition").value = user.position;
      document.getElementById("editRole").value = user.role;
    })
    .catch(error => console.error("Lỗi khi tải dữ liệu user:", error));
}

// Xử lý cập nhật user khi submit form
document.getElementById("editUserForm").addEventListener("submit", async function (event) {
  event.preventDefault();

  const updatedUser = {
    fullName: document.getElementById("editFullName").value,
    email: document.getElementById("editEmail").value,
    phoneNumber: document.getElementById("editPhoneNumber").value,
    address: document.getElementById("editAddress").value,
    birthday: document.getElementById("editDOB").value,
    department: document.getElementById("editDepartment").value,
    position: document.getElementById("editPosition").value,
    role: document.getElementById("editRole").value
  };

  try {
    const response = await fetch(`http://localhost:8080/api/users/${userId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(updatedUser)
    });

    if (response.ok) {
      alert("Cập nhật user thành công!");
      window.location.href = "list_user.html"; // Quay lại danh sách user
    } else {
      alert("Cập nhật thất bại!");
    }
  } catch (error) {
    console.error("Lỗi khi cập nhật user:", error);
  }
});

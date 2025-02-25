// Lấy user_id từ URL
const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get("user_id");

// Nếu userId tồn tại thì gọi API để lấy thông tin user
if (userId) {
  fetch(`http://localhost:8080/api/users/${userId}`)
    .then(response => response.json())
    .then(user => {
      document.getElementById("viewUserId").value = user.user_id;
      document.getElementById("viewFullName").value = user.fullName;
      document.getElementById("viewEmail").value = user.email;
      document.getElementById("viewPhoneNumber").value = user.phoneNumber;
      document.getElementById("viewAddress").value = user.address;
      document.getElementById("viewDOB").value = user.birthday;
      document.getElementById("viewDepartment").value = user.department;
      document.getElementById("viewPosition").value = user.position;
      document.getElementById("viewRole").value = user.role;
    })
    .catch(error => console.error("Lỗi khi tải dữ liệu user:", error));
}

document.getElementById("editBtn").addEventListener("click", function () {
    const userId = document.getElementById("viewUserId").value;

    if (userId) {
        window.location.href = `edit_user.html?user_id=${userId}`;
    } else {
        alert("Không tìm thấy ID người dùng!");
    }
});


document.addEventListener("DOMContentLoaded", async function () {
    // Giả lập fetch dữ liệu từ API
    const response = await fetch(`http://localhost:8080/api/users/${userId}`);
    const user = await response.json();

    if (user) {
        document.getElementById("viewUserId").value = user.user_id || "";
        document.getElementById("viewFullName").value = user.fullName || "N/A";
        document.getElementById("viewEmail").value = user.email || "N/A";

        // Cập nhật nút trạng thái
        const statusBtn = document.getElementById("statusBtn");
        if (user.isActive == 1) {
            statusBtn.textContent = "De-active";
            statusBtn.classList.add("btn-danger");
        } else {
            statusBtn.textContent = "Active";
            statusBtn.classList.add("btn-success");
        }

        // Xử lý khi click vào nút Active/De-active
        statusBtn.addEventListener("click", async function () {
            const newStatus = user.isActive == 1 ? 0 : 1;

            // Cập nhật API theo đúng đường dẫn có sẵn
            const updateResponse = await fetch(`http://localhost:8080/api/users/${userId}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    ...user,  // Giữ nguyên dữ liệu user
                    isActive: newStatus  // Chỉ thay đổi trạng thái
                }),
            });

            if (updateResponse.ok) {
                alert("Cập nhật trạng thái thành công!");
                window.location.reload();
            } else {
                alert("Lỗi khi cập nhật trạng thái!");
            }
        });

    } else {
        alert("Không tìm thấy thông tin người dùng!");
    }
});

async function createUser(event) {
    event.preventDefault(); // Ngăn reload trang

    const token = localStorage.getItem("token");
    if (!token) {
        alert("Bạn cần đăng nhập!");
        return;
    }

    const fullName = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("http://localhost:8080/api/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify({
                full_name: fullName,
                email: email,
                password: password,
            }),
        });

        const data = await response.json();
        if (data.code === 200) {
            alert("Thêm user thành công!");
            document.getElementById("createUserForm").reset(); // Xóa dữ liệu form sau khi thêm thành công
            fetchUserData(); // Gọi lại API để cập nhật bảng user
        } else {
            alert("Lỗi: " + data.message);
        }
    } catch (error) {
        console.error("Lỗi kết nối API:", error);
    }
}
document.getElementById("createUserForm").addEventListener("submit", createUser);
async function fetchUserData() {
    const token = localStorage.getItem("token");
    if (!token) {
        alert("Bạn cần đăng nhập!");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/users", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        });

        const data = await response.json();
        if (data.code === 200) {
            const userTableBody = document.getElementById("userTableBody");
            userTableBody.innerHTML = ""; // Xóa dữ liệu cũ

            data.result.forEach((user, index) => {
                const row = `
                    <tr>
                        <td class="py-2 px-4 border-b">${index + 1}</td>
                        <td class="py-2 px-4 border-b">${user.full_name || "N/A"}</td>
                        <td class="py-2 px-4 border-b">${user.email}</td>
                        <td class="py-2 px-4 border-b">${user.roles[0]?.description || "N/A"}</td>
                        <td class="py-2 px-4 border-b">${user.createdAt ? new Date(user.createdAt).toLocaleString() : "N/A"}</td>
                        <td class="py-2 px-4 border-b">
                            <button class="text-blue-500"><i class="fas fa-eye"></i></button>
                            <button class="text-yellow-500 ml-2"><i class="fas fa-edit"></i></button>
                            <button class="text-red-500 ml-2"><i class="fas fa-trash"></i></button>
                        </td>
                    </tr>
                `;
                userTableBody.innerHTML += row;
            });
        } else {
            alert("Lỗi: " + data.message);
        }
    } catch (error) {
        console.error("Lỗi kết nối API:", error);
    }
}

// Gọi API lấy danh sách user ngay khi trang load
document.addEventListener("DOMContentLoaded", fetchUserData);

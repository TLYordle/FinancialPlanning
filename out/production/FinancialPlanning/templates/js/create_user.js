document.getElementById("createUserForm").addEventListener("submit", async function(event) {
    event.preventDefault(); // Ngăn reload trang

    const userData = {
        fullName: document.getElementById("fullName").value,
        email: document.getElementById("email").value,
        password: "123456",  // Nếu có trường nhập password thì thay bằng giá trị nhập
        address: document.getElementById("address").value,
        birthday: convertDateFormat(document.getElementById("dob").value),
        phoneNumber: document.getElementById("phoneNumber").value,
        department: document.getElementById("department").value,
        position: document.getElementById("position").value,
        role: document.getElementById("roles").value,
        isActive: 1
    };

    console.log("Dữ liệu gửi lên:", JSON.stringify(userData)); // Kiểm tra dữ liệu

    try {
        const response = await fetch("http://localhost:8080/api/users/create", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(userData)
        });

        const data = await response.json();
        console.log("Kết quả API:", data);

        if (response.ok) {
            alert("Thêm user thành công!");
            document.getElementById("createUserForm").reset();
        } else {
            // Kiểm tra nếu lỗi là email đã tồn tại
            if (data.message && data.message.includes("Email already exists")) {
                alert("Email đã tồn tại, vui lòng chọn email khác!");
            } else {
                alert("Lỗi: " + data.message);
            }
        }
    } catch (error) {
        console.error("Lỗi kết nối API:", error);
    }
});


// Xử lý nút Cancel (xóa dữ liệu form)
document.getElementById("cancelBtn").addEventListener("click", function () {
    document.getElementById("createUserForm").reset();
});

// Hàm chuyển đổi ngày từ MM/dd/yyyy -> yyyy-MM-dd
function convertDateFormat(dateString) {
    const dateParts = dateString.split("-"); // Input từ input type="date" là yyyy-MM-dd
    if (dateParts.length === 3) {
        return `${dateParts[0]}-${dateParts[1]}-${dateParts[2]}`; // Giữ nguyên yyyy-MM-dd
    }
    return ""; // Trả về rỗng nếu sai định dạng
}

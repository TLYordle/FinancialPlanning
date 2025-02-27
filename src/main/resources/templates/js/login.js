async function login(event) {
    event.preventDefault();

    try {
        const response = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email: document.getElementById("email").value,
                password: document.getElementById("password").value,
            }),
        });

        const dt = await response.json();
        if (!response.ok) throw new Error(dt.message || "Đăng nhập thất bại");

        const token = dt.result.token;
        localStorage.setItem("token", token);

        // Giải mã token để lấy thông tin
        const payloadBase64 = token.split(".")[1];
        const decodedPayload = JSON.parse(decodeURIComponent(escape(atob(payloadBase64))));

        const fullName = decodedPayload.full_name;
        const roles = decodedPayload.scope;
        const department = decodedPayload.department;
        const user_id = decodedPayload.sub;

        localStorage.setItem("department", department);
        localStorage.setItem("full_name", fullName);
        localStorage.setItem("user_id", user_id)

        if (roles.includes("ADMIN")) {
            localStorage.setItem("role", "ADMIN");
            window.location.href = "../Admin/home_admin.html";
        } else if (roles.includes("STAFF")) {
            localStorage.setItem("role", "STAFF");
            window.location.href = "../user/home.html";
        } else {
            localStorage.setItem("role", "ACCOUNTANT");
            window.location.href = "../user/home.html";
        }
    } catch (error) {
        alert(error.message);
        console.error("Lỗi đăng nhập:", error.message);
    }
}

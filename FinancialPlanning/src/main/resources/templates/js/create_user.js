document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("cancelBtn").addEventListener("click", function () {
        window.location.href = "/FinancialPlanning/templates/list_user.html";
    });
    
    document.getElementById("createUserForm").addEventListener("submit", async function (event) {
        event.preventDefault();

        const user = {
            userName: document.getElementById("username").value,
            fullName: document.getElementById("fullName").value,
            email: document.getElementById("email").value,
            phoneNumber: document.getElementById("phoneNumber").value,
            dob: document.getElementById("dob").value,
            address: document.getElementById("address").value,
            department: document.getElementById("department").value,
            position: document.getElementById("position").value,
            password: "default123", // Mật khẩu mặc định
            role: document.getElementById("role").value,
            status: document.getElementById("status").value,
            note: document.getElementById("note").value
        };

        try {
            const response = await fetch("http://localhost:8080/users", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(user)
            });

            if (response.ok) {
                alert("User added successfully!");
                window.location.href = "/FinancialPlanning/templates/list_user.html";
            } else {
                const errorData = await response.json();
                alert("Error adding user: " + errorData.message);
            }
        } catch (error) {
            console.error("Error:", error);
            alert("Failed to connect to the server.");
        }
    });
});

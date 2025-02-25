document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("forgot-password-form");
    const emailInput = document.getElementById("email");
    const message = document.getElementById("message");
    const cancelButton = document.getElementById("cancel-button");

    if (!form || !emailInput || !message || !cancelButton) {
        console.error("Form elements not found!");
        return;
    }

    form.addEventListener("submit", async function (event) {
        event.preventDefault();

        const email = emailInput.value;

        try {
            const response = await fetch("http://localhost:8080/api/auth/forgot-password", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ email })
            });

            const result = await response.json(); // Đọc JSON từ phản hồi

            if (response.ok && result.code === 200) {
                message.textContent = result.message; // Hiển thị thông báo từ API
                message.classList.remove("text-red-600");
                message.classList.add("text-green-600");
            } else {
                message.textContent = result.message || "Failed to send reset link. Please try again.";
                message.classList.remove("text-green-600");
                message.classList.add("text-red-600");
            }
        } catch (error) {
            message.textContent = "An error occurred. Please try again later.";
            message.classList.remove("text-green-600");
            message.classList.add("text-red-600");
        }
    });

    cancelButton.addEventListener("click", function () {
        form.reset();
        message.textContent = "";
    });
});

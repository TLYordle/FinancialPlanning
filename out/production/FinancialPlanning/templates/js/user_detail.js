document.addEventListener("DOMContentLoaded", function () {
    const statusEl = document.getElementById("status");
    const activateBtn = document.getElementById("activate-btn");
    const cancelBtn = document.getElementById("cancel-btn");

    function toggleStatus() {
        const isActive = statusEl.classList.contains("active");
        const confirmation = confirm(
            `Are you sure you want to ${isActive ? "deactivate" : "activate"} this user?`
        );

        if (confirmation) {
            if (isActive) {
                statusEl.classList.remove("active");
                statusEl.classList.add("inactive");
                statusEl.innerText = "Inactive";
                activateBtn.innerText = "Activate User";
                activateBtn.classList.replace("btn-danger", "btn-success");
            } else {
                statusEl.classList.remove("inactive");
                statusEl.classList.add("active");
                statusEl.innerText = "Active";
                activateBtn.innerText = "Deactivate User";
                activateBtn.classList.replace("btn-success", "btn-danger");
            }
        }
    }

    function goBack() {
        window.history.back();
    }

    activateBtn.addEventListener("click", toggleStatus);
    cancelBtn.addEventListener("click", goBack);
});

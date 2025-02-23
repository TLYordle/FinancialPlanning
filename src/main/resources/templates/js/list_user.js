document.addEventListener("DOMContentLoaded", function () {
    fetch("/users")
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector("tbody");
            const paginationContainer = document.querySelector("#pagination");
            tableBody.innerHTML = "";
            paginationContainer.innerHTML = "";

            const itemsPerPage = 5;
            let currentPage = 1;
            const pageCount = Math.ceil(data.length / itemsPerPage);

            function renderTable(page) {
                tableBody.innerHTML = "";
                let start = (page - 1) * itemsPerPage;
                let end = start + itemsPerPage;
                let paginatedItems = data.slice(start, end);

                paginatedItems.forEach(user => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${user.userID}</td>
                        <td>${user.userName}</td>
                        <td>${user.email}</td>
                        <td>${user.department}</td>
                        <td>${user.position}</td>
                        <td>
                            <button class="text-blue-500"><a href="/FinancialPlanning/templates/user_detail.html"><i class="fas fa-eye"></i></a></button>
                            <button class="text-yellow-500 ml-2"><i class="fas fa-edit"></i></button>
                            <button class="text-red-500 ml-2"><i class="fas fa-trash"></i></button>
                        </td>
                    `;
                    tableBody.appendChild(row);
                });
            }

            function renderPagination() {
                paginationContainer.innerHTML = `
                    <button id="prevPage" class="pagination-arrow" ${currentPage === 1 ? "disabled" : ""}>
                        <i class="fas fa-arrow-left"></i>
                    </button>
                    <button id="nextPage" class="pagination-arrow" ${currentPage === pageCount ? "disabled" : ""}>
                        <i class="fas fa-arrow-right"></i>
                    </button>
                `;

                document.querySelector("#prevPage").addEventListener("click", function () {
                    if (currentPage > 1) {
                        currentPage--;
                        renderTable(currentPage);
                        renderPagination();
                    }
                });
                
                document.querySelector("#nextPage").addEventListener("click", function () {
                    if (currentPage < pageCount) {
                        currentPage++;
                        renderTable(currentPage);
                        renderPagination();
                    }
                });
            }

            renderTable(currentPage);
            renderPagination();
        })
        .catch(error => console.error("Error fetching users:", error));
});

// Thêm CSS để làm đẹp phân trang
const style = document.createElement("style");
style.innerHTML = `
    .icon-btn {
        border: none;
        background: transparent;
        cursor: pointer;
        font-size: 1.2rem;
        margin: 0 5px;
        transition: transform 0.2s, color 0.3s;
    }
    .view-btn { color: #28a745; }
    .edit-btn { color: #ffc107; }
    .delete-btn { color: #dc3545; }
    .icon-btn:hover {
        transform: scale(1.2);
    }
    table {
        width: 100%;
        border-collapse: collapse;
    }
    th, td {
        padding: 10px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }
    th {
        background-color: #f4f4f4;
    }
    #pagination {
        display: flex;
        justify-content: center;
        margin-top: 20px;
    }
    .pagination-arrow {
        border: 1px solid #ddd;
        padding: 5px 10px;
        margin: 0 10px;
        cursor: pointer;
        background-color: white;
        transition: background 0.3s;
    }
    .pagination-arrow:disabled {
        cursor: not-allowed;
        opacity: 0.5;
    }
    .pagination-arrow:hover:not(:disabled) {
        background-color: #007bff;
        color: white;
    }
`;
document.head.appendChild(style);

// Thêm div chứa phân trang vào HTML
const paginationDiv = document.createElement("div");
paginationDiv.id = "pagination";
document.body.appendChild(paginationDiv);

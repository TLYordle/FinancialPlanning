document.getElementById('bimport').addEventListener('click', function() {
    window.location.href = 'Plan_import_step_2.html';
});

// lấy Dữ liệu của term
document.addEventListener("DOMContentLoaded", function () {
    fetchTermData();
});

let allterm = [];
function fetchTermData(){
    fetch("http://localhost:8080/api/terms",{
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((res) => res.json())
        .then((data) => {
            console.log("Dữ liệu API trả về:", data);

            if (Array.isArray(data)) {
                allterm = data;  // Lưu dữ liệu vào biến toàn cục
                optiontermname(allterm); // Tự động điền tên vào term search
            } else {
                console.error("Lỗi lấy dữ liệu: Dữ liệu API không đúng định dạng");
            }
        })
        .catch((error) => console.error("Lỗi kết nối API:", error));
}
function optiontermname(terms){
    const optionterms = document.getElementById("term");
    const uniquetermname = [...new Set(terms.map(term => term.termName))];

    uniquetermname.forEach(name => {
        const option = document.createElement("option");
        option.value = name;
        option.textContent = name
        optionterms.appendChild(option);
    });
}
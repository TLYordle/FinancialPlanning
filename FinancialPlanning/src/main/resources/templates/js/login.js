async function login(event) {
event.preventDefault();
  fetch(`http://localhost:8080/api/auth/token`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      email: document.getElementById("email").value,
      password: document.getElementById("password").value,
    }),
  })
    .then((res) => res.json())
    .then((dt) => {
      console.log(dt);
      localStorage.setItem("token", dt.result.token);
      fetch(`http://localhost:8080/api/users/my-info`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
      }).then((res) =>  res.json())
      .then((dt) => {
        localStorage.setItem("full_name", dt.result.full_name)
        var Role = dt.result.roles;
        console.log(Role);
        Role.forEach(element => {
          var role = element.name;
          localStorage.setItem("role", role)
          console.log(role);
          if(role === "ADMIN"){
            window.location.href = "home_admin.html";
          }else{
            window.location.href = "home.html"
          }
        });
      })
    });
}
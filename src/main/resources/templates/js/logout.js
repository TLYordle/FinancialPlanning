async function logout() {
      fetch(`http://localhost:8080/api/auth/logout`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
            token: `${localStorage.getItem("token")}`
        }),
      })
        .then((res) => res.json())
        .then((dt) => {
          console.log(dt);
          if(dt.message === "Success"){
            window.location.href = "../login.html"
          }
        });
    }
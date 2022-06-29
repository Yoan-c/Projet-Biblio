let PATH = "http://localhost:8081/";

function ConnectApi(mail, password, divErr){
    fetch('http://localhost:8081/connexion', {
          method: 'post',
          body: 'username='+mail+'&password='+password,
          headers: { 'Content-type': 'application/x-www-form-urlencoded'},
          credentials: 'include'
        })
        .then(res => res.json())
        .then(data => {

         // console.log("res script " + res.ok)
          console.log("res script " + data[0].response)
          if (data[0].response === "sucess")
            document.forms["form_connect"].submit();
          else {
            divErr.textContent="Erreur : mail / mot de passe invalide"
          }
        })
        .catch(err => {
          console.log("res script  err " + err)
        })
  }

//let PATH = "http://localhost:8080/json/";

window.onload = () => {
  /* form connexion */
  let btnConnect = document.getElementById("btnFormConnect");
  if (btnConnect) {
    btnConnect.addEventListener('click', e => {

      let formConnect = document.getElementById("form_connect");
      e.preventDefault();
      let mail = document.getElementById("mail").value;
      let password = document.getElementById("password").value;
      if (!mail || !password)
        console.log("erreur contenu")
      else {
        formConnect.submit();
      }
    })
  }

}

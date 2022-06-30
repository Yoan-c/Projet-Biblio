window.onload = () => {
    /* form connexion */
    let btnConnect = document.getElementById("btnFormConnect");
    let divErr = document.getElementById("err_connect");
    if (btnConnect) {
        btnConnect.addEventListener('click', e => {
            e.preventDefault();
            let mail = document.getElementById("mail").value;
            let password = document.getElementById("password").value;
            if (!mail || !password) {
                divErr.textContent = "Erreur : mail / mot de passe invalide"
            }
            else {
                divErr.textContent = ""
                ConnectApi(mail, password, divErr);
            }
        })
    }
}

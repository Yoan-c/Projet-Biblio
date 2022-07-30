function createUser() {
    let prenom = document.getElementById("firstName").value;
    let nom = document.getElementById("lastName").value;
    let mail = document.getElementById("mail_create").value;
    let mdpConfirm = document.getElementById("password_confirm").value;
    let mdp = document.getElementById("password_create").value;

    let data = {
        "nom": nom,
        "prenom": prenom,
        "mdp": mdp,
        "email": mail,
        "confirmMdp": mdpConfirm

    }

    fetch(PATH + "create", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(res => res.json())
        .then(data => {
            let msg = document.getElementById("msg_creat")
            if (data[0].response === "success")
                msg.textContent = "Le compte a été crée avec un succes";
            else {
                msg.textContent = "Une erreur est survenu lors de la creation du compte"
            }
        })
        .catch(err => {
            getConnectionPage()
        })
}
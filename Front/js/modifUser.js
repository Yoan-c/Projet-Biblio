window.onload = () => {
    fetch(PATH + "info?token=" + getCookie("token"), {
        method: 'GET',
    })
        .then(res => res.json())
        .then(data => {
            FillInfoUserModif(data)
        })
        .catch(err => {
            getConnectionPage()
        })
}
let btnForm = document.getElementById("btnValidModif");
btnForm.addEventListener('click', (e) => {
    e.preventDefault();
    setmessageErrorReset("errorModifMsg");
    setmessageErrorReset("resModifSuccess");
    setmessageErrorReset("resModifError");
    let nom = document.getElementById("lastNameModif").value;
    let prenom = document.getElementById("firstNameModif").value;
    let email = document.getElementById("mailModif").value;
    let mdpModif = document.getElementById("mdpModif").value;
    let mdpConfirm = document.getElementById("mdpConfirmModif").value;

    if (nom == "" || prenom == "" || email == "") {
        setmessageErrorModif("Veuillez vérifier les champs");
        return;
    }
    else if (!ValidateEmail(email)) {
        setmessageErrorModif("Veuillez vérifier votre adresse mail");
        return;
    }
    else if (mdpModif !== mdpConfirm || mdpConfirm == "" || mdpModif == "") {
        setmessageErrorModif("Les mots de passe doivent être identique");
        return;
    }
    else
        updateUser(nom, prenom, email, mdpModif, mdpConfirm)
})

function FillInfoUserModif(data) {
    document.getElementById("lastNameModif").value = data.nom;
    document.getElementById("firstNameModif").value = data.prenom;
    document.getElementById("mailModif").value = data.email;
}

function updateUser(nom, prenom, mail, mdp, mdpConfirm) {
    let data = {
        "nom": nom,
        "prenom": prenom,
        "mdp": mdp,
        "mail": mail,
        "confirmMdp": mdpConfirm,
        "token": getCookie("token")

    }

    fetch(PATH + "update", {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    })
        .then(res => res.json())
        .then(data => {
            if (data.result.indexOf("Erreur") > -1)
                setmessageErrorModif(data)
            else {
                setCookie("token", data.token)
                document.location.href = "./modifUser.html";
            }

        })
        .catch(err => {
            getConnectionPage()
        })
}
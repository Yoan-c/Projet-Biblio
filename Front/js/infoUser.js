window.onload = () => {
    fetch(PATH + "info", {
        method: 'GET',
        credentials: 'include',
    })
        .then(res => res.json())
        .then(data => {
            console.log("infoUser " + data.prenom)
            FillInfoUser(data)
        })
        .catch(err => {
            console.log("err InfoUser " + err);
            getConnectionPage()
        })
}

function FillInfoUser(data) {
    document.getElementById("nameId").textContent = data.nom;
    document.getElementById("prenomId").textContent = data.prenom;
    document.getElementById("mailId").textContent = data.email;
}
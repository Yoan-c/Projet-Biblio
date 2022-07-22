window.onload = () => {
    fetch(PATH + "info", {
        method: 'GET',
        credentials: 'include',
    })
        .then(res => res.json())
        .then(data => {
            FillInfoUser(data)
        })
        .catch(err => {
            getConnectionPage()
        })
}

function FillInfoUser(data) {
    document.getElementById("nameId").textContent = data.nom;
    document.getElementById("prenomId").textContent = data.prenom;
    document.getElementById("mailId").textContent = data.email;
}
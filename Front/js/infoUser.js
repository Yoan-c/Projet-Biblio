window.onload = () => {
    fetch(PATH + "info?token=" + getCookie("token"), {
        method: 'GET',
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
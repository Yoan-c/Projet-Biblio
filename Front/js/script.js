let PATH = "http://localhost:8081/";

function ConnectApi(mail, password, divErr) {
  fetch(PATH + 'connexion', {
    method: 'post',
    body: 'username=' + mail + '&password=' + password,
    headers: {
      'Content-type': 'application/x-www-form-urlencoded',
    },
    credentials: 'include'
  })
    .then(res => res.json())
    .then(data => {

      // console.log("res script " + res.ok)
      console.log("res script " + data[0].response)
      if (data[0].response === "sucess")
        // document.forms["form_connect"].submit();
        document.location.href = "./book.html"
      else {
        divErr.textContent = "Erreur : mail / mot de passe invalide"
      }
    })
    .catch(err => {
      console.log("res script  err " + err)
    })
}
function DeconnectApi() {
  fetch(PATH + "deconnexion", {
    method: 'GET',
    credentials: 'include'
  })
    .finally(() => {
      setTimeout(() => {
        document.location.href = "./connexion.html"
      }, 4000);
    })

}

/*
Fonction a modifier et mettre en place pour la reservation de livre
function reserve() {
  // let msgModal = document.getElementById("msg_bottom_modal");
  //msgModal.innerText = "En l'attente du développement de la réservation en ligne, veuillez contacter la bibliothèque afin d'emprunter ce livre"
  let dataDispo = document.getElementById("idDispo").textContent;
  let dataIsbn = document.getElementById("idISBN").textContent;

  let nb = parseInt(dataDispo.split(":")[1].trim());
  let isbn = parseInt(dataIsbn.split(":")[1].trim());

  if (isNaN(nb) || isNaN(isbn)) {
    console.log("Afficher une erreur");
  }
  else if (nb <= 0) {
    console.log("plus de stock disponible")
  }
  else {
    fetch(PATH + "pret", {
      method: 'PUT',
      credentials: 'include'
    })
      .then(data => {
        console.log("data" + data);
      })
  }

}
*/
function getSearch(data) {
  let titre = document.getElementById("search_title").value;
  let auteur = document.getElementById("search_auteur").value;
  let genre = document.getElementById("search_genre").value;
  let langue = document.getElementById("search_langue").value;

  fetch(PATH + "search?titre=" + titre + "&auteur=" + auteur + "&genre=" + genre + "&langue=" + langue, {
    method: 'GET',
    credentials: 'include',
  })
    .then(resp => resp.json())
    .then((data) => {

      createDom(data);
    })
    .catch(err => {
      console.log("err search " + err)
    })
}
let PATH = "http://localhost:81/";
//let PATH = "https://bibliostudi.herokuapp.com/";
function getConnectionPage() {
  document.location.href = "./connexion.html"
}
function ConnectApi(mail, password, divErr) {
  fetch(PATH + 'connexion', {
    method: 'post',
    body: 'username=' + mail + '&password=' + password,
    headers: {
      'Content-type': 'application/x-www-form-urlencoded',
    },
  })
    .then(res => res.json())
    .then(data => {

      if (data[0].response === "success") {
        setCookie("token", data[0].token)
        document.location.href = "./book.html"
      }
      else {
        divErr.textContent = "Erreur : mail / mot de passe invalide"
      }
    })
    .catch(err => {
      console.log("res script  err " + err)
    })
}
function DeconnectApi() {
  fetch(PATH + "deconnexion?token=" + getCookie("token"), {
    method: 'GET',
  })
    .finally(() => {
      setTimeout(() => {
        getConnectionPage()
      }, 2500);
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

  fetch(PATH + "search?token=" + getCookie("token") + "&titre=" + titre + "&auteur=" + auteur + "&genre=" + genre + "&langue=" + langue, {
    method: 'GET',
  })
    .then(resp => resp.json())
    .then((data) => {
      if (data[0].response === "success")
        createDom(JSON.parse(data[0].data));
      else {
        getConnectionPage()
      }
    })
    .catch(err => {
      console.log("err search " + err)
    })
}

function createDom(books) {

  listBook = books;
  document.getElementById("contentBook").remove();
  let idBook = document.getElementById("book_1");
  let divContentBook = document.createElement("div");
  divContentBook.setAttribute('class', 'content_book');
  divContentBook.id = "contentBook";
  for (let i = 0; i < books.length; i++) {
    // div content

    // card
    let card = document.createElement("div");
    card.setAttribute("class", "card_book");
    // left card
    let lcard = document.createElement("div");
    lcard.setAttribute("class", "left_card");
    let limg = document.createElement("img");
    limg.src = PATH + books[i].isbn.cover;
    limg.alt = "image de couverture";
    limg.setAttribute("width", "120");

    lcard.appendChild(limg);
    card.appendChild(lcard);

    // partie droite card
    let rcard = document.createElement("div");
    rcard.setAttribute("class", "right_card");
    let p = document.createElement("p");
    let p2 = document.createElement("p");
    p.textContent = books[i].isbn.titre;
    p2.textContent = parserGenre(books[i].isbn.genres)
    //p2.textContent = books[i][4];
    let button = document.createElement("button");
    button.setAttribute("class", "btn_info_book");
    button.id = "show_book" + i;
    button.setAttribute("onclick", "showModal(" + i + ")");
    button.textContent = "Afficher";
    rcard.appendChild(p);
    rcard.appendChild(p2);
    rcard.appendChild(button);
    card.appendChild(rcard);
    divContentBook.appendChild(card);
  }
  // ajout dans la recherche de la page
  let search = document.getElementById("search");
  search.appendChild(divContentBook);
  is_slide = form_search.classList.contains("slide-in");
  let classN = "sect_search_champs";
  form_search.setAttribute('class', (is_slide) ? classN + " slide-out" : classN + " slide-in");

}

function Pro_book(isbn) {
  fetch(PATH + "pret?idPret=" + isbn + "&token=" + getCookie("token"), {
    method: 'PATCH',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
  })
    .then(resp => resp.json())
    .then((data) => {
      if (data[0].response == "success") {
        let btn = document.getElementById("btn_reserve" + isbn);
        btn.classList.remove("active_lend")
        btn.classList.add("inactive_lend")
        btn.textContent = "Impossible de prolonger la période de prêt une seconde fois"
      }
    })
    .catch(err => {
      console.log("err search " + err)
    })
}
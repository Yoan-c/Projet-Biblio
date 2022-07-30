let tabInfos
window.onload = () => {
  fetch(PATH + "?token=" + getCookie("token"), {
    method: 'GET',
  })
    .then(res => res.json())
    .then(data => {
      if (data[0].response === "success")
        formatData(JSON.parse(data[0].data));
      else {
        getConnectionPage()
      }
    })
    .catch(err => {
      getConnectionPage()
      console.log("book " + err)
    })
}

function formatData(data) {
  tabInfos = getInfo(data);

  let tabBooks = tabInfos[0].book;
  for (let i = 0; i < tabBooks.length; i++) {
    let divBook = createBalise("div", "card_book", "book_" + i);
    let divLeftCard = createBalise("div", "left_card", "");
    let divRightCard = createBalise("div", "right_card", "");
    let pTitle = createBalise("p", "", "")
    let pContent = createBalise("p", "", "")
    pTitle.textContent = tabBooks[i].isbn.titre
    for (let j = 0; j < tabBooks[i].isbn.genres.length; j++) {
      pContent.textContent = pContent.textContent + " " + tabBooks[i].isbn.genres[j].nom
    }
    divRightCard.appendChild(pTitle);
    divRightCard.appendChild(pContent);
    let img = document.createElement("img");
    img.src = PATH + tabBooks[i].isbn.cover
    img.alt = "Photo de couverture"
    img.width = 120;
    let btn = document.createElement("button");
    btn.setAttribute("class", "btn_info_book");
    btn.setAttribute("id", "btn" + i);
    btn.setAttribute("onclick", "showModal(" + i + ")");
    btn.textContent = "Afficher";
    divRightCard.appendChild(btn);
    divLeftCard.appendChild(img);
    divBook.appendChild(divLeftCard);
    divBook.appendChild(divRightCard);
    document.getElementById("contentBook").appendChild(divBook);
  }
}

function getInfo(data) {
  let tabInfos = [];
  let tabGenre = [];
  let tabLangue = [];
  let tabAuteur = [];
  let tabEditeur = [];

  for (let i = 0; i < data.length; i++) {
    tabGenre = getGenre(data, i, tabGenre);
    tabLangue = getLangue(data, i, tabLangue);
    tabAuteur = getAuteur(data, i, tabAuteur);
    tabEditeur = getEditeur(data, i, tabEditeur);
  }
  let info = {
    genre: tabGenre,
    langue: tabLangue,
    auteur: tabAuteur,
    editeur: tabEditeur,
    book: data
  }
  tabInfos.push(info);
  return tabInfos;
}
/*
function getGenre(data, i, tabGenre) {

  for (let j = 0; j < data[i].isbn.genres.length; j++) {
    let genres = data[i].isbn.genres;
    if (!tabGenre)
      tabGenre.push(genres[j].nom);
    else {
      let find = false;
      for (let k = 0; k < tabGenre.length; k++) {
        if (tabGenre[k] == genres[j].nom)
          find = true;
      }
      if (!find)
        tabGenre.push(genres[j].nom);
    }
  }
  return tabGenre;
}

function getLangue(data, i, tabLangue) {

  let langue = data[i].isbn.langue;
  let find = false;
  for (let k = 0; k < tabLangue.length; k++) {
    if (tabLangue[k] == langue.nom)
      find = true;
  }
  if (!find)
    tabLangue.push(langue.nom);
  return tabLangue;
}
function getEditeur(data, i, tabEditeur) {

  let editeur = data[i].isbn.editeur;
  let find = false;
  for (let k = 0; k < tabEditeur.length; k++) {
    if (tabEditeur[k] == editeur.nom)
      find = true;
  }
  if (!find)
    tabEditeur.push(editeur.nom);
  return tabEditeur;
}

function getAuteur(data, i, tabauteur) {

  for (let j = 0; j < data[i].isbn.auteurs.length; j++) {
    let auteurs = data[i].isbn.auteurs;
    if (!tabauteur)
      tabauteur.push(auteurs[j]);
    else {
      let find = false;
      for (let k = 0; k < tabauteur.length; k++) {
        if (tabauteur[k].nom == auteurs[j].nom && tabauteur[k].prenom == auteurs[j].prenom)
          find = true;
      }
      if (!find)
        tabauteur.push(auteurs[j]);
    }
  }
  return tabauteur;
}
function parserAuteur(data) {
  let auteur = "";
  let max = data.length;
  for (let i = 0; i < max; i++) {
    auteur += data[i].nom + " " + data[i].prenom + ", ";
  }
  auteur = auteur.substring(0, auteur.length - 2);
  return auteur;
}

function parserGenre(data) {
  let genre = "";
  for (let i = 0; i < data.length; i++) {
    genre += data[i].nom + ", ";
  }
  genre = genre.substring(0, genre.length - 2);
  return genre;
}
*/
let modal_book = document.getElementById("modal_book");
function showModal(idBook) {
  let infoBook = tabInfos[0].book[idBook];
  let auteur = parserAuteur(infoBook.isbn.auteurs)
  let genre = parserGenre(infoBook.isbn.genres)
  let nb = infoBook.stock;
  let edition = infoBook.isbn.editeur.nom;
  let date = new Date(infoBook.isbn.datePublication);
  let formatDate = date.getDate() + " / " + date.getMonth() + " / " + date.getFullYear();

  //    let book = parserdata(dataBook, dataAuteur, dataGenre, exemplaire, editeur);
  let left_modal = document.getElementById("left_modal");
  let getImg = document.getElementById("add_img_modal");
  let right_modal = document.getElementById("right_modal");
  let firstP_modal = document.getElementById("idTitre");
  let descModal = document.getElementById("idDescModal");
  let reserve = document.getElementById("idBtnReserver")
  let divBtnModal = document.getElementById("idBtnModal");
  if (getImg == null) {
    let img = document.createElement('img');

    img.src = PATH + infoBook.isbn.cover;
    img.alt = "image de couverture";
    img.id = "add_img_modal";
    left_modal.appendChild(img);
  }
  else {
    getImg.src = PATH + infoBook.isbn.cover;
  }

  if (firstP_modal == null) {

    getFormatP("Titre", infoBook.isbn.titre, right_modal);
    getFormatP("Auteur", auteur, right_modal);
    getFormatP("Editeur", edition, right_modal);
    getFormatP("Genre", genre, right_modal);
    getFormatP("Langue", infoBook.isbn.langue.nom, right_modal);
    getFormatP("Date de publication", formatDate, right_modal);
    getFormatP("ISBN", infoBook.isbn.isbn, right_modal);
    getFormatP("Disponibilité", nb, right_modal);
    formatDesc("Présentation", infoBook.isbn.description, descModal);
    formatBtn(nb, divBtnModal);
  }
  else {
    getChangeInfoP("Titre", infoBook.isbn.titre, right_modal);
    getChangeInfoP("Auteur", auteur, right_modal);
    getChangeInfoP("Editeur", edition, right_modal);
    getChangeInfoP("Genre", genre, right_modal);
    getChangeInfoP("Langue", infoBook.isbn.langue.nom, right_modal);
    getChangeInfoP("Date de publication", formatDate, right_modal);
    getChangeInfoP("ISBN", infoBook.isbn.isbn, right_modal);
    getChangeInfoP("Disponibilité", nb, right_modal);
    formatDescModify("Présentation", infoBook.isbn.description, descModal);
    formatBtnModify(nb, divBtnModal);
  }
  let msgModal = document.getElementById("msg_bottom_modal");
  msgModal.innerText = "En l'attente du développement de la réservation en ligne, veuillez contacter la bibliothèque afin d'emprunter ce livre"
  modal_book.style.display = "block";
}

function formatBtnModify(nb) {
  let text = "";
  let cl = "btn_reserver";
  let btn = document.getElementById("idBtnReserver");

  btn.classList.add(cl);
  if (nb > 0) {
    text = "réserver";
    cl = "active_lend";
    btn.classList.remove("inactive_lend");
  }
  else {
    text = "indisponible";
    cl = "inactive_lend";
    btn.classList.remove("active_lend");
  }
  btn.classList.add(cl);
  btn.textContent = text;
  btn.setAttribute("onclick", 'reserve()')
}
function formatBtn(nb, divBtnModal) {
  let text = "";
  let btn = document.createElement("button");
  let cl = "btn_reserver";
  btn.classList.add(cl);
  if (nb > 0) {
    text = "réserver";
    cl = "active_lend";
  }
  else {
    text = "indisponible";
    cl = "inactive_lend";
  }
  btn.textContent = text;
  btn.id = "idBtnReserver";
  btn.classList.add(cl);
  btn.setAttribute("onclick", 'reserve()')
  divBtnModal.appendChild(btn);
}

function formatDesc(title, data, desc) {
  let h3 = document.createElement('h3');
  h3.textContent = title + " : ";

  //let centerM = document.getElementById("idCenterModal");
  let p = document.createElement('p');
  p.id = "idDesc";
  p.textContent = data
  desc.appendChild(h3);
  desc.appendChild(p);
  //centerM.appendChild(desc)
}


function formatDescModify(title, data, desc) {
  let p = document.getElementById("idDesc");
  p.textContent = data

}
function getFormatP(title, data, r_modal) {
  let p = document.createElement('p');

  p.textContent = title + " : " + data;
  if (title == "Disponibilité")
    p.id = "idDispo";
  else if (title == "Date de publication")
    p.id = "idDate";
  else
    p.id = "id" + title;
  r_modal.appendChild(p);
}
function getChangeInfoP(title, data, r_modal) {
  let p = document.getElementById("id" + title);

  if (title == "Disponibilité") {
    p = document.getElementById("idDispo");
  }
  if (title == "Date de publication") {
    p = document.getElementById("idDate");
  }
  p.textContent = title + " : " + data;

}
function hideModal() {
  modal_book.style.display = "none";
  let msgModal = document.getElementById("msg_bottom_modal").innerText = "";
}

let logo_search = document.getElementById("logo_search");
let form_search = document.getElementById("sect_search_champs");
logo_search.addEventListener('click', () => {
  let selectGenre = document.getElementById("search_genre");
  selectGenre = addOptionSelect(selectGenre, "genre");
  let selectLangue = document.getElementById("search_langue");
  selectLangue = addOptionSelect(selectLangue, "langue");

  let is_slide = form_search.classList.contains("slide-in");
  let cl = "sect_search_champs";
  form_search.setAttribute('class', (is_slide) ? cl + " slide-out" : cl + " slide-in");
})

function addOptionSelect(select, option) {
  let data
  if (option == "genre")
    data = tabInfos[0].genre;
  else if (option == "langue")
    data = tabInfos[0].langue;
  let options = [""];
  data.forEach((value) => {
    options.push(value)
  })
  options.forEach(function (element, key) {
    select[key] = new Option(element, element);
  });
  return select
}

// Creation DOM RECHERCHE
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
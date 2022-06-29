window.onload = ()=>{

    /* animation menu */
    let nav = document.getElementById("top_menu");
    let menu = document.getElementById("menu");
    let nbclick = 0;
    menu.addEventListener('click', () => {
        let is_slide = nav.classList.contains("slidein");
        let cl = "top_menu";
        nav.setAttribute('class', (is_slide) ? cl+" slideout" : cl+" slidein");

    });

    /* animation recherche */

    let logo_search = document.getElementById("logo_search");
    let form_search = document.getElementById("sect_search_champs");
    if (logo_search)
    console.log("test ", logo_search);
        logo_search.addEventListener('click', () => {
            let is_slide = form_search.classList.contains("slide-in");
            let cl = "sect_search_champs";
            form_search.setAttribute('class', (is_slide) ? cl+" slide-out" : cl+" slide-in");
        })

    // a tester
    function setmessageErrorModif(message){
        let errorModif = document.getElementById("errorModif");
        let p = document.getElementById("errorModifMsg");
        if (p == null){
            p = document.createElement("p");
            p.id = "errorModifMsg";
        }
        p.textContent = message;
        errorModif.appendChild(p);
    }
    function setmessageErrorReset(id){
        let errorModif = document.getElementById(id);
        if (errorModif){
            errorModif.textContent = "";
        }
    }
    function ValidateEmail(mail)
    {
        if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))
        {
            return (true)
        }
        return (false)
    }

    let btnForm = document.getElementById("btnValidModif");
    if(btnForm){
        btnForm.addEventListener('click', (e) => {
            e.preventDefault();
            setmessageErrorReset("errorModifMsg");
            setmessageErrorReset("resModifSuccess");
            setmessageErrorReset("resModifError");
            let lastName = document.getElementById("lastNameModif").value;
            let firstName = document.getElementById("firstNameModif").value;
            let mailModif = document.getElementById("mailModif").value;
            let mdpModif = document.getElementById("mdpModif").value;
            let mdpConfirm = document.getElementById("mdpConfirmModif").value;
            let token = document.getElementById("token").value;
            let mail = document.getElementById("mailCompte").value;
            if (lastName == "" || firstName == "" || mailModif == ""){
                setmessageErrorModif("Veuillez vérifier les champs");
                return;
            }
            else if(!ValidateEmail(mailModif)){
                setmessageErrorModif("Veuillez vérifier votre adresse mail");
                return;
            }
            else if(mdpModif !== mdpConfirm || mdpConfirm == "" || mdpModif == "") {
                setmessageErrorModif("Les mots de passe doivent être identique");
                return;
            }
            else
                updateUser(lastName, firstName, mailModif, mdpModif, token, mail);
        })
    }

}

function parserAuteur(data){
    let auteur = "";
    let max = Object.keys(data).length;
    for(let i = 0; i < max; i++){
        auteur += data[i]["firstName"]+" "+data[i]["lastName"]+", ";
    }
    auteur = auteur.substring(0, auteur.length-2);
    return auteur;
}

function parserGenre(data){
    let genre = "";
    for (let i = 0; i < data.length; i++){
      genre += data[i]["genre"]+", ";
    }
    genre = genre.substring(0, genre.length-2);
    return genre;
}

function parserdata(data, dataAuteur, dataGenre, exemplaire, editeur){
    let book = new Object();
    let dataBook = data.substring(0, data.length-1);
    book.book = parserBook(dataBook);
    book.auteur = parserAuteur(dataAuteur);
    book.genre = parserGenre(dataGenre);
    book.editeur = editeur;
    book.exemplaire = exemplaire;
    return book;
}
function parserBook(d){
    d = JSON.stringify(d);
    let data = d;
    let newData = data.substring(6, data.length-1);
    const regex_desc = /description=/g;
    let newData_before = data.substring(7, data.search(regex_desc) - 2);
    const regex_l = /langue=/g;
    let newData_after = data.substring(data.search(regex_desc), data.search(regex_l) - 2);
    let newData_end = data.substring(data.search(regex_l), data.length-1);
    newData = newData_before.split(',');
    let book = new Object();
    for (let i = 0; i < newData.length; i++) {
        let info = newData[i].split('=');
        book[info[0].trim()] = info[1].trim();
    }
    newData_end = newData_end.split(',');
    for (let i = 0; i < newData_end.length; i++) {
        let info = newData_end[i].split('=');
        book[info[0].trim()] = info[1].trim();
    }
    let info = newData_after.split('=');
    book[info[0].trim()] = info[1].trim();
    return book;
}
/* modal */

let modal_book = document.getElementById("modal_book");
function showModal(idBook){
    let infoBook = listBook[idBook];
    let auteur = parserAuteur(infoBook[1]);
    let genre = parserGenre(infoBook[2]);
    let nb = infoBook[3];
    let edition = infoBook[4];
    let date = new Date(infoBook[0]["date_publication"]);
    let formatDate = date.getDate()+" / "+date.getMonth()+" / "+date.getFullYear();

//    let book = parserdata(dataBook, dataAuteur, dataGenre, exemplaire, editeur);
    let left_modal = document.getElementById("left_modal");
    let getImg = document.getElementById("add_img_modal");
    let right_modal = document.getElementById("right_modal");
    let firstP_modal = document.getElementById("idTitre");
    let descModal = document.getElementById("idDescModal");
    let reserve = document.getElementById("idBtnReserver")
    let divBtnModal = document.getElementById("idBtnModal");
    if(getImg == null){
        let img = document.createElement('img');

        img.src = infoBook[0]["cover"];
        img.alt = "image de couverture";
        img.id = "add_img_modal";
        left_modal.appendChild(img);
    }
    else {
        getImg.src = infoBook[0]["cover"];
    }

    if (firstP_modal == null )
    {

        getFormatP("Titre", infoBook[0]["title"], right_modal);
        getFormatP("Auteur", auteur, right_modal);
        getFormatP("Editeur", edition, right_modal);
        getFormatP("Genre", genre, right_modal);
        getFormatP("Langue", infoBook[0]["langue"], right_modal);
        getFormatP("Date de publication", formatDate, right_modal);
        getFormatP("ISBN", infoBook[0]["isbn"], right_modal);
        getFormatP("Disponibilité", nb, right_modal);
        formatDesc("Présentation", infoBook[0]["description"], descModal);
        formatBtn(nb, divBtnModal);
    }
    else{
        getChangeInfoP("Titre",infoBook[0]["title"], right_modal);
        getChangeInfoP("Auteur", auteur, right_modal);
        getChangeInfoP("Editeur", edition, right_modal);
        getChangeInfoP("Genre", genre, right_modal);
        getChangeInfoP("Langue", infoBook[0]["langue"], right_modal);
        getChangeInfoP("Date de publication", formatDate, right_modal);
        getChangeInfoP("ISBN",  infoBook[0]["isbn"], right_modal);
        getChangeInfoP("Disponibilité", nb, right_modal);
        formatDescModify("Présentation", infoBook[0]["description"], descModal);
        formatBtnModify(nb, divBtnModal);
    }
    modal_book.style.display= "block";
}

function formatBtnModify(nb){
    let text="";
    let cl = "btn_reserver";
    let btn = document.getElementById("idBtnReserver");

    btn.classList.add(cl);
    if (nb > 0){
        text = "réserver";
        cl = "active_lend";
        btn.classList.remove("inactive_lend");
    }
    else{
        text = "indisponible";
        cl = "inactive_lend";
        btn.classList.remove("active_lend");
    }
    btn.classList.add(cl);
    btn.textContent = text;
    btn.onclick = reserve;
}
function formatBtn(nb, divBtnModal) {
    let text="";
    let btn = document.createElement("button");
    let cl = "btn_reserver";
    btn.classList.add(cl);
    if (nb > 0){
        text = "réserver";
        cl = "active_lend";
    }
    else{
        text = "indisponible";
        cl = "inactive_lend";
    }
    btn.textContent = text;
    btn.id = "idBtnReserver";
    btn.classList.add(cl);
    btn.onclick = reserve;
    divBtnModal.appendChild(btn);


}
 function formatDesc(title, data, desc){
    let h3 = document.createElement('h3');
    h3.textContent = title+" : ";

    let centerM = document.getElementById("idCenterModal");
     let p = document.createElement('p');
     p.id = "idDesc";
     p.textContent = data
     desc.appendChild(h3);
     desc.appendChild(p);
 }

 function formatDescModify(title, data, desc){
     let p = document.getElementById("idDesc");
     p.textContent = data

}
function getFormatP(title, data, r_modal){
    let p = document.createElement('p');

    p.textContent = title+" : "+data;
    if (title == "Disponibilité")
        p.id = "idDispo";
    else if(title == "Date de publication")
        p.id = "idDate";
    else
        p.id = "id"+title;
    r_modal.appendChild(p);
}
function getChangeInfoP(title, data, r_modal) {
    let p = document.getElementById("id" + title);

    if (title == "Disponibilité") {
        p = document.getElementById("idDispo");
    }
    if(title == "Date de publication") {
        p = document.getElementById("idDate");
    }
    p.textContent = title+" : "+data;

}
function hideModal(){
    modal_book.style.display= "none";
}

let nav = document.getElementById("top_menu");
let menu = document.getElementById("menu");
let nbclick = 0;
menu.addEventListener('click', () => {
    let is_slide = nav.classList.contains("slidein");
    let cl = "top_menu";
    nav.setAttribute('class', (is_slide) ? cl+" slideout" : cl+" slidein");

});

/* animation recherche */

let logo_search = document.getElementById("logo_search");
let form_search = document.getElementById("sect_search_champs");
if (logo_search)
    logo_search.addEventListener('click', () => {
        let is_slide = form_search.classList.contains("slide-in");
        let cl = "sect_search_champs";
        form_search.setAttribute('class', (is_slide) ? cl+" slide-out" : cl+" slide-in");
    })

// a tester
function setmessageErrorModif(message){
    let errorModif = document.getElementById("errorModif");
    let p = document.getElementById("errorModifMsg");
    if (p == null){
        p = document.createElement("p");
        p.id = "errorModifMsg";
    }
    p.textContent = message;
    errorModif.appendChild(p);
}
function setmessageErrorReset(id){
    let errorModif = document.getElementById(id);
    if (errorModif){
        errorModif.textContent = "";
    }
}
function ValidateEmail(mail)
{
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))
    {
        return (true)
    }
    return (false)
}

let btnForm = document.getElementById("btnValidModif");
if(btnForm){
    btnForm.addEventListener('click', (e) => {
        e.preventDefault();
        setmessageErrorReset("errorModifMsg");
        setmessageErrorReset("resModifSuccess");
        setmessageErrorReset("resModifError");
        let form = document.getElementById("formModif");
        let lastName = document.getElementById("lastNameModif").value;
        let firstName = document.getElementById("firstNameModif").value;
        let mailModif = document.getElementById("mailModif").value;
        let mdpModif = document.getElementById("mdpModif").value;
        let mdpConfirm = document.getElementById("mdpConfirmModif").value;
        let token = document.getElementById("token").value;
        let mailCompte = document.getElementById("mailCompte").value;
        if (lastName == "" || firstName == "" || mailModif == ""){
            setmessageErrorModif("Veuillez vérifier les champs");
            return;
        }
        else if(!ValidateEmail(mailModif)){
            setmessageErrorModif("Veuillez vérifier votre adresse mail");
            return;
        }
        else if(mdpModif !== mdpConfirm || mdpConfirm == "" || mdpModif == "") {
            setmessageErrorModif("Les mots de passe doivent être identique");
            return;
        }
        else
            updateUser(lastName, firstName, mailModif, mdpModif, token, mailCompte, mdpConfirm);
    })
}


// Creation DOM RECHERCHE
function createDom(data){

  data = JSON.parse(data);
  let dataJson = data["book"];
  let books = JSON.parse(data["book"]);
  listBook = books;
  document.getElementById("contentBook").remove();
  let idBook = document.getElementById("book_1");
  let divContentBook= document.createElement("div");
  divContentBook.setAttribute('class', 'content_book');
  divContentBook.id = "contentBook";
  for (let i = 0 ; i < books.length; i++)
  {
      // div content

      // card
      let card = document.createElement("div");
      card.setAttribute("class", "card_book");
      // left card
      let lcard = document.createElement("div");
      lcard.setAttribute("class", "left_card");
      let limg = document.createElement("img");
      limg.src = books[i][0]["cover"];
      limg.alt="image de couverture";
      limg.setAttribute("width", "120");

      lcard.appendChild(limg);
      card.appendChild(lcard);

      // partie droite card
      let rcard = document.createElement("div");
      rcard.setAttribute("class", "right_card");
      let p = document.createElement("p");
      let p2 = document.createElement("p");
      p.textContent = books[i][0]["title"];
      p2.textContent = books[i][4];
      let button = document.createElement("button");
      button.setAttribute("class", "btn_info_book");
      button.id="show_book"+i;
      button.setAttribute("onclick", "showModal("+i+")");
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
      form_search.setAttribute('class', (is_slide) ? classN+" slide-out" : classN+" slide-in");

}

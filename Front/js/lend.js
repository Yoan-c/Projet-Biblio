let tabInfos
window.onload = () => {
    fetch(PATH + "pret?token=" + getCookie("token"), {
        method: 'GET',
    })
        .then(res => res.json())
        .then(data => {
            if (data[0].response === "success") {
                data = JSON.parse(data[0].data);
                if (data.length > 0)
                    showLend(data);
                else
                    showNoLend();
            }
            else {
                getConnectionPage();
            }

        })
        .catch(err => {
            getConnectionPage();
        })
}
function showNoLend() {
    let section = createBalise("section", "", "");
    let div = createBalise("div", "", "");
    let p = createBalise("p", "", "");
    p.textContent = "aucun prêt en cours"
    div.appendChild(p)
    section.appendChild(div)
    section.setAttribute("id", "noLend")
    document.getElementById("lend").appendChild(section)
}

function showLend(data) {
    for (let i = 0; i < data.length; i++) {
        let section = createBalise("section", "", "");
        let divImgBook = createBalise("div", "img_book", "");
        let imgBook = createBalise("img", "", "");
        imgBook.src = PATH + data[i].exemplaire.isbn.cover
        imgBook.alt = "image de couvertur";
        imgBook.width = 120;
        divImgBook.appendChild(imgBook);
        let divInfoBook = createBalise("div", "info_book", "");
        let divDescBook = createBalise("div", "desc_book", "");
        divDescBook = addPtoDescBook(divDescBook, data[i].exemplaire, data[i].dateDebut, data[i].dateFin);
        divInfoBook.appendChild(divDescBook);
        let divBtn = createBalise("div", "reserve_book", "");
        let btn = createBalise("button", "", "btn_reserve" + data[i].exemplaire.isbn.isbn);
        btn.classList.add("btn_reserve_book");
        if (data[i].renouvele) {
            btn.classList.add("inactive_lend");
            btn.textContent = "Impossible de prolonger la période de prêt une seconde fois"
        }
        else {
            btn.classList.add("active_lend");
            btn.textContent = "Prolonger la période de pret"
            btn.setAttribute("onclick", "Pro_book(" + data[i].exemplaire.isbn.isbn + ") ")
        }
        divBtn.appendChild(btn)
        divInfoBook.appendChild(divBtn)
        section.appendChild(divImgBook)
        section.appendChild(divInfoBook)
        document.getElementById("lend").appendChild(section)
    }
}

function addPtoDescBook(divDescBook, data, dateDebut, dateFin) {
    let p = CreateformateP("Titre : " + data.isbn.titre)
    divDescBook.appendChild(p);
    p = CreateformateP("Auteur : " + parserAuteur(data.isbn.auteurs))
    divDescBook.appendChild(p);
    p = CreateformateP("Genre : " + parserGenre(data.isbn.genres))
    divDescBook.appendChild(p);
    p = CreateformateP("Editeur : " + data.isbn.editeur.nom)
    divDescBook.appendChild(p);
    p = CreateformateP("Date de publication : " + data.isbn.datePublication)
    divDescBook.appendChild(p);
    p = CreateformateP("ISBN : " + data.isbn.isbn)
    divDescBook.appendChild(p);
    let date = new Date(dateDebut);
    dateDebut = date.getDate() + " / " + date.getMonth() + " / " + date.getFullYear();
    p = CreateformateP("Début du prêt : " + dateDebut)
    divDescBook.appendChild(p);
    date = new Date(dateFin);
    dateFin = date.getDate() + " / " + date.getMonth() + " / " + date.getFullYear();
    p = CreateformateP("fin du prêt : " + dateFin)
    divDescBook.appendChild(p);
    return divDescBook
}

function CreateformateP(content) {
    let p = document.createElement("p");
    p.textContent = content
    return p
}
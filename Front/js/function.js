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

function createBalise(balise, varClass, id) {
    let b = document.createElement(balise);
    if (varClass)
        b.setAttribute("class", varClass);
    if (id)
        b.setAttribute("id", id);
    return b;
}

function reserve(){
    let dataDispo = document.getElementById("idDispo").textContent;
    let dataIsbn = document.getElementById("idISBN").textContent;

    let nb = parseInt(dataDispo.split(":")[1].trim());
    let isbn = parseInt(dataIsbn.split(":")[1].trim());

    if(isNaN(nb) || isNaN(isbn)){
        console.log("Afficher une erreur");
    }
    else if (nb <= 0){
        console.log("plus de stock disponible")
    }
    else {
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", "reservation", true);
        xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                // Response
                var response = this.responseText;
            }
        };
        xhttp.send("stock="+nb+"&isbn="+isbn);
    }

}

function Pro_book(isbn){
  let token = document.getElementById("idToken").value;
  let mail = document.getElementById("idMail").value
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", PATH+"prolonger", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Response
            var response = this.responseText;
        }
    };
    xhttp.send("mail="+mail+"&token="+token+"&isbn="+isbn);
}

function getSearch(data){
    let titre = document.getElementById("search_title").value;
    let auteur = document.getElementById("search_auteur").value;
    let genre = document.getElementById("search_genre").value;
    let langue = document.getElementById("search_langue").value;
    let token = document.getElementById("idToken").value;

    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", PATH+"searchBook", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Response
            var response = this.responseText;
            createDom(response);
        }
    };
    xhttp.send("token="+token+"&titre="+titre+"&auteur="+auteur+"&genre="+genre+"&langue="+langue);
}

function getLend(){
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", PATH+"lend", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Response
            var response = this.responseText;
            createDom(response);
        }
    };
    xhttp.send("token="+token+"&mail="+mail);
}

function updateUser(lastName, firstName, mailModif, mdpModif, token, mail, mdpConfirm){
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", PATH+"updateCompte", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Response
            var response = this.responseText;
            document.location.href="./info.php";
          //  createDom(response);
        }
    };
    xhttp.send("lastName="+lastName+"&firstName="+firstName+"&mailModif="+mailModif+"&mdp="+mdpModif+"&token="+token+"&mail="+mail+"&mdpConfirm="+mdpConfirm);
}

function createUser(){
  let firstName = document.getElementById("firstName").value;
  let lastName = document.getElementById("lastName").value;
  let mail = document.getElementById("mail_create").value;
  let password_confirm = document.getElementById("password_confirm").value;
  let password = document.getElementById("password_create").value;
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", PATH+"addUser", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Response
            var response = this.responseText;

            document.location.href="./";
          //  createDom(response);
        }
    };
    xhttp.send("lastName="+lastName+"&firstName="+firstName+"&mail="+mail+"&password="+password+"&password_confirm="+password_confirm);
}

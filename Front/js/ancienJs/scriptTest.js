let PATH = "http://localhost:8081/";

  function ConnectApi(mail, password, divErr){
    fetch('http://localhost:8081/connexion', {
          method: 'post',
          body: 'username='+mail+'&password='+password,
      headers: {
        'Content-type': 'application/x-www-form-urlencoded',
        'Access-Control-Allow-Origin': ' *',
      },
          credentials: 'include'
        })
        .then(res => res.json())
        .then(data => {
         
         // console.log("res script " + res.ok)
          console.log("res script " + data[0].response)
          if (data[0].response === "sucess")
            document.forms["form_connect"].submit();
          else {
            divErr.textContent="Erreur : mail / mot de passe invalide"
          }
        })
        .catch(err => {
          console.log("res script  err " + err)
        })
  }
          let dataPost = {
            "username":"test@gmail.com",
            "password":"test"
        };
        let t = 'https://httpbin.org/post'
       /* let form = new FormData()
        form.append("username", "test@gmail.com");
        form.append("password", "test")
        *//*
        fetch('http://localhost:8081/login', {
          method: 'post',
          body: JSON.stringify(dataPost),
          headers: { 'Content-type': 'application/json' }
        })
        .then(res => res.text())
        .then(d => {
          console.log(d)
        })
        */
       /*
        fetch('http://localhost:8081/connexion', {
          method: 'post',
          body: 'username=test@gmail.com&password=test',
          headers: { 'Content-type': 'application/x-www-form-urlencoded'},
          credentials: 'include'
        })
        .then(res => res.text())
        .then(d => {
          
          console.log("res "+d);
      
          document.forms["form_connect"].submit();
        })
        */
        //function postData() {
         /* const formm = document.getElementById('form_connect')
          let data = new FormData(formm)
         // data.append('username', "test@gmail.com")
          //data.append('password', "test")
        */
         /* fetch("http://localhost:8081/connexion", {
            method: 'post',
            body: JSON.stringify({"username":"test@gmail.com", "password":"test"}),
            headers: {
              "Content-Type": "application/json"
            }
          }).then(response => response.text())
          .then(data => {
            console.log("data "+ data)
          }).catch(console.error)

// Ajax de Dennis:
      /*  let url = 'localhost:8080/test';
        url = "http://localhost:8081/connexion";
        let id = $('input').val();
        // let user = $('input.username').val();
        // let pass = $('input.password').val();

         $.post( url, {
            // id: id,
            username: "test@gmail.com",
            password: "test"
          })
            .done((response) => {
                try {
                  console.log(response);
                  response = JSON.parse(response);

                } catch(e) {
                  console.log("response n'est pas un json")
                }
              if (response.success) {

                $('#editFolder').modal('show');

                $('#editFolderForm input[name="editFolder[url]"]').val(response.data.url);
                $('#editFolderForm input[name="editFolder[title]"]').val(response.data.title);
                $('#editFolderForm input[name="editFolder[description]"]').val(response.data.description);
                $('#editFolderForm input[name="editFolder[id]"]').val(response.data.id);
                $('#editFolderForm input[name="editFolder[org]"]').val(response.data.org);
              } else {
                // display errors
              }
            })
            */
// end Ajax de Dennis:

       // }
      /* $.ajax({
        type: "POST",
        url: "http://localhost:8081/login",  
        //beforeSend: function(xhr){
          //  xhr.setRequestHeader(header, token);
        //},
        data: "username=test@gmail.com&password=test",
        success: function(html){
             console.log("logged in " + html);
         }
       });
        */  
    
        
     // location.href = "http://localhost:8080/login";

/*
  function recup_csrf(data){
   
    let index = data.indexOf("_csrf");
    data = data.substring(index);
    index = data.indexOf("value=");
    data = data.substring(index);
    index = data.indexOf("\"");
    data = data.substring(index+1);
    let indexEnd = data.indexOf("\"")
    let csrf = data.substring(0, indexEnd)
    return csrf;
  }
  */

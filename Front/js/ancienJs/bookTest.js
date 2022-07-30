
window.onload = () => {

  let dataPost = {
    "username": "test@gmail.com",
    "password": "test"
  };
  let t = 'https://httpbin.org/post'

  /*
   fetch('http://localhost:8081/', {
     method: 'get',
    //  headers: { 'Access-Control-Allow-Origin':' *' },
     //credentials: 'include'
   })
   .then(res => res.text())
   .then(d => {
       console.log("test "+d)
   })
   */
  /*      fetch('http://localhost:8081/connexion', {
          method: 'post',
          body: 'username=test@gmail.com&password=test',
          headers: { 'Content-type': 'application/x-www-form-urlencoded',},
         //  headers: { 'Access-Control-Allow-Origin':' *' },
         credentials: 'include'
        })
        .then(res => res.text())
        .then(d => {
            document.cookie = "JSESSIONID="+d
            console.log("test "+document.cookie)
            fetch('http://localhost:8081/', {
          method: 'get',

           //headers: { 'Access-Control-Allow-Origin':' *' },
          credentials: 'include'
        })
        .then(res => res.text())
        .then(d => {
            console.log("test 2 "+d)
        })
        })
*/

  fetch('http://localhost:8081/', {
    method: 'GET',

    // headers: { 'Access-Control-Allow-Origin':' *' },
    credentials: 'include'
  })
    .then(res => res.text())
    .then(d => {
      console.log("test 2 " + d)
    })
    .catch(err => {
      console.log("err " + err);
    })

  /* $.get("http://localhost:8081/", {
 
   })
     .done((response) => {
       try {
         console.log(response);
         response = JSON.parse(response);
 
       } catch (e) {
         console.log("response n'est pas un json test 3")
       }
       if (response.success) {
         console.log("test 3 ")
       } else {
         // display errors
       }
     })
     */
}

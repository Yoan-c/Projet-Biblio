
window.onload = ()=>{

        fetch('http://localhost:8081/', {
          method: 'get',
           //headers: { 'Access-Control-Allow-Origin':' *' },
          credentials: 'include'
        })
        .then(res => res.text())
        .then(d => {
            console.log("test "+document.cookie.length)
        })

}

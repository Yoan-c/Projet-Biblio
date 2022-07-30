window.onload = () => {
    fetch(PATH + "stats?token=" + getCookie("token"), {
        method: 'GET',
    })
        .then(res => res.json())
        .then(data => {
            if (data[0].response === "success")
                setStatsData(JSON.parse(data[0].data));
            else {
                getConnectionPage()
            }
        })
        .catch(err => {
            console.log("err stat " + err);
            getConnectionPage()
        })
}

function setStatsData(data) {
    let divStat = document.getElementById("stats")
    createCharts(data.genre, 'pie', "Genre", divStat)
    createCharts(data.auteur, 'pie', "Auteur", divStat)
    createCharts(data.livre, 'pie', "Livre", divStat)
    createCharts(data.editeur, 'pie', "Editeur", divStat)
    let nbEmprunt = document.getElementById("nbEmprunt")
    nbEmprunt.textContent = data.total
}
function createCharts(infos, typeChart, title, divStat) {
    let tabStats = []
    let tabName = []
    let divGenreChart = createBalise("div", "", "stat" + title)
    for (let i = 0; i < infos.length; i++) {
        tabStats.push(parseInt(infos[i].stats))
        tabName.push(infos[i].nom)
    }
    let data = [{
        values: tabStats,
        labels: tabName,
        type: typeChart,
    }];

    var layout = {
        height: 390,
        width: 400,
        legend: {
            font: {
                size: 10,
                family: "Arial",
            }
        },
        margin: {
            autoexpand: false,
            b: 10,
            l: 80,
            r: 140,
            t: 80

        },
        title: {
            text: title,
            font: {
                color: "#028586",
                size: 16
            },
            x: 0.1,
            y: 0.8
        }
    };
    Plotly.newPlot(divGenreChart, data, layout);
    divStat.appendChild(divGenreChart)
    return data
}




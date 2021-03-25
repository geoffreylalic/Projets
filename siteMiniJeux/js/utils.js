//fonction issue des tps de M. Buffa
function create2DArray(rows, columns) {
    let arr = [];

    for (let l = 0; l < rows; l++) {
        for (let c = 0; c < columns; c++) {
            arr[l] = [];
        }
    }
    return arr;
}
//récupération du profil connecté
window.addEventListener("DOMContentLoaded", () => {
    console.log("niveau " + localStorage.getItem("niveau"));

    //pour afficher le pseudo actifd dans la navbar
    let pseudoNav = document.querySelector("#pseudoNav");
    let chargementJoueur = localStorage.getItem("listeJoueur");
    chargementJoueur = JSON.parse(chargementJoueur);
    let joueurActif;
    let trouve = false;
    chargementJoueur.forEach(joueur => {
        if (joueur.actif === true) {
            trouve = true;
            joueurActif = joueur;
            pseudoNav.innerHTML = joueurActif.pseudo;
        } 
    });
    if (trouve === false){
            pseudoNav.innerHTML = "aucun"
    }

});


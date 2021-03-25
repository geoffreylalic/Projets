window.addEventListener("DOMContentLoaded", () => {
    //récupération des informations du html
    let affPseudo = document.getElementById("pseudo");
    let affGeneral = document.getElementById("general");
    let affDem = document.getElementById("dem");
    let affMf = document.getElementById("mf");
    let affSucces = document.getElementById("succes");

    //récupération du joueur actif
    let listeJoueur = localStorage.getItem("listeJoueur");
    listeJoueur = JSON.parse(listeJoueur);
    console.log(listeJoueur);

    let trouve = false;
    listeJoueur.forEach(joueur => {
        if (joueur.actif === true) {
            //affichage du joueur et de ses stats
            trouve = true;
            console.log(joueur);
            affPseudo.innerHTML = joueur.pseudo;
            affGeneral.innerHTML = "Vous avez joué " + (joueur.nbPartieMF + joueur.nbPartieDem) + " fois (Démineur et Motfléchés)"
            affDem.innerHTML = "Vous avez joué " + joueur.nbPartieDem + " fois";
            affMf.innerHTML = "Vous avez joué " + joueur.nbPartieMF + " fois";
            // comparation des stats pour les défis
            if (joueur.nbPartieDem >= 5) {
                affSucces.innerHTML += '<p>"Grand Génie" débloqué, vous etes fort</p>'
            } if (joueur.nbPartieMF >= 5) {
                affSucces.innerHTML += '<p>"Grand Maître" débloqué, vous etes très fort</p>'
            }
        }
    });
    //aucun profil actif
    if(trouve === false){
        affPseudo.innerHTML = "Aucun compte";
    }
});
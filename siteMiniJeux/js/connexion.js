window.addEventListener("DOMContentLoaded", () => {
    //les statistique du joueur
    function Joueur(pseudo, mdp) {
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.experience = 0;
        this.actif = false;
        this.nbPartieMF = 0;
        this.nbPartieDem = 0;
    }

    //récup des infos sur html
    let connexion = document.querySelector("#btnConnex");
    //permet de connecter un profil
    connexion.addEventListener("click", () => {
        let pseudoConnex = document.querySelector("#pseudoConnex").value;
        let mdpConnex = document.querySelector("#mdpConnex").value;
        let resConnex = document.getElementById("connexion");
        //recupération des données json
        let listeJoueur = localStorage.getItem("listeJoueur")
        listeJoueur = JSON.parse(listeJoueur);
        let trouve = false;
        for (let joueur = 0; joueur < listeJoueur.length; joueur++) {
            if (listeJoueur[joueur].pseudo === pseudoConnex && listeJoueur[joueur].mdp === mdpConnex) {
                trouve = true;
                listeJoueur[joueur].actif = true;
                resConnex.innerHTML = "vous etes connecté";
                break;
            }
        }
        if (trouve === false) {
            resConnex.innerHTML = "mauvais pseudo ou mot de passe";
        }
        //on renvoie les changements
        listeJoueur = JSON.stringify(listeJoueur);
        localStorage.setItem("listeJoueur",listeJoueur);
        document.location.reload();

    });

    //permet de déconnecter un profil actif
    let deconnexion = document.querySelector("#btnDeconnex");
    deconnexion.addEventListener("click",()=>{
        let listeJoueur = localStorage.getItem("listeJoueur");
        listeJoueur = JSON.parse(listeJoueur);
        listeJoueur.forEach(joueur =>{
            joueur.actif = false;
        });
        console.log("deco "+ listeJoueur)
        listeJoueur = JSON.stringify(listeJoueur);
        localStorage.setItem("listeJoueur",listeJoueur);
        document.location.reload();
    });

    //permet d'ajouter un profil
    let register = document.querySelector("#btnRegister");
    register.addEventListener("click", () => {
        let pseudoRegister = document.querySelector("#pseudoRegister").value;
        let mdpRegister = document.querySelector("#mdpRegister").value;
        let resRegister = document.getElementById("register");
        let nouveauJoueur = new Joueur(pseudoRegister, mdpRegister);

        //on récupère la liste de joueur de localStorage
        let listeJoueur = localStorage.getItem("listeJoueur");
        listeJoueur = JSON.parse(listeJoueur);
        if (listeJoueur === null) {
            listeJoueur.push(nouveauJoueur);
            //on retransforme listeJoueur en objet JSON
            let nouvelleListe = JSON.stringify(listeJoueur);
            //on remplace par localStorage par la nouvelle liste de joueur
            localStorage.setItem("listeJoueur", nouvelleListe);
            resRegister.innerHTML = "Ajout d'un nouveau profil";
        } else {
            let trouve = false;
            for (let joueur = 0; joueur < listeJoueur.length; joueur++) {
                if (listeJoueur[joueur].pseudo === pseudoRegister) {
                    resRegister.innerHTML = "Le pseudo existe déjà veuillez taper un autre pseudo";
                    trouve = true;
                    break;
                }
            } if (trouve === false) {
                //on ajoute le nouveau joueur dans la liste
                listeJoueur.push(nouveauJoueur);
                //on retransforme listeJoueur en objet JSON
                let nouvelleListe = JSON.stringify(listeJoueur);
                //on remplace par localStorage par la nouvelle liste de joueur
                localStorage.setItem("listeJoueur", nouvelleListe);
                resRegister.innerHTML = "Ajout d'un nouveau profil";
            }
        }

    });



});

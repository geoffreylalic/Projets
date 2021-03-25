console.log("niveau " + localStorage.getItem("niveau"));
//récupération de localStorage
let chargementJoueur = localStorage.getItem("listeJoueur");
chargementJoueur = JSON.parse(chargementJoueur);
let joueurActif;
let trouve = false;
chargementJoueur.forEach(joueur => {
    if (joueur.actif === true) {
        trouve = true;
        joueurActif = joueur;
    }
});
if (trouve === false) {
    joueurActif = "personne";
}
console.log(joueurActif);

class AbsGrille extends Abs {
    constructor() {
        super();

    }

    /**
     * Pour comparer les messages envoyés dans l'agent PAC
     * @param {*} message classe MESSAGE de PAC.js
     * @param {*} piecejointe objet envoyé à travers le PAC
     */
    reçoitMessage(message, piecejointe) {
        let result = "";
        if (message === MESSAGE.TABLEAU_CASE) {
            //on récupère le tableau et la une case dans la piecejointe
            result = this.diffusion(piecejointe[0], piecejointe[1]);
        } else if (message === MESSAGE.DIFFUSION_INDICES) {
            result = this.diffusionIndices(piecejointe[0], piecejointe[1]);
        }
        else {
            //message d'erreur
            result = super.reçoitMessage(message, piecejointe);
        }
        return result;
    }

    /**
     * revoie une liste de case qui contient les case ayant des indices
     * @param {*} listeCaseDecouverte 
     * @param {*} tabCasePres 
     */
    diffusionIndices(listeCaseDecouverte, tabCasePres) {
        let result = [];
        listeCaseDecouverte.forEach(caseDecouverte => {
            let ligneCaseCourante = parseInt(caseDecouverte.ligne);
            let colonneCaseCourante = parseInt(caseDecouverte.colonne);
            let caseCourante = tabCasePres[ligneCaseCourante][colonneCaseCourante];

            if (caseCourante.decouvert && !caseCourante.mine && caseCourante.indice === 0) {

                //Case au sud
                if (tabCasePres[ligneCaseCourante + 1] !== undefined && tabCasePres[ligneCaseCourante + 1][colonneCaseCourante].indice > 0 && !tabCasePres[ligneCaseCourante + 1][colonneCaseCourante].decouvert && !tabCasePres[ligneCaseCourante + 1][colonneCaseCourante].mine) {
                    let listSud = tabCasePres[ligneCaseCourante + 1][colonneCaseCourante];
                    result = result.concat(listSud);
                    tabCasePres[ligneCaseCourante + 1][colonneCaseCourante].decouvert = true;
                }

                //Case au nord
                if (tabCasePres[ligneCaseCourante - 1] !== undefined && tabCasePres[ligneCaseCourante - 1][colonneCaseCourante].indice > 0 && !tabCasePres[ligneCaseCourante - 1][colonneCaseCourante].decouvert && !tabCasePres[ligneCaseCourante - 1][colonneCaseCourante].mine) {
                    let listNord = tabCasePres[ligneCaseCourante - 1][colonneCaseCourante];
                    result = result.concat(listNord);
                    tabCasePres[ligneCaseCourante - 1][colonneCaseCourante].decouvert = true;
                }


                //Case au ouest  
                if (tabCasePres[ligneCaseCourante][colonneCaseCourante - 1] !== undefined && tabCasePres[ligneCaseCourante][colonneCaseCourante - 1].indice > 0 && !tabCasePres[ligneCaseCourante][colonneCaseCourante - 1].decouvert && !tabCasePres[ligneCaseCourante][colonneCaseCourante - 1].mine) {
                    let listOuest = tabCasePres[ligneCaseCourante][colonneCaseCourante - 1];
                    result = result.concat(listOuest);
                    tabCasePres[ligneCaseCourante][colonneCaseCourante - 1].decouvert = true;
                }

                //Case au est 
                if (tabCasePres[ligneCaseCourante][colonneCaseCourante + 1] !== undefined && tabCasePres[ligneCaseCourante][colonneCaseCourante + 1].indice > 0 && !tabCasePres[ligneCaseCourante][colonneCaseCourante + 1].decouvert && !tabCasePres[ligneCaseCourante][colonneCaseCourante + 1].mine) {
                    let listEst = tabCasePres[ligneCaseCourante][colonneCaseCourante + 1];
                    result = result.concat(listEst);
                    tabCasePres[ligneCaseCourante][colonneCaseCourante + 1].decouvert = true;
                }


                //Case sud est
                if (tabCasePres[ligneCaseCourante + 1] !== undefined && tabCasePres[ligneCaseCourante][colonneCaseCourante + 1] !== undefined && tabCasePres[ligneCaseCourante + 1][colonneCaseCourante + 1].indice > 0 && !tabCasePres[ligneCaseCourante + 1][colonneCaseCourante + 1].decouvert && !tabCasePres[ligneCaseCourante + 1][colonneCaseCourante + 1].mine) {
                    let listSudEst = tabCasePres[ligneCaseCourante + 1][colonneCaseCourante + 1];
                    result = result.concat(listSudEst);
                    tabCasePres[ligneCaseCourante + 1][colonneCaseCourante + 1].decouvert = true;
                }

                //Case sud ouest
                if (tabCasePres[ligneCaseCourante + 1] !== undefined && tabCasePres[ligneCaseCourante][colonneCaseCourante - 1] !== undefined && tabCasePres[ligneCaseCourante + 1][colonneCaseCourante - 1].indice > 0 && !tabCasePres[ligneCaseCourante + 1][colonneCaseCourante - 1].decouvert && !tabCasePres[ligneCaseCourante + 1][colonneCaseCourante - 1].mine) {
                    let listSudOuest = tabCasePres[ligneCaseCourante + 1][colonneCaseCourante - 1];
                    result = result.concat(listSudOuest);
                    tabCasePres[ligneCaseCourante + 1][colonneCaseCourante - 1].decouvert = true;

                }

                //Case nord est
                if (tabCasePres[ligneCaseCourante - 1] !== undefined && tabCasePres[ligneCaseCourante][colonneCaseCourante + 1] !== undefined && tabCasePres[ligneCaseCourante - 1][colonneCaseCourante + 1].indice > 0 && !tabCasePres[ligneCaseCourante - 1][colonneCaseCourante + 1].decouvert && !tabCasePres[ligneCaseCourante - 1][colonneCaseCourante + 1].mine) {
                    let listNordEst = tabCasePres[ligneCaseCourante - 1][colonneCaseCourante + 1];
                    result = result.concat(listNordEst);
                    tabCasePres[ligneCaseCourante - 1][colonneCaseCourante + 1].decouvert = true;
                }

                //Case nord ouest
                if (tabCasePres[ligneCaseCourante - 1] !== undefined && tabCasePres[ligneCaseCourante][colonneCaseCourante - 1] !== undefined && tabCasePres[ligneCaseCourante - 1][colonneCaseCourante - 1].indice > 0 && !tabCasePres[ligneCaseCourante - 1][colonneCaseCourante - 1].decouvert && !tabCasePres[ligneCaseCourante - 1][colonneCaseCourante - 1].mine) {
                    let listNordOuest = tabCasePres[ligneCaseCourante - 1][colonneCaseCourante - 1];
                    result = result.concat(listNordOuest);
                    tabCasePres[ligneCaseCourante - 1][colonneCaseCourante - 1].decouvert = true;
                }

            }


        });
        return result;

    }

    /**
     * Permet de faire la diffusion entre les case non minés dans le jeu et de les découvrir
     * @param {*} tableauCase 
     * @param {*} caseCourante 
     */
    diffusion(tableauCase, caseCourante) {
        let result = [];
        let tabCasePres = tableauCase;

        //on convertie en int les coordonnées
        let ligneCaseCourante = parseInt(caseCourante.ligne);
        let colonneCaseCourante = parseInt(caseCourante.colonne);


        if (!caseCourante.decouvert && !caseCourante.mine && caseCourante.indice === 0) {
            result.push(caseCourante);
            caseCourante.decouvert = true;

            //Case au sud
            if (tabCasePres[ligneCaseCourante + 1] !== undefined && tabCasePres[ligneCaseCourante + 1][colonneCaseCourante].indice === 0) {
                let listSud = this.diffusion(tabCasePres, tabCasePres[ligneCaseCourante + 1][colonneCaseCourante]);
                result = result.concat(listSud);
                tabCasePres[ligneCaseCourante + 1][colonneCaseCourante].decouvert = true;
            }

            //Case au nord
            if (tabCasePres[ligneCaseCourante - 1] !== undefined && tabCasePres[ligneCaseCourante - 1][colonneCaseCourante].indice === 0) {
                let listNord = this.diffusion(tabCasePres, tabCasePres[ligneCaseCourante - 1][colonneCaseCourante]);
                result = result.concat(listNord);
                tabCasePres[ligneCaseCourante - 1][colonneCaseCourante].decouvert = true;
            }


            //Case au ouest  
            if (tabCasePres[ligneCaseCourante][colonneCaseCourante - 1] !== undefined && tabCasePres[ligneCaseCourante][colonneCaseCourante - 1].indice === 0) {
                let listOuest = this.diffusion(tabCasePres, tabCasePres[ligneCaseCourante][colonneCaseCourante - 1]);
                result = result.concat(listOuest);
                tabCasePres[ligneCaseCourante][colonneCaseCourante - 1].decouvert = true;
            }

            //Case au est 
            if (tabCasePres[ligneCaseCourante][colonneCaseCourante + 1] !== undefined && tabCasePres[ligneCaseCourante][colonneCaseCourante + 1].indice === 0) {
                let listEst = this.diffusion(tabCasePres, tabCasePres[ligneCaseCourante][colonneCaseCourante + 1]);
                result = result.concat(listEst);
                tabCasePres[ligneCaseCourante][colonneCaseCourante + 1].decouvert = true;
            }

            //si l'indice est supérieur a 0
        } else if (!caseCourante.decouvert && !caseCourante.mine && caseCourante.indice > 0) {
            let caseARetourner = [caseCourante];
            result = result.concat(caseARetourner);
            caseCourante.decouvert = true;
        }

        return result;
    }
}

class PresGrille extends Pres {
    /**
     * constructeur de la presentation
     * @param {*} niveau 
     */
    constructor(niveau) {
        super();

        this.niveau = niveau;
        this.nbLignes = 9;
        this.nbColonnes = 9;
        this.tabCase;
        this.nbMines = JEUX[this.niveau].nbMines;
        this.nbDrapeau = this.nbMines;
        this.tabMine = [];
        this.nbCaseDecouvertes = 0;
        //pour dessiner la grille grâce au css
        this.grille = document.querySelector("#grille");
        this.difficulte = JEUX[this.niveau].difficulte;
    }

    /**
     * Pour comparer les messages envoyés dans l'agent PAC
     * @param {*} message classe MESSAGE de PAC.js
     * @param {*} piecejointe objet envoyé à travers le PAC
     */
    reçoitMessage(message, piecejointe) {
        let result = "";
        if (message == MESSAGE.INIT) {
            this.construireGrille();
            this.remplirTableau();
            this.ajouterIndices();
            this.ctrl.reçoitMessageDeLaPresentation(MESSAGE.ENVOIEDRAPEAU, this.nbDrapeau);
            this.ctrl.reçoitMessageDeLaPresentation(MESSAGE.ENVOIEMINES, this.nbMines);
            this.ctrl.reçoitMessageDeLaPresentation(MESSAGE.DIFFICULTE, this.difficulte);
            //abonnement de la fonction à l'écouteur
            this.grille.addEventListener("click", (evt) => {
                let clickCible = evt.target;
                this.clickSurCase(clickCible);
                this.finDePartie();
            });
            this.grille.addEventListener("contextmenu", (evt) => {
                if (evt.target.tagName === 'IMG') {
                    let clickDroit = evt.target;
                    this.ajoutDrapeau(clickDroit);
                } if (this.nbDrapeau > 0) {
                    evt.preventDefault();
                    let clickDroit = evt.target;
                    this.ajoutDrapeau(clickDroit);
                } else {
                    evt.preventDefault();
                }
            });
        } else if (message === MESSAGE.CLICK_TRICHE) {
            this.afficherMine();
        } else {
            //message d'erreur
            result = super.reçoitMessage(message, piecejointe);
        }
        return result;
    }/**
     * affiche les mines trouvé 
     */

    afficherMine() {
        let toutesLesDivs = document.querySelectorAll("#grille div");
        toutesLesDivs.forEach(div => {
            div.classList.add("mineTrouve");
            if (this.tabCase[div.dataset.ligne][div.dataset.colonne].mine) {
                div.append(this.tabCase[div.dataset.ligne][div.dataset.colonne].image);
            }
        });
    }
    /**
     * termine la partie du démineur si toute les cases son decouvert
     */

    finDePartie() {
        this.nbCaseDecouvertes = 0;
        for (let ligne = 0; ligne < this.nbLignes; ligne++) {
            for (let colonne = 0; colonne < this.nbColonnes; colonne++) {
                if (this.tabCase[ligne][colonne].decouvert) {
                    this.nbCaseDecouvertes++;
                }
            }
        }
        if (this.nbCaseDecouvertes === (this.nbLignes * this.nbColonnes - this.nbMines)) {
            alert("Gagné !!");
            if (joueurActif !== 'personne') {
                joueurActif.nbPartieDem += 1;
                console.log('gagné ' + joueurActif);
            }
            this.ctrl.reçoitMessageDeLaPresentation(MESSAGE.GAGNER);
        }
    }
    /**
     * permet d'ajouter un drapeau
     * @param {*} clickDroit 
     */

    ajoutDrapeau(clickDroit) {
        if (clickDroit.tagName === 'DIV') {
            let img = document.createElement("img");
            img.src = "assets/images/demineur/drapeau.jpg";
            img.width = 80;
            img.height = 80;
            clickDroit.appendChild(img);
            this.nbDrapeau--;
            this.ctrl.reçoitMessageDeLaPresentation(MESSAGE.ENVOIEDRAPEAU, this.nbDrapeau);
        } else if (clickDroit.tagName === 'IMG') {
            if (clickDroit.src.endsWith("drapeau.jpg")) {
                clickDroit.remove();
                clickDroit.src = "";
                this.nbDrapeau++;
                this.ctrl.reçoitMessageDeLaPresentation(MESSAGE.ENVOIEDRAPEAU, this.nbDrapeau);
            }
        }
    }
    /**
     * Permet d'ajouter des indices sur les cases autour d'une mine
     */
    ajouterIndices() {
        for (let ligne = 0; ligne < this.nbLignes; ligne++) {
            for (let colonne = 0; colonne < this.nbColonnes; colonne++) {
                if (this.tabCase[ligne][colonne].mine) {
                    this.tabMine.push(this.tabCase[ligne][colonne]);
                }
            }
        }
        this.tabMine.forEach(caseMine => {
            let ligne = parseInt(caseMine.ligne);
            let colonne = parseInt(caseMine.colonne);
            //Case sud
            if (this.tabCase[ligne + 1] !== undefined && !this.tabCase[ligne + 1][colonne].mine) {
                this.tabCase[ligne + 1][colonne].indice += 1;
            }
            //Case au nord
            if (this.tabCase[ligne - 1] !== undefined && !this.tabCase[ligne - 1][colonne].mine) {
                this.tabCase[ligne - 1][colonne].indice += 1;
            }
            //Case au ouest  
            if (this.tabCase[ligne][colonne - 1] !== undefined && !this.tabCase[ligne][colonne - 1].mine) {
                this.tabCase[ligne][colonne - 1].indice += 1;
            }
            //Case au est 
            if (this.tabCase[ligne][colonne + 1] !== undefined && !this.tabCase[ligne][colonne + 1].mine) {
                this.tabCase[ligne][colonne + 1].indice += 1;
            }
            //Case sud est
            if (this.tabCase[ligne + 1] !== undefined && this.tabCase[ligne][colonne + 1] !== undefined && !this.tabCase[ligne + 1][colonne + 1].mine) {
                this.tabCase[ligne + 1][colonne + 1].indice += 1;
            }
            //Case sud ouest
            if (this.tabCase[ligne + 1] !== undefined && this.tabCase[ligne][colonne - 1] !== undefined && !this.tabCase[ligne + 1][colonne - 1].mine) {
                this.tabCase[ligne + 1][colonne - 1].indice += 1;
            }
            //Case nord est
            if (this.tabCase[ligne - 1] !== undefined && this.tabCase[ligne][colonne + 1] !== undefined && !this.tabCase[ligne - 1][colonne + 1].mine) {
                this.tabCase[ligne - 1][colonne + 1].indice += 1;
            }
            //Case nord ouest
            if (this.tabCase[ligne - 1] !== undefined && this.tabCase[ligne][colonne - 1] !== undefined && !this.tabCase[ligne - 1][colonne - 1].mine) {
                this.tabCase[ligne - 1][colonne - 1].indice += 1;
            }
        });
    }


    /**
     * Permet de dessiner la contenu d'une case en fonction de son parametre de Case.mine
     * @param {*} piecejointe 
     */
    clickSurCase(divClick) {
        let ligne = divClick.dataset.ligne;
        let colonne = divClick.dataset.colonne;
        let toutesLesDivs = document.querySelectorAll("#grille div");
        let largeur = Math.floor(Math.sqrt(toutesLesDivs.length));
        if (this.tabCase[ligne][colonne].mine) {
            toutesLesDivs.forEach(div => {
                div.classList.add("caseClick");
                if (this.tabCase[div.dataset.ligne][div.dataset.colonne].mine) {
                    div.append(this.tabCase[div.dataset.ligne][div.dataset.colonne].image);
                }
            });
            alert('Perdu!');
            console.log(joueurActif);
            if (joueurActif !== 'personne') {
                joueurActif.nbPartieDem += 1;
                console.log('perdu ' + joueurActif);
            }
            this.ctrl.reçoitMessageDeLaPresentation(MESSAGE.PERDU);
        }
        else if (!this.tabCase[ligne][colonne].mine) {
            //on effectue la diffusion des case ayant des indices 0 par rapport a la case clické
            let listAMontrer = this.ctrl.reçoitMessageDeLaPresentation(MESSAGE.TABLEAU_CASE, [this.tabCase, this.tabCase[ligne][colonne]]);
            listAMontrer.forEach(casee => {
                let ligne = parseInt(casee.ligne);
                let colonne = parseInt(casee.colonne)
                this.caseNonMine(toutesLesDivs.item(ligne * largeur + colonne));
            });
            //on effectue une deuxieme diffusion sur les case découverte ayant des indices = 0 dans les cases autour aient des indices >0 
            let indicesAMontrer = this.ctrl.reçoitMessageDeLaPresentation(MESSAGE.DIFFUSION_INDICES, [listAMontrer, this.tabCase]);
            indicesAMontrer.forEach(caseIndice => {
                let ligne = parseInt(caseIndice.ligne);
                let colonne = parseInt(caseIndice.colonne)
                this.caseNonMine(toutesLesDivs.item(ligne * largeur + colonne));
            });
        } else {
            console.log("la case n'est plus disponible");
        }
    }
    /**
     * Pour changer la couleur d'une case qui n'est pas miné une clické
     * @param {*} div 
     */
    caseNonMine(div) {
        let ligne = parseInt(div.dataset.ligne);
        let colonne = parseInt(div.dataset.colonne);
        let image = document.createElement("img");
        if (this.tabCase[ligne][colonne].decouvert) {
            switch (this.tabCase[ligne][colonne].indice) {
                case 0:
                    div.append(this.tabCase[div.dataset.ligne][div.dataset.colonne].image)
                    break;
                case 1:
                    image.src = "assets/images/demineur/1.png";
                    image.width = 80;
                    image.height = 80;
                    div.append(image);
                    break;
                case 2:
                    image.src = "assets/images/demineur/2.png";
                    image.width = 80;
                    image.height = 80;
                    div.append(image);
                    break;
                case 3:
                    image.src = "assets/images/demineur/3.png";
                    image.width = 80;
                    image.height = 80;
                    div.append(image);
                    break;
                case 4:
                    image.src = "assets/images/demineur/4.png";
                    image.width = 80;
                    image.height = 80;
                    div.append(image);
                    break;
                case 5:
                    image.src = "assets/images/demineur/5.png";
                    image.width = 80;
                    image.height = 80;
                    div.append(image);
                    break;
                case 6:
                    image.src = "assets/images/demineur/6.png";
                    image.width = 80;
                    image.height = 80;
                    div.append(image);
                    break;
                case 7:
                    image.src = "assets/images/demineur/7.png";
                    image.width = 80;
                    image.height = 80;
                    div.append(image);
                    break;
                case 8:
                    image.src = "assets/images/demineur/8.png";
                    image.width = 80;
                    image.height = 80;
                    div.append(image);
                    break;
            }
        }
        div.classList.add("caseClick");
    }

    /**
     * Permet de construire la grille
     */
    construireGrille() {
        for (let ligne = 0; ligne < this.nbLignes; ligne++) {
            for (let colonne = 0; colonne < this.nbColonnes; colonne++) {
                let div = document.createElement("div");
                this.grille.append(div);
                div.dataset.ligne = ligne;
                div.dataset.colonne = colonne;
            }
        }
    }

    /**
     * Permet de remplir la grille de case contenant des mines ou non
     */
    remplirTableau() {
        this.tabCase = create2DArray(this.nbLignes, this.nbColonnes);
        let toutesLesDivs = document.querySelectorAll("#grille div");
        let indexCase = Math.floor(Math.random() * toutesLesDivs.length);
        let ligne = Math.floor(indexCase / this.nbColonnes);
        let colonne = indexCase % this.nbLignes;
        let tabIndiceMine = [];


        while (tabIndiceMine.length < this.nbMines) {
            if (!tabIndiceMine.includes(indexCase)) {
                tabIndiceMine.push(indexCase);
                ligne = Math.floor(indexCase / this.nbColonnes);
                colonne = indexCase % this.nbLignes;
                this.tabCase[ligne][colonne] = new Case(ligne, colonne, true);
            } else {
                indexCase = Math.floor(Math.random() * toutesLesDivs.length);
            }
        }

        // on crée les case non miné
        toutesLesDivs.forEach(div => {
            ligne = parseInt(div.dataset.ligne);
            colonne = parseInt(div.dataset.colonne);
            if (this.tabCase[ligne][colonne] === undefined) {
                this.tabCase[ligne][colonne] = new Case(ligne, colonne, false);
            }
        })

    }


}


class CtrlGrille extends Ctrl {
    constructor(abs, pres) {
        super(abs, pres);
    }

    reçoitMessageDUnEnfant(message, piecejointe, ctrl) {
        let result = "";

        // l'utilisateur souhaite tricher
        if (message === MESSAGE.CLICK_TRICHE) {
            this.pres.reçoitMessage(message);
        } else if (message === MESSAGE.CLICK_INDICE) {
            this.pres.reçoitMessage(message);
        }
        else {
            result = super.reçoitMessageDunEnfnant(message, piecejointe, ctrl)
        }
        return result;
    }

    reçoitMessageDeLaPresentation(message, piecejointe) {
        let result = "";
        if (message === MESSAGE.CASE_CLICK) {
            result = this.abs.reçoitMessage(MESSAGE.CASE_CLICK, piecejointe);
        }
        else if (message === MESSAGE.TABLEAU_CASE) {
            result = this.abs.reçoitMessage(MESSAGE.TABLEAU_CASE, piecejointe);
        } else if (message === MESSAGE.DIFFUSION_INDICES) {
            result = this.abs.reçoitMessage(message, piecejointe);
        } else if (message === MESSAGE.ENVOIEDRAPEAU) {
            this.enfants.forEach(enfant => {
                enfant.reçoitMessageDuParent(message, piecejointe);
            });
        } else if (message === MESSAGE.ENVOIEMINES) {
            this.enfants.forEach(enfant => {
                enfant.reçoitMessageDuParent(message, piecejointe);
            });
        } else if (message === MESSAGE.DIFFICULTE) {
            this.enfants.forEach(enfant => {
                enfant.reçoitMessageDuParent(message, piecejointe);
            });
        }
        else if (message === MESSAGE.GAGNER) {
            this.majJoueur();
            this.enfants.forEach(enfant => {
                enfant.reçoitMessageDuParent(message, piecejointe);
            });
        } else if (message === MESSAGE.PERDU) {
            this.majJoueur();
            this.enfants.forEach(enfant => {
                enfant.reçoitMessageDuParent(message, piecejointe);
            });
        }
        else {
            result = super.reçoitMessageDeLaPresentation(message, piecejointe);
        }

        return result;
    }

    reçoitMessageDeLAbstraction(message, piecejointe) {
        let result = "";

        if (message == MESSAGE.DIFFUSION) {
            this.pres.reçoitMessage(message, piecejointe);
        } else if (message == MESSAGE.UNE_CASE) {
            this.pres.reçoitMessage(message.piecejointe);

        }
        else {
            result = super.reçoitMessageDeLAbstraction(message, piecejointe);
        }
    }

    majJoueur() {
        chargementJoueur = JSON.stringify(chargementJoueur);
        localStorage.setItem("listeJoueur", chargementJoueur);
    }

    init() {
        this.pres.reçoitMessage(MESSAGE.INIT);
        this.enfants.forEach(enfant => {
            enfant.reçoitMessageDuParent(MESSAGE.INIT);
        });


    }



}
//Récupération des données de l'utilisateur en ligne
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
if(trouve === false){
    joueurActif = "personne";
}
console.log(joueurActif);

class AbsGrille extends Abs {
    constructor(niveau) {
        super();
        //récupère le niveau sélectionner
        this.niveau = niveau;

        //la difficulté
        this.difficulte = JEUX[this.niveau].difficulte;
        //les solutions
        this.listeDeMots = JEUX[this.niveau].listeMots;
        this.nbLignes = this.listeDeMots.length;
        this.nbColonnes = this.listeDeMots[this.niveau].length;

        //grille que remplie l'utilisateur
        this.grilleUser = create2DArray(this.nbLignes, this.nbColonnes);
        for (let ligne = 0; ligne < this.nbLignes; ligne++) {
            for (let colonne = 0; colonne < this.nbColonnes; colonne++) {
                let lettre = this.listeDeMots[ligne][colonne];
                if (lettre === "|" || lettre === "%") {
                    this.grilleUser[ligne][colonne] = lettre;
                } else {
                    this.grilleUser[ligne][colonne] = ".";
                }
            }
        }

        //liste des indices
        this.listeIndices = JEUX[this.niveau].listeIndices;
        //liste des solutions lignes
        this.solutionLigne = JEUX[this.niveau].solutionLigne;
        //liste des solutions colonnes
        this.solutionColonne = JEUX[this.niveau].solutionColonne;
    }

    /**
     * permet de traiter les messages arrivant à l'abstraction
     * @param {*} message 
     * @param {*} piecejointe 
     * @returns 
     */
    reçoitMessage(message, piecejointe) {
        let result = "";
        if (message === MESSAGE.LISTE_MOTS) {
            result = this.listeDeMots;
        } else if (message === MESSAGE.CLICK_INDICE) {
            //piecejointe = caseClické sur présentation
            let lettre = this.indice(piecejointe);
            this.ctrl.reçoitMessageDeLAbstraction(MESSAGE.CLICK_INDICE_RENVOI, lettre);
        }
        else if (message === MESSAGE.REJOUER_MAJ) {
            this.rejouerGrille();
        } else if (message === MESSAGE.DIFFICULTE) {
            this.ctrl.reçoitMessageDeLAbstraction(message, this.difficulte);
        } else if (message === MESSAGE.LISTE_INDICES) {
            result = this.listeIndices;
        } else if (message === MESSAGE.LETTRE) {
            this.ajoutLettre(piecejointe);
            this.verificationLigne();
            this.verificationColonne();
            this.finDePartie();
        } else if (message === MESSAGE.CLICK_TRICHE) {
            this.ctrl.reçoitMessageDeLAbstraction(message, this.listeDeMots);
            this.finTriche();
        } else if (message === MESSAGE.CLICK_REJOUER) {
            this.ctrl.reçoitMessageDeLAbstraction(message, this.listeDeMots);
        } else {
            result = super.reçoitMessage(message, piecejointe);
        }
        return result;
    }

    /**
     * permet d'afficher la lettre correspondant à la case
     * @param {*} caseClicke 
     * @returns 
     */
    indice(caseClicke) {
        let ligne = parseInt(caseClicke.dataset.ligne);
        let colonne = parseInt(caseClicke.dataset.colonne);
        this.grilleUser[ligne][colonne] = this.listeDeMots[ligne][colonne];
        return this.grilleUser[ligne][colonne];
    }

    /**
     * permet de réinitialiser la grille de l'utilisateur
     */
    rejouerGrille() {
        for (let ligne = 0; ligne < this.nbLignes; ligne++) {
            for (let colonne = 0; colonne < this.nbColonnes; colonne++) {
                if (this.grilleUser[ligne][colonne] !== "|" && this.grilleUser[ligne][colonne] !== "%") {
                    this.grilleUser[ligne][colonne] = ".";
                }
            }
        }
        //on remet les solutions lignes et colonnes 
        this.solutionLigne = JEUX[this.niveau].solutionLigne;
        this.solutionColonne = JEUX[this.niveau].solutionColonne;
    }

    /**
     * envoie un message d'alerte
     */
    finTriche() {
        alert("Fin de partie, vous avez triché");
    }

    /**
     * permet de vérifier si une partie est terminer
     */
    finDePartie() {
        let fin = 0;
        for (let ligne = 0; ligne < this.nbLignes; ligne++) {
            let mot = this.grilleUser[ligne].join();
            mot = mot.replaceAll(",", "");
            this.listeDeMots.forEach(motSolution => {
                //vérification de chaque ligne de la grille solution par rapport à la grille de l'utilisateur
                if (motSolution === mot) {
                    fin++;
                }
            });
        }
        if (fin === this.nbLignes) {
            alert("Gagné!!");
            if (joueurActif !=='personne') {
                joueurActif.nbPartieMF += 1;
                console.log('gagné ' + joueurActif);
            }
            this.ctrl.reçoitMessageDeLAbstraction(MESSAGE.GAGNER);
        }
    }

    /**
     * ajoute une lettre dans la grille de l'uilisateur
     * @param {} piecejointe correspond à la ligne colonne et la lettre entrée dans une liste
     */
    ajoutLettre(piecejointe) {
        //on place la lettre ajouter dans this.grilleUSer
        let ligne = piecejointe[0];
        let colonne = piecejointe[1];
        let lettre = piecejointe[2];
        this.grilleUser[ligne][colonne] = lettre;
    }

    /**
     * permet de vérifier les ligne de la grille de l'utilisateur
     */
    verificationLigne() {
        for (let ligne = 0; ligne < this.nbLignes; ligne++) {
            for (let indice = 0; indice < this.solutionLigne.length; indice++) {
                let mot = this.grilleUser[ligne].join();
                mot = mot.replaceAll(",", "");
                //pour contrer les doublons de réponses justes
                if (mot.includes(this.solutionLigne[indice]) && this.listeDeMots[ligne].includes(this.solutionLigne[indice]) && mot.indexOf(this.solutionLigne[indice]) === this.listeDeMots[ligne].indexOf(this.solutionLigne[indice])) {
                    //indice de la premiere lettre
                    let posPremier = mot.indexOf(this.solutionLigne[indice]);
                    let posDernier = posPremier + this.solutionLigne[indice].length;
                    if ((this.listeDeMots[ligne][posPremier - 1] === undefined || this.listeDeMots[ligne][posPremier - 1] === "|" || this.listeDeMots[ligne][posPremier - 1] === "%") && (this.listeDeMots[ligne][posDernier] === undefined || this.listeDeMots[ligne][posDernier] === "|" || this.listeDeMots[ligne][posDernier] === "%")) {
                        //on supprime le mot trouvé de la solution
                        this.solutionLigne.splice(indice, 1);
                        //envoyé a la présentation les lettre marqué;
                        for (let position = posPremier; position < posDernier; position++) {
                            this.ctrl.reçoitMessageDeLAbstraction(MESSAGE.LETTRE_JUSTE, [ligne, position]);
                        }
                    }
                }
            }
        }
    }

    /**
     * permet de vérifier les colonnes de la grille de l'utilisateur
     */
    verificationColonne() {
        //on tourne le tableau (colonnes deviennent des ligne et vice-versa)
        let grilleInverse = create2DArray(11, 9);
        for (let ligne = 0; ligne < this.nbColonnes; ligne++) {
            for (let colonne = 0; colonne < this.nbLignes; colonne++) {
                grilleInverse[ligne][colonne] = this.grilleUser[colonne][ligne];
            }
        }

        let listeDeMotsInverse = create2DArray(11, 9);
        for (let ligne = 0; ligne < this.nbColonnes; ligne++) {
            for (let colonne = 0; colonne < this.nbLignes; colonne++) {
                listeDeMotsInverse[ligne][colonne] = this.listeDeMots[colonne][ligne];
            }
        }

        for (let ligne = 0; ligne < this.nbColonnes; ligne++) {
            for (let indice = 0; indice < this.solutionColonne.length; indice++) {
                let mot = grilleInverse[ligne].join();
                mot = mot.replaceAll(",", "");
                let solutionInverse = listeDeMotsInverse[ligne].join();
                solutionInverse = solutionInverse.replaceAll(",", "");
                if (mot.includes(this.solutionColonne[indice]) && solutionInverse.includes(this.solutionColonne[indice]) && mot.indexOf(this.solutionColonne[indice]) === solutionInverse.indexOf(this.solutionColonne[indice])) {
                    //indice de la premiere lettre
                    let posPremier = mot.indexOf(this.solutionColonne[indice]);
                    let posDernier = posPremier + this.solutionColonne[indice].length;
                    // on vérifie si avant la premeiere la dernière lettre il y a un indice
                    if ((solutionInverse[posPremier - 1] === undefined || solutionInverse[posPremier - 1] === "|" || solutionInverse[posPremier - 1] === "%") && (solutionInverse[posDernier] === undefined || solutionInverse[posDernier] === "|" || solutionInverse[posDernier] === "%")) {
                        //on supprime le mot trouvé de la solution
                        this.solutionColonne.splice(indice, 1);
                        //envoyé a la présentation les lettre marqué;
                        for (let position = posPremier; position < posDernier; position++) {
                            this.ctrl.reçoitMessageDeLAbstraction(MESSAGE.LETTRE_JUSTE, [position, ligne]);
                        }
                    }
                }
            }
        }


    }

}

class PresGrille extends Pres {
    constructor() {
        super();
        this.nbLignes = 9;
        this.nbColonnes = 11;
        this.tabCase;

        this.grille = document.querySelector("#grilleMF");

        this.tabCaseClick = [];

    }

    /**
     * permet de traiter les messages arrivant à l'abstraction
     * @param {*} message 
     * @param {*} piecejointe 
     * @returns 
     */
    reçoitMessage(message, piecejointe) {
        let result;
        if (message === MESSAGE.INIT) {
            this.dessineGrille();
            this.remplirIndices();
        } else if (message === MESSAGE.CLICK_INDICE) {
            //on vérefie qu'une case a été clické
            if (this.tabCaseClick.length > 0) {
                //on envoie à l'abstraction la case clické
                this.ctrl.reçoitMessageDeLaPresentation(message, this.tabCaseClick[0]);
            }
        } else if (message === MESSAGE.CLICK_INDICE_RENVOI) {
            this.tabCaseClick[0].innerHTML = piecejointe;
        }
        else if (message === MESSAGE.LETTRE_JUSTE) {
            this.lettreTrouve(piecejointe);
        } else if (message === MESSAGE.CLICK_TRICHE) {
            this.triche(piecejointe);
        } else if (message === MESSAGE.CLICK_REJOUER) {
            this.rejouer(piecejointe);
        } else {
            result = super.reçoitMessage(message, piecejointe);
        }

        return result;
    }

    /**
     * permet de réinitialiser la grille présenté
     * @param {*} listeSolution représente la grille de solution et permet de récupérer l'emplacement des indices
     */
    rejouer(listeSolution) {
        for (let ligne = 0; ligne < this.nbLignes; ligne++) {
            for (let colonne = 0; colonne < this.nbColonnes; colonne++) {
                if (listeSolution[ligne][colonne] !== "|" && listeSolution[ligne][colonne] !== "%" && (!this.tabCase[ligne][colonne].classList.contains("marqueLettre") || !this.tabCase[ligne][colonne].classList.contains("triche"))) {
                    this.tabCase[ligne][colonne].innerText = "";
                    // on enleve toutes les classes css aux mots trouvé
                    this.tabCase[ligne][colonne].classList.remove("marqueLettre");
                    this.tabCase[ligne][colonne].classList.remove("triche");
                }
            }
        }
        //on met a jour la grille de l'utilisateur dans l'abstraction
        this.ctrl.reçoitMessageDeLaPresentation(MESSAGE.REJOUER_MAJ);
    }

    /**
     * permet d'afficher toutes les lettres aux bons emplacements
     * @param {*} listeSolution représente la grille de solution et permet de récupérer l'emplacement des indices et des lettre
     */
    triche(listeSolution) {
        for (let ligne = 0; ligne < this.nbLignes; ligne++) {
            for (let colonne = 0; colonne < this.nbColonnes; colonne++) {
                if (listeSolution[ligne][colonne] !== "|" && listeSolution[ligne][colonne] !== "%" && !this.tabCase[ligne][colonne].classList.contains("marqueLettre")) {
                    this.tabCase[ligne][colonne].innerText = listeSolution[ligne][colonne];
                    //ajouter une classe css
                    this.tabCase[ligne][colonne].classList.add("triche");
                }

            }
        }
    }

    /**
     * permet de marqué une lettre si elle a été trouvé par l'utilisateur
     * @param {*} piecejointe représente la ligne et la colonne de la case trouvé
     */
    lettreTrouve(piecejointe) {
        let ligne = piecejointe[0];
        let colonne = piecejointe[1];
        this.tabCase[ligne][colonne].classList.add("marqueLettre");
        this.tabCase[ligne][colonne].classList.remove("caseSelectionne");
        //pour enlever la derniere case clické en cas de mot trouvé;
        this.tabCaseClick.pop();
    }

    /**
     * permet d'ajouter des grille et la forme de la grille se fait grace au css
     */
    dessineGrille() {
        this.tabCase = create2DArray(this.nbLignes, this.nbColonnes);
        for (let ligne = 0; ligne < this.nbLignes; ligne++) {
            for (let colonne = 0; colonne < this.nbColonnes; colonne++) {
                let div = document.createElement("div");
                this.grille.append(div);
                this.tabCase[ligne][colonne] = div;
                this.tabCase[ligne][colonne].dataset.ligne = ligne;
                this.tabCase[ligne][colonne].dataset.colonne = colonne;
            }
        }
    }

    /**
     * permet de remplir les indices de la grilles avec la liste d'indice, '|' et '%' permette de savoir s'il y a 1 ou 2 indice par case
     */
    remplirIndices() {
        let indexIndice = 0;
        let listeMotsAbs = this.ctrl.reçoitMessageDeLaPresentation(MESSAGE.LISTE_MOTS);
        let listeIndices = this.ctrl.reçoitMessageDeLaPresentation(MESSAGE.LISTE_INDICES);
        for (let ligne = 0; ligne < this.nbLignes; ligne++) {
            for (let colonne = 0; colonne < this.nbColonnes; colonne++) {
                let lettre = listeMotsAbs[ligne][colonne];
                //afficher deux indices sur une case
                if (lettre === '|') {
                    let divHaut = document.createElement("div");
                    divHaut.classList.add("haut");
                    divHaut.innerText = listeIndices[indexIndice];
                    indexIndice += 1;
                    let divBas = document.createElement("div");
                    divBas.innerText = listeIndices[indexIndice];
                    indexIndice += 1;
                    divBas.classList.add("bas");
                    this.tabCase[ligne][colonne].append(divHaut);
                    this.tabCase[ligne][colonne].append(divBas);
                    //afficher un indice sur une case
                } else if (lettre === '%') {
                    this.tabCase[ligne][colonne].innerText = listeIndices[indexIndice];
                    this.tabCase[ligne][colonne].classList.add("divIndice");
                    indexIndice += 1;
                } else {
                    // sur les autres cases on ajoute des listenner 
                    this.tabCase[ligne][colonne].classList.add("caseARemplir");
                    this.tabCase[ligne][colonne].addEventListener("click", () => {
                        if (this.tabCaseClick.length > 0) {
                            this.tabCaseClick[0].classList.remove("caseSelectionne");
                            this.tabCaseClick.pop();
                            this.tabCaseClick.push(this.tabCase[ligne][colonne]);
                            this.tabCaseClick[0].classList.add("caseSelectionne");
                        } else {
                            this.tabCaseClick.push(this.tabCase[ligne][colonne]);

                            this.tabCaseClick[0].classList.add("caseSelectionne");
                        }
                    });
                }
            }
        }
        //listener sur clavier
        window.addEventListener("keypress", (evt) => {
            let lettreTape = evt.key.toUpperCase();
            //remplit la case si elle est selectionné
            if (this.tabCaseClick.length > 0) {
                this.tabCaseClick[0].innerText = lettreTape;
                let ligne = parseInt(this.tabCaseClick[0].dataset.ligne);
                let colonne = parseInt(this.tabCaseClick[0].dataset.colonne);
                this.ctrl.reçoitMessageDeLaPresentation(MESSAGE.LETTRE, [ligne, colonne, lettreTape]);
            }

        });
    }

}




class CtrlGrille extends Ctrl {
    constructor(abs, pres) {
        super(abs, pres);
    }

    // toutes les méthodes permette de réceptionner les message envoyé par les autres agent PAC

    reçoitMessageDUnEnfant(message, piecejointe, ctrl) {
        let result = "";

        // l'utilisateur souhaite tricher
        if (message === MESSAGE.CLICK_TRICHE) {
            this.abs.reçoitMessage(message);
        } else if (message === MESSAGE.CLICK_REJOUER) {
            this.abs.reçoitMessage(message);
        } else if (message === MESSAGE.CLICK_INDICE) {
            this.pres.reçoitMessage(message);
        }
        else {
            result = super.reçoitMessageDunEnfnant(message, piecejointe, ctrl)
        }
        return result;
    }

    reçoitMessageDeLAbstraction(message, piecejointe) {
        let result = "";
        if (message === MESSAGE.LETTRE_JUSTE) {
            this.pres.reçoitMessage(message, piecejointe);
        } else if (message === MESSAGE.CLICK_INDICE_RENVOI) {
            this.pres.reçoitMessage(message, piecejointe);
        }
        else if (message === MESSAGE.DIFFICULTE) {
            this.enfants.forEach(enfant => {
                enfant.reçoitMessageDuParent(message, piecejointe);
            });
        }
        else if (message === MESSAGE.GAGNER) {
            this.majJoueur();
            this.enfants.forEach(enfant => {
                enfant.reçoitMessageDuParent(message);
            });
        }
        else if (message === MESSAGE.CLICK_TRICHE) {
            this.pres.reçoitMessage(message, piecejointe);
        } else if (message === MESSAGE.CLICK_REJOUER) {
            this.pres.reçoitMessage(message, piecejointe);
        } else {
            result = super.reçoitMessageDeLAbstraction(message, piecejointe);
        }
        return result;
    }

    reçoitMessageDeLaPresentation(message, piecejointe) {
        let result = "";
        if (message === MESSAGE.LISTE_MOTS) {
            result = this.abs.reçoitMessage(message);
        } else if (message === MESSAGE.CLICK_INDICE) {
            this.abs.reçoitMessage(message, piecejointe);
        }
        else if (message === MESSAGE.LISTE_INDICES) {
            result = this.abs.reçoitMessage(message);
        } else if (message === MESSAGE.LETTRE) {
            this.abs.reçoitMessage(message, piecejointe);
        } else if (message === MESSAGE.REJOUER_MAJ) {
            this.abs.reçoitMessage(message);
        }
        else {
            result = super.reçoitMessageDeLaPresentation(message, piecejointe);
        }
        return result;
    }

    /**
     * permet de mettre a jour la liste de joueur
     */
    majJoueur() {
        chargementJoueur = JSON.stringify(chargementJoueur);
        localStorage.setItem("listeJoueur", chargementJoueur);
    }

    /**
     * envoie des message sur chaque agent afin de lancer l'initialisation
     */
    init() {
        this.pres.reçoitMessage(MESSAGE.INIT);
        this.abs.reçoitMessage(MESSAGE.DIFFICULTE);
        this.enfants.forEach(enfant => {
            enfant.reçoitMessageDuParent(MESSAGE.INIT);
        });
    }
}
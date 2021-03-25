class Case {/**
 * construteur de Case.js
 * @param {*} ligne 
 * @param {*} colonne 
 * @param {*} mine 
 * @param {*} div 
 */
    constructor(ligne, colonne, mine, div) {
        this.mine = mine;
        this.ligne = ligne;
        this.colonne = colonne;
        this.drapeau = false;
        this.indice = 0;
        this.decouvert = false;
        this.div = div;
        
        if (this.mine) {
            this.image = document.createElement("img");
            this.image.src = "assets/images/demineur/mine.jpg";
            this.image.width = 80;
            this.image.height = 80;
            this.image.dataset.mine = true;
            this.image.dataset.ligne = this.ligne;
            this.image.dataset.colonne = this.ligne;
        }//assigne l'image mine si c'est une mine
         if (!this.mine) {
            this.image = document.createElement("img");
            this.image.src = "assets/images/demineur/caseVide.png";
            this.image.width = 80;
            this.image.height = 80;
            this.image.dataset.ligne = this.ligne;
            this.image.dataset.colonne = this.ligne;
        }//assigne l'image case vide si c'est une case vide
    }

}
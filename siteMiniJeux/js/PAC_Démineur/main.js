window.addEventListener("DOMContentLoaded", () => {
    //selection de niveau
    let niveau = localStorage.getItem('niveau');
    //creation de la structure du PAC de Grille
    let absGrille = new AbsGrille();
    let presGrille = new PresGrille(niveau);
    let ctrlGrille = new CtrlGrille(absGrille, presGrille);
    //creation de la structure du PAC de Score

    let absScore = new AbsScore();
    let presScore = new PresScore();
    let ctrlScore = new CtrlScore(absScore, presScore);

    ctrlGrille.addEnfant(ctrlScore);// lien entre le ctrl de Grille et de Score

    ctrlGrille.init();//init de Grille
});


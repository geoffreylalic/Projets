//initialisation du modèle PAC
window.addEventListener("DOMContentLoaded", () => {
    //selection de niveau
    let niveau = localStorage.getItem('niveau');

    let absGrille = new AbsGrille(niveau);
    let presGrille = new PresGrille();
    let ctrlGrille = new CtrlGrille(absGrille, presGrille);

    let absScore = new AbsScore();
    let presScore = new PresScore();
    let ctrlScore = new CtrlScore(absScore,presScore);

    ctrlGrille.addEnfant(ctrlScore);
    ctrlGrille.init();

    
});

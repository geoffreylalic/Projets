package moteur;

import app.Application;
import donnees.Identification;
import donnees.PiocheBatimentVisible;
import donnees.PiocheOuvrierVisible;
import donnees.action.Action;
import donnees.action.ActionPasserAction;
import moteur.reseau.EchangesAvecLeClient;
import moteur.reseau.EnvoiDesMessages;
import moteur.reseau.RéceptionDesMessages;
import moteur.vue.VueServeur;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Serveur extends Application implements RéceptionDesMessages {

    private Moteur moteur;
    private EnvoiDesMessages connexion;

    private Object synchroAttenteDebut = new Object();
    /**
     * pour stocker l'action choisie : une seule variable, cela ne marche que pour des demandes séquentielles (on ne fait pas une autre demande tant qu'on n'a pas reçue la réponse
     */
    Action actionChoisie;

    public static void main(String [] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Serveur serveur = new Serveur("127.0.0.1",10101, Integer.parseInt(args[0]), args[1]);
        serveur.démarrer();
        if (args.length>2) {
            if (args[2].equals("500")) {
                serveur.lancer500Partie();
            } else {
                serveur.lancerPartie();
            }
        } else {
            serveur.lancerPartie();
        }
    }


    public void lancerPartie() {
        getVue().afficheMessage("on attend que tout le monde soit là");

        synchronized (synchroAttenteDebut) {
            try {
                synchroAttenteDebut.wait();
            } catch (InterruptedException e) {
                getVue().afficheMessageErreur("on a été interrompu, on n'a pas pu attendre que tout le monde soit là");
            }
        }

        getVue().afficheMessage("on peut commencer la partie");
        // lancement de la partie

        Identification[] joueurs = getMoteur().getJoueurs();
        moteur.getPaquetOuvrier().getPaquetOuvrier().size();
        for (Identification j : joueurs) {
            int min = 0;
            int max =   moteur.getPaquetOuvrier().getSizePaquetOuvrier();
            int numRand = (int) (min + (Math.random() * (max - min)));
            String typeApprenti =  moteur.getPaquetOuvrier().getCartePaquetOuvrier(numRand).getType();
            while (typeApprenti.contains("apprenti") == false) {
                numRand = (int) (min + (Math.random() * (max - min))); //tant que c'est pas un apprentit, on pioche pas
                typeApprenti =  moteur.getPaquetOuvrier().getCartePaquetOuvrier(numRand).getType();
            }
            moteur.getInventaireDuJoueur(j).getMainOuvrier().add( moteur.getPaquetOuvrier().getCartePaquetOuvrier(numRand));
            moteur.getPaquetOuvrier().retirerCartePaquetOuvrier(numRand);
        }


        while (getMoteur().estPartieFinie() == false) {
            faireUnTour(joueurs, false);
        }

        fin();

    }

    public void lancer500Partie() {
        int nbPartie = 1;

        getVue().afficheMessage("on attend que tout le monde soit là");

        synchronized (synchroAttenteDebut) {
            try {
                synchroAttenteDebut.wait();
            } catch (InterruptedException e) {
                getVue().afficheMessageErreur("on a été interrompu, on n'a pas pu attendre que tout le monde soit là");
            }
        }

        getVue().afficheMessage("on peut commencer la partie");
        // lancement de la partie

        Identification[] joueurs = getMoteur().getJoueurs();
        moteur.getPaquetOuvrier().getPaquetOuvrier().size();


        while (nbPartie<501) {

            if (nbPartie != 1 ) {
                moteur.getPaquetOuvrier().resetPaquet();
                moteur.getPaquetBatiment().resetPaquet();
            }

            getVue().afficheMessage("début de la partie numéro : "+ nbPartie);

            for (Identification j : joueurs) {
                moteur.resetInventaireDuJoueur(j);
                int min = 0;
                int max =   moteur.getPaquetOuvrier().getSizePaquetOuvrier();
                int numRand = (int) (min + (Math.random() * (max - min)));
                String typeApprenti =  moteur.getPaquetOuvrier().getCartePaquetOuvrier(numRand).getType();
                while (typeApprenti.contains("apprenti") == false) {
                    numRand = (int) (min + (Math.random() * (max - min))); //tant que c'est pas un apprentit, on pioche pas
                    typeApprenti =  moteur.getPaquetOuvrier().getCartePaquetOuvrier(numRand).getType();
                }
                moteur.getInventaireDuJoueur(j).getMainOuvrier().add( moteur.getPaquetOuvrier().getCartePaquetOuvrier(numRand));
                moteur.getPaquetOuvrier().retirerCartePaquetOuvrier(numRand);
            }
            while (getMoteur().estPartieFinie() == false) {
                faireUnTour(joueurs, true);
            }
            nbPartie++;
            Identification gagnant = moteur.getGagnant();
            for (Identification j : joueurs) {
                moteur.getInventaireDuJoueur(j).setAllStats(gagnant.equals(j));
            }
        }
        fin500(moteur.getJoueurs());// todo get celui qui a gagné le plus de partie
    }

    /**
     * pour faire un tour de jeu. Il y a une boucle for pour appeler chaque joueur
     *
     * @param joueurs
     */
    protected void faireUnTour(Identification[] joueurs, boolean v500) {
        for (Identification j : joueurs) {
            moteur.getInventaireDuJoueur(j).resetNbAction();
            moteur.getInventaireDuJoueur(j).resetNbPasserDansTour();

            while (moteur.getInventaireDuJoueur(j).getNbAction() > 0) {
                getConnexion().demandeAuJoueurDeJouer(j, getMoteur().getInventaireDuJoueur(j), new PiocheBatimentVisible(moteur.getPiocheVisibleBatiment()), new PiocheOuvrierVisible(moteur.getPiocheVisibleOuvrier()));
                if (!v500) {
                    getVue().afficheMessage("on attend que " + j + " donne son choix");
                }
                synchronized (synchroAttenteDebut) {
                    try {
                        synchroAttenteDebut.wait();
                    } catch (InterruptedException e) {
                        getVue().afficheMessageErreur("on a été interrompu, on n'a pas pu attendre que " + j + " ait joué");
                    }
                }
                // il faut récupérer l'action du joueur, qui a été reçue via socketIO
                // et l'appliquer... on pourrait faire des vérifications (à placer dans les actions)
                Action action = getEtResetActionChoisie();
                if (action.verifier(getMoteur())) {
                    action.appliquerAction(getMoteur());
                }
                else {
                    action = new ActionPasserAction(j);
                    action.appliquerAction(getMoteur());
                }
            }
        }
    }

    public Serveur(String ip, int port, int nbJoueurs, String nomPaquet) {
        setVue(new VueServeur());
        setMoteur(new Moteur(this, nbJoueurs, nomPaquet));
        setConnexion(new EchangesAvecLeClient(ip, port, this));
    }

    public void démarrer() {
        getConnexion().permettreConnexion();
    }

    @Override
    public boolean nouveauJoueur(donnees.Identification id) {
        boolean resultat =  getMoteur().ajouterJoueur(id);
        String msg = "Acceptation de ";
        if (! resultat) msg = "Refus de ";
        getVue().afficheMessage(msg+id);

        synchronized (synchroAttenteDebut) {
            if (getMoteur().estPartieComplete()) {
                getVue().afficheMessageErreur("on notifie...");
                synchroAttenteDebut.notify();
            }
        }
        return resultat;
    }

    private void fin() {
        getConnexion().envoyerSignalFin(getMoteur().getGagnant());
        statistiquePartie(getMoteur().getJoueurs());
    }

    private void fin500 (Identification[] idJoueurs) {
        statistique500Partie(idJoueurs);
        getConnexion().envoyerSignalFin(getGagnant500());
    }

    private Identification getGagnant500 () {
        Identification gagnant = moteur.getJoueurs()[0];

        for (Identification j:
             moteur.getJoueurs()) {
            if (moteur.getInventaireDuJoueur(j).getNbVictoireStats() > moteur.getInventaireDuJoueur(gagnant).getNbVictoireStats()) {
                gagnant = j;
            }
        }
        return gagnant;
    }

    /**
     * adaptation de la liaison vue - application avec la liaison connexion - serveur
     * @param msg le message à afficher
     */
    @Override
    public void transfèreMsg(String msg) {
        transfèreMessage(msg);
    }

    @Override
    public void transfèreAction(Action action) {
        actionChoisie = action;
        synchronized (synchroAttenteDebut) {
         // getVue().afficheMessageErreur("on notifie... la réception de la 'action " + action);
            synchroAttenteDebut.notify();
        }
    }

    protected Action getEtResetActionChoisie() {
        Action résultat = actionChoisie;
        actionChoisie = null;
        return résultat;
    }

    public void setMoteur(Moteur moteur) {
        this.moteur = moteur;
    }

    public Moteur getMoteur() {
        return moteur;
    }

    public void setConnexion(EnvoiDesMessages connexion) {
        this.connexion = connexion;
    }

    public EnvoiDesMessages getConnexion() {
        return connexion;
    }

    public void statistiquePartie( Identification[] joueurs){
        for (Identification j : joueurs) {
            getVue().afficheMessage("le joueur " + j + " a " + moteur.getInventaireDuJoueur(j).getEcus()
                    + " piece, " + moteur.getInventaireDuJoueur(j).getCouronnes()
                    + " point de partie, Action: recruter " + moteur.getInventaireDuJoueur(j).getMainOuvrier().size()
                    + ", revenus des batiments " + moteur.getInventaireDuJoueur(j).getRevenusBatiment()
                    + "\n");
        }
        getVue().afficheMessage("le gagnant est " + getMoteur().getGagnant());
    }

    public void statistique500Partie( Identification[] joueurs){
        for (Identification j : joueurs) {
            getVue().afficheMessage("le joueur " + j + " a gagné " + moteur.getInventaireDuJoueur(j).getNbVictoireStats()
                    + " parties, " +  moteur.getInventaireDuJoueur(j).getCouronnesStats()/500
                    + " couronnes en moyenne, " + moteur.getInventaireDuJoueur(j).getRevenusBatimentStats()/500
                    + " , revenus des batiments en moyenne par partie " + moteur.getInventaireDuJoueur(j).getNbRecrue()/500
                    + " nombre de recrue en moyenne par partie"
                    + "\n");
        }
    }
}


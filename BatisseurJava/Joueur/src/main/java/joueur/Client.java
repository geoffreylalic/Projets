package joueur;

import app.Application;
import cartes.CarteBatiment;
import cartes.CarteOuvrier;
import donnees.Identification;
import donnees.Inventaire;
import donnees.PiocheBatimentVisible;
import donnees.PiocheOuvrierVisible;
import donnees.action.Action;
import joueur.ia.Bot;
import joueur.ia.BotBatisseur;
import joueur.reseau.EchangesAvecLeServeur;
import joueur.vue.VueClient;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.github.javafaker.Faker;

public class Client extends Application {


    private Identification identification;
    private EchangesAvecLeServeur connexion;
    private Bot ia;

    public static void main(String [] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Faker nameFaker = new Faker();


        Client client = new Client(nameFaker.harryPotter().character(), new BotBatisseur());

        client.rejoindreUnePartie();
    }


    public Client(String nom,  Bot bot) {
        setIdentification(new Identification(nom));
        setVue(new VueClient(this));
        setIa(bot);
        setConnexion(new EchangesAvecLeServeur("http://127.0.0.1:10101", this));
    }


    public void rejoindreUnePartie() {
        getVue().afficheMessage("en attente de déconnexion");
        getConnexion().seConnecter();
    }



    public void transfèreMessage(String msg) {
        getVue().afficheMessage(msg);
    }

    public void signaleErreur(String err) {
        getVue().afficheMessageErreur(err);
    }

    public void aprèsConnexion() {
        getVue().afficheMessage("on est connecté ! et on s'identifie ");
        this.connexion.envoyerId(getIdentification());
    }

    public void finPartie() {
        getVue().finit();
        getConnexion().stop();
    }


    public void résultat(boolean gagné) {
        getVue().afficheMessage(" C'est fini ");

        if (gagné) getVue().afficheMessage("j'ai gagné");
        else getVue().afficheMessage("j'ai perdu");

        finPartie();

    }


    public void jouer(Inventaire inv, PiocheBatimentVisible piocheBatiment, PiocheOuvrierVisible piocheOuvrier) {
        getVue().afficheMessage("c'est à moi de jouer "+inv);
        Action actionChoisie = getIa().jouer(inv, getIdentification(), piocheBatiment, piocheOuvrier);
        getVue().afficheMessage("je joue "+actionChoisie);
        getConnexion().envoyerActionChoisie(actionChoisie);
    }

    /********* méthodes pour les propriétés **********/


    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    public void setConnexion(EchangesAvecLeServeur connexion) {
        this.connexion = connexion;
    }

    public EchangesAvecLeServeur getConnexion() {
        return connexion;
    }


    public void setIa(Bot ia) {
        this.ia = ia;
    }

    public Bot getIa() {
        return ia;
    }
}
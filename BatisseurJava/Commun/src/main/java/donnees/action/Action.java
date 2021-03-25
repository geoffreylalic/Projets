package donnees.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import donnees.Identification;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        //@JsonSubTypes.Type(value = ActionRisquée.class, name = "ActionRisquée"),
        //@JsonSubTypes.Type(value = ActionSure.class, name = "ActionSure"),
        @JsonSubTypes.Type(value = ActionPasserAction.class, name = "ActionPasserAction"),
        @JsonSubTypes.Type(value = ActionAcheterAction.class, name = "ActionAcheterAction"),
        @JsonSubTypes.Type(value = ActionPiocherBatiment.class, name = "ActionPiocherBatiment"),
        @JsonSubTypes.Type(value = ActionPiocherOuvrier.class, name = "ActionPiocherOuvrier"),
        @JsonSubTypes.Type(value = ActionAffecterOuvrier.class, name = "ActionAffecterOuvrier")
})
public abstract class Action {
    /**
     * le joueur qui fait le choix de cette action
      */
   private Identification joueur;

    public Identification getJoueur() {
        return joueur;
    }

    public void setJoueur(Identification joueur) {
        this.joueur = joueur;
    }

    /**
     * application de l'action choisie, implémentée dans les classes concrètes
     * @param moteur le moteur qui rappelle cette action pour fournir l'inventaire et les autres informations nécessaires
     */
    public abstract void appliquerAction(MoteurDeJeu moteur) ;

    /**
     * pour vérifier si l'action est légale
     * méthode à surcharger dans chaque classe concrète, avec une implémentation par défaut
     * Par défaut : retourne toujours true
     * @param moteur : objet qui gère la partie en cours
     * @return true si l'action est légale, false sinon
     */
    public abstract boolean  verifier(MoteurDeJeu moteur) ;


    }


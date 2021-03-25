package donnees.action;

import cartes.CarteBatiment;
import cartes.CarteOuvrier;
import donnees.Identification;
import donnees.Inventaire;
import donnees.PiocheBatimentVisible;
import donnees.PiocheOuvrierVisible;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActionPiocherOuvrierTest {
    //on créer un faux moteur de jeu
    @Mock
    MoteurDeJeu moteur;

    @Test
    public void testPiocherOuvrier() {

        //on créé l'inventaire testé
        Inventaire invTest = new Inventaire();
        PiocheOuvrierVisible piochetest = new PiocheOuvrierVisible();

        // quand le faux moteur demande l'inventaire du joueur dans la logique de l'action on renvoie systématiquement l'inventaire testé, peu importe l'ID.
        when(moteur.getInventaireDuJoueur(any(Identification.class))).thenReturn(invTest);
        // quand le faux moteur demande La pioche apprente dans la logique de l'action on renvoie systématiquement la pioche visble testé.
        when(moteur.getPiocheOuvrierVisible()).thenReturn(piochetest);

        //on créé la pioche ouvrier
        CarteOuvrier c1 = new CarteOuvrier(1,"compagnon",1,1,1,1,1);
        CarteOuvrier c2 = new CarteOuvrier(1, "compagnon",4,1,1,1,1);
        CarteOuvrier c3 = new CarteOuvrier(1, "manœuvre",3,2, 0, 0,1);
        CarteOuvrier c4 = new CarteOuvrier(1, "manœuvre",3,1,2, 0, 0);
        CarteOuvrier c5 = new CarteOuvrier(1, "manœuvre",3, 0, 0,1,2);
        piochetest.getPiocheOuvrierVisible().add(c1);
        piochetest.getPiocheOuvrierVisible().add(c2);
        piochetest.getPiocheOuvrierVisible().add(c3);
        piochetest.getPiocheOuvrierVisible().add(c4);
        piochetest.getPiocheOuvrierVisible().add(c5);

        Identification idTest = new Identification("cobaye");
        ActionPiocherOuvrier p = new ActionPiocherOuvrier(idTest, c2);
        p.appliquerAction(moteur);

        // On voit si il a piocher la meme carte.
        assertEquals(invTest.getMainOuvrier().get(0), c2);
    }

}

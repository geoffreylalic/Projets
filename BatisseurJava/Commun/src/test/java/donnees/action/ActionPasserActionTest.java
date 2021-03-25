package donnees.action;

import donnees.Identification;
import donnees.Inventaire;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ActionPasserActionTest {

    //on créer un faux moteur de jeu
    @Mock
    MoteurDeJeu moteur;

    @Test
    public void testPasserAction () {

        //on créé l'inventaire testé
        Inventaire invTest = new Inventaire();

        // quand le faux moteur demande l'inventaire du joueur dans la logique de l'action on renvoie systématiquement l'inventaire testé, peu importe l'ID.
        when(moteur.getInventaireDuJoueur(any(Identification.class))).thenReturn(invTest);

        Identification idTest =  new Identification("cobaye");
        ActionPasserAction passer = new ActionPasserAction(idTest);
        passer.appliquerAction(moteur);

        //première fois qu'on passe le tour : + 1 pièce
        assertEquals(11, invTest.getEcus());

        ActionPasserAction passer2 = new ActionPasserAction(idTest);
        passer2.appliquerAction(moteur);

        //deuxième fois qu'on passe le tour : +2 pièces
        assertEquals(13, invTest.getEcus());

        ActionPasserAction passer3 = new ActionPasserAction(idTest);
        passer2.appliquerAction(moteur);

        //troisième fois qu'on passe le tour : +3 pièces
        assertEquals(16, invTest.getEcus());
    }

}

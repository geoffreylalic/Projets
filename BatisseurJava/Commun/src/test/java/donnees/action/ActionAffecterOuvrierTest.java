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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class ActionAffecterOuvrierTest {
    @Mock
    MoteurDeJeu moteur;

    @Test
    public void testAffecterOuvrier() {

        //on créé l'inventaire testé
        Inventaire invTest = new Inventaire();
        PiocheOuvrierVisible piochetest = new PiocheOuvrierVisible();
        // quand le faux moteur demande l'inventaire du joueur dans la logique de l'action on renvoie systématiquement l'inventaire testé, peu importe l'ID.
        when(moteur.getInventaireDuJoueur(any(Identification.class))).thenReturn(invTest);
        // quand le faux moteur demande La pioche apprente dans la logique de l'action on renvoie systématiquement la pioche visble testé.
        when(moteur.getPiocheOuvrierVisible()).thenReturn(piochetest);
//on créé la pioche ouvrier
        CarteOuvrier o1 = new CarteOuvrier(1,"compagnon",1,1,1,1,1);
        CarteOuvrier o2 = new CarteOuvrier(1, "compagnon",4,1,1,1,1);
        CarteOuvrier o3 = new CarteOuvrier(1, "manœuvre",3,2, 0, 0,1);
        CarteOuvrier o4 = new CarteOuvrier(1, "manœuvre",3,1,2, 0, 0);
        CarteOuvrier o5 = new CarteOuvrier(1, "manœuvre",3, 0, 0,1,2);
        piochetest.getPiocheOuvrierVisible().add(o1);
        piochetest.getPiocheOuvrierVisible().add(o2);
        piochetest.getPiocheOuvrierVisible().add(o3);
        piochetest.getPiocheOuvrierVisible().add(o4);
        piochetest.getPiocheOuvrierVisible().add(o5);

        Identification idTest = new Identification("cobaye");
        ActionPiocherOuvrier p = new ActionPiocherOuvrier(idTest, o2);
        p.appliquerAction(moteur);


        // On voit si il a piocher la meme carte.
        assertEquals(invTest.getMainOuvrier().get(0), o2);
        //on créé la fausse pioche
        PiocheBatimentVisible piochetest2 = new PiocheBatimentVisible();
        CarteBatiment c1 = new CarteBatiment("la tonnelle",0,1,0,1,8,0,0,0,0,0);
        CarteBatiment c2 = new CarteBatiment("la cabane perchée",0,2,1,0,6,1,0,0,0,0);
        CarteBatiment c3 = new CarteBatiment("la hutte de paille",0,1,0,2,6,1,0,0,0,0);
        CarteBatiment c4 = new CarteBatiment("le lavoir",0,1,0,2,6,1,0,0,0,0);
        CarteBatiment c5 = new CarteBatiment("le pont en pierre",0,2,1,0,6,1,0,0,0,0);
        piochetest2.getPiocheBatimentVisible().add(c1);
        piochetest2.getPiocheBatimentVisible().add(c2);
        piochetest2.getPiocheBatimentVisible().add(c3);
        piochetest2.getPiocheBatimentVisible().add(c4);
        piochetest2.getPiocheBatimentVisible().add(c5);
        // quand le faux moteur demande La pioche apprente dans la logique de l'action on renvoie systématiquement la pioche visble testé.
        when(moteur.getPiocheBatimentVisible()).thenReturn(piochetest2);

        ActionPiocherBatiment p2 = new ActionPiocherBatiment(idTest, c2);
        p2.appliquerAction(moteur);
        // On voit si il a piocher la meme carte.
        assertEquals(c2, invTest.getMainBatiment(0));
        ActionAffecterOuvrier affec = new ActionAffecterOuvrier(idTest,invTest.getMainOuvrier().get(0),invTest.getMainBatiment().get(0));
        affec.appliquerAction(moteur);
        // Il est plus disponible car il est affecté
        assertFalse(invTest.getMainOuvrier(0).getDisponible());


       assertEquals(1,invTest.getMainBatiment(0).getSavoir());
        assertEquals(0,invTest.getMainBatiment(0).getTuile());
        ActionPiocherOuvrier p3 = new ActionPiocherOuvrier(idTest, o3);
        p3.appliquerAction(moteur);
        ActionAffecterOuvrier affec2 = new ActionAffecterOuvrier(idTest,invTest.getMainOuvrier().get(1),invTest.getMainBatiment().get(0));
        affec2.appliquerAction(moteur);
        assertEquals(0,invTest.getMainBatiment(0).getTuile());
        assertEquals(0,invTest.getMainBatiment(0).getSavoir());
        assertEquals(9,invTest.getEcus());
        assertTrue(invTest.getMainOuvrier(0).getDisponible());
        assertTrue(invTest.getMainOuvrier(1).getDisponible());
        assertTrue(invTest.getMainBatiment(0).getConstruit());


    }
}

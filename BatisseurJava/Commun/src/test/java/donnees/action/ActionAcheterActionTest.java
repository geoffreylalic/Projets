package donnees.action;

import donnees.Identification;
import donnees.Inventaire;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActionAcheterActionTest {
    @Mock
    MoteurDeJeu moteur;

    @Test
    public void testAcheterAction(){


     Inventaire invTest = new Inventaire();
     when(moteur.getInventaireDuJoueur(any(Identification.class))).thenReturn(invTest);
     Identification idTest =  new Identification("cobaye");
     ActionAcheterAction achet = new ActionAcheterAction(idTest);

     achet.appliquerAction(moteur);
     assertEquals(4, invTest.getNbAction());

     assertEquals(invTest.getEcus(),5);


    }
}

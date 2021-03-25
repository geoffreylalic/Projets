package cartes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarteBatimentTest {
    @Test
    public void machineTest(){
        CarteOuvrier ouvrier1 = new CarteOuvrier(0,"ouvrier",1,0,1,1,2);
        CarteBatiment batiment1 = new CarteBatiment("machine",1,2,1,0,1,3,1,5,2,1);
        batiment1.addChantierOuvrier(ouvrier1);
        assertEquals(1,batiment1.getChantierOuvrier().size());
        CarteOuvrier machine;
        machine = batiment1.isMachine();
        assertEquals("machine",machine.getType());
        assertEquals(1,machine.getPierre());
        assertEquals(5,machine.getTuile());
        assertEquals(2,machine.getSavoir());
        assertEquals(1,machine.getBois());
        assertEquals(0,machine.getCout());
        assertEquals(true,machine.getDisponible());
    }
}

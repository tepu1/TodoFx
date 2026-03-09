import fi.jyu.ohj2.ttkelaw.todo.Main;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

public class KeskiarvoTesti {
    @Test
    void keskiarvoLaskeeOikein() {
        // 1. Arrange = Valmistellaan testiin liittyvä data
        List<Integer> luvut = List.of(1,2,3,4,5);
        //2. Act = Suoritetaan testattava toiminto
        double tulos = Main.keskiarvo(luvut, 10);
        //3. Assert = Varmistetaan, että tulos vastaa odotettua
        assertEquals(3.0, tulos, "Keskiarvon pitäisi olla 3.0");
    }



}

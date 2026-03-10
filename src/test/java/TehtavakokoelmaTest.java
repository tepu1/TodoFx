import fi.jyu.ohj2.ttkelaw.todo.model.Tehtava;
import fi.jyu.ohj2.ttkelaw.todo.model.Tehtavakokoelma;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class TehtavakokoelmaTest {

    @Test
    void tehtavakokoelmaTehtavanLisaysToimii() {

        Tehtavakokoelma tehtavakokoelma = new Tehtavakokoelma(Path.of("tehtavattest.json"));
        tehtavakokoelma.lisaaTehtava("Mene nukkumaan");
        assertEquals(1, tehtavakokoelma.getTehtavat().size(), "Uusi tehtävä lisättiin kokoelmaan");

        Tehtava tehtava = tehtavakokoelma.getTehtavat().getFirst();
        assertEquals("Mene nukkumaan", tehtava.getOtsikko());
        assertFalse(tehtava.isTehty(), "Uusi tehtävä on tekemätön");

    }
    @Test
    void tehtavakokoelmaTehtavanPoistoToimii() {
        Tehtavakokoelma tehtavakokoelma = new Tehtavakokoelma(Path.of("tehtavattest.json"));
        tehtavakokoelma.lisaaTehtava("Mene nukkumaan");
        Tehtava tehtava = tehtavakokoelma.getTehtavat().getFirst();
        tehtavakokoelma.poistaTehtava(tehtava);
        assertEquals(0, tehtavakokoelma.getTehtavat().size(), "Tehtävän poisto toimii");
    }
    @Test
    void tehtavakokoelmaTyhjanLisaysEiToimi() {
        Tehtavakokoelma tehtavakokoelma = new Tehtavakokoelma(Path.of("tehtavattest.json"));
        tehtavakokoelma.lisaaTehtava(null);
        assertEquals(0, tehtavakokoelma.getTehtavat().size(), "null-otsikolla ei voi lisätä tehtävää");

        tehtavakokoelma.lisaaTehtava("");
        assertEquals(0,tehtavakokoelma.getTehtavat().size(), "Tyhjää tehtävää ei voi lisätä");

        tehtavakokoelma.lisaaTehtava("   ");
        assertEquals(0, tehtavakokoelma.getTehtavat().size(), "Vain välilyöntiotsikolla ei voi lisätä tehtävää");

    }
    @Test
    void tehtavakokoelmaEiValilyontejaAlussaJaLopussa() {
        Tehtavakokoelma tehtavakokoelma = new Tehtavakokoelma(Path.of("tehtavattest.json"));
        tehtavakokoelma.lisaaTehtava(" Mene nukkumaan ");
        String otsikko = tehtavakokoelma.getTehtavat().getFirst().getOtsikko();
        char eka = otsikko.charAt(0);
        char vika = otsikko.charAt(otsikko.length() - 1);
        assertNotEquals(" ", eka, "Ensimmäinen ei ole välilyönti");
        assertNotEquals(" ", vika, "Viimeinen ei ole välilyönti");
    }
    @Test
    void tehtavakokoelmaKaksiSamanNimistaTehtavaa() {
        Tehtavakokoelma tehtavakokoelma = new Tehtavakokoelma(Path.of("tehtavattest.json"));
        tehtavakokoelma.lisaaTehtava("Mene nukkumaan");
        tehtavakokoelma.lisaaTehtava("Mene nukkumaan");
        assertEquals(2, tehtavakokoelma.getTehtavat().size(), "Saman nimisen tehtävän lisäys onnistuu");
    }
    @Test
    void tehtavakokoelmaTehtavanMerkintaToimii() {
        Tehtavakokoelma tehtavakokoelma = new Tehtavakokoelma(Path.of("tehtavatttest.json"));
        tehtavakokoelma.lisaaTehtava("Herää");
        tehtavakokoelma.lisaaTehtava("Herää");

        Tehtava tehtava = tehtavakokoelma.getTehtavat().getFirst();
        tehtava.setTehty(true);

        Tehtava tehtava2 = tehtavakokoelma.getTehtavat().get(1);

        assertTrue(tehtava.isTehty(), "ensimmäinen listaan lisätty samanniminen tehtävä on tehty");
        assertFalse(tehtava2.isTehty(), "toisena listaan lisätty tehtävä ei ole tehty");

    }
    @Test
    void tehtavakokoelmaJarjestysOikein() {
        Tehtavakokoelma tehtavakokoelma = new Tehtavakokoelma(Path.of("tehtavattest.json"));
        tehtavakokoelma.lisaaTehtava("Herää");
        tehtavakokoelma.lisaaTehtava("Syö");

        assertEquals("Herää", tehtavakokoelma.getTehtavat().getFirst().getOtsikko(), "Ensimmäinen lisätty on listassa ekana");
        assertEquals("Syö", tehtavakokoelma.getTehtavat().get(1).getOtsikko(), "Toinen listaan lisätty tehtävä on toisena");
    }


}

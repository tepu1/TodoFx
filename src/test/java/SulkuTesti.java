import fi.jyu.ohj2.ttkelaw.todo.Main;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class SulkuTesti {

    @Test
    void sulutTyhjaSyote() {
        String mjono = "";
        int tulos = Main.sulut(mjono);
        assertEquals(0, tulos, "Tyhjän mjonon tulos pitäisi olla 0");
    }
    @Test
    void sulutKolmeSisakkain() {
        String mjono = "([{}])";
        int tulos = Main.sulut(mjono);
        assertEquals(3, tulos, "kolmen sisäkkäisen parin tulos pitäisi olla 3");
    }
    @Test
    void sulutKirjaimetSivuutetaan() {
        String mjono = "a(b)c";
        int tulos = Main.sulut(mjono);
        assertEquals(1, tulos, "Kirjaimet sivuutetaan, tulos on 1");
    }
    @Test
    void sulutPariPuuttuu() {
        String mjono = "())";
        int tulos = Main.sulut(mjono);
        assertEquals(-1, tulos, "Puuttuvien parien tulos aina -1");
    }
    @Test
    void sulutVaaraJarjestys() {
        String mjono = ")(";
        int tulos = Main.sulut(mjono);
        assertEquals(-1, tulos, "Väärin päin sulut, tulos aina -1");
    }

}

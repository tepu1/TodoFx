package fi.jyu.ohj2.ttkelaw.todo;

import javafx.application.Application;

import java.util.*;

public class Main {


    public static double keskiarvo(List<Integer> luvut, int lopetusluku) {
        if (luvut.isEmpty()) {
            throw new IllegalArgumentException("Lista ei saa olla tyhjä");
        }
        int summa = 0;
        int lukujenMaara = 0;
        for (int luku : luvut) {
            if (luku >= lopetusluku) {
                break;
            }
            summa += luku;
            lukujenMaara++;
        }
        return (double) summa / lukujenMaara;
    }

    public static int sulut(String mjono) {

        int lkm = 0;
        Deque<Character> pino = new ArrayDeque<>();
        Map<Character,Character> haku = new HashMap<>(Map.of('}','{',
                ']','[',
                ')', '(',
                '>' , '<'));

        if (mjono.isEmpty()) {
            return 0;
        }
        for (char merkki : mjono.toCharArray()) {
            if (haku.containsValue(merkki)) {
                pino.push(merkki);
            }
            else if (haku.containsKey(merkki)) {

                if (pino.isEmpty()) {
                    return -1;
                }
                char edellinen = pino.pop();
                if (edellinen != haku.get(merkki)) {
                    return -1;
                }
                lkm++;
            }
        }
        if (!pino.isEmpty()) {
            return -1;
        }
        return lkm;
    }

    public static void main(String[] args) { Application.launch(App.class, args); }
}

package fi.jyu.ohj2.ttkelaw.todo.data;

public class Tehtava {
    private String teksti;
    private boolean tehty;


    public Tehtava() {}

    public Tehtava(String teksti, boolean tehty) {

        this.teksti = teksti;
        this.tehty = tehty;
    }

    public String getTeksti() {
        return teksti;
    }

    public boolean isTehty() {
        return tehty;
    }




}

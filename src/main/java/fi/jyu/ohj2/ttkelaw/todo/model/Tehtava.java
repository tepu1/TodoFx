package fi.jyu.ohj2.ttkelaw.todo.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tehtava {
    private StringProperty teksti = new SimpleStringProperty("");
    private BooleanProperty tehty = new SimpleBooleanProperty(false);


    public Tehtava() {}

    public Tehtava(String teksti, boolean tehty) {

        setTeksti(teksti);
        setTehty(tehty);
    }

    public String getTeksti() {
        return teksti.get();
    }
    public void setTeksti(String teksti) {
        this.teksti.set(teksti);
    }
    public StringProperty tekstiProperty() {
        return this.teksti;
    }

    public boolean isTehty() {
        return tehty.get();
    }
    public void setTehty(boolean tehty) {
        this.tehty.set(tehty);
    }
    public BooleanProperty tehtyProperty() {
        return this.tehty;
    }

    @Override
    public String toString() {
        return getTeksti() + ": " + (isTehty() ? "TEHTY" : "EI TEHTY");
    }


}

package fi.jyu.ohj2.ttkelaw.todo.model;

import javafx.beans.property.*;

public class Tehtava {
    private final StringProperty otsikko = new SimpleStringProperty("");
    private final StringProperty kuvaus = new SimpleStringProperty("");
    private final BooleanProperty tehty = new SimpleBooleanProperty(false);
    private final ObjectProperty<Prioriteetti> prioriteetti = new SimpleObjectProperty<>(Prioriteetti.KESKI);

    @SuppressWarnings("unused")
    public Tehtava() {}

    public Tehtava(String otsikko, boolean tehty) {
        setOtsikko(otsikko);
        setTehty(tehty);
    }

    public String getOtsikko() { return otsikko.get(); }
    public void setOtsikko(String otsikko) { this.otsikko.set(otsikko); }
    public StringProperty otsikkoProperty() { return this.otsikko; }

    public boolean isTehty() { return tehty.get(); }
    public void setTehty(boolean tehty) { this.tehty.set(tehty); }
    public BooleanProperty tehtyProperty() { return this.tehty; }

    public String getKuvaus() { return kuvaus.get(); }
    public void setKuvaus(String kuvaus) {this.kuvaus.set(kuvaus); }
    public StringProperty kuvausProperty() { return this.kuvaus; }

    public Prioriteetti getPrioriteetti() { return prioriteetti.get(); }
    public void setPrioriteetti(Prioriteetti prioriteetti) { this.prioriteetti.set(prioriteetti);}
    public ObjectProperty<Prioriteetti> prioriteettiProperty() { return this.prioriteetti; }


    @Override
    public String toString() {
        return getOtsikko() + ": " + (isTehty() ? "TEHTY" : "EI TEHTY");
    }
}

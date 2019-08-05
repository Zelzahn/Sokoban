package sample.stukken;

public class Speler extends Stuk {
    private Stuk lastWalked;

    public Speler() {
        super("sample/stukken/images/Speler.gif", true);
        lastWalked = new Blanco();
    }

    public Stuk getLastWalked() {
        return lastWalked;
    }

    public void setLastWalked(Stuk lastWalked) {
        this.lastWalked = lastWalked;
    }
}

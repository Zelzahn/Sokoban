package sample.stukken;

public class Kist extends Stuk {
    private Stuk lastWalked;

    public Kist() {
        super("sample/stukken/images/Box.gif", true);
        lastWalked = new Blanco();
    }

    public Stuk getLastWalked() {
        return lastWalked;
    }

    public void setLastWalked(Stuk lastWalked) {
        this.lastWalked = lastWalked;
    }
}

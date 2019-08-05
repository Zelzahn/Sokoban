package sample;

import sample.stukken.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

class Model {
    private ArrayList<ArrayList<Stuk>> conf;
    private int[] spelerPos;
    private int aantalLampen;
    private int huidigeAantalGeactiveerdeLampen;

    Model() {
        huidigeAantalGeactiveerdeLampen = 0;
        aantalLampen = 0;
        spelerPos = new int[2];
        conf = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\Friedrich\\IdeaProjects\\Sokoban\\src\\sample\\spelbordEditor.txt")))) {
            String regel = br.readLine();
            int ctr = 0;
            while (regel != null && regel.length() != 0) {
                ArrayList<Stuk> temp = new ArrayList<>();
                for (int i = 0; i < regel.length(); i++) {
                    Stuk s = charToStuk(regel.charAt(i));

                    if (s instanceof Speler) {
                        spelerPos[0] = ctr;
                        spelerPos[1] = i;
                    } else if (s instanceof Lamp) {
                        aantalLampen++;
                    }

                    temp.add(s);
                }
                conf.add(temp);

                regel = br.readLine();
                ctr++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Stuk charToStuk(char c) {
        switch (c) {
            case 'x':
                return new Blok();
            case 'o':
                return new Speler();
            case 'd':
                return new Kist();
            case '0':
                return new Lamp();
            default:
                return new Blanco();
        }
    }

    ArrayList<ArrayList<Stuk>> getSpelbord() {
        return conf;
    }

    void move(int x, int y) {
        if (!conf.get(spelerPos[0] + x).get(spelerPos[1] + y).getSolid()) {
            playerMove(x, y);
        } else if (conf.get(spelerPos[0] + x).get(spelerPos[1] + y) instanceof Kist && !conf.get(spelerPos[0] + 2 * x).get(spelerPos[1] + 2 * y).getSolid()) {
            if (conf.get(spelerPos[0] + 2 * x).get(spelerPos[1] + 2 * y) instanceof Lamp) {
                huidigeAantalGeactiveerdeLampen++;
            }

            kistMove(x, y);
            playerMove(x, y);
        }
    }

    private void kistMove(int x, int y) {
        Stuk volgendeTegel = conf.get(spelerPos[0] + 2 * x).get(spelerPos[1] + 2 * y).getSolid() ? new Blanco() : conf.get(spelerPos[0] + 2 * x).get(spelerPos[1] + 2 * y);

        conf.get(spelerPos[0] + 2 * x).set(spelerPos[1] + 2 * y, conf.get(spelerPos[0] + x).get(spelerPos[1] + y));
        conf.get(spelerPos[0] + x).set(spelerPos[1] + y, ((Kist) conf.get(spelerPos[0] + 2 * x).get(spelerPos[1] + 2 * y)).getLastWalked());

        if (((Kist) conf.get(spelerPos[0] + 2 * x).get(spelerPos[1] + 2 * y)).getLastWalked() instanceof Lamp) {
            huidigeAantalGeactiveerdeLampen--;
        }

        ((Kist) conf.get(spelerPos[0] + 2 * x).get(spelerPos[1] + 2 * y)).setLastWalked(volgendeTegel);
    }

    private void playerMove(int x, int y) {
        Stuk volgendeTegel = conf.get(spelerPos[0] + x).get(spelerPos[1] + y).getSolid() ? new Blanco() : conf.get(spelerPos[0] + x).get(spelerPos[1] + y);

        conf.get(spelerPos[0] + x).set(spelerPos[1] + y, conf.get(spelerPos[0]).get(spelerPos[1]));
        conf.get(spelerPos[0]).set(spelerPos[1], ((Speler) conf.get(spelerPos[0]).get(spelerPos[1])).getLastWalked());

        spelerPos[0] += x;
        spelerPos[1] += y;
        ((Speler) conf.get(spelerPos[0]).get(spelerPos[1])).setLastWalked(volgendeTegel);
    }

    boolean completed() {
        return huidigeAantalGeactiveerdeLampen == aantalLampen;
    }
}

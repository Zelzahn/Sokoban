package sample.stukken;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Stuk extends ImageView {
    private boolean solid;

    Stuk(String path, boolean solid) {
        setImage(new Image(path));
        this.solid = solid;
    }

    public boolean getSolid() {
        return solid;
    }
}

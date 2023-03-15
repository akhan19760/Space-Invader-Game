package shield;

import java.awt.*;
import static game.Essentials.SQUARE_DIM;

class Square extends Rectangle  {

    boolean show;

    Square(int x, int y) {
        super(x, y, SQUARE_DIM, SQUARE_DIM);
        setShow(true);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }
    
    public void setShow(boolean show) {
        this.show = show;
    }
}


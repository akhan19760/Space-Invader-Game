package shield;

import sprites.Mobility;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import static game.Essentials.SQUARE_DIM;

public class Shield {

    private List<Square> squares;

    public Shield(int x, int y) {
        squares=new LinkedList<>();
        for(int i=0; i<4; i++) {
            for (int j = 0; j < 6; j++) {
                squares.add(new Square(x + SQUARE_DIM * j, y + SQUARE_DIM * i));
            }
        }
    }

        public void draw(Graphics g) {
        for(Square square : squares) {
            if(square.show) square.draw(g);
        }
    }
        
    public void Collision(Mobility obj) {
        for(Square square : squares) {
            if(square.show && square.intersects(obj.getBoundary())) {
                square.setShow(false);
                obj.Die();
            }
        }
    }


}



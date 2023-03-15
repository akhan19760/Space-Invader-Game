package sprites;

import static game.Essentials.LASER_HEIGHT;
import static game.Essentials.LASER_SPEED;
import static game.Essentials.LASER_WIDTH;

public class Laser extends Mobility {

    Laser(int x, int y) {
        super(x, y);
        FixImage("laser.png");
        width=LASER_WIDTH;
        height=LASER_HEIGHT;
        dy=-LASER_SPEED;
    }

    @Override
    public void Moving() {
        if(y<=0)
            this.Die();
        super.Moving();
    }

}



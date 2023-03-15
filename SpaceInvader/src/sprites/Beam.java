package sprites;

import static game.Essentials.*;

public class Beam extends Mobility {

    Beam(int x, int y) {
        super(x, y);
        FixImage("beam.png");
        width=BEAM_WIDTH;
        height=BEAM_HEIGHT;
        dy=BEAM_SPEED;
    }

    @Override
    public void Moving() {
        if(y>BASE_LINE-BEAM_HEIGHT)
            this.Die();
        super.Moving();
    }
}

package sprites;

import java.util.Random;
import static game.Essentials.ALIEN_HEIGHT;
import static game.Essentials.ALIEN_WIDTH;

public class Alien extends Mobility {

    Beam beam;
    boolean abtDead;     //for proper projection of explosion
    private Random rand;

    public Beam getBeam() {
        return beam;
    }

    Alien(int x, int y) {
        super(x, y);
        rand = new Random();
        FixImage("alien.png");
        width=ALIEN_WIDTH;
        height=ALIEN_HEIGHT;
        dx=1;
        abtDead=false;
        beam=new Beam(0, 0);
        beam.Die();
    }

    void beamFire() {
        int random = rand.nextInt()%500;
        if(random==1 && !this.beam.show && this.show){
            this.beam.x=this.x+ALIEN_WIDTH/2;
            this.beam.y=this.y+ALIEN_HEIGHT;
            this.beam.show=true;
        }
    }

    void setAbtToDie(boolean abtToDie) {
        this.abtDead = abtToDie;
    }

    @Override
    public void Moving() {
        super.Moving();
    }

}

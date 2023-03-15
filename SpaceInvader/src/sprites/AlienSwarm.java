package sprites;

import game.Game;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import static game.Essentials.*;

public class AlienSwarm  {

    private List<Alien> aliens;
    private Integer numberOfAliens;
    private int alienSpeed;

    public List<Alien> getAliens() {
        return aliens;
    }

    public Integer getNoAliens() {
        return numberOfAliens;
    }

    public void decreaseAliens() {
        numberOfAliens--;
        SCORE+=1;
    }

    public AlienSwarm() {
        aliens = new LinkedList<>();
        for(int i=0; i<4; i++) {
            for (int j = 0; j < 9; j++) {
                aliens.add(new Alien(ALIEN_X + 32 * j, ALIEN_Y + 32 * i));
            }
        }
        numberOfAliens=36;
        alienSpeed=1;
    }

    public void draw(Graphics g, Game game) {
        for (Alien alien : aliens) {
            if (alien.show)
                alien.draw(g, game);
            if (alien.beam.show)
                alien.beam.draw(g, game);
        }
    }

    public boolean HitGround() {
        for(Alien alien: aliens) {
            if (alien.show && alien.y + alien.height > SHIELD_Y) {
                return true;
            }
        }
        return false;
    }

    public void AlienStatus() {
        for(Alien alien : aliens) {
            if(alien.dying) {
                alien.setAbtToDie(true);
                alien.setDying(false);
            }
            else if(alien.abtDead) {
                alien.Die();
                alien.setAbtToDie(false);
            }
            else if(alien.show)
                alien.Moving();
        }
    }

    public void beamMove() {
        for(Alien alien: aliens) {
            if(alien.beam.show) {
                alien.beam.Moving();
            }
        }
    }

    public void AlienShoot() {
        for(Alien alien: aliens) {
            alien.beamFire();
        }
    }

    public void AlienAccel() {
        boolean b=false;

        if(numberOfAliens==20) {
            alienSpeed = 2;
            b = true;
        }

        if(numberOfAliens==10) {
            alienSpeed = 3;
            b = true;
        }
    }

    public void WallCollision() {
        for(Alien alien: aliens) {
            if(alien.x>SCREEN_WIDTH-ALIEN_WIDTH) {
                for(Alien alienReversed : aliens) {
                    alienReversed.dx = -alienSpeed;
                    alienReversed.y += 12;
                }
                return;
            }

            if(alien.x<0) {
                for(Alien alienReversed : aliens) {
                    alienReversed.dx = alienSpeed;
                    alienReversed.y += 12;
                }
                return;
            }
        }
    }
}

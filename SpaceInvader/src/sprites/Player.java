package sprites;

import java.awt.event.KeyEvent;
import static game.Essentials.*;

public class Player extends Mobility {

    Laser l;

    public Player (int x, int y) {
        super(x, y);
        FixImage("player.png");
        width=PLAYER_WIDTH;
        height=PLAYER_HEIGHT;
        l = new Laser(0, 0);
        l.Die();
    }

    public void MovingLaser() {
        if(l.Showing()) {
            l.Moving();
        }
    }
    
    public Laser getL() {
        return l;
    }
    
    public void Respawn() {
        FixImage("player.png");
        setDying(false);
        x = SCREEN_WIDTH/2;
    }

    public void KeyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_SPACE) {
            if(!l.show) {
                l.show = true;
                l.x = this.x + PLAYER_WIDTH/2;
                l.y = this.y;
            }
        }
        
        if (key == KeyEvent.VK_RIGHT) {
            dx = PLAYER_SPEED;
        }
        
        if (key == KeyEvent.VK_LEFT) {
            dx = -PLAYER_SPEED;
        }
    }

    public void KeyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        
        if(key == KeyEvent.VK_LEFT) {
            dx = 0;
        }
    }

    @Override
    public void Moving() {
        if(x > SCREEN_WIDTH-PLAYER_WIDTH){
            x = SCREEN_WIDTH-PLAYER_WIDTH;
        }
        else if(x < 0){
            x = 0;
        }
        else{
            super.Moving();
        }
    }

}

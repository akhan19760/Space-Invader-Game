package sprites;

import game.Game;
import javax.swing.*;
import java.awt.*;

public class Mobility {

    Image img;
    int x;
    int y;
    
    //Speed of x & y
    int dx;
    int dy;     
    
    int width;
    int height;
    boolean show;
    boolean dying;
    

    Mobility(int x, int y) {
        this.x = x;
        this.y = y;
        show = true;
        dying = false;
    }

    void FixImage(String png) {
        ImageIcon icon = new ImageIcon(png);
        img = icon.getImage();
    }

    public void Bang() {
        FixImage("bang.png");
        setDying(true);
    }

    public Rectangle getBoundary() {
        return new Rectangle(x, y, width, height);
    }

    public boolean Collision(Mobility m) {
        return this.getBoundary().intersects(m.getBoundary());
    }

    public void draw(Graphics g, Game game) {
        g.drawImage(img, x, y, width, height, game);
    }
    
    public void Moving() {
        x += dx;
        y += dy;
    }

    public void Die() {
        show = false;
    }

    public boolean Showing() {
        return show;
    }

    void setDying(boolean b) {
        dying=b;
    }

    public boolean isDying() {
        return dying;
    }

}

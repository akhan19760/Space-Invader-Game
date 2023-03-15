/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import javax.swing.*;
import static game.Essentials.SCREEN_HEIGHT;
import static game.Essentials.SCREEN_WIDTH;
import java.io.IOException;

public class SpaceInvader extends JFrame{

    public SpaceInvader() throws IOException {
        GameStart();
    }

    private void GameStart() throws IOException {
        this.add(new Game());

        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);

        setTitle("SPACE INVADERS");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new SpaceInvader();
    }
}

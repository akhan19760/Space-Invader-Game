package game;

import shield.Shield;
import sprites.Alien;
import sprites.AlienSwarm;
import sprites.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import static game.Essentials.*;
import java.io.*;
import java.io.IOException;
import java.util.Collections;

public class Game extends JPanel implements Runnable {

    Player player;
    AlienSwarm alienSwarm;
    List<Shield> shields;

    boolean gameLoop;
    Integer lives;
    String message;     //message for the end of a game

    Game() {

        gameLoop = true;
        lives = 5;

        player = new Player(START_X, START_Y);
        alienSwarm = new AlienSwarm();

        shields = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            shields.add(new Shield(SHIELD_X + i * 90, SHIELD_Y));
        }

        addKeyListener(new KAdapter());     //for Key events
        setFocusable(true);
        setBackground(Color.BLACK);
    }

    @Override
    public void addNotify(){
        super.addNotify();
        
        Thread game = new Thread(this);
        game.run();
    }

   
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (gameLoop) {
            repaint();
            animationCycle();       //mechanics of a game

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
            }
            beforeTime = System.currentTimeMillis();
        }
        gameOver();
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        Font font = new Font("Impact", Font.PLAIN, 18 );
        g.setColor(Color.WHITE);
        g.setFont(font);

        g.drawString("LIVES: " + lives.toString(), SCREEN_WIDTH - 70, 20);
        g.drawString("ENEMIES LEFT: " + alienSwarm.getNoAliens().toString(), 10, 20);
        g.drawString("SCORE: " + SCORE, SCREEN_WIDTH - 320, 20);

        g.setColor(Color.RED);
        g.drawLine(0, BASE_LINE, SCREEN_WIDTH, BASE_LINE);
        

        player.draw(g, this);
        if (player.getL().Showing()) {
            player.getL().draw(g, this);
        }

        alienSwarm.draw(g, this);

        for (Shield shield : shields) {
            shield.draw(g);
        }
    }

    private void animationCycle() {
        player.Moving();
        player.MovingLaser();
        AlienSwarmMoving();
        LaserHitAliens();
        BeamHitPlayer();
        HitShields();
        
        if (alienSwarm.getNoAliens() == 0) {
            gameLoop = false;
            message = "YOU SLAYED EM ALL! CONGRATS!";
        }

        if (player.isDying()) {
            lives--;
            if (lives != 0) {
                player.Respawn();
            } else {
                gameLoop = false;
                message = "GAME OVER!";
            }
        }

        if (alienSwarm.HitGround()) {
            gameLoop = false;
            message = "GAME OVER!";
        }

        message = message + (" YOUR KILL COUNT IS: " + String.valueOf(SCORE));
    }

    private void AlienSwarmMoving() {
        alienSwarm.AlienStatus();
        alienSwarm.beamMove();
        alienSwarm.AlienShoot();
        alienSwarm.AlienAccel();
        alienSwarm.WallCollision();
    }

    private void LaserHitAliens() {
        if (player.getL().Showing()) {
            for (Alien alien : alienSwarm.getAliens()) {
                if (alien.Showing() && player.getL().Collision(alien)) {
                    alien.Bang();
                    alienSwarm.decreaseAliens();
                    player.getL().Die();
                }
            }
        }
    }

    private void BeamHitPlayer() {
        for (Alien alien : alienSwarm.getAliens()) {
            if (alien.getBeam().Showing() && alien.getBeam().Collision(player)) {
                player.Bang();
                alien.getBeam().Die();
            }
        }
    }

    private void HitShields() {
        for (Shield shield : shields) {
            shield.Collision(player.getL());
            for (Alien alien : alienSwarm.getAliens()) {
                shield.Collision(alien.getBeam());
            }
        }
    }

    private void gameOver() {
        Graphics g = this.getGraphics();
        super.paintComponent(g);

        Font font = new Font("Garamond", Font.BOLD, 18);
        FontMetrics ft = this.getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(message, (SCREEN_WIDTH - ft.stringWidth(message)) / 2, SCREEN_HEIGHT / 2);

        //Wait 3 seconds
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        //Append the score to file
        try {
            FileWriter fw = new FileWriter("LEADERBOARD.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(SCORE));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        //Read the file and dump into list
        List<String> lb = new LinkedList<>();
//        try {
//            FileInputStream fis = new FileInputStream("leaderboard.txt");
//            Scanner sc = new Scanner(fis);
//
//            while (sc.hasNextLine()) {
//                if (!lb.contains(sc.nextLine())) {
//                    lb.add(sc.nextLine());
//                }
//            }
//            sc.close();
//        } catch (IOException e) {}
        try {
            File file = new File("LEADERBOARD.txt");   
            FileReader fr = new FileReader(file);  
            BufferedReader br = new BufferedReader(fr);  
                  
            String line;
            while ((line = br.readLine()) != null) {
                if (!lb.contains(line)) {
                lb.add(line);      
   
            }
            }
            fr.close();      
        } catch (IOException e) {}
        //Sort the list
       List<Integer> sortedLB = new LinkedList<>();
        for(String s : lb){
        sortedLB.add(Integer.valueOf(s));
    }
    Collections.sort(sortedLB);
    Collections.reverse(sortedLB);

        int yValue = 50;
        super.paintComponent(g);
        g.drawString("LEADERBOARD ", 10, 20);
        for (Integer line : sortedLB) {
            g.drawString(String.valueOf(line), 40, yValue);
            yValue += 20;
        }
    }

    private class KAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            player.KeyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.KeyReleased(e);
        }

    }
}

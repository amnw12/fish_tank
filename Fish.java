/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishTank;

/**
 *
 * @author amnwaqar
 */

import java.awt.*;
import java.util.*;

public class Fish implements Runnable{
    private double x,y, dx, dy, size;
    private boolean isAlive;
    public static final int WORLD_WIDTH = 500, WORLD_HEIGHT = 500;
    private Color[] color;
    private FishShoal shoal;

    public Fish(FishShoal shoal) {
        Random rand = new Random();
        this.shoal = shoal;
        this.size = rand.nextInt(15) + 10;
        this.x = rand.nextInt(this.WORLD_WIDTH / 2) + this.getSize();
        this.y = rand.nextInt(this.WORLD_HEIGHT / 2) + this.getSize();
        this.dx = rand.nextInt(7) - 4;
        this.dy = rand.nextInt(7) - 4;
        this.isAlive = true;
        this.color = new Color[3];
        this.color[0] = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)); 
        this.color[1] = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)); 
        this.color[2] = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)); 
    }

    @Override
    public void run(){
        int movements = 0;
        Random rand = new Random();
        double dxNoise;
        double dyNoise;
        while (isAlive)
        {
            if ((movements % 30) == 0)
            {
                dxNoise = rand.nextDouble();
                dyNoise = rand.nextDouble();
                
                if ((dx + dxNoise) < 10)
                {
                    dx += dxNoise;
                }
                
                 if ((dy + dyNoise) < 10)
                {
                    dy += dyNoise;
                }
            }
           
            move();
            movements++; 
               
           try {
                Thread.sleep(25);
            } catch (InterruptedException e) {}
        }
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }
    
    public synchronized void kill()
    {
        shoal.remove(this);
    }
    
    private void move()
    {
        x += dx;
        y += dy;
       
        if( x <=0 || x + getSize()>= WORLD_WIDTH)
        {
            dx *= -1;
        }
        if( y <=0 || y + getSize()>= WORLD_HEIGHT)
        {
            this.dy *= -1;
        }
        
        Fish target = shoal.canEat(this);
        
        if(target != null)
        {
            eat(target);
            target.kill();
        }
    }
    
    public synchronized void eat(Fish target)
    {
        if (getSize() < 40)
        {
            size += 3;
        }
    }
    
    public void draw(Graphics g)
    {
        double speed = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double velX = (size*dx)/(2*speed);
        double velY = (size*dy)/(2*speed);
        g.setColor(color[0]);
        g.drawLine((int) (x - velX + velY), (int) (y - velX - velY), (int) x, (int) y);
        g.setColor(color[1]);
        g.drawLine((int) (x - velX - velY), (int) (y + velX - velY), (int) x, (int) y);
        g.setColor(color[2]);
        g.drawLine((int) (x - (2*velX)), (int) (y - (2*velY)), (int) x, (int) y);
    }
}

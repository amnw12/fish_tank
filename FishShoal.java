/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishTank;

import java.util.*;
import java.awt.Graphics;
import java.lang.Math;

/**
 *
 * @author amnwaqar
 */
public class FishShoal {
    private List<Fish> fishList;

    public FishShoal() 
    {
        fishList = new ArrayList<Fish>();
    }
    
    public void add(Fish fish)
    {
        fishList.add(fish);
        Thread thread = new Thread (fish);
        thread.start();
    }
    
    public void remove(Fish fish)
    {
        fishList.remove(fish);
    }
    
    public void drawShoal(Graphics g)
    {
        for (Fish fish : fishList)
        {
            fish.draw(g);
        }
    }
    
    public synchronized Fish canEat(Fish fish)
    {
        for (int k = 0; k < fishList.size();k++)
        {
            Fish target = fishList.get(k);
            double range = (fish.getSize() + target.getSize())/2;
            double ratio = fish.getSize()/target.getSize();
            double distance = Math.sqrt(Math.pow((fish.getX()-target.getX()), 2) + Math.pow((fish.getY()-target.getY()), 2));
            
            if (distance < range)
            {
                if (ratio >= 1.4)
                {
                    return target;
                }
            }
        }
        return null;
    }
}

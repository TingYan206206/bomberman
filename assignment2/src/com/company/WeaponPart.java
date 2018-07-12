package com.company;


import java.util.Random;

abstract class WeaponPart
{
    public WeaponPart()
    {
        random = new Random();
    }

    // get time to create a certain type of weaponPart
    public int timeToCreate()
    {
        int time = (int) (random.nextGaussian() * stddev() + mean());
        if(time < 0)
            time = 0;
        return time;
    }


    public abstract double mean();
    public abstract double stddev();

    private Random random;
}
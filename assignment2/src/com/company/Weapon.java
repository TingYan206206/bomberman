package com.company;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

abstract class Weapon
{
    public Weapon ()
    {

    }

    public int getAttackPower()
    {
        return attackPower;
    }

    public String getName()
    {
        return name;
    }

    public abstract String[] getWeaponPartList();

    private int attackPower;
    private String name;

}

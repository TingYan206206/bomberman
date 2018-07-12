package com.company;

/**
 * Created by tingyan on 6/24/17.
 */
public class Gun extends Weapon
{

    public Gun()
    {
        // initiate the weaponPart type list for gun
        weaponPartsTypeList = new String [3];
        weaponPartsTypeList[0] = "steel";
        weaponPartsTypeList[1]= "Wood";
        weaponPartsTypeList[2]= "Gunpowder";
        // attachPower for gun
        attackPower = 10;
    }

    public String[] getWeaponPartList()
    {
        return weaponPartsTypeList;
    }

    @Override
    public int getAttackPower() {
        return attackPower;
    }

    private String[] weaponPartsTypeList;

    private int attackPower;


}

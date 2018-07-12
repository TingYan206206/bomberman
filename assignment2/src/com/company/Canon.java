package com.company;

/**
 * Created by tingyan on 6/24/17.
 */
public class Canon extends Weapon
{
    public Canon()
    {
        // initiate the weaponPart type list for cannon
        weaponPartsTypeList = new String[3];
        weaponPartsTypeList[0] = "steel";
        weaponPartsTypeList[1]= "rope";
        weaponPartsTypeList[2]= "gunpowder";
        attackPower = 20;

    }

    public  String[] getWeaponPartList()
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

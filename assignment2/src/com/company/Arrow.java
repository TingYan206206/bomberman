package com.company;

/**
 * Created by tingyan on 6/24/17.
 */
public class Arrow extends Weapon
{
    public Arrow()
    {
        // initiate the weaponPart type list for arrow
        weaponPartsTypeList = new String[3];
        weaponPartsTypeList[0] = "steel";
        weaponPartsTypeList[1]= "wood";
        weaponPartsTypeList[2]= "rope";
        attackPower = 5;

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
    //private String name;
}

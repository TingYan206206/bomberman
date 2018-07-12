package com.company;

import java.util.concurrent.LinkedBlockingQueue;


public class Consumer extends Thread
{
    public Consumer(String castleName,int n, String weapon, LinkedBlockingQueue<String> shared1,
                    LinkedBlockingQueue<String> shared2, LinkedBlockingQueue<String> shared3, LinkedBlockingQueue weaponList)
    {
        this.castleName = castleName;
        name = n;

        //decide which weapon this consumer works
        weaponTypeName = weapon;
        switch (weaponTypeName)
        {
            case "gun": weaponType = new Gun();
                        break;
            case "cannon": weaponType = new Canon();
                        break;
            case "arrow": weaponType = new Arrow();
                        break;

        }
        //get 3 queues to make specific weapon
        sharedQueue1 =shared1;
        sharedQueue2 =shared2;
        sharedQueue3 =shared3;

        // weapon list is where the weapon stores
        sharedWeaponList =weaponList;
    }

    public void run()
    {
        try
        {
            // take each piece of weapon part from each of the 3 weapon part queue to make a specific weapon
            sharedQueue1.take();
            sharedQueue2.take();
            sharedQueue3.take();
            //System.out.println("Castle " + castleName + " Consumer#" + name + " Remove one " + weaponType.getWeaponPartList()[0] + " and " +
                    //weaponType.getWeaponPartList()[1] + " and " + weaponType.getWeaponPartList()[2] + " from queue");

            //add the new weapon to weapon list
            sharedWeaponList.put(weaponType);
            //System.out.println("Castle " + castleName + " Consumer#" + name + " produced one " + weaponTypeName + " and add it to the armory.\n");

        } catch (InterruptedException e) {
//            e.printStackTrace();

        }

    }


    private int name;   // name of the consumer
    private String castleName;      //name of the castle he works for
    private String weaponTypeName;  // name of the weapon
    private Weapon weaponType;      //weapon he produces
    private LinkedBlockingQueue<String> sharedQueue1;       // weapon part queue to make a specific weapon
    private LinkedBlockingQueue<String> sharedQueue2;
    private LinkedBlockingQueue<String> sharedQueue3;
    private LinkedBlockingQueue sharedWeaponList;           //weapon list is where the weapon stores

}

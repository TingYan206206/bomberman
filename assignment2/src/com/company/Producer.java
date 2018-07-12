package com.company;


import java.util.concurrent.LinkedBlockingQueue;


public class Producer extends Thread
{
    public Producer(String castleName,int n, String weaponPart, LinkedBlockingQueue<String> shared)
    {
        this.castleName = castleName;
        name = n;

        //decide which weapon part he produces
        weaponPartName =weaponPart;
        switch (weaponPartName)
        {
            case "steel": weaponPartType = new Steel();
                            break;
            case "gunpowder": weaponPartType = new Gunpowder();
                            break;
            case "wood": weaponPartType = new Wood();
                         break;
            case "rope": weaponPartType = new Rope();
                            break;
        }

        // get produced time of this weapon type
        produceTime = weaponPartType.timeToCreate();
        sharedQueue = shared;

    }

    public void run()
    {
        try
        {
            while(!isInterrupted())
            {
                sleep((long)produceTime);
                sharedQueue.put(weaponPartName);        //add specific weapon part to its queue

                //prompt message about the produce result
               // System.out.println("Castle " + castleName+ " Producer#" + name + " produced one " + weaponPartName + "\n");
            }

        }
        catch (InterruptedException e)
                {
//                    e.printStackTrace();
                }


    }


    private String castleName;    //name of the castle this producer belongs to
    private WeaponPart weaponPartType;   //weapon part he produces
    private String weaponPartName;      // name of the weapon part
    private int name;                   //name of this producer
    private double produceTime;         // produce time for specific type of weapon part
    private LinkedBlockingQueue<String> sharedQueue;        // shared Queue for this weapon part


}

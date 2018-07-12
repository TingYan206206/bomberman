package com.company;

import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by tingyan on 6/23/17.
 */
public class Castle
{
    public Castle (String n, int consumerNum, int producerNum)
    {
        castleName = n;
        consumerList = new Consumer[consumerNum];
        producerList = new Producer[producerNum];
        weaponList = new LinkedBlockingQueue<>();

        // initiate queues for each weapon part
        gunpowderQueue = new LinkedBlockingQueue<>();
        woodQueue = new LinkedBlockingQueue<>();
        steelQueue = new LinkedBlockingQueue<>();
        ropeQueue = new LinkedBlockingQueue<>();

        HP = 50;
    }

    // this method initiate different type of producer according to its numbers
    public void setProducerList (int numSteelPro, int numGunPowderPro, int numWoodPro, int numRopePro)
    {
        // each type of producer corresponding to each type of queue
        for (int i = 0; i< numSteelPro; i++)
            producerList[i] = new Producer(castleName,i,"steel", steelQueue);

        for (int i = numSteelPro; i< numSteelPro + numGunPowderPro; i++)
            producerList[i] = new Producer(castleName,i,"gunpowder", gunpowderQueue);

        for (int i = numSteelPro + numGunPowderPro; i< numSteelPro + numGunPowderPro + numWoodPro; i++)
            producerList[i] = new Producer(castleName,i,"wood", woodQueue);

        for (int i = producerList.length-numRopePro; i< producerList.length; i++)
            producerList[i] = new Producer(castleName,i,"rope", ropeQueue);

    }

    // this method initiate different type of consumer according to its numbers
    public void setConsumerList (int numGunCon, int numCannonCon, int numArrowCon)
    {
        // each type of consumer corresponding to 3 types of weaponpart queues and the weaponList(armory)
        for (int i = 0; i< numGunCon; i++)
            consumerList[i] = new Consumer(castleName,i,"gun",  steelQueue, woodQueue, gunpowderQueue,weaponList);

        for (int i = numGunCon; i< numGunCon + numCannonCon; i++)
            consumerList [i] = new Consumer(castleName,i,"cannon",  steelQueue, ropeQueue, gunpowderQueue,weaponList);

        for (int i = consumerList.length-numArrowCon; i< consumerList.length; i++)
            consumerList [i] = new Consumer(castleName, i,"arrow",  steelQueue, woodQueue, ropeQueue,weaponList);
    }

    // this method runs all the workers
    public void run()
    {
        System.out.println("Producers of castle " + castleName + " start making weapon parts");
        for (Producer producer : producerList)
        {
            producer.start();
        }
        System.out.println("Consumers of castle " + castleName + " start making weapon");
        for (Consumer consumer : consumerList)
        {
            consumer.start();
        }
    }

    public String getName ()
    {
        return castleName;
    }

    public int getHP()
    {
        return HP;
    }


    // this method update castle's HP after attack by opponent
    public void updateHP(int attack)
    {
        HP -= attack;
        System.out.println("OUCH!!! Castle "+castleName + " lost "+ attack + " HP.");
        System.out.println("Current HP of castle "+castleName +" is "+ HP +"\n");

    }

    // get attack power of the castle
    public int attackPower()
    {
        if ( !weaponList.isEmpty())
        {
            Weapon currentWeapon = null;
            try
            {
                currentWeapon = weaponList.take();  // take out weapon from weaponList(armory)
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            int currentAttackPower = currentWeapon.getAttackPower();   // get the attach power of current weapon

            return currentAttackPower;
        }
        else return 0;
    }
    public Consumer[] getConsumerList()
    {
        return consumerList;
    }
    public Producer[] getProducerList()
    {
        return producerList;
    }


    private String castleName;
    private Consumer[] consumerList;
    private Producer[] producerList;

    private LinkedBlockingQueue<Weapon> weaponList;
    private LinkedBlockingQueue<String> gunpowderQueue;
    private LinkedBlockingQueue<String> woodQueue;
    private LinkedBlockingQueue<String> steelQueue;
    private LinkedBlockingQueue<String> ropeQueue;

    private int HP;

}

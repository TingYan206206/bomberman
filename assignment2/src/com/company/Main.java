// Ting Yan
// ID: 010765513

package com.company;


public class Main {

    public static void main(String[] args)
    {
	    // initiate two castles and it's producer and consumer number
        Castle A = new Castle("A", 3,11);
        Castle B = new Castle("B",4,10);

        // set the numbers for different type of producers and consumers
        A.setProducerList(4,3,2,2);
        A.setConsumerList(1,1,1);

        B.setProducerList(3,3,2,2);
        B.setConsumerList(1,2,1);

        // get producer list and consumer list
        Producer[] producerListA = A.getProducerList();
        Producer[] producerListB = B.getProducerList();
        Consumer[] consumerListA = A.getConsumerList();
        Consumer[] consumerListB = B.getConsumerList();


        // start running all workers
        A.run();
        B.run();

        while(true)
        {
            // consumers of two castles make weapons and attack the opponent
            for (Consumer consumerA : consumerListA)
            {
                consumerA.run();
                int attackFromA = A.attackPower();
                B.updateHP(attackFromA);
            }
            for (Consumer consumerB : consumerListB)
            {
                consumerB.run();
                int attackFromB = B.attackPower();
                A.updateHP(attackFromB);
            }

            // check if either castle's HP is below 0, then interrupt thread and jump out of the loop
            if (A.getHP() <= 0 || B.getHP() <= 0)
            {
                for (Producer producer1 : producerListA)
                    producer1.interrupt();
                for (Producer producer2 : producerListB)
                    producer2.interrupt();
//                for (Consumer consumerA : consumerListA)
//                    consumerA.interrupt();
//                for (Consumer consumerB : consumerListB)
//                    consumerB.interrupt();

                break;
            }
        }

        // report result
        if (A.getHP() < B.getHP() )
        {
            System.out.println("Castle " + A.getName() + " lose.");
            System.out.println("Castle " + A.getName()+ " HP: " + A.getHP());
            System.out.println("Castle " + B.getName()+ " HP: " + B.getHP());
        }
        else if (A.getHP() == B.getHP() )
        {
            System.out.println("It's a tie.");
            System.out.println("Castle " + A.getName()+ " HP: " + A.getHP());
            System.out.println("Castle " + B.getName()+ " HP: " + B.getHP());
        }
        else
        {
            System.out.println("Castle " + B.getName() + " lose.");
            System.out.println("Castle " + A.getName()+ " HP: " + A.getHP());
            System.out.println("Castle " + B.getName()+ " HP: " + B.getHP());
        }


}
}

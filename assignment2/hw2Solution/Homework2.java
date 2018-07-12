import java.util.Arrays;

public class Homework2
{
	public static void main(String[] args)
	{
		WeaponPartType blade		= new WeaponPartType("blade",		5000,	1000);
		WeaponPartType handle		= new WeaponPartType("handle",		3000,	500);
		WeaponPartType fuse			= new WeaponPartType("fuse",		1000,	500);
		WeaponPartType gunpowder	= new WeaponPartType("gunpowder",	12000,	2500);
		WeaponPartType casing		= new WeaponPartType("casing",		2000,	1000);
		WeaponPartType mechanism	= new WeaponPartType("mechanism",	24000,	5000);
		
		WeaponType sword		= new WeaponType("sword",			Arrays.asList(blade, handle),			10,		5);
		WeaponType bomb			= new WeaponType("bomb",			Arrays.asList(fuse, gunpowder, casing),	10,		10);
		WeaponType hiddenBlade	= new WeaponType("hidden blade",	Arrays.asList(blade, mechanism),		20,		2);
		
		Castle red = new Castle("Red");
		Castle blue = new Castle("Blue");
		
		// 32 workers total
		red.setEnemy(blue);
		red.addProducers(blade, 5);
		red.addProducers(handle, 5);
		red.addProducers(fuse, 2);
		red.addProducers(gunpowder, 5);
		red.addProducers(casing, 3);
		red.addProducers(mechanism, 9);
		red.addConsumer(sword);
		red.addConsumer(bomb);
		red.addConsumer(hiddenBlade);
		
		// 32 workers total
		blue.setEnemy(red);
		blue.addProducers(blade, 5);
		blue.addProducers(handle, 1);
		blue.addProducers(mechanism, 24);
		blue.addConsumer(sword);
		blue.addConsumer(hiddenBlade);
		
		while(red.hp() > 0 && blue.hp() > 0)
		{
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			
			red.update();
			blue.update();
		}
		
		red.stopWorkers();
		blue.stopWorkers();
		
		if(red.hp() == 0 && blue.hp() > 0)
			System.out.println("Blue has prevailed!");
		else if(red.hp() > 0 && blue.hp() == 0)
			System.out.println("Red has prevailed!");
		else
			System.out.println("Red and Blue have destroyed one another!");
	}
}

import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Castle
{
	Castle(String name)
	{
		m_name = name;
	}

	public void addProducer(WeaponPartType type)
	{
		Queue<WeaponPart> queue = m_queues.get(type.name());
		Lock lock = m_locks.get(type.name());

		if(queue == null)
		{
			queue = new LinkedList<>();
			m_queues.put(type.name(), queue);

			lock = new ReentrantLock();
			m_locks.put(type.name(), lock);
		}

		Producer producer = new Producer(type, lock, queue);
		producer.start();

		m_workers.add(producer);
	}

	public void addProducers(WeaponPartType type, int number)
	{
		for(int i = 0; i < number; ++i)
			addProducer(type);
	}

	public void addConsumer(WeaponType type)
	{
		List<Queue<WeaponPart>> queues = new ArrayList<>();
		List<Lock> locks = new ArrayList<>();

		for(WeaponPartType partType : type.parts())
		{
			queues.add(m_queues.get(partType.name()));
			locks.add(m_locks.get(partType.name()));

			if(m_queues.get(partType.name()) == null)
				throw new RuntimeException("Cannot consume from a nonexistant queue!");
		}

		Consumer consumer = new Consumer(type, locks, queues, m_armoryLock, m_armory);
		consumer.start();

		m_workers.add(consumer);
	}

	public void addConsumers(WeaponType type, int number)
	{
		for(int i = 0; i < number; ++i)
			addConsumer(type);
	}

	public void setEnemy(Castle enemy)
	{
		m_enemy = enemy;
	}

	public int hp()
	{
		return m_hp;
	}

	// precondition: armoryLock is locked already
	public int calculateAttack()
	{
		int attack = 0;
		for(Weapon weapon : m_armory)
			attack += weapon.attack();
		return attack;
	}

	public void update()
	{
		System.out.println("==== " + m_name + " ====");

		System.out.println("---- Parts ----");
		for(String key : m_queues.keySet())
		{
			m_locks.get(key).lock();
			System.out.println(key + "s: " + m_queues.get(key).size());
			m_locks.get(key).unlock();
		}

		System.out.println("---- Weapons ----");
		m_armoryLock.lock();
		int attack = calculateAttack();
		System.out.println(m_armory.size() + " weapons in the armory with a total attack of " + attack);
		m_armoryLock.unlock();

		m_enemy.m_hp -= attack; 
		if(m_enemy.m_hp < 0)
			m_enemy.m_hp = 0;

		System.out.println("---- HP: " + m_hp + " ----\n");
	}

	public void stopWorkers()
	{
		for(Thread thread : m_workers)
			thread.interrupt();
	}

	private String m_name;
	private int m_hp = 100000;
	private Castle m_enemy = null;

	private List<Weapon> m_armory = new ArrayList<>();
	private Lock m_armoryLock = new ReentrantLock();

	private Map<String, Queue<WeaponPart>> m_queues = new HashMap<>();
	private Map<String, Lock> m_locks = new HashMap<>();

	private List<Thread> m_workers = new ArrayList<>();
}

import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;

public class Consumer extends Thread
{
	public Consumer(WeaponType type, List<Lock> queueLocks, List<Queue<WeaponPart>> queues, Lock armoryLock, List<Weapon> armory)
	{
		m_type			= type;
		m_queueLocks	= queueLocks;
		m_queues		= queues;
		m_armoryLock	= armoryLock;
		m_armory		= armory;
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				List<WeaponPart> acquiredParts = new LinkedList<>();
				
				// attempt to acquire locks and take resources
				int i;
				for(i = 0; i < m_queueLocks.size(); ++i)
				{
					if(m_queueLocks.get(i).tryLock())
					{
						try
						{
							if(!m_queues.get(i).isEmpty())
								acquiredParts.add(m_queues.get(i).poll());
							else
								break;
						}
						finally
						{
							m_queueLocks.get(i).unlock();
						}
					}
					else
						break;
				}
				
				if(i < m_queueLocks.size())
				{
					// if we did not acquire all resources, put back what we *did* get
					for(int j = 0; j < i; ++j)
					{
						m_queueLocks.get(j).lockInterruptibly();
						m_queues.get(j).add(acquiredParts.get(j));
						m_queueLocks.get(j).unlock();
					}
					
					// wait a moment before trying again
					Thread.sleep(1000);
				}
				else
				{
					// if we acquired all resources, build the weapon
					m_armoryLock.lockInterruptibly();
					m_armory.add(new Weapon(m_type));
					m_armoryLock.unlock();
				}
			}
		}
		catch(InterruptedException e)
		{
			// when interrupted, producer silently stops working
		}
	}
	
	private WeaponType m_type;
	private List<Lock> m_queueLocks;
	private List<Queue<WeaponPart>> m_queues;
	private Lock m_armoryLock;
	private List<Weapon> m_armory;
}

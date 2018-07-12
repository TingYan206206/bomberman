import java.util.Queue;
import java.util.concurrent.locks.Lock;

public class Producer extends Thread
{
	public Producer(WeaponPartType type, Lock lock, Queue<WeaponPart> queue)
	{
		m_type = type;
		m_lock = lock;
		m_queue = queue;
	}
	
	@Override
	public void run()
	{
		try
		{
			while(true)
			{
				// Make the part
				WeaponPart part = new WeaponPart(m_type);
				
				// Sleep for the amount of time it takes to make the part
				Thread.sleep(part.delay());
				
				// Insert the new part into the queue
				m_lock.lockInterruptibly();
				try
				{
					m_queue.add(part);
				}
				finally
				{
					m_lock.unlock();
				}
			}
		}
		catch(InterruptedException e)
		{
			// when interrupted, producer silently stops working
		}
	}
	
	private WeaponPartType m_type;
	private Lock m_lock;
	private Queue<WeaponPart> m_queue;
}

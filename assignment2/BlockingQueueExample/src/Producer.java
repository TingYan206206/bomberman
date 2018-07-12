import java.util.concurrent.BlockingQueue;

public class Producer extends Thread{

    private BlockingQueue queue;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while(!isInterrupted())
            {
                sleep(1000);
                queue.put("1");
                System.out.println("producer put item in");
            }
//            Thread.sleep(1000);
//            queue.put("2");
//            Thread.sleep(1000);
//            queue.put("3");
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
    }
}
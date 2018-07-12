import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueExample
{
    public static void main(String[] args) throws Exception {

        BlockingQueue queue1 = new LinkedBlockingQueue();
        BlockingQueue queue2 = new LinkedBlockingQueue();
        BlockingQueue queue3 = new LinkedBlockingQueue();

        Producer producer11 = new Producer(queue1);
        Producer producer12 = new Producer(queue1);
        Producer producer21 = new Producer(queue2);
        Producer producer22 = new Producer(queue2);
        Consumer consumer = new Consumer(queue1,queue2,queue3);


        consumer.start();
        producer11.start();
        producer12.start();
        producer21.start();
        producer22.start();


        while (true)
        {
            consumer.run();
            if (queue3.size() > 4)
            {
                producer11.interrupt();
                producer12.interrupt();
                producer21.interrupt();
                producer22.interrupt();
                break;
            }
        }



//        System.out.println(queue.toString());
//        Thread.sleep(5000);
        System.out.println("Q1" + queue1);
        System.out.println("Q2" + queue2);
        System.out.println("Q3" + queue3);

        System.out.println("main is ended");
    }
}

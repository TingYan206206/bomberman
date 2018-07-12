import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread{

    private BlockingQueue queue1;
    private BlockingQueue queue2;
    private BlockingQueue queue3;

    public Consumer(BlockingQueue queue1,BlockingQueue queue2, BlockingQueue queue3) {
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.queue3 = queue3;
    }

    public void run() {
//        BlockingQueue temp = null;
        try {

            queue1.take();
            queue2.take();
            System.out.println("queue1 and queue2 have been taken");
            queue3.put("gun");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
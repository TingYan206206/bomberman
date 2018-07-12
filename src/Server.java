import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by tingyan on 7/17/17.
 */
public class Server {






    public static void main(String[] args)
    {
        Game game = new Game(true);
        game.run();
    }
}

package comunication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class servidor_udp {

    public static void main(String[] args) throws IOException {
        byte msg[] = new byte[1024];
        DatagramSocket s = new DatagramSocket(Constantes.PUERTO_DEL_SERVIDOR);
        System.out.println("Servidor activo.");
        while(true){
            DatagramPacket recibido = new DatagramPacket(new byte[1024], 1024);
            System.out.println("Esperando...");
            s.receive(recibido);
            HiloServer hiloServer = new HiloServer(recibido, s);
        }
    }
}

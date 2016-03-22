package comunication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class cliente_udp {
    public static void main(String[] args) throws Exception {
        while (true) {
            Thread.sleep(3000);
            DatagramSocket dgSocket;
            DatagramPacket datagram;
            InetAddress destination = null;
            String saludo = new String("hola");
            byte msg[] = saludo.getBytes();
            dgSocket = new DatagramSocket(Constantes.PUERTO_DEL_CLIENTE, InetAddress.getByName(Constantes.HOST_DEL_CLIENTE));
            try {
                destination = InetAddress.getByName(Constantes.HOST_DEL_SERVIDOR);
            } catch (UnknownHostException uhe) {
                System.out.println("Host no encontrado: " + uhe);
                System.exit(-1);
            }
            byte msgR[] = new byte[1024];
            datagram = new DatagramPacket(msg, msg.length, destination, Constantes.PUERTO_DEL_SERVIDOR);
            dgSocket.send(datagram);
            System.out.println("Dato enviado.");
            datagram = new DatagramPacket(msgR, msgR.length);
            dgSocket.receive(datagram);
            String received = new String(datagram.getData());
            String total = "";
            for (int i = 0; i < datagram.getLength(); i++) {
                char test = received.charAt(i);
                String test1 = String.valueOf(test);
                total += test1;
            }
            System.out.println("DATOS DEL DATAGRAMA: " + total);
            dgSocket.close();
        }
    }
}

package comunication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

public class servidor_udp {

    public static void main(String[] args) throws IOException {
        byte msg[] = new byte[1024];
        DatagramSocket s = new DatagramSocket(Constantes.PUERTO_DEL_SERVIDOR);
        System.out.println("Servidor activo.");
        while(true){
            DatagramPacket recibido = new DatagramPacket(new byte[1024], 1024);
            System.out.println("Esperando...");
            s.receive(recibido);
            System.out.println("Ha llegado una peticion \n");
            System.out.println("Procedente de: " + recibido.getAddress());
            System.out.println("En el puerto: " + recibido.getPort());
            String recive = new String(recibido.getData());
            String total = "";
            for (int i = 0; i < recibido.getLength(); i++){
                char test = recive.charAt(i);
                String test1 = String.valueOf(test);
                total += test1;
            }
            System.out.println("Dato: " + total);
            System.out.println("Sirviendo la petición.");
            String message = new String("HORA DEL SERVIDOR: " + new Date());
            msg = message.getBytes();
            DatagramPacket paquete = new DatagramPacket(msg, msg.length, recibido.getAddress(), recibido.getPort());
            s.send(paquete);
        }
    }
}

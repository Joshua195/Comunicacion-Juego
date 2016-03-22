package comunication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class HiloServer extends Thread{
    DatagramPacket datagramPacket;
    DatagramSocket datagramSocket;

    public HiloServer(DatagramPacket datagramPacket, DatagramSocket datagramSocket){
        this.datagramPacket = datagramPacket;
        this.datagramSocket = datagramSocket;
        start();
    }

    public void run(){
        System.out.println("Ha llegado una peticion \n");
        System.out.println("Procedente de: " + datagramPacket.getAddress());
        System.out.println("En el puerto: " + datagramPacket.getPort());
        String recive = new String(datagramPacket.getData());
        String total = "";
        for (int i = 0; i < datagramPacket.getLength(); i++){
            char test = recive.charAt(i);
            String test1 = String.valueOf(test);
            total += test1;
        }
        String[] cadena = total.split(",");
        float posicionX = Float.parseFloat(cadena[1]);
        float posicionY = Float.parseFloat(cadena[2]);

        if (cadena[0].equals("a")){
            byte msg[];
            Clientes.poscionXCliente1 = posicionX;
            Clientes.poscionYCliente1 = posicionY;
            String message = new String(posicionX + "," + posicionY);
            msg = message.getBytes();
            DatagramPacket paquete = new DatagramPacket(msg, msg.length, datagramPacket.getAddress(), datagramPacket.getPort());
            try {
                datagramSocket.send(paquete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (cadena[0].equals("b")){
            byte msg[];
            Clientes.poscionXCliente2 = posicionX;
            Clientes.poscionYCliente2 = posicionY;
            String message = new String(posicionX + "," + posicionY);
            msg = message.getBytes();
            DatagramPacket paquete = new DatagramPacket(msg, msg.length, datagramPacket.getAddress(), datagramPacket.getPort());
            try {
                datagramSocket.send(paquete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

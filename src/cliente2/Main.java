package cliente2;

import com.sun.org.apache.bcel.internal.generic.FLOAD;
import comunication.Constantes;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Main extends JComponent {
    ArrayList<Pelota> pelotas;
   // volatile Hilo thread;
    
    public Main(ArrayList<Pelota> pelotas) throws InterruptedException {
        setPreferredSize(new Dimension(Ctes.ANCHO, Ctes.ALTO));
        this.pelotas = pelotas;

    }

     public void paint(Graphics g) {
         //pelotas = thread.pelotas;
         g.setColor(Color.WHITE);
         g.fillRect(0, 0, Ctes.ANCHO, Ctes.ALTO);
         for (int i = 0; i < Pelota.nPelota; i++) {
             g.setColor(pelotas.get(i).color);
             g.fillOval(Math.round(pelotas.get(i).x), Math.round(pelotas.get(i).y), Ctes.DIAMETRO, Ctes.DIAMETRO);
             /*g.setColor(Color.BLACK);
             g.drawString("Num: "+Pelota.nPelota, 20, 20);*/
         }
     }

    public void cicloPrincipalJuego() throws Exception {
        long tiempoViejo = System.nanoTime();
        while (true) {
            //pelotas = thread.pelotas;
            long tiempoNuevo = System.nanoTime();
            float dt = (tiempoNuevo - tiempoViejo) / 1000000000f;
            tiempoViejo = tiempoNuevo;
            for (int i = 0; i < Pelota.nPelota; i++) {
                try{
                    pelotas.get(i).fisica(dt);
                }catch(Exception e){
                }
            }
            dibuja();
            test(parce());
        }
    }

     private void dibuja() throws Exception {
        try{
         SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                paintImmediately(0, 0, Ctes.ANCHO, Ctes.ALTO);
            }
        });
        }catch(Exception e){
        } 
    }

    public void test(String posiciones) throws Exception {
        DatagramSocket dgSocket;
        DatagramPacket datagram;
        InetAddress destination = null;
        byte msg[] = posiciones.getBytes();
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
        for (int i = 0; i < datagram.getLength(); i++){
            char test = received.charAt(i);
            String test1 = String.valueOf(test);
            total += test1;
        }
        String[] cadena = total.split(",");
        float posicionX = Float.parseFloat(cadena[0]);
        float posicionY = Float.parseFloat(cadena[1]);
        pelotas.get(1).setX(posicionX);
        pelotas.get(1).setY(posicionY);
        System.out.println("DATOS DEL DATAGRAMA: " + total);
        dgSocket.close();
    }

    public String parce(){
        return new String("b" + pelotas.get(0).x + "," + pelotas.get(0).y);
    }

    public static void main(String[] args) throws Exception {
        JFrame jf = new JFrame("Juego...");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        ArrayList<Pelota> pelotas = new ArrayList<>();
        pelotas.add(new Pelota());
        pelotas.add(new Pelota(0,0));
        Main demo1 = new Main(pelotas);
        jf.getContentPane().add(demo1);
        jf.pack();
        jf.setVisible(true);
        demo1.cicloPrincipalJuego();
    }

}
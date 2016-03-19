package juego1;

import comunication.Constantes;
import oracle.jrockit.jfr.JFR;

import java.awt.*;
import java.awt.event.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.*;

public class Main extends JComponent implements MouseListener{

    private final static int ANCHO = 512;
    private final static int ALTO = 384;
    private final static int DIAMETRO = 20;
    private float x, y;
    private float vx, vy;

    public Main() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        x = 10;
        y = 20;
        vx = 50;
        vy = 50;
    }

    public float getPocisionX() {
        return x;
    }

    public float getPocisiony() {
        return y;
    }

    public void setPocisionX(float x) {
        this.x = x;
    }

    public void setPocisiony(float y) {
        this.y = y;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, ANCHO, ALTO);    
        g.setColor(Color.RED);
        g.fillOval(Math.round(x), Math.round(y),DIAMETRO, DIAMETRO);
    }
    
    public void cicloPrincipalJuego() throws Exception {
        long tiempoViejo = System.nanoTime();
        while (true) {
            long tiempoNuevo = System.nanoTime();
            float dt = (tiempoNuevo - tiempoViejo) / 1000000000f;
            tiempoViejo = tiempoNuevo;
            fisica(dt);
            dibuja();
            test(parce());
        }
    }

    public String parce(){
        return new String("(" + getPocisionX() + "," + getPocisiony() + ")");
    }
    
    private void dibuja() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                paintImmediately(0, 0, ANCHO, ALTO);
            }
        });
    }
    
    private void fisica(float dt) {
        x += vx * dt;
        y += vy * dt;
        if (vx < 0 && x <= 0 || vx > 0 && x + DIAMETRO >= ANCHO) {
            vx = -vx;
        }
        if (vy < 0 && y < 0 || vy > 0 && y + DIAMETRO >= ALTO) {
            vy = -vy;
        }
    }
    
    public static void main(String[] args) throws Exception {
        JFrame jf = new JFrame("Juego...");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        Main demo1 = new Main();
        jf.getContentPane().add(demo1);
        jf.addMouseListener(demo1);
        jf.pack();
        jf.setVisible(true);
        demo1.cicloPrincipalJuego();
    }

    public static void test(String posiciones) throws Exception {
        DatagramSocket dgSocket;
        DatagramPacket datagram;
        InetAddress destination = null;
        //String saludo = new String("hola");
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
        System.out.println("DATOS DEL DATAGRAMA: " + total);
        dgSocket.close();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("X: " + e.getX() + " Y: " + e.getY());
        setPocisionX(e.getX());
        setPocisiony(e.getY());
        /*try {
            test(parce());
        } catch (Exception e1) {
            e1.printStackTrace();
        }*/
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
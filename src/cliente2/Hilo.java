package cliente2;


import java.util.ArrayList;


public class Hilo extends Thread{
    public ArrayList<Pelota> pelotas = new ArrayList<>();
    public int posicionXCliente2;
    public int posicionYCliente2;
    public Pelota pelota;

     public Hilo(ArrayList<Pelota> pelotas){
         this.pelotas = pelotas;
         start();
     }

    public Hilo(Pelota pelota){
        /*this.pelotas = pelotas;
        this.posicionXCliente2 = posicionXCliente2;
        this.posicionYCliente2 = posicionYCliente2;*/
        this.pelota = pelota;
    }
    
    synchronized public void run(){
        pelotas.add(pelota);
    }
    
}

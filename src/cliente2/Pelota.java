package cliente2;

import java.awt.*;

public class Pelota {

    
    public float x, y;
    private float vx, vy;
    
    public Color color;

    public static int nPelota=0;
    public int idPelota;

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Pelota() {
        idPelota=nPelota++;
        x = 10;
        y = 20;
        vx = 50;
        vy = 50;
        color = Color.RED;
    }
    
    
    public Pelota(float x, float y) {
        idPelota=nPelota++;
        this.x = x;
        this.y = y;
        vx = 50;
        vy = 50;
        color= Color.BLACK;
    }
    
    
    public void fisica(float dt) {
        x += vx * dt;
        y += vy * dt;
        if (vx < 0 && x <= 0 || vx > 0
                && x + Ctes.DIAMETRO >= Ctes.ANCHO) {
            vx = -vx;
        }
        if (vy < 0 && y < 0 || vy > 0
                && y + Ctes.DIAMETRO >= Ctes.ALTO) {
            vy = -vy;
        }
        
    }
}

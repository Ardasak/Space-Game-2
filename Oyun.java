
package uzayoyunu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates{
    private int x;
    private int y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}

public class Oyun extends JPanel implements KeyListener, ActionListener{
    Timer timer = new Timer(5,this);
    private int top_status = 0;
    private double basladigi_zaman;
    private double bittigi_zaman;
    private int harcanan_ates = 0;
    private BufferedImage image;
    private ArrayList<Ates> atesler = new ArrayList<>();
    private BufferedImage bg;
    private final int atesdirY = 4;
    
    private int topX = 0;
    
    private final int topdirX = 2;
    
    private int uzaygemisiX = 0;
    
    private final int dirUzayX = 20;
    public boolean kontrol_et(){
        for(Ates ates : atesler){
            if(new Rectangle(ates.getX(),ates.getY()+4,10,20).intersects(new Rectangle(topX,0,20,20))){
                return true;
            }
        }
        return false;
    }
    public Oyun() {
        basladigi_zaman = System.currentTimeMillis();
        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
            bg = ImageIO.read(new FileImageInputStream(new File("uzay.jpg")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.black);
        
        timer.start();
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void paint(Graphics g) {
        
        
        super.paint(g);
        g.setColor(Color.red);
        g.fillOval(topX, 0, 20, 20);
        
        g.drawImage(image, uzaygemisiX, 490, image.getWidth()/10, image.getHeight()/10, this);
        
        for(Ates ates : atesler){
            if(ates.getY()<0){
                atesler.remove(ates);
            }
        }
        g.setColor(Color.blue);
        
        for(Ates ates : atesler){
            g.fillRect(ates.getX(), ates.getY(), 10, 20);
        }
        
        if(kontrol_et()){
            bittigi_zaman = System.currentTimeMillis();
            timer.stop();
            String message = "Kazandınız. \n"+
                    "Harcanan Ateş : "+harcanan_ates + " Geçen Süre : " + (bittigi_zaman-basladigi_zaman)/1000;
            
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, 800 ,600,null);
    }
    
    
    
    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        int c = arg0.getKeyCode();
        if(c==KeyEvent.VK_LEFT){
            if(uzaygemisiX==0){
                uzaygemisiX=0;
            }
            else{
                uzaygemisiX-=dirUzayX;
            }
            
           
        }
        else if(c==KeyEvent.VK_RIGHT){
            
            if(uzaygemisiX==740){
                uzaygemisiX=740;
            }
            else{
                uzaygemisiX+=dirUzayX;
            }
            
        }
        else if(c==KeyEvent.VK_UP){
            if(atesler.size()<8){
                atesler.add(new Ates(uzaygemisiX+15,470));
            
                harcanan_ates++;
            }
            else{
                
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        for(Ates ates : atesler){
            ates.setY(ates.getY()-atesdirY);
        }
        if(topX<=0){
            top_status=0;
        }
        if(topX>=765){
            top_status=1;
        }
        
        if(top_status == 0){
            topX+=topdirX;
        }
        else{
            topX-=topdirX;
        }
        repaint();
    }
    
}

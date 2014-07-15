import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Console extends JFrame {
	private static final long serialVersionUID = 1L;
	ConsoleCanvas canvas;
	Game game;
	
	public Console(Game g) {
		super("Automagically");
		setVisible(true);
		setSize(640+15, 300+39);
		game = g;
		canvas = new ConsoleCanvas(game);
		canvas.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent arg0) {
				game.key[(arg0.getKeyCode()+256)%256] = true;
			}
			public void keyReleased(KeyEvent arg0) {
				game.key[(arg0.getKeyCode()+256)%256] = false;
			}
			public void keyTyped(KeyEvent arg0) {}
		});
		add(canvas);
		canvas.repaint();
	}
	
	public void Draw() {
		canvas.repaint();
	}
}

class ConsoleCanvas extends Canvas {
	private static final long serialVersionUID = 1L;
	Game game;
	BufferedImage characters[] = new BufferedImage[256];
	BufferedImage characterSheet;
	int pixelColors[] = {0x000000,0x000080,0x008000,0x008080,0x800000,0x800080,0x808000,0xC0C0C0,0x808080,0x0000FF,0x00FF00,0x00FFFF,0xFF0000,0xFF00FF,0xFFFF00,0xFFFFFF};
	
	public ConsoleCanvas(Game g) {
		game = g;
		setSize(game.width*8, game.height*12);
		init();
	}
	
	public void paint(Graphics g) {
		int width = this.getWidth() - this.getWidth() % 8;
		int height = this.getHeight() - this.getHeight() % 12;
		BufferedImage di = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D dg = di.createGraphics();
		for(int x = (int) (game.player.x - width/16); x <= game.player.x + width/16 + 1; x++)
			for(int y = (int) (game.player.y - height/24); y <= game.player.y + height/24 + 1; y++)
				if(x>=0&&y>=0&&x<=1023&&y<=1023)
					dg.drawImage(characters[(game.world.map[x][y]+256)%256], x*8 - (int)game.player.x*8 + width/2, y*12 - (int)game.player.y*12 + height/2, this);
		dg.drawImage(characters['8'+2], width/2, height/2, this);
		for(int x = (int) (game.player.x - width/16); x <= game.player.x + width/16 + 1; x++)
			for(int y = (int) (game.player.y - height/24); y <= game.player.y + height/24 + 1; y++)
				if(x>=0&&y>=0&&x<=1023&&y<=1023)
					if((game.world.map[x][y]+256)%256==('i'+258)%256)
					{
						Point2D center = new Point2D.Float(x*8 - (int)game.player.x*8 + width/2+4, y*12 - (int)game.player.y*12 + height/2+6);
					     float radius = 100 + game.rand.nextInt() % 5;
					     float[] dist = {0.0f, 0.5f};
					     Color[] colors = {new Color(255, 255, 128, 100), new Color(0, 0, 0, 0)};
					     RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
					     dg.setPaint(p);
					     dg.fillOval(x*8 - (int)game.player.x*8 + width/2+4 - (int)(radius/2), y*12 - (int)game.player.y*12 + height/2+6 - (int)(radius/2), (int)radius, (int)radius);
					}
		g.drawImage(di, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	public void update(Graphics g)
	{
		paint(g);
	}
	
	public Image changeColor(BufferedImage i, short c)
	{
		int[] pgPixels = new int[96];
		PixelGrabber pg = new PixelGrabber(i, 0, 0, 8, 12, pgPixels, 0 , 8);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Image i2;
		for(int y = 0; y < 8; y++)
			for(int x = 0; x < 12; x++)
			{
				int p = y*12+x;
				pgPixels[p] *= -1;
				int r = (pgPixels[p] & 0x00FF0000)>>16;
			if(r==0)
				pgPixels[p] = pixelColors[(c&0xF0)>>4]+0xFF000000;
			else
				pgPixels[p] = pixelColors[c&0xF]+0xFF000000;
			}
		i2 = createImage(new MemoryImageSource(8,12,pgPixels,0,8));
		return i2;
	}
	
	public void init() {
		try {
			characterSheet = ImageIO.read(new File("characters.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for(int x = 0; x < 16; x++)
			for(int y = 0; y < 16; y++)
				characters[y*16+x] = characterSheet.getSubimage(x * 8, y * 12, 8, 12);
	}
}
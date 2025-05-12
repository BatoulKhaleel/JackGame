package Pbasic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	
	final int originalTileSize = 16; // 16×16 pixels, size of the player, characters, tiles.. etc.
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;   // 48*48 tile
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;  // 768 pixels
	final int screenHeight = tileSize * maxScreenRow;  // 576 pixels
	
	// FPS
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this,keyH);
	
	// To Set Player Default Position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel () {
		
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

/*	@Override
	public void run() {
		while (gameThread != null) {
			//System.out.println("Pato Game Running");
			
		//	long currentTime = System.nanoTime();
		//	//long currentTimeByMillis = System.currentTimeMillis();  // not so efficient.
		//	System.out.println("current Time :"+currentTime);
			
			// Sleep Method.
			double drawinterval = 1000000000/FPS;  // 0.01666... second, we draw the screen every 0.01666.. seconds so we can achieve the 60fps.
			double nextDrawTime = System.nanoTime()+ drawinterval;
			// The allocated time for single loop is 0.01666 seconds.
			
			
			// 1. UPDATE: update information such as character positions.
			update();
			
			// 2. DRAW: draw the screen with the updated information.
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime /1000000; // Convert from nanoTime to Millis;
				
				if (remainingTime < 0) {remainingTime = 0;}
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawinterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
*/
	// Delta Method.
	@Override
	public void run() {
		
		double drawinterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer=0;
		int drawCounter=0;
		
		
		while(gameThread!=null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawinterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta>=1) {
				update();
				repaint();
				delta--;
				drawCounter++;
				
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS : "+drawCounter+(player.getClass().getResourceAsStream(TOOL_TIP_TEXT_KEY)));
				drawCounter = 0;
				timer = 0;
			}
			
			
		}
		
	}
	
	public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		player.draw(g2);
		g2.dispose();  // good to save memory.
		
	}
	
	

}

package invaders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;




public class Board extends JPanel implements ActionListener{

	private int B_WIDTH = 800, B_HEIGHT = 800;
	private Timer delay_per_frames;
	private int delay = 5;
			
	private boolean inGame = true;
	
	private Alien alien = new Alien(this);
	private Ship ship = new Ship(this);
	private boolean rDirection = false;
	private boolean lDirection = false;

	private int hpBarHeight = 20;
	
	public Board(Color color){
		
		addKeyListener(new KeyAction());
		setBackground(color);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		setFocusable(true);
		initGame();
		
	}
	public void initGame() {
		
		ship.getDimFromBoard(B_WIDTH, B_HEIGHT);
		alien.getDimFromBoard(B_WIDTH, B_HEIGHT);
		ship.setPos(new Point<Integer>(B_WIDTH/2 - ship.getWidth()/2, B_HEIGHT - ship.getHeight()));
		delay_per_frames = new Timer(delay, this);
		delay_per_frames.start();
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		doDrawing(g);
	}
	public void doDrawing(Graphics g) {
		
		if(inGame){
		    
			g.setColor(Color.BLUE);
			for(Point<Integer> shipBullet : ship.getBulletsPos()){
				
				if(shipBullet != null){
					
					g.fillRect(shipBullet.getX(), shipBullet.getY(), ship.bulletWidth, ship.bulletHeight);
				}
			}
			g.setColor(Color.red);
			for(Point<Integer> alienBullet : alien.getBulletsPos()){
				
				if(alienBullet != null){
					
					g.fillRect(alienBullet.getX(), alienBullet.getY(), alien.bulletWidth, alien.bulletHeight);
				}
			}
			//shipHp bar
			g.setColor(Color.yellow);
			g.fillRect(0, 0, ship.getHP(), hpBarHeight);
			g.setColor(Color.red);
			g.fillRect(ship.getHP(), 0, B_WIDTH - ship.getHP(), hpBarHeight);
			
			g.drawImage(ship.getImage(), ship.getPos().getX(), ship.getPos().getY(), this);
			g.drawImage(alien.getImage(), alien.getPos().getX(), alien.getPos().getY(), this);
			
			//alienHp bar
			g.setColor(Color.yellow);
			int hpBarWidth = (int) (alien.getWidth()*((double)alien.getHP()/alien.B_HEIGHT));
			g.fillRect(alien.getPos().getX(), alien.getPos().getY() - 5, hpBarWidth, 5);
			int hpBarDestroyed = (int) (alien.getWidth() * ((double)(alien.B_WIDTH - alien.getHP())/alien.B_WIDTH));
			g.setColor(Color.blue);
			g.fillRect(alien.getPos().getX() + hpBarWidth, alien.getPos().getY() - 5, hpBarDestroyed, 5);
		}
		else{
			if(ship.isDead()) {
				gameOver(g);
			}
			else {
				winScreen(g);
			}
		}
	
	}
	public void winScreen(Graphics g) {
		int startX = B_WIDTH/4, startY = B_HEIGHT/3;
		int lengthX = (B_WIDTH - startX*2), lengthY = (B_HEIGHT - startY*2);
		
		String msg = "You Win";
		Font font = new Font("Consolas", Font.BOLD, 30);
		g.setFont(font);
		g.fillRect(startX, startY, lengthX, lengthY);
		g.setColor(Color.blue);
		g.drawString(msg, (B_WIDTH - getFontMetrics(font).stringWidth(msg))/2, B_HEIGHT/2);
	}
	public void gameOver(Graphics g){
		
		int startX = B_WIDTH/4, startY = B_HEIGHT/3;
		int lengthX = (B_WIDTH - startX*2), lengthY = (B_HEIGHT - startY*2);
		
		String msg = "Game Over";
		Font font = new Font("Consolas", Font.BOLD, 30);
		g.setFont(font);
		g.fillRect(startX, startY, lengthX, lengthY);
		g.setColor(Color.blue);
		g.drawString(msg, (B_WIDTH - getFontMetrics(font).stringWidth(msg))/2, B_HEIGHT/2);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		inGame = ship.getHP() > 0 && alien.getHP() > 0;
		if(inGame == true) {
			ship.move(rDirection, lDirection);
			alien.move();
			ship.shoot(alien.getPos(), alien.getHP());
			alien.shoot(ship.getPos(), ship.getHP());
			ship.checkDamageTaken(alien.getBulletsPos());
			alien.checkDamageTaken(ship.getBulletsPos());
		}
		else {
			delay_per_frames.stop();
		}
		repaint();
	}
	private class KeyAction extends KeyAdapter {
	
		public void keyPressed(KeyEvent e){
			int key = e.getKeyCode();
			
			if(key == KeyEvent.VK_A){
				rDirection = false;
				lDirection = true;
			}
			if(key == KeyEvent.VK_D){
				rDirection = true;
				lDirection = false;
			}
		}
		public void keyReleased(KeyEvent e){
			int key = e.getKeyCode();
			
			if(key == KeyEvent.VK_A){
				lDirection = false;
			}
			if(key == KeyEvent.VK_D){
				rDirection = false;
			}
			if(key == KeyEvent.VK_SPACE && ship.getBulletsPos().size() < ship.maxBullets){
				ship.newBullet = true;
			}
		}
	
	}
}

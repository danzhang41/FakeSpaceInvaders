package invaders;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public abstract class Entities {
	
	
	protected int B_WIDTH;
	protected int B_HEIGHT;
	
	protected int bulletHeight = 50;
	protected int bulletWidth = 10;
	protected final int maxBullets = 4;
	
	public abstract void setHP(int hp); //setters and getters
	public abstract int getHP();
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract void setImage(JPanel board);
	public abstract void setDim(JPanel board);
	public abstract ArrayList<Point<Integer>> getBulletsPos();
	public abstract void shoot(Point<Integer> enemyPos, int enemyHP);
	public abstract boolean checkCollision(Point<Integer> enemyBullet);
	public abstract void checkDamageTaken(ArrayList<Point<Integer>> enemyBulletList);
	public abstract Image getImage();
	public abstract Point<Integer> getPos();
	public abstract void setPos(Point<Integer> pos);
	public abstract boolean isDead();
	public void getDimFromBoard(int B_WIDTH, int B_HEIGHT) {
		
		this.B_WIDTH = B_WIDTH;
		this.B_HEIGHT = B_HEIGHT;
	}
	public boolean collides(Point<Integer> aTL, Point<Integer> aBR, Point<Integer> bTL , Point<Integer> bBR) {
		
		if(aTL.getX().compareTo(bBR.getX()) > 0 || bTL.getX().compareTo(aBR.getX()) > 0){
			
			return false;
		}
		if(aTL.getY().compareTo(bBR.getY()) > 0 || bTL.getY().compareTo(aBR.getY()) > 0){
			
			return false;
		}
		return true;
	}
}
class Alien extends Entities{
	
	int alienHP;
	Point<Integer> alienPos = new Point<>(350,100);
	ArrayList<Point<Integer>> bulletList = new ArrayList<Point<Integer>>();
	private Image alienImage;
	private int alienHeight, alienWidth;
	//move variables
	int speed = 5;
	int moveCount = 0;
	int moveCap;
	boolean direction = false; //left is false
	int alienMaxBullets = 10;

	Alien(JPanel board){
		setHP(800);
		setImage(board);
		setDim(board);
		moveCap = 5;
	}
	@Override
	public void setHP(int hp) { alienHP = hp; }
	@Override
	public int getHP() { return alienHP; }
	@Override
	public int getHeight() { return alienHeight; }
	@Override
	public int getWidth() { return alienWidth; }
	@Override
	public ArrayList<Point<Integer>> getBulletsPos(){ return bulletList; }
	@Override
	public void setImage(JPanel board) {
		alienImage = new ImageIcon(getClass().getResource("/alien.png")).getImage();	
	}
	@Override
	public Image getImage() { return alienImage; }
	@Override
	public void setPos(Point<Integer> pos) { this.alienPos = pos; }
	@Override 
	public Point<Integer> getPos(){ return alienPos; }
	@Override
	public void setDim(JPanel board) {
		alienHeight = alienImage.getHeight(board);
		alienWidth = alienImage.getWidth(board);
	}
	@Override
	public void shoot(Point<Integer> enemyPos, int enemyHP) {
		
		boolean newBullet = true;
		int ghostHeight = 100; //this is to "increase" size of bullet so they are spread out
		Point<Integer> bSpawnTL = new Point<Integer>((alienPos.getX() + alienWidth/2) - 2, alienPos.getY() + alienHeight);
		Point<Integer> bSpawnBR = new Point<Integer>(bSpawnTL.getX() + bulletWidth , bSpawnTL.getY() + bulletHeight + ghostHeight);
		for(int i = 0; i < bulletList.size(); i++) {
			if(!bulletList.isEmpty()) {
				Point<Integer> tempBullet = bulletList.get(i);
				if(tempBullet != null) {
					Point<Integer> tempBulletBR = new Point<Integer>(tempBullet.getX() + bulletWidth, tempBullet.getY() + bulletHeight);
					
					if(collides(tempBullet, tempBulletBR, bSpawnTL, bSpawnBR)) {
						newBullet = false;
					}
					if(tempBullet.getY() > B_HEIGHT) {
						bulletList.remove(i);
					}
					else {
						tempBullet.setY(tempBullet.getY() + 3);
						bulletList.set(i, tempBullet);
					}
				}
			}
		}
		if(bulletList.size() < alienMaxBullets && newBullet == true) {
			bulletList.add(bSpawnTL);
		}
	}	
	public void move() {
		if(direction == true) {
			if(moveCount < moveCap) {
				moveCount++;
				if((alienPos.getX() + alienWidth - 20) < B_WIDTH)
					alienPos.setX(alienPos.getX() + speed);
			}
			else if(moveCount >= moveCap){
				direction = false;
				moveCap = getCap();
				moveCount = 0;
			}
		}
		else {
			if(moveCount < moveCap) {
				moveCount++;
				if(alienPos.getX() > 0)
					alienPos.setX(alienPos.getX() - speed);
			}
			else if(moveCount >= moveCap){
				direction = true;
				moveCap = getCap();
				moveCount = 0;
			}
		}	
	}
	public int getCap() {
		return (int)(Math.random()*30) + 5;
	}
	@Override
	public boolean checkCollision(Point<Integer> enemyBullet) {
		
		Point<Integer> bulletTL = enemyBullet;
		Point<Integer> bulletBR = new Point<Integer>(bulletTL.getX() + bulletWidth, bulletTL.getX() + bulletHeight);
		
		int disparity = 20;
		Point<Integer> alienTL = new Point<>(alienPos.getX() + disparity, alienPos.getY());
		Point<Integer> alienBR = new Point<>(alienTL.getX() + alienWidth - (2*disparity), alienTL.getY() + alienHeight - disparity);
		
		if(collides(bulletTL, bulletBR, alienTL, alienBR))
			return true;
		return false;
	}
	@Override
	public void checkDamageTaken(ArrayList<Point<Integer>> enemyBulletList) {
		
		for(int index = 0; index < enemyBulletList.size(); index++) {
			if(checkCollision(enemyBulletList.get(index))) {
				alienHP -= 50;
				enemyBulletList.remove(index);
			}
		}
	}
	@Override
	public boolean isDead() {
		return alienHP <= 0;
	}
}
class Ship extends Entities {
	
	int shipHP;
	int shipHeight, shipWidth;

	int bulletCount;
	boolean newBullet;
	ArrayList<Point<Integer>> bulletList = new ArrayList<Point<Integer>>();
	
	private Point<Integer> shipPos;
	private Image shipImage;
	
	Ship(JPanel board){
		setHP(800);
		setImage(board);
		setDim(board);
	}
	@Override
	public void setHP(int hp) { shipHP = hp; }
	@Override
	public int getHP() { return shipHP; }
	@Override
	public int getHeight() { return shipHeight; }
	@Override
	public int getWidth() { return shipWidth; } 
	@Override
	public ArrayList<Point<Integer>> getBulletsPos(){
		return bulletList;
	}
	@Override
	public void setImage(JPanel board) {
		shipImage = new ImageIcon(getClass().getResource("/spaceInvaders.png")).getImage();
	}
	@Override
	public Image getImage() { return shipImage; }
	@Override
	public void setPos(Point<Integer> pos) { this.shipPos = pos; }
	@Override
	public Point<Integer> getPos(){ return shipPos; }
	@Override
	public void setDim(JPanel board) { 
		shipHeight = shipImage.getHeight(board);
		shipWidth = shipImage.getWidth(board);
	}
	@Override
	public void shoot(Point<Integer> alienPos, int alienHP) {
		
		if(newBullet == true && bulletList.size() < maxBullets){
			
			bulletList.add(new Point<Integer>(shipPos.getX() + shipWidth/2 - 5, shipPos.getY()));
			newBullet = false;
			bulletCount++;
		}
		for(int i = 0; i < bulletList.size(); i++){
			if(!bulletList.isEmpty()) {
				Point <Integer> tempBullet = bulletList.get(i);

				if(tempBullet != null){
					if(tempBullet.getY() < 0){
						bulletList.remove(i);
					}
					else{
						tempBullet.setY(tempBullet.getY() -3);
					}
				}
			}
		}
	}
	public void move(boolean rDirection, boolean lDirection) { // false = left true = right
		
		if(rDirection == true && shipPos.getX() < (B_WIDTH - shipWidth)) {
			shipPos.setX(shipPos.getX() + 3);
		}
		if(lDirection == true && shipPos.getX() > 0) {
			shipPos.setX(shipPos.getX() - 3);
		}
	}
	@Override
	public boolean checkCollision(Point<Integer> enemyBullet) {
					
		Point<Integer> bulletTL = enemyBullet; //Top Left of the bullet
		Point<Integer> bulletBR = new Point<>(bulletTL.getX() + bulletWidth, bulletTL.getY() + bulletHeight);
		
		Point<Integer> shipTopTL = new Point<Integer>(shipPos.getX() + 38, B_HEIGHT - shipHeight); //38 is the indent the nozzle is at
		Point<Integer> shipTopBR = new Point<Integer>(shipTopTL.getX() + 23, shipTopTL.getY()); //23 is the length of the nozzle
		Point<Integer> shipBotTL = new Point<Integer>(shipPos.getX(), shipPos.getY() + 23);
		Point<Integer> shipBotBR = new Point<Integer>(shipBotTL.getX() + shipWidth, shipBotTL.getY() + shipHeight);
		
		if(collides(bulletTL, bulletBR, shipTopTL, shipTopBR))
			return true;
		if(collides(bulletTL, bulletBR, shipBotTL, shipBotBR))
			return true;
		return false;
	}
	@Override
	public void checkDamageTaken(ArrayList<Point<Integer>> enemyBulletList) {
		
		for(int index = 0; index < enemyBulletList.size(); index++) {
			if(checkCollision(enemyBulletList.get(index))) {
				shipHP -= 200;
				enemyBulletList.remove(index);
			}
		}
	}
	@Override
	public boolean isDead() {
		return shipHP <= 0;
	}
}

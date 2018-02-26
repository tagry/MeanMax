import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class MapObject {
	int id;
	int radius;
	Point position;

	public MapObject(int id, int radius, int x, int y) {
		this.id = id;
		this.radius = radius;
		this.position.x = x;
		this.position.y = y;
	}

	protected void refresh(int x, int y) {
		this.position.x = x;
		this.position.y = y;
	}
}

class Reaper extends MapObject {
	int mass;
	Point speed;
	int playerId;

	public Reaper(int id, int radius, int x, int y, int vx, int vy, int mass, int playerId) {
		super(id, radius, x, y);
		this.mass = mass;
		this.playerId = playerId;
		this.position.x = x;
		this.position.y = y;
		this.speed.x = vx;
		this.speed.y = vy;
	}

	public void refresh(int x, int y, int vx, int vy) {
		refresh(x, y);
		this.speed.x = vx;
		this.speed.y = vy;
	}
}

class Wrack extends MapObject {
	private int water;

	public Wrack(int id, int radius, int x, int y, int water) {
		super(id, radius, x, y);
		this.water = water;
	}
}

class Player{
	int id;
	int score;
	List<Integer> reapers = new ArrayList<>();
	
	public Player(int id) {
		this.id = id;
	}
	
	public void refresh(int score) {
		this.score = score;
		reapers.clear();
	}
	
	public void addReaper(int reaperId) {
		if(!reapers.contains(reaperId))
			reapers.add(reaperId);
	}
}

class Data{
	static public Map<Integer, Reaper> reapers = new HashMap<>();
	static public Map<Integer, Player> players = new HashMap<>();
}

class Main {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);

		// game loop
		while (true) {
			int myScore = in.nextInt();
			int enemyScore1 = in.nextInt();
			int enemyScore2 = in.nextInt();
			int myRage = in.nextInt();
			int enemyRage1 = in.nextInt();
			int enemyRage2 = in.nextInt();
			int unitCount = in.nextInt();
			for (int i = 0; i < unitCount; i++) {
				int unitId = in.nextInt();
				int unitType = in.nextInt();
				int player = in.nextInt();
				float mass = in.nextFloat();
				int radius = in.nextInt();
				int x = in.nextInt();
				int y = in.nextInt();
				int vx = in.nextInt();
				int vy = in.nextInt();
				int extra = in.nextInt();
				int extra2 = in.nextInt();
			}

			// Write an action using System.out.println()
			// To debug: System.err.println("Debug messages...");

			System.out.println("WAIT");
			System.out.println("WAIT");
			System.out.println("WAIT");
		}
	}
}
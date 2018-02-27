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
	float mass;
	Point speed;
	int playerId;

	public Reaper(int id, int radius, int x, int y, int vx, int vy, float mass, int playerId) {
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
	int water;

	public Wrack(int id, int radius, int x, int y, int water) {
		super(id, radius, x, y);
		this.water = water;
	}

	public void refresh(int x, int y, int water) {
		refresh(x, y);
		this.water = water;
	}
}

class Player {
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
		if (!reapers.contains(reaperId))
			reapers.add(reaperId);
	}
}

class Data {
	public Map<Integer, Reaper> reapers = new HashMap<>();
	public Map<Integer, Wrack> wracks = new HashMap<>();
	public List<Player> players = new ArrayList<>();

	public Data() {
		players.add(new Player(0));
		players.add(new Player(1));
		players.add(new Player(2));
	}

	/*
	 * *****************************************************************************
	 * ************************ INIT DATA
	 */
	public void updatePlayers(int myScore, int enemyScore1, int enemyScore2, int myRage, int enemyRage1,
			int enemyRage2) {
		refreshPlayer(0, myScore);
		refreshPlayer(1, enemyScore1);
		refreshPlayer(2, enemyScore2);
	}

	public void updateObject(int unitId, int unitType, int player, float mass, int radius, int x, int y, int vx, int vy,
			int extra, int extra2) {
		switch (unitType) {
		// is a Reaper
		case 0:
			if (!reapers.containsKey(unitId))
				reapers.put(unitId, new Reaper(unitId, radius, x, y, vx, vy, mass, player));

			refreshReaper(unitId, player, x, y, vx, vy);
			break;

		// is a Wrack
		case 4:
			if (!wracks.containsKey(unitId))
				wracks.put(unitId, new Wrack(unitId, radius, x, y, extra));

			refreshWrack(unitId, x, y, extra);
			break;
		}
	}

	private void refreshPlayer(int idPlayer, int score) {
		players.get(idPlayer).refresh(score);
	}

	private void refreshReaper(int reaperId, int playerId, int x, int y, int vx, int vy) {
		reapers.get(reaperId).refresh(x, y, vx, vy);
		players.get(playerId).addReaper(reaperId);
	}

	private void refreshWrack(int wrackId, int x, int y, int water) {
		wracks.get(wrackId).refresh(x, y, water);
	}

	/*
	 * *****************************************************************************
	 * *************
	 */

	public List<Reaper> getPlayerReapers(int playerId) {
		List<Reaper> playerReapers = new ArrayList<>();

		for (int reaperId : players.get(playerId).reapers) {
			playerReapers.add(reapers.get(reaperId));
		}

		return playerReapers;
	}
}

class Move {
	boolean waitMove;
	Point destination;
	int acc;
}

class StrategyContext {
	Strategy strategy = new BasicStrategy();

	public void play(Data data) {
		move(strategy.getMoves(data));
	}

	private void move(List<Move> moves) {
		for (Move move : moves) {
			if (move.waitMove)
				System.out.println("WAIT");
			else
				System.out.println(move.destination.x + " " + move.destination.y + " " + move.acc);
		}

		// if some cars not exist
		for (int i = 0; i < 3 - moves.size(); i++)
			System.out.println("WAIT");
	}
}

abstract class Strategy {
	public abstract List<Move> getMoves(Data data);
}

class BasicStrategy extends Strategy {

	@Override
	public List<Move> getMoves(Data data) {
		List<Move> moves = new ArrayList<>();

		for (int reaperId : data.players.get(0).reapers) {
			moves.add(getReaperMove(data, reaperId));
		}

		return moves;
	}

	public Move getReaperMove(Data data, int reaperId) {
		return null;
	}

	public Point getReaperTarget(Data data, int reaperId) {
		return null;
	}
	
	/*
	 * Algo
	 * 
	 */
	public int getClosestWrack(Data data, int reaperId) {
		return -1;
	}

}

class Main {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Data data = new Data();

		// game loop
		while (true) {
			int myScore = in.nextInt();
			int enemyScore1 = in.nextInt();
			int enemyScore2 = in.nextInt();
			int myRage = in.nextInt();
			int enemyRage1 = in.nextInt();
			int enemyRage2 = in.nextInt();

			data.updatePlayers(myScore, enemyScore1, enemyScore2, myRage, enemyRage1, enemyRage2);

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

				data.updateObject(unitId, unitType, player, mass, radius, x, y, vx, vy, extra, extra2);
			}

			// Write an action using System.out.println()
			// To debug: System.err.println("Debug messages...");

			System.out.println("WAIT");
			System.out.println("WAIT");
			System.out.println("WAIT");
		}
	}
}
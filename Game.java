import java.util.Random;

public class Game {
	public int width = 80;
	public int height = 25;
	public World world = new World();
	public Entity player = new Entity();
	public boolean key[] = new boolean[256];
	public Random rand = new Random();
}
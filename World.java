public class World {
	public short map[][];
	
	public World() {
		map = new short[1024][1024];
		
		for(int x = 0; x < map.length; x++)
			for(int y = 0; y < map.length; y++)
				if(x == 0 || x == 1023 || y == 0 || y == 1023)
					map[x][y] = (short) '�'+2;
				else
					map[x][y] = 0;
		
		map[12][12] = 'i' + 2;
		map[17][15] = 'i' + 2;
	}
}
package maps;

import java.util.ArrayList;
import java.util.List;

public abstract class Level {
	static final int TILE = 16;
	final List<Room> rooms = new ArrayList<>();
	public Room getRoom(int i){
		return rooms.get(i);
	}
}

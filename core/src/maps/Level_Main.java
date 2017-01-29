package maps;

import java.util.Arrays;

public class Level_Main extends Level {

	public Level_Main(){
		rooms.addAll(Arrays.asList(
				new Room_OutsideHouse(this), // 0
				new Room_Cemetery(this), 	 // 1
				new Room_OutsideChurch(this),// 2
				new Room_InsideChurch(this), // 3
				new Room_Basement(this),	 // 4
				new Room_Catacombs(this)));  // 5
	}
}

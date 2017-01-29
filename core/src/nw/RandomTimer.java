package nw;

import java.util.Random;

public class RandomTimer extends Timer {
	
	Random random = new Random();

	public RandomTimer(int count, boolean isDuration) {
		super(count, isDuration);
	}
	
	@Override
	public void countUp(){
		if (random.nextBoolean()) timer++;
	}

}

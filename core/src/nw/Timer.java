package nw;

public class Timer {
	
	protected int count;
	protected int timer;
	
	public Timer(int count, boolean isDuration){
		this.count = count;
		if (isDuration) timer = 0;
		else timer = count + 1;
	}
	
	public void restart(){
		timer = 0;
	}
	
	public void countUp(){
		timer++;
	}
	
	public void countDown(){ timer--; }
	
	public void setTime(int time){ count = time; }
	
	public boolean timeUp(){ return (timer > count); }
	
	public int getTime(){ return timer; }
	public int getCount(){ return count; }
	
	public void stop(){
		timer = count + 1;
	}
	
}

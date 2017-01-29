package maps;

import java.util.ArrayList;
import java.util.List;

import nw.Main;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import entities.Entity;
import entities.Player;

public abstract class Room {
	TiledMap map;
	MapObjects mapObjects;
	final TmxMapLoader tmxMapLoader = new TmxMapLoader();
	final List<Rectangle> rectangleList = new ArrayList<>();
	final List<Entity> entityList = new ArrayList<>();
	final List<Entity> newEntityList = new ArrayList<>();
	final Vector2 startPosition = new Vector2();
	Music roomMusic;
	final Level superLevel;
	static final int TILE = 16;
	public boolean bigBorder = false;
	public boolean clouds = false;
	public boolean visited = false;
	float r, g, b = 1;
	float a = 0;
	
	public Room(Level superLevel){
		this.superLevel = superLevel;
	}
	
	protected boolean notCat(){
		return (!visited && !Main.gotCat);
	}
	
	public void setup(){
		MapLayers layers = map.getLayers();
		mapObjects = layers.get(layers.getCount()-1).getObjects(); // gets the top layer
		for(RectangleMapObject mapObject: mapObjects.getByType(RectangleMapObject.class)){		
			rectangleList.add(mapObject.getRectangle());
		}
		visited = true;
	}
	
	public TiledMap getMap(){ return map; }
	public List<Rectangle> getRectangleList(){ return rectangleList; }
	public List<Entity> getEntityList(){ return entityList; }
	public Vector2 getStartPosition(){ return startPosition; }
	public void stopMusic(){ roomMusic.stop(); }
	public Music getMusic(){ return roomMusic; }
	public float getR(){ return r; }
	public float getB(){ return b; }
	public float getG(){ return g; }
	public float getA(){ return a; }

	public void initEntities(Player player){
		clearOut();
		if (entityList.contains(player)) entityList.remove(player);
		player.setPosition(startPosition);
		entityList.add(player);
		roomMusic.setLooping(true);
		roomMusic.setVolume(Main.getVolume());
		roomMusic.play();
	}
	
	public void addEntity(Entity e){
		newEntityList.add(e);
	}
	
	public void update(){
//		for (Entity e: entityList){
//			System.out.println(e.getClass());
//		}
		for (Entity e: newEntityList){
			entityList.add(e);
		}
		newEntityList.clear();
	}
	
	void clearOut(){
		rectangleList.clear();
		entityList.clear();
	}
	
	void setStartPosition(float x, float y){
		startPosition.x = x;
		startPosition.y = y;
	}
	
	public void removeEntity(Entity en){
		getEntityList().remove(en);
		getRectangleList().remove(en.getImage().getBoundingRectangle());
	}
}

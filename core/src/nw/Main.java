package nw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import maps.*;
import entities.*;
import entities.Entity.Direction;
import entities.Entity.State;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import cutscenes.Cutscene;
import cutscenes.Cutscene_Ending;
import cutscenes.Cutscene_Intro;

public class Main extends ApplicationAdapter implements InputProcessor, ControllerListener {

	/* DEBUG */
	private final int startRoom = 0; // FINAL: 0
	public static boolean gotCat = false; // FINAL: false
	public static boolean hardMode = false; // FINAL: false
	public static boolean debug = false; // FINAL: false
	/* GRAPHICS */
	private SpriteBatch batch;
	private Player player;
	private final OrthographicCamera cam = new OrthographicCamera();
	private final int camAdjustmentSpeed = 8;
	private static final float ZOOM = 1/3f;
	private OrthogonalTiledMapRenderer renderer;
	Texture border, bigBorder, tint, clouds, eye, pupil, pupilShader,
	dead, deadleft, keyboardControls, controllerControls, pressJumpToBegin,
	shredBase, shred1, shred2, shred3, shred4, credits, creditsHard, logo;
	TextureRegion bossBorder, creditPet;
	Animation bossAnim, nextButtonK, nextButtonC, creditPetEasy, creditPetHard;
	static Sound collect, finalCollect, shredReveal;
	private final float blackR = (float)21/255;
	private final float blackG = (float)34/255;
	private final float blackB = (float)43/255;
	private final float screenAdjust = 2f;
	/* MAP */
	private final static int TILE = 16;
	public static final float SCREENWIDTH  = (19*TILE)/ZOOM;
	public static final float SCREENHEIGHT = (14*TILE)/ZOOM;
	private Level activeLevel; 
	private static Room activeRoom;
	private Room nextRoom;
	private Vector2 nextPos;
	private TiledMap map;
	private int mapWidth;
	private int mapHeight;
	private final List<Rectangle> rectangleList = new ArrayList<>();
	/* CONTROLLER */
	private Controller controller;
	private final float deadZone = 0.12f;
	/* MUSIC */
	private static float volume = 0f;
	private float heartVolume = 0f;
	static Music heartBeat, finalChase, creditsMusic;
	/* CUTSCENES */
	Cutscene cutsceneIntro;
	static Cutscene activeCutscene;
	/* TIMERS */
	private final Timer fireCooldown = new Timer(14, false);
	private final Timer transition = new Timer(20, false);
	private final Timer transition2 = new Timer(2, false);
	private final Timer deathLength = new Timer(200, false);
	private final Timer controlLength = new Timer(30, true);
	private final static Timer shredLength = new Timer(240, true);
	private final List<Timer> timerList = new ArrayList<Timer>
	(Arrays.asList(fireCooldown, transition, transition2, deathLength, controlLength, shredLength));
	/* MISC */
	private ControlType controlType = ControlType.CONTROLLER;
	private static GameState gameState;
	private float signum = 0;
	public static int deltaTime = 0;
	public static boolean gotShred1, gotShred2, gotShred3, gotShred4 = false;

	@Override public void create () {
		bossAnim = Entity.makeAnimation("sprites/bossbordersheet.PNG", 4, 1, 10f, PlayMode.LOOP);
		creditPetEasy = Entity.makeAnimation("sprites/ending pet 1.PNG", 2, 1, 40f, PlayMode.LOOP);
		creditPetHard = Entity.makeAnimation("sprites/ending pet 2.PNG", 2, 1, 40f, PlayMode.LOOP);
		nextButtonK = Entity.makeAnimation("sprites/nextarrow.PNG", 2, 1, 20f, PlayMode.LOOP);
		nextButtonC = Entity.makeAnimation("sprites/nextarrowc.PNG", 2, 1, 20f, PlayMode.LOOP);

		border = new Texture(Gdx.files.internal("sprites/border.PNG"));
		bigBorder = new Texture(Gdx.files.internal("sprites/bigborder.PNG"));
		tint = new Texture(Gdx.files.internal("sprites/tint.PNG"));
		clouds = new Texture(Gdx.files.internal("sprites/clouds3.PNG"));
		eye = new Texture(Gdx.files.internal("sprites/eye.PNG"));
		pupil = new Texture(Gdx.files.internal("sprites/pupil.PNG"));
		pupilShader = new Texture(Gdx.files.internal("sprites/pupilShader.PNG"));
		dead = new Texture(Gdx.files.internal("sprites/dead.PNG"));
		deadleft = new Texture(Gdx.files.internal("sprites/deadleft.PNG"));
		keyboardControls = new Texture(Gdx.files.internal("scenes/kcontrols.PNG"));
		controllerControls = new Texture(Gdx.files.internal("scenes/ccontrols.PNG")); 
		pressJumpToBegin = new Texture(Gdx.files.internal("scenes/pressjumptobegin.PNG")); 
		credits = new Texture(Gdx.files.internal("sprites/credits.PNG"));
		creditsHard = new Texture(Gdx.files.internal("sprites/creditshard.PNG"));
		shredBase = new Texture(Gdx.files.internal("scenes/scrapbase.PNG"));
		shred1 = new Texture(Gdx.files.internal("scenes/scrap1.PNG"));
		shred2 = new Texture(Gdx.files.internal("scenes/scrap2.PNG"));
		shred3 = new Texture(Gdx.files.internal("scenes/scrap3.PNG"));
		shred4 = new Texture(Gdx.files.internal("scenes/scrap4.PNG"));
		logo = new Texture(Gdx.files.internal("sprites/logo.PNG"));

		bossBorder = new Sprite(bossAnim.getKeyFrame(0));
		creditPet = new Sprite(creditPetEasy.getKeyFrame(0));

		shredReveal = Gdx.audio.newSound(Gdx.files.internal("sfx/mystery.wav"));
		collect = Gdx.audio.newSound(Gdx.files.internal("sfx/collect2.wav"));
		finalCollect = Gdx.audio.newSound(Gdx.files.internal("sfx/collect3.wav"));

		heartBeat = Gdx.audio.newMusic(Gdx.files.internal("music/heartbeat reverb.mp3"));
		heartBeat.setLooping(true);
		finalChase = Gdx.audio.newMusic(Gdx.files.internal("music/final chase 2.mp3")); 
		finalChase.setLooping(true);
		creditsMusic = Gdx.audio.newMusic(Gdx.files.internal("music/5 4 in the morning.mp3")); // doesn't loop

		cutsceneIntro = new Cutscene_Intro();
		activeCutscene = cutsceneIntro;
		batch = new SpriteBatch();

		try{ setupController(); }
		catch (Exception e){ controlType = ControlType.KEYBOARD; }

		startGame();
		gameState = GameState.CONTROLS;

		if (debug) gameState = GameState.GAME;
	}

	void startGame(){
		gameState = GameState.GAME;
		player = new Player(this);
		activeLevel = new Level_Main();
		activeRoom = activeLevel.getRoom(startRoom);
		map = activeRoom.getMap();

		Gdx.gl.glClearColor(blackR, blackG, blackB, 1);
		cam.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		cam.zoom = ZOOM;
		changeRoom(activeRoom, activeRoom.getStartPosition());

		heartBeat.setVolume(heartVolume);
		activeRoom.getMusic().setVolume(volume);
		heartBeat.play();
		updateGraphics();
		updateCamera();
	}

	@Override public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		deltaTime++;
		if (gameState != GameState.CONTROLS && gameState != GameState.CUTSCENE) updateVolume();
		updateTimers();
		if (controlType == ControlType.KEYBOARD) handleKeyboardInput();
		switch(gameState){
		case GAME: renderGame(); break;
		case TRANSITION: renderTransition(); break;
		case DEATH: renderDeath(); break;
		case CONTROLS: renderControls(); break;
		case CUTSCENE: renderCutscene(); break;
		case SHREDS: renderShreds(); break;
		case CREDITS: renderCredits(); break;
		default: break;
		}
	}

	void renderGame(){
		if (controlType == ControlType.CONTROLLER) handleControllerInput();
		activeRoom.update();
		updateEntities();
		updateGraphics();
		updateCamera();
		if (bossActive()) {
			if (!finalChase.isPlaying()) finalChase.play();
			updateBoss();
		}
	}

	void renderTransition(){
		if (transition.timeUp()){
			gameState = GameState.GAME;
			changeRoom(nextRoom, nextPos);
		}
	}

	void renderDeath(){
		batch.begin();
		if (player.getDirection() == Direction.LEFT) batch.draw(deadleft, player.getPosition().x, player.getPosition().y);
		else batch.draw(dead, player.getPosition().x, player.getPosition().y);
		batch.end();
		if (deathLength.timeUp()){
			gameState = GameState.GAME;
			restartLevel();
		}
	}

	void renderControls(){
		batch.begin();
		float posX = cam.position.x - (ZOOM*SCREENWIDTH/2);
		float posY = cam.position.y - (ZOOM*SCREENHEIGHT/2);
		if (controlType == ControlType.KEYBOARD) batch.draw(keyboardControls, posX, posY);
		else if (controlType == ControlType.CONTROLLER) batch.draw(controllerControls, posX, posY);
		batch.draw(logo, posX, posY+2);
		if (controlLength.timeUp()) batch.draw(pressJumpToBegin, posX + (ZOOM*SCREENWIDTH/2) - pressJumpToBegin.getWidth()/2, posY + TILE*4);
		batch.end();
	}

	void renderCutscene(){
		if (finalChase.isPlaying()) finalChase.stop();
		activeCutscene.updateCutscene();
		batch.begin();
		batch.draw(activeCutscene.getScene().getImage(), lowerLeftX(), lowerLeftY());
		batch.end();
		renderBorder(true);
		if (activeCutscene.sceneTimer.timeUp()) {
			batch.begin();
			if (controlType == ControlType.KEYBOARD) batch.draw(nextButtonK.getKeyFrame(deltaTime), 
					lowerLeftX() - TILE, lowerLeftY() - TILE);
			if (controlType == ControlType.CONTROLLER) batch.draw(nextButtonC.getKeyFrame(deltaTime), 
					lowerLeftX() - TILE, lowerLeftY() - TILE);
			batch.end();
		}
	}

	void renderShreds(){
		if (shredLength.timeUp()) setGameState(GameState.CREDITS);
		if (!hardMode){
			batch.begin();
			batch.draw(shredBase, lowerLeftX(), lowerLeftY());
			if (gotShred1) batch.draw(shred1, lowerLeftX(), lowerLeftY());
			if (gotShred2) batch.draw(shred2, lowerLeftX(), lowerLeftY());
			if (gotShred3) batch.draw(shred3, lowerLeftX(), lowerLeftY());
			if (gotShred4) batch.draw(shred4, lowerLeftX(), lowerLeftY());
			batch.end();
		}
		renderBorder(true);
	}

	float creditSpeed = 0.5f;
	float creditPosX = 0;
	boolean creditsStarted = false;

	void renderCredits(){

		if (!creditsStarted) {
			creditPosX = lowerLeftX();
			creditsStarted = true;
		}

		creditPosX = MathUtils.clamp(creditPosX -= creditSpeed, -(credits.getWidth()) + lowerLeftX() + TILE*15, lowerLeftX()); 
		batch.begin();

		if (hardMode) {
			batch.draw(creditPetHard.getKeyFrame(deltaTime), 
					lowerLeftX() - TILE*2 + SCREENWIDTH*ZOOM/2 - creditPet.getRegionWidth()/2, lowerLeftY() + TILE*7);
			batch.draw(creditsHard, creditPosX, lowerLeftY());
		}

		else {
			batch.draw(creditPetEasy.getKeyFrame(deltaTime), 
					lowerLeftX() - TILE*2 + SCREENWIDTH*ZOOM/2 - creditPet.getRegionWidth()/2, lowerLeftY() + TILE*7);
			batch.draw(credits, creditPosX, lowerLeftY());
		}
		batch.end();
		renderBorder(true);
	}

	float lowerLeftX(){ return cam.position.x + (TILE*2) - (SCREENWIDTH*ZOOM)/2; }

	float lowerLeftY(){ return cam.position.y + (TILE*2) - (SCREENHEIGHT*ZOOM)/2; }

	void setupController(){
		Controllers.addListener(this);
		controller = Controllers.getControllers().first();
	}

	private float crouchCheck = 0.7f;

	void handleControllerInput(){
		signum = controller.getAxis(1);
		if(Math.abs(controller.getAxis(1)) > deadZone){
			if (player.isGrounded()){
				player.state = State.RUN; return;
			}
		}
		else if (player.isGrounded()) player.state = State.STAND;
	}

	void handleKeyboardInput(){
		boolean action = Gdx.input.isKeyPressed(Keys.L);
		if (controlType == ControlType.KEYBOARD && gameState == GameState.CUTSCENE){
			if (action){
				cutsceneToGame();
			}
			return;
		}
		if (controlType == ControlType.KEYBOARD && gameState == GameState.CONTROLS) {
			if (controlLength.timeUp() && action){
				gameState = GameState.CUTSCENE;
				return;
			}
			else return; 
		}
		if (Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.D)) signum = 0;
		else if (Gdx.input.isKeyPressed(Keys.A)) signum = -1;
		else if (Gdx.input.isKeyPressed(Keys.D)) signum =  1;
		else if (!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)) signum = 0;

		if (action) handleCommand(Command.JUMP);
		if (Gdx.input.isKeyPressed(Keys.J)) handleCommand(Command.FIRE);
	}

	private boolean checkDown(){ 
		boolean downHeld = false;
		if (controlType == ControlType.KEYBOARD) downHeld = (Gdx.input.isKeyPressed(Keys.S));
		else if (controlType == ControlType.CONTROLLER) downHeld = (controller.getAxis(0) > crouchCheck);
		if (downHeld){
			boolean cantCrouch = player.setCrouch();
			if (cantCrouch) signum = 0;
		}
		return downHeld;
	}

	public boolean buttonDown(Controller controller, int buttonCode) {
		if (controlType == ControlType.CONTROLLER && gameState == GameState.CUTSCENE){
			cutsceneToGame();
			return false;
		}
		if (controlType == ControlType.CONTROLLER && gameState == GameState.CONTROLS) {
			if (controlLength.timeUp()){
				gameState = GameState.CUTSCENE;
				return false;
			}
			else return false;
		}
		if (buttonCode == 0) handleCommand(Command.JUMP);
		if (buttonCode == 1) handleCommand(Command.FIRE);
		return false;
	}

	private void cutsceneToGame(){
		if (activeRoom != null) activeRoom.getMusic().stop();
		boolean b = activeCutscene.updateScene();
		if (b){
			if (activeCutscene instanceof Cutscene_Ending){
				gameState = GameState.SHREDS;
			}
			else{
				activeCutscene = activeCutscene.nextCutscene();
				startGame();
				try{ changeRoom(nextRoom, nextPos); }
				catch(Exception e){ 
					nextRoom = activeLevel.getRoom(0);
					activeRoom = nextRoom;
					nextPos = nextRoom.getStartPosition();
					changeRoom(nextRoom, nextPos);
				}
			}
		}

	}

	private void handleCommand(Command com){
		if (com == Command.JUMP) player.prepJump();
		if (com == Command.FIRE) fire();
	}

	void updateVolume(){
		heartVolume = MathUtils.clamp((player.MAXHEALTH - player.getHealth())/player.MAXHEALTH, 0, 1);
		volume = 1 - heartVolume;
		if (!deathLength.timeUp()){
			heartVolume -= MathUtils.clamp((float)deathLength.getTime()*1.33f/(float)deathLength.getCount(), 0, 1);
		}
		heartBeat.setVolume(heartVolume);

		if (bossActive()){
			finalChase.setVolume(volume);
			activeRoom.getMusic().setVolume(0);
		}
		else{
			finalChase.setVolume(0);
			activeRoom.getMusic().setVolume(volume);
		}
	}

	void updateCamera(){
		cam.position.x = (cam.position.x*(camAdjustmentSpeed-1)+player.getPosition().x)/camAdjustmentSpeed;
		cam.position.y = (cam.position.y*(camAdjustmentSpeed-1)+player.getPosition().y + TILE * 2)/camAdjustmentSpeed;
		cam.position.x = MathUtils.round(MathUtils.clamp(cam.position.x, 
				SCREENWIDTH/(screenAdjust/ZOOM),  mapWidth -(SCREENWIDTH/(screenAdjust/ZOOM))  ) );
		cam.position.y = MathUtils.round(MathUtils.clamp(cam.position.y, 
				SCREENHEIGHT/(screenAdjust/ZOOM), mapHeight-(SCREENHEIGHT/(screenAdjust/ZOOM)) ) );
		cam.update();
		renderer.setView(cam);
	}

	void updateTimers(){
		for (Timer t: timerList) t.countUp();
	}

	void updateEntities(){
		Iterator<Entity> entityIter = activeRoom.getEntityList().iterator();
		while (entityIter.hasNext()){
			Entity e = entityIter.next();
			e.update(signum, rectangleList, activeRoom.getEntityList(), player, deltaTime, checkDown());
			if ( e.isOOB(mapWidth, mapHeight) || e.toRemove() ) entityIter.remove();
		}
		if (player.getHealth() <= 0){
			die();
		}
	}

	void updateGraphics(){
		batch.setProjectionMatrix(cam.combined);
		int[] arr;

		arr = new int[]{0};  // render back
		renderer.render(arr);

		if (activeRoom.clouds){ // render clouds
			batch.begin();
			if (bossActive()){
				batch.draw(clouds, (200+deltaTime*2)%2400, mapHeight-400);
				batch.draw(clouds, (200+deltaTime*2)%2400 - 2400, mapHeight-400);
			}
			else{
				batch.draw(clouds, (200+deltaTime)%2400, mapHeight-400);
				batch.draw(clouds, (200+deltaTime)%2400 - 2400, mapHeight-400);
			}
			batch.end();
		}

		int numLayers = map.getLayers().getCount() - 1;  // render tiles
		arr = new int[numLayers];
		for (int i = 0; i < arr.length; ++i) arr[i] = i+1;
		renderer.render(arr);
		
		batch.begin();  // render background entities
		for (Entity e: activeRoom.getEntityList()){
			if (e instanceof Window) batch.draw(e.getImage(), e.getPosition().x, e.getPosition().y);
		}
		batch.end();

		batch.begin();  // render normal entities
		for (Entity e: activeRoom.getEntityList()){
			if (!e.inFront && !(e instanceof Ghost || e instanceof Window)) batch.draw(e.getImage(), e.getPosition().x, e.getPosition().y);
		}
		batch.end();

		arr = new int[]{numLayers-1};  // render foreground
		renderer.render(arr);

		batch.begin();  // render  ghosts
		for (Entity e: activeRoom.getEntityList()){
			if (e instanceof Ghost) {
				Ghost g = (Ghost) e;
				boolean doneAppearing = g.hauntTimer.timeUp();
				if (deltaTime%8 > 4 || doneAppearing) batch.draw(e.getImage(), e.getPosition().x, e.getPosition().y);
			}
		}
		batch.end();

		renderBorder(false);

		batch.begin(); 	// render tint
		setTint();
		batch.draw(tint, cam.position.x - SCREENWIDTH/(screenAdjust/ZOOM), cam.position.y - SCREENHEIGHT/(screenAdjust/ZOOM));
		batch.setColor(1, 1, 1, 1);
		batch.end();

		batch.begin();  // render foreground entities
		for (Entity e: activeRoom.getEntityList()){
			if (e.inFront) batch.draw(e.getImage(), e.getPosition().x, e.getPosition().y);
		}
		batch.end();

		float posX = cam.position.x + (SCREENWIDTH-(TILE*3.5f)/ZOOM)/(screenAdjust/ZOOM);
		float posY = cam.position.y - ((40)/ZOOM)/(screenAdjust/ZOOM);

		if (bossActive()){  // draw eye
			bossBorder = bossAnim.getKeyFrame(deltaTime);
			batch.begin();
			batch.draw(eye, posX, posY);
			batch.draw(pupil, getPupilX(posX, posY), getPupilY(posX, posY));
			setTint();
			batch.draw(pupilShader, getPupilX(posX, posY), getPupilY(posX, posY));
			batch.setColor(1, 1, 1, 1);
			batch.end();
		}
	}

	private void renderBorder(boolean decisive){
		batch.begin(); // render border
		if (decisive) batch.draw(border, cam.position.x - SCREENWIDTH/(screenAdjust/ZOOM), cam.position.y - SCREENHEIGHT/(screenAdjust/ZOOM));
		else if (activeRoom.bigBorder) batch.draw(bigBorder, cam.position.x - SCREENWIDTH/(screenAdjust/ZOOM), cam.position.y - SCREENHEIGHT/(screenAdjust/ZOOM));
		else if (bossActive()) batch.draw(bossBorder, cam.position.x - SCREENWIDTH/(screenAdjust/ZOOM), cam.position.y - SCREENHEIGHT/(screenAdjust/ZOOM));
		else batch.draw(border, cam.position.x - SCREENWIDTH/(screenAdjust/ZOOM), cam.position.y - SCREENHEIGHT/(screenAdjust/ZOOM));
		batch.end();
	}

	private float eyeMod = 12;

	private float getPupilX(float posX, float posY){
		if (activeRoom instanceof Room_OutsideHouse) return (float) (posX + + eye.getWidth()/4 + deltaTime%4 - 4 * Math.random());
		return MathUtils.clamp(posX + eye.getWidth()/4 - Math.abs(275 - getPupilY(posX, posY))/30, posX+TILE/4, posX + eye.getWidth());
	}

	private float getPupilY(float posX, float posY){
		if (activeRoom instanceof Room_OutsideHouse) return (float) (posY + 4*deltaTime%40);
		else return posY + eye.getHeight()/5 - Eyeball.getAngle(posX, posY, player)*eyeMod;
	}

	private void updateBoss(){
		float posX = cam.position.x + (SCREENWIDTH-(TILE*4)/ZOOM)/(screenAdjust/ZOOM);
		float posY = cam.position.y - ((40)/ZOOM)/(screenAdjust/ZOOM);
		boolean normShoot, finalShoot = false;
		if (hardMode){
			normShoot = (deltaTime%300 == 239 || deltaTime%240 == 147);
			finalShoot = (deltaTime%150 == 128 || deltaTime%125 == 85);
		}
		else{
			normShoot = (deltaTime%250 == 239 || deltaTime%190 == 147 || deltaTime%130 == 86);
			finalShoot = (deltaTime%100 == 98 || deltaTime%75 == 55 || deltaTime%38 == 36);
		}
		if (normShoot || (finalShoot && activeRoom instanceof Room_OutsideHouse)) activeRoom.addEntity(new Eyeball(posX + TILE/2, posY + TILE, player));
		if (activeRoom instanceof Room_OutsideHouse) bossAnim.setFrameDuration(5);
		else bossAnim.setFrameDuration(10);
	}

	private void fire(){
		if (fireCooldown.timeUp() && !player.isCrouching()){
			fireCooldown.restart();
			Projectile p = new Projectile(player);
			activeRoom.addEntity(p);
		}
	}

	public static void addEntity(Entity e){
		activeRoom.addEntity(e);
	}

	private void setTint(){
		if (!transition2.timeUp()){
			batch.setColor(blackR, blackG, blackB, 1);
			return;
		}
		float adjustment = (float)3f/11f;
		float r = (float) MathUtils.clamp(activeRoom.getR() + ( (player.MAXHEALTH-player.getHealth()) * adjustment*1.5f), 0.0f, 1.0f);
		float b = (float) MathUtils.clamp(activeRoom.getB() - ( (player.MAXHEALTH-player.getHealth()) * adjustment*2.5f), 0.0f, 1.0f);
		float g = (float) MathUtils.clamp(activeRoom.getG() - ( (player.MAXHEALTH-player.getHealth()) * adjustment), 0.0f, 1.0f);
		float a = (float) MathUtils.clamp(activeRoom.getA() + ( (player.MAXHEALTH-player.getHealth()) * adjustment/1.5f), 0.0f, 0.5f);
		batch.setColor(r, g, b, a);
	}

	public void transition(Room room, Vector2 position, boolean isCutscene){
		nextRoom = room;
		nextPos = position;
		if (isCutscene) {
			heartBeat.setVolume(0);
			gameState = GameState.CUTSCENE;
		}
		else gameState = GameState.TRANSITION;
		transition.restart();
	}

	private void changeRoom (Room room, Vector2 position) {
		deltaTime = 0;
		transition2.restart();
		activeRoom.stopMusic();
		activeRoom = room;
		map = activeRoom.getMap();
		activeRoom.initEntities(player);
		player.setPosition(position);
		renderer = new OrthogonalTiledMapRenderer(map, 1);
		cam.position.x = player.getPosition().x;
		cam.position.y = player.getPosition().y + TILE * (1/ZOOM);
		cam.update();
		renderer.setView(cam);
		mapWidth  = map.getProperties().get("width",  Integer.class)*TILE;
		mapHeight = map.getProperties().get("height", Integer.class)*TILE;
		rectangleList.clear();
		activeRoom.setup();
		rectangleList.addAll(activeRoom.getRectangleList());
	}

	private void die(){
		deathLength.restart();
		gameState = GameState.DEATH;
	}

	private void restartLevel(){
		finalChase.stop();
		player.reset();
		try { activeLevel = activeLevel.getClass().newInstance(); }
		catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
		if (gotCat) {
			changeRoom(activeLevel.getRoom(4), activeLevel.getRoom(4).getStartPosition());
			player.direction = Direction.LEFT;
		}
		else changeRoom(activeLevel.getRoom(0), activeLevel.getRoom(0).getStartPosition());
	}

	private boolean bossActive(){
		return gotCat && !activeRoom.bigBorder;
	}

	public static float getVolume(){
		return volume;
	}

	public static void setGameState(GameState newGameState) {
		if (newGameState == GameState.SHREDS) {
			if (hardMode) newGameState = GameState.CREDITS;
			else{
				stopAllMusic();
				shredReveal.play();
				shredLength.restart();
			}
		}
		if (newGameState == GameState.CREDITS) {
			stopAllMusic();
			creditsMusic.play();
		}
		gameState = newGameState;
	}

	private static void stopAllMusic(){
		activeRoom.getMusic().stop();
		finalChase.stop();
		heartBeat.stop();
	}

	public static void setHardMode(){
		hardMode = true;
	}

	public static void setShred(int num){
		switch(num){
		case 1: gotShred1 = true; break;
		case 2: gotShred2 = true; break;
		case 3: gotShred3 = true; break;
		case 4: gotShred4 = true; break;
		default: break;
		}
		if (gotShred1 && gotShred2 && gotShred3 && gotShred4) finalCollect.play(0.52f);
		else collect.play(0.5f);
	}

	private enum ControlType { SELECT, CONTROLLER, KEYBOARD }
	public enum GameState { GAME, TRANSITION, CUTSCENE, DEATH, CONTROLS, CREDITS, SHREDS, TITLE }
	private enum Command { LEFT, RIGHT, JUMP, FIRE }

	public boolean buttonUp(Controller controller, int buttonCode) { return false; }
	public boolean axisMoved(Controller controller, int axisCode, float value) { return false; }
	public void connected(Controller controller) { }
	public void disconnected(Controller controller) { }
	public boolean povMoved(Controller controller, int povCode, PovDirection value) { return false; }
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) { return false; }
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) { return false; }
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) { return false; }

	@Override public boolean keyUp(int keycode) { return false; }
	@Override public boolean keyDown(int keycode) { return false; }
	@Override public boolean keyTyped(char character) { return false; }
	@Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
	@Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
	@Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
	@Override public boolean mouseMoved(int screenX, int screenY) { return false; }
	@Override public boolean scrolled(int amount) { return false; }
}
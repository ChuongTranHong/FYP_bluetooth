package com.piezo.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.piezo.maingame.PiezoGame;
import com.piezo.util.ImageButton;

public class GameOverScreen extends GameScreen {
	PiezoGame game;
	Skin skin;
	Stage ui;
	int screenHeight, screenWidth;
	SpriteBatch spriteBatch;
	TextureRegion backgoundTexture;

	public GameOverScreen(final PiezoGame game, int score) {
		this.game = game;
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		skin = new Skin(Gdx.files.internal("data/uiskinconfig.json"),
				Gdx.files.internal("data/uiskinover.png"));
		ui = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		Gdx.input.setInputProcessor(ui);
		BitmapFont font  = new BitmapFont(Gdx.files.internal("data/sim100.fnt"),
				Gdx.files.internal("data/sim100.png"), false);
		Label label = new Label("Game Over", new LabelStyle(font,null),
				"label");
		
		label.x = screenWidth / 2-label.width/2;
		label.y = screenHeight / 2 + 150;
		BitmapFont font1 = new BitmapFont(Gdx.files.internal("data/sim38.fnt"),
				Gdx.files.internal("data/sim38.png"), false);
		Label scoreText=new Label("Score "+score,new LabelStyle(font1, null),
				"score");
		scoreText.x= screenWidth / 2 - scoreText.width/2;
		scoreText.y= screenHeight /2 + 100;
		backgoundTexture = new TextureRegion(new Texture(
				Gdx.files.internal("data/gameoverbackground.jpg")));
		Texture replay = new Texture(
				Gdx.files.internal("data/retryTexture.png"));
		ImageButton replayButton = new ImageButton(new TextureRegion(replay,
				0f, 0f, 0.5f, 1f), new TextureRegion(replay, 0.5f, 0f, 1f, 1f));
		
		System.out.println("replay button width "+replayButton.width);
		replayButton.scaleX = 1.5f;
		replayButton.scaleY = 1.5f;
		System.out.println("replay button width "+replayButton.width);
		replayButton.x = (float) (screenWidth / 2 - 50-replayButton.width*1.5);
		replayButton.y = screenHeight / 2 - 80;
		replayButton.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				// System.out.println("return button click ");
				game.setScreen(new RunningScreen(game));
			}
		});
		Texture quit = new Texture(Gdx.files.internal("data/quitTexture.png"));
		ImageButton quitButton = new ImageButton(new TextureRegion(quit, 0f,
				0f, 0.5f, 1f), new TextureRegion(quit, 0.5f, 0f, 1f, 1f));
		quitButton.x = screenWidth / 2 + 50;
		quitButton.y = screenHeight / 2 - 70;
		quitButton.scaleX = 1.5f;
		quitButton.scaleY = 1.5f;
		quitButton.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				// System.out.println("return quit click ");
				game.setScreen(new MainMenu(game));
			}
		});

		ui.addActor(label);
		ui.addActor(scoreText);
		ui.addActor(replayButton);
		ui.addActor(quitButton);
		spriteBatch = new SpriteBatch();
	}

	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		ui.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		// Table.drawDebug(ui);

		spriteBatch.begin();
		spriteBatch.disableBlending();
		spriteBatch.draw(backgoundTexture, 0, 0, screenWidth, screenHeight);
		spriteBatch.enableBlending();
		spriteBatch.end();
		ui.draw();
	}

	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	public void show() {
		// TODO Auto-generated method stub

	}

	public void hide() {
		// TODO Auto-generated method stub

	}

	public void pause() {
		// TODO Auto-generated method stub

	}

	public void resume() {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}

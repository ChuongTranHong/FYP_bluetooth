package com.piezo.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.piezo.maingame.PiezoGame;
import com.piezo.model.VoltageDiagram;
import com.piezo.util.Setting;

public class TrainingScreen extends GameScreen {
	PiezoGame game;
	Skin skin;
	Stage ui;
	int screenHeight, screenWidth;
	public static VoltageDiagram voltageDiagram;
	public TrainingScreen(final PiezoGame game){
		Setting.training=true;
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		this.game = game;
		ui = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		Gdx.input.setInputProcessor(ui);
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				Gdx.files.internal("data/uiskin.png"));
		Button menuButton = new TextButton("Menu",
				skin.getStyle(TextButtonStyle.class), "menu");
		menuButton.x = screenWidth - 150;
		menuButton.y = screenHeight - 50;
		menuButton.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				System.out.println("in click menu");
//				PoolStore.runningScreen= this;
				game.setScreen(new MainMenu(game));
			}
		});

		ui.addActor(menuButton);
		voltageDiagram = new VoltageDiagram(0, 0,
				screenWidth , screenHeight);
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		ui.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		Table.drawDebug(ui);

		ui.draw();
		voltageDiagram.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}

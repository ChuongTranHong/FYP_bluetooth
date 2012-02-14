package com.piezo.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.piezo.maingame.PiezoGame;
import com.piezo.util.Config;

public class HelpScreen extends GameScreen{
	PiezoGame game;
	Skin skin;
	Stage ui;
//	private Table window;
	public HelpScreen(final PiezoGame game){
		this.game= game;
		skin = new Skin(Gdx.files.internal("data/uiskinconfig.json"),
				Gdx.files.internal("data/uiskinhelp.png"));
		ui = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		Gdx.input.setInputProcessor(ui);
		FileHandle handle = Gdx.files.internal("data/helpcontent.txt");
		String content = handle.readString();
		BitmapFont font  = new BitmapFont(Gdx.files.internal("data/verdana22.fnt"),
				Gdx.files.internal("data/verdana22.png"), false);
		Label label1 = new Label(content,
				new LabelStyle(font,null), "music");
//		label1.setWrap(true);
//		window = new Table("window");
//		window.width = ui.width();
//		window.height = ui.height()-50;
//		window.x = 0;
//		window.y = ui.height()-10;
//		window.debug();
//		window.row().padBottom(10).padTop(10);
//		window.add(label1);
		final ScrollPane scrollPane2 = new ScrollPane(label1, skin.getStyle(ScrollPaneStyle.class), "scroll");
//		scrollPane2.
		scrollPane2.x = 50;
		scrollPane2.y = 100;
		scrollPane2.width= ui.width()-80;
		scrollPane2.height=ui.height()-100;
		Button backButton = new TextButton("Back",
				skin.getStyle(TextButtonStyle.class), "back");
		backButton.align("bottom").align("left");
		backButton.padTop(10).padBottom(10).padRight(20).padLeft(10);
		backButton.setClickListener(new ClickListener() {

			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(new MainMenu(game));
			}
		});
		Texture texture = new Texture(
				Gdx.files.internal("data/helpbackground.jpg"));
		Image background = new Image(texture);
		float ratiox = ui.width() / texture.getWidth();
		float ratioy = ui.height() / texture.getHeight();
		background.scaleX = ratiox;
		background.scaleY = ratioy;
		ui.addActor(background);
		ui.addActor(scrollPane2);
		ui.addActor(backButton);
		System.out.println(" scroll widght "+scrollPane2.width+" screen "+ui.width());
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		ui.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		// Table.drawDebug(ui);
		ui.draw();
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

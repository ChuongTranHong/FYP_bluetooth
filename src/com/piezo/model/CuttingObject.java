package com.piezo.model;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Region;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.piezo.screen.RunningScreen;
import com.piezo.util.Config;
import com.piezo.util.PoolStore;
import com.piezo.util.Setting;
import com.piezo.util.TextOutput;
import com.piezo.util.Timer;

public abstract class CuttingObject {

	protected boolean bomb=false;
	protected byte initTimer;
	Timer timer;
	float x,y,width,height;
	byte cutStep=0;
	public boolean finishAnimation=true;
//	protected Pool<TextOutput> pool;
	protected  short  lifeSpan;
	protected short currentLife;
	protected List<TextOutput> textOutList=new ArrayList<TextOutput>();
	protected byte compatibleCut;
	protected Texture texture;
	protected String texturePath;
	protected TextureRegion textureRegion;
	protected float beginingRatio=2f;
	protected float currentRatio=beginingRatio;
	protected float targetRatio=beginingRatio;
	TextOutput textBonus;
	protected TextureRegion textureLifeSpan= new TextureRegion(new Texture((Gdx.files.internal(Config.asString("lifeSpanTexture")))));
	protected int index;
	boolean cutLeft=false, cutRight=false;
	public abstract boolean supportCut(byte typeCut);
	public abstract boolean  takeCut(byte typeCut,boolean left);
	public abstract void takeCut(byte typeCut,byte times);
	public float currentPercentLifeSpan(){
		return (float)currentLife/lifeSpan;
	}
	public TextureRegion getTexture(){
		return textureRegion;
	}
	public void draw(SpriteBatch spriteBatch,BitmapFont font,TextureRegion leftTexture,TextureRegion rightTexture){
		
		finishAnimation = true;
		if(currentRatio>targetRatio){
			currentRatio-=0.05;
		}else currentRatio=targetRatio;
		
		//render the life span
		if(!bomb)spriteBatch.draw(textureLifeSpan,this.x+50,height -50,0f,0f,textureLifeSpan.getRegionWidth(),textureLifeSpan.getRegionHeight(),currentRatio,1f,0f);
		
		if(currentRatio==0){
			
			if(textBonus==null){
				RunningScreen.score+=50;
				textBonus = PoolStore.textPool.obtain();
				textBonus.x=0;
				textBonus.y=0;
			}
			textBonus.text=" Bonus 50 points ";
			font.draw(spriteBatch, textBonus.text, this.x+100, height-150+textBonus.y);
			textBonus.y+=2;
			if(textBonus.y>=40){
				PoolStore.textPool.free(textBonus);
				textBonus =null;
			}else finishAnimation = false;
			
		}
		
		//render timer
		timer.render(spriteBatch, font,this.x+width-50,this.y+height-50);
		
		//render the texture of object
		if(!Setting.debug)
			spriteBatch.draw(textureRegion, this.x+50, height- 400);
		else spriteBatch.draw(textureRegion, this.x+50, height- 300);
		
		//render the damage text
		for(index=0;index<textOutList.size();index++){
			TextOutput temp=textOutList.get(index);
			
			font.draw(spriteBatch, temp.text, this.x+50, height-150+temp.y);
			
			temp.y +=2;
			if(temp.y == 20){
				textOutList.remove(index);
				
				PoolStore.textPool.free(temp);
			}else finishAnimation= false;
		}
		
		//render the cut animation
		if(cutRight){
			
			if(!Setting.debug)
				spriteBatch.draw(leftTexture,this.x+50+20*cutStep,height-300);
			else spriteBatch.draw(leftTexture,this.x+50+20*cutStep,height-300); 
			cutStep++;
			if(cutStep>10){
				
				cutRight=false;
				cutStep=0;
			}else finishAnimation= false;
		}else if(cutLeft){
			
			if(!Setting.debug)
				spriteBatch.draw(rightTexture,this.x+textureRegion.getRegionWidth()+50-20*cutStep,height-300);
			else spriteBatch.draw(rightTexture,this.x+textureRegion.getRegionWidth()+50-20*cutStep,height-100);
			cutStep++;
			if(cutStep>10){
				
				cutLeft=false;
				cutStep=0;
			}else finishAnimation= false;
		}
		
	}
//	public void setPool(Pool<TextOutput> pool){
//		this.pool=pool;
//	}
	public void decreaseTime(){
		timer.decreaseTime();
	}
	public boolean isDead(){
		return (timer.timeCountDown<=0 || currentLife<=0);
	}
//	public void setPool(Pool pool){
//		this.pool = pool;
//	}
	public void setRegion(float x, float y ,float width, float height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	public void copySetting(CuttingObject object){
		this.x=object.x;
		this.y=object.y;
		this.width= object.width;
		this.height= object.height;
	
	}
	public void reset(){
		textureLifeSpan.setRegion(new Texture((Gdx.files.internal(Config.asString("lifeSpanTexture")))));

		texture = new  Texture(Gdx.files.internal(Config.asString(texturePath)));

		currentLife=lifeSpan;
		timer.timeCountDown=initTimer;
		currentRatio=targetRatio=beginingRatio;
		cutStep=0;
		cutLeft=false;
		cutRight=false;
		while(textOutList.size()>0){
			PoolStore.textPool.free(textOutList.get(0));
			textOutList.remove(0);
		}
		

	}
	public abstract void free();
	public  static CuttingObject obtain() {
		return null;
	}
	
}

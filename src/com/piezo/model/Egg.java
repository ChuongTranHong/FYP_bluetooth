package com.piezo.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.piezo.screen.RunningScreen;
import com.piezo.util.Command;
import com.piezo.util.Config;
import com.piezo.util.PoolStore;
import com.piezo.util.TextOutput;
import com.piezo.util.Timer;

public class Egg extends CuttingObject {
	protected final byte DAMAGE_STRONG_FORCE = Config.asByte("egg.StrongDamage", (byte) 0);
	protected final byte DAMAGE_NORMAL_FORCE = Config.asByte("egg.NormalDamage", (byte) 10);
	protected byte timeFail=0;
	public Egg(){
		texturePath = "eggTexture";
		lifeSpan =Config.asShort("egg.LifeSpan", (short) 50);
		currentLife=lifeSpan;
		
		texture=new Texture(Gdx.files.internal(Config.asString(texturePath)));
		textureRegion= new TextureRegion(texture,0,0,200,256);
		initTimer = Config.asByte("egg.Timer", (byte) 10);
		timer= new Timer(initTimer);
		this.x=0;
		this.y=0;
		this.width=0;
		this.height=0;
	}
	
	public Egg(float x, float y, float width,float height){
		texturePath = "eggTexture";
		lifeSpan =Config.asShort("egg.LifeSpan", (short) 50);
		currentLife=lifeSpan;
		texture=new Texture(Gdx.files.internal(Config.asString(texturePath)));
		textureRegion= new TextureRegion(texture,0,0,200,256);
		initTimer = Config.asByte("egg.Timer", (byte) 10);
		timer= new Timer(initTimer);
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	@Override
	public boolean supportCut(byte typeCut) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean takeCut(byte typeCut, boolean left) {
		// TODO Auto-generated method stub
		if(currentLife<=0) return true;
		if(typeCut== Command.NOCOMMAND || typeCut==Command.UNDETERMINED)return false;
//		if(typeCut==Command.STRONG_SHORT_FORCE){
//			
//			if(currentLife!=lifeSpan){
//				RunningScreen.failTime++;
//
//				return true;
//			}
//		}
		finishAnimation=false;
		TextOutput temp = PoolStore.textPool.obtain();
		temp.x=0;
		temp.y=0;
		if(typeCut== Command.STRONG_SHORT_FORCE){
			currentLife-=DAMAGE_STRONG_FORCE;
			temp.text="-"+DAMAGE_STRONG_FORCE+" damage";
			if(currentLife!=lifeSpan){
				timeFail++;
//				if(timeFail==3) currentLife=0;
				temp.text = timeFail+" wrong cut";
			}
			RunningScreen.score+=DAMAGE_STRONG_FORCE;
		}else
		 if(typeCut== Command.NORMAL_SHORT_FORCE){
			currentLife -= DAMAGE_NORMAL_FORCE;
			temp.text="-"+DAMAGE_NORMAL_FORCE+" damage";
			RunningScreen.score+=DAMAGE_NORMAL_FORCE;
		}
		if(currentLife<0 ){
			currentLife=0;
		}
		targetRatio=currentPercentLifeSpan() * beginingRatio;
		
		
		
		textOutList.add(temp);
		if(left)cutLeft=true;
		else cutRight=true;
		return true;
	
	}
	public void reset(){
		super.reset();
		timeFail=0;
	}
	@Override
	public void takeCut(byte typeCut, byte times) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void free() {
		// TODO Auto-generated method stub
		PoolStore.eggPool.free(this);
	}

	public static CuttingObject obtain() {
		// TODO Auto-generated method stub
		return PoolStore.eggPool.obtain();
	}
	public boolean isDead(){
		return (super.isDead() ||timeFail>=3);
	}
}

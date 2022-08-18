package com.fornellogames.vivos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.fornellogames.fasesinfo.Faseum;
import com.fornellogames.main.Jogo;

public class Inimigoa extends Vivo {

	private double speed = 1;
	
	public static int quantidade = 10;
	
	private int vidasolida = 2;
	private int fusao = 2;
	private int vidaliquida = 2;
	private int vapor = 6;
	
	private int maskx = 8;
	private int masky = 8;
	private int maskw = 10;
	private int maskh = 10;
	
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;
	private boolean moveu = false;
	private BufferedImage[] atacando;
	private BufferedImage[] liquido;
	
	public Inimigoa(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		atacando = new BufferedImage[2];
		atacando[0] = Jogo.spritesheet.getSprite(0, 32, 16, 16);
		atacando[1] = Jogo.spritesheet.getSprite(16, 32, 16, 16);
		
		liquido = new BufferedImage[2];
		liquido[0] = Jogo.spritesheet.getSprite(32, 32, 16, 16);
		liquido[1] = Jogo.spritesheet.getSprite(48, 32, 16, 16);
		
	}
	
	public void rodar() {
		if(Jogo.rand.nextInt(100) < 15) {
			
			if(colidindoComBarreira() == false) {
			if((int)x < 81 && Faseum.isFree((int)(x+speed), this.getY())) {
				moveu = true;
				x+=speed;
			}
			else if((int)x > 81 && Faseum.isFree((int)(x-speed), this.getY())){
				moveu = true;
				x-=speed;
			}
		
			if(moveu) {
				frames++;
				if(frames == maxFrames) {
					frames = 0;
					index++;
					if(index > maxIndex) {
						index = 0;
					}
				}
			}
		}else {
			if(colidindoComBarreira() == true) {
				Jogador.barreira_vida--;
			}
		}
		}
			
		
			this.collidingBalas();
			this.collidingBalasL();
			
			if(vidasolida <= 0 && fusao <= 0 && vidaliquida <= 0 && vapor <= 0) {
				quantidade--;
				Jogo.vivos.remove(this);
				return;
				
			}
	}
	
	public void collidingBalas() {
		for(int i = 0; i < Jogo.balas.size(); i++) {
			Vivo atual = Jogo.balas.get(i);
			if(atual instanceof Bala) {
				if(Vivo.isColliding(this, atual)) {
					if(vidasolida > 0) {
						vidasolida--;
						Jogo.balas.remove(i);
						return;
					}else if(vidasolida <= 0 && fusao <= 0 && vidaliquida > 0) {
						vidaliquida--;
						Jogo.balas.remove(i);
						return;
					}	
				}
			}
		}
		
	}
	
	public void collidingBalasL() {
		for(int i = 0; i < Jogo.balasl.size(); i++) {
			Vivo atual = Jogo.balasl.get(i);
			if(atual instanceof Balal) {
				if(Vivo.isColliding(this, atual)) {
					if(vidasolida <= 0 && fusao > 0) {
						fusao--;
						Jogo.balasl.remove(i);
						return;
					}else if(vidasolida <= 0 && fusao <= 0 && vidaliquida <= 0) {
						vapor--;
						Jogo.balasl.remove(i);
						return;
					}
				}
			}
		}
		
	}
	
	public boolean colidindoComBarreira() {
		Rectangle inimigoColisao = new Rectangle(this.getX() - maskx, this.getY() - masky, maskw, maskh);
		Rectangle barreira = new Rectangle(64, 48, 16, 112);
		
		if (inimigoColisao.intersects(barreira)) {
			return true;
		}
		
		return false;
	}
	
	public void renderizar(Graphics g) {
		if(moveu && vidasolida > 0 && fusao > 0) {
			g.drawImage(atacando[index], this.getX(), this.getY(), null);
		}else if(moveu && vidasolida <= 0 && fusao > 0) {
			g.drawImage(atacando[index], this.getX(), this.getY(), null);
		}else if(moveu && vidasolida <= 0 && fusao <= 0) {
			g.drawImage(liquido[index], this.getX(), this.getY(), null);
		}
	}

}

package com.fornellogames.vivos;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.fornellogames.fasesinfo.Faseum;
import com.fornellogames.graficos.Fundos;
import com.fornellogames.graficos.Spritesheet;
import com.fornellogames.main.Jogo;

public class Jogador extends Vivo {
	
	
	public boolean up, down;
	public double speed = 0.8;
	
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;
	private boolean moveu = false;
	private BufferedImage[] animavertical;
	
	private int grames = 0, maxGrames = 20, ondex = 0, maxOndex = 1;
	private boolean parado = true;
	private BufferedImage[] paradodireita;
	
	private int newframes = 0, maxNewframes = 1;
	public boolean trocando = false;
	
	private boolean armado = false;
	
	public boolean atirando = false;
	
	public boolean transformando = false;
	
	public static int barreira_vida = 280;
	
	public Jogador(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		animavertical = new BufferedImage[2];
		paradodireita = new BufferedImage[2];
		
		for(int i =0; i < 2; i++) {
			animavertical[i] = Jogo.spritesheet.getSprite(0 + (i*16), 16, 16, 16);
		}

		for(int i =0; i < 2; i++) {
			paradodireita[i] = Jogo.spritesheet.getSprite(0 + (i*16), 0, 16, 16);
		}
		
	}
	
	public void rodar() {
		moveu = false;
		parado = true; 
		
		if(up && Faseum.isFree(this.getX(), (int)(y-speed))) {
			moveu  = true;
			parado = false;
			y-=speed; 
		}
		else if(down && Faseum.isFree(this.getX(), (int)(y+speed))) {
			moveu  = true;
			parado = false;
			y+=speed;
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

		if(parado) {
			grames++;
			if(grames == maxGrames) {
				grames = 0;
				ondex++;
				if(ondex > maxOndex) {
					ondex = 0;
				}
			}
		}
		
		this.checkArma();
		
		if(atirando && newframes == 0) {
			atirando = false;
			if(armado) {
			int dx = 0;
			int px = 0;
			int py = 6;
			if(up) {
				px = 18;
				dx = 1;
			}else {
				px = 18;
				dx = 1;
			}
			
			Bala bala = new Bala(this.getX()+px, this.getY()+py, 3, 3, null, dx, 0);
			Jogo.balas.add(bala);
			}
		}
		
		if(transformando && newframes == 1) {
			transformando = false;
			if(armado) {
			int dx = 0;
			int px = 0;
			int py = 6;
			if(up) {
				px = 18;
				dx = 1;
			}else {
				px = 18;
				dx = 1;
			}
			
			Balal balal = new Balal(this.getX()+px, this.getY()+py, 3, 3, null, dx, 0);
			Jogo.balasl.add(balal);
			}
		}
		
		if(barreira_vida <= 0) {
			Jogo.gameState = "GAME_OVER";
			barreira_vida = 0;
		}
		
		if(trocando) {
			trocando = false;
			newframes++;
			if(newframes > maxNewframes) {
				newframes = 0;
			}
		}
		
	}
	
	public void checkArma() {
		for(int i = 0; i < Jogo.vivos.size(); i++) {
			Vivo atual = Jogo.vivos.get(i);
			if(atual instanceof Caixa) {
				if(Vivo.isColliding(this, atual)) {
					armado = true;
					Jogo.vivos.remove(atual);
				}
			}
		}	
	}

	
	public void renderizar(Graphics g) {
		if(moveu) {
			g.drawImage(animavertical[index], this.getX(), this.getY(), null);
			if(armado && newframes == 0) {
				g.drawImage(Vivo.ARMACS_EN, this.getX() +11, this.getY(), null);
			}else if(armado && newframes == 1) {
				g.drawImage(Vivo.ARMACL_EN, this.getX() +11, this.getY(), null);
			}
			
		}else if(parado) {
			g.drawImage(paradodireita[ondex], this.getX(), this.getY(), null);
			if(armado && newframes == 0) {
				g.drawImage(Vivo.ARMACS_EN, this.getX() +11, this.getY(), null);
			}else if (armado && newframes == 1) {
				g.drawImage(Vivo.ARMACL_EN, this.getX() +11, this.getY(), null);
			}
		}

		
		}
			}


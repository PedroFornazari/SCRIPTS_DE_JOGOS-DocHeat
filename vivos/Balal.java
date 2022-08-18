package com.fornellogames.vivos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.fornellogames.main.Jogo;

public class Balal extends Vivo {

	private int dx;
	private int dy;
	private double speedBalal = 5;
	
	private int vida = 50, curVida = 0;
	
	public Balal(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void rodar() {
		x+=dx*speedBalal;
		y+=dy*speedBalal;	
		curVida++;
		if(curVida == vida) {
			Jogo.balas.remove(this);
			return;
		}
	}
	
	public void renderizar(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillOval(this.getX(), this.getY(), width, height);
		
	}
	
}

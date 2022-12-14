package com.fornellogames.vivos;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.fornellogames.main.Jogo;

public class Vivo {

	public static BufferedImage ARMACS_EN = Jogo.spritesheet.getSprite(32, 0, 16, 16);
	public static BufferedImage ARMACL_EN = Jogo.spritesheet.getSprite(48, 0, 16, 16);
	public static BufferedImage BARREIRA_EN = Jogo.spritesheet.getSprite(32, 16, 16, 16);
	public static BufferedImage CAIXA_EN = Jogo.spritesheet.getSprite(48, 16, 16, 16);
	public static BufferedImage AGUA_EN = Jogo.spritesheet.getSprite(0, 32, 16, 16);
	public static BufferedImage SAL_EN = Jogo.spritesheet.getSprite(0, 48, 16, 16);
	public static BufferedImage ALUMINIO_EN = Jogo.spritesheet.getSprite(0, 64, 16, 16);
	public static BufferedImage COBRE_EN = Jogo.spritesheet.getSprite(0, 80, 16, 16);
	public static BufferedImage FERRO_EN = Jogo.spritesheet.getSprite(0, 96, 16, 16);
	public static BufferedImage OURO_EN = Jogo.spritesheet.getSprite(0, 112, 16, 16);
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	private int maskx, masky, maskw, maskh;
	
	public Vivo(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.maskw = width;
		this.maskh = height;
		
	}
	
	public void setMask(int maskx, int masky, int maskw, int maskh) {
		this.maskx =  maskx;
		this.masky = masky;
		this.maskw = maskw;
		this.maskh = maskh;
		
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int) this.x;
	}
	
	public int getY() {
		return (int) this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void rodar() {
		
	}
	
	public static boolean isColliding(Vivo e1, Vivo e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.maskw, e1.maskh);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.maskw, e2.maskh);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void renderizar(Graphics g) {
		g.drawImage(sprite, this.getX(), this.getY(), null);
		
	}
	
}

package com.fornellogames.fasesinfo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.fornellogames.main.Jogo;

public class Tile {

	public static BufferedImage TILE_CHAO = Jogo.fundos.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_QUIMICO = Jogo.fundos.getSprite(16, 0, 16, 16);
	public static BufferedImage TILE_DETONADO = Jogo.fundos.getSprite(32, 0, 16, 16);
	public static BufferedImage TILE_MESAPE = Jogo.fundos.getSprite(48, 0, 16, 16);
	public static BufferedImage TILE_MESAPD = Jogo.fundos.getSprite(96, 0, 16, 16);
	public static BufferedImage TILE_MESAC = Jogo.fundos.getSprite(64, 0, 16, 16);
	public static BufferedImage TILE_MESAPC = Jogo.fundos.getSprite(80, 0, 16, 16);
	public static BufferedImage TILE_PAREDE = Jogo.fundos.getSprite(112, 0, 16, 16);
	public static BufferedImage TILE_PROTECAO = Jogo.fundos.getSprite(128, 0, 16, 16);
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		
	}
	
	public void renderizar(Graphics g) {
		g.drawImage(sprite, x, y, null);
		
	}
	
}

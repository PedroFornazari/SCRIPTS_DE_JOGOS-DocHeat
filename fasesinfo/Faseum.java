package com.fornellogames.fasesinfo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.fornellogames.graficos.Fundos;
import com.fornellogames.graficos.Spritesheet;
import com.fornellogames.main.Jogo;
import com.fornellogames.vivos.Inimigoa;
import com.fornellogames.vivos.Jogador;
import com.fornellogames.vivos.Armacs;
import com.fornellogames.vivos.Barreira;
import com.fornellogames.vivos.Caixa;
import com.fornellogames.vivos.Vivo;

public class Faseum {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public Faseum(String path) {
		try {
			BufferedImage mapaum = ImageIO.read(getClass().getResource(path));
			int [] pixels = new int [mapaum.getWidth() * mapaum.getHeight()];
			WIDTH = mapaum.getWidth();
			HEIGHT = mapaum.getHeight();
			tiles = new Tile[mapaum.getWidth() * mapaum.getHeight()];
			mapaum.getRGB(0, 0, mapaum.getWidth(), mapaum.getHeight(), pixels, 0, mapaum.getWidth());
			for(int xx = 0; xx < mapaum.getWidth(); xx++) {
				for(int yy = 0; yy < mapaum.getHeight(); yy++) {
					int pixelAtual = pixels [xx + (yy*mapaum.getWidth())];
					
					if (pixelAtual == 0xFF808080) {
						//Chão
						tiles[xx + (yy * WIDTH)] = new Pisos(xx*16, yy*16, Tile.TILE_CHAO);
					}else if (pixelAtual == 0xFF37FF00) {
						//Chão com Químico
						tiles[xx + (yy * WIDTH)] = new Pisos(xx*16, yy*16, Tile.TILE_QUIMICO);
					}else if (pixelAtual == 0xFF000000) {
						//Chão Detonado
						tiles[xx + (yy * WIDTH)] = new Bloqueios(xx*16, yy*16, Tile.TILE_DETONADO);
					}else if (pixelAtual == 0xFFFFAF00) {
						//Mesa PE
						tiles[xx + (yy * WIDTH)] = new Bloqueios(xx*16, yy*16, Tile.TILE_MESAPE);
					}else if (pixelAtual == 0xFFFFA000) {
						//Mesa PD
						tiles[xx + (yy * WIDTH)] = new Bloqueios(xx*16, yy*16, Tile.TILE_MESAPD);
					}else if (pixelAtual == 0xFFFF9000) {
						//Mesa Meio
						tiles[xx + (yy * WIDTH)] = new Bloqueios(xx*16, yy*16, Tile.TILE_MESAC);
					}else if (pixelAtual == 0xFFFF8000) {
						//Mesa PC
						tiles[xx + (yy * WIDTH)] = new Bloqueios(xx*16, yy*16, Tile.TILE_MESAPC);
					}else if (pixelAtual == 0xFFFFFFFF) {
						//Parede
						tiles[xx + (yy * WIDTH)] = new Bloqueios(xx*16, yy*16, Tile.TILE_PAREDE);
					}else if (pixelAtual == 0xFFFFFF00) {
						//Proteção
						tiles[xx + (yy * WIDTH)] = new Bloqueios(xx*16, yy*16, Tile.TILE_PROTECAO);
					}else if (pixelAtual == 0xFF0C00FF) {
						//Jogador
						Jogo.jogador.setX(xx*16);
						Jogo.jogador.setY(yy*16);
						tiles[xx + (yy * WIDTH)] = new Pisos(xx*16, yy*16, Tile.TILE_CHAO);
					}else if (pixelAtual == 0xFFFF0000) {
						//Caixa de Armas
						Jogo.vivos.add(new Caixa(xx*16, yy*16, 16, 16, Vivo.CAIXA_EN));
						tiles[xx + (yy*WIDTH)] = new Pisos(xx*16, yy*16, Tile.TILE_CHAO);
					}else if (pixelAtual == 0xFF7D3411) {
						//Barreira
						Jogo.vivos.add(new Barreira(xx*16, yy*16, 16, 16, Vivo.BARREIRA_EN));
						tiles[xx + (yy * WIDTH)] = new Bloqueios(xx*16, yy*16, Tile.TILE_CHAO);
					}else if (pixelAtual == 0xFF00FBFF) {
						//Inimigo Água
						Jogo.vivos.add(new Inimigoa(xx*16, yy*16, 16, 16, Vivo.AGUA_EN));
						tiles[xx + (yy * WIDTH)] = new Pisos(xx*16, yy*16, Tile.TILE_CHAO);
					}else {
						//Chão
						tiles[xx + (yy * WIDTH)] = new Pisos(xx*16, yy*16, Tile.TILE_MESAC);
					}
					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;
		
		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;
		
		return ! ((tiles[x1 + (y1*Faseum.WIDTH)] instanceof Bloqueios) ||
				(tiles[x2 + (y2*Faseum.WIDTH)] instanceof Bloqueios) ||
				(tiles[x3 + (y3*Faseum.WIDTH)] instanceof Bloqueios) ||
				(tiles[x4 + (y4*Faseum.WIDTH)] instanceof Bloqueios));
	}
	
	public static void restartGame() {
		Jogo.vivos.clear();
		Jogo.vivos = new ArrayList<Vivo>();
		Jogo.fundos = new Fundos("/fundos.png");
		Jogo.spritesheet = new Spritesheet("/spritesheet.png");
		Jogo.jogador = new Jogador(0, 0, 16, 16, Jogo.spritesheet.getSprite(0, 0, 16, 16));
		Jogador.barreira_vida = 280;
		Inimigoa.quantidade = 10;
		Jogo.vivos.add(Jogo.jogador);
		Jogo.faseum = new Faseum("/mapa1.png");
		return;
	}
			
	public void renderizar(Graphics g) {
		int xstart = 0;
		int ystart = 0;
		
		int xfinal = xstart + (Jogo.WIDTH / 16) + 1;
		int yfinal = ystart + (Jogo.HEIGHT / 16);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles [xx + (yy*WIDTH)];
				tile.renderizar(g);
				
			}
			
		}
		
	}
			
	
}

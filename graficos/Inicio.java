package com.fornellogames.graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Inicio {

	private BufferedImage inicio;
	
	public Inicio(String path) {
		try {
			inicio = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return inicio.getSubimage(x, y, width, height);
	}
	
}

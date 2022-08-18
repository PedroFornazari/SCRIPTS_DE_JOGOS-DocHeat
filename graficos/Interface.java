package com.fornellogames.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.fornellogames.main.Jogo;
import com.fornellogames.vivos.Jogador;

public class Interface {

	public void renderizar(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(10, 10, 280, 10);
		g.setColor(Color.BLUE);
		g.fillRect(10, 10, (int)Jogador.barreira_vida, 10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("comic sans pro", Font.PLAIN, 9));
		g.drawString((int)Jogador.barreira_vida+"/"+(int)280, 70, 18);
		g.setColor(Color.BLACK);
		g.setFont(new Font("comic sans pro", Font.PLAIN, 15));
		g.drawString("Fase 1: GELO", 100, 175);
		
	}
	
}

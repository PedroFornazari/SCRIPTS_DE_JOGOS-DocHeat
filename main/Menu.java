package com.fornellogames.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.fornellogames.fasesinfo.Faseum;

public class Menu {
	
	public String[] options = {"Jogar", "Sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up, down, enter;
	
	private boolean showMessageMenu = true;
	private int framesMenu = 0;
	
	public void rodar() {
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		
		if(enter) {
			enter = false;
			if(options[currentOption] == "Jogar") {
				Faseum.restartGame();
				Jogo.gameState = "NORMAL";
			}
			
		}
		
		this.framesMenu++;
		if(this.framesMenu == 50) {
			this.framesMenu = 0;
			if(this.showMessageMenu)
				this.showMessageMenu = false;
				else
					this.showMessageMenu = true;
		}
	}
	
	public void renderizar(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.ITALIC, 100));
		g.drawString("Dr. Heat", 740, 100);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.ITALIC, 75));
		g.drawString("Jogar", 830, 220);
		g.setFont(new Font("arial", Font.ITALIC, 55));
		g.drawString("Em Breve", 810, 580);
		
		if(options[currentOption] == "Jogar" && showMessageMenu) {
			g.setColor(Color.BLACK);
			g.drawString("-->", 730, 220);
		}

		
	}

}

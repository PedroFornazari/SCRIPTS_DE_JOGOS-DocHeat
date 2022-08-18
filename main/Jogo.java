package com.fornellogames.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.fornellogames.fasesinfo.Faseum;
import com.fornellogames.graficos.Fundos;
import com.fornellogames.graficos.Inicio;
import com.fornellogames.graficos.Interface;
import com.fornellogames.graficos.Spritesheet;
import com.fornellogames.vivos.Bala;
import com.fornellogames.vivos.Balal;
import com.fornellogames.vivos.Inimigoa;
import com.fornellogames.vivos.Jogador;
import com.fornellogames.vivos.Vivo;


public class Jogo extends Canvas implements Runnable, KeyListener {


	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean rodando = true;
	public static final int WIDTH = 300;
	public static final int HEIGHT = 180;
	public static final int SCALE = 4;
	
	private BufferedImage image;
	
	public static List <Vivo> vivos;
	public static List <Bala> balas;
	public static List <Balal> balasl;
	public static Spritesheet spritesheet;
	
	public static Fundos fundos;
	
	public static Inicio inicio;
	private BufferedImage tela;
	
	public static Faseum faseum;
	
	public static Jogador jogador;
	
	public static Random rand;
	
	public static String gameState = "MENU";
	
	private boolean showMessageOver = true;
	private int framesOver = 0;
	private boolean recomeçar = false;
	private boolean retornar = false;
	
	public Interface ui;
	
	public Menu menu;


	public Jogo(){
		rand = new Random();
		addKeyListener(this);
		setPreferredSize(new Dimension( WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		ui = new Interface();
		image = new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		vivos = new ArrayList<Vivo>();
		balas = new ArrayList<Bala>();
		balasl = new ArrayList<Balal>();
		fundos = new Fundos("/fundos.png");
		spritesheet = new Spritesheet("/spritesheet.png");
		jogador = new Jogador(0, 0, 16, 16, spritesheet.getSprite(0, 0, 16, 16));
		vivos.add(jogador);
		faseum = new Faseum("/mapa1.png");
		inicio = new Inicio("/inicio.png");
		tela = inicio.getSprite(0, 0, 100, 60);
		menu = new Menu();
		
	}
	
	public void initFrame() {
		frame = new JFrame ("Dr. Heat");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public synchronized void inicio() {
		thread = new Thread(this);
		rodando = true;
		thread.start ();
		
	}
	
	public synchronized void parar() {
		rodando = false; 
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[] ){
		Jogo jogo = new Jogo();
		jogo.inicio();
		
	}

	public void rodar () {
		if(gameState == "NORMAL") {
		this.recomeçar = false;
		this.retornar = false;
		for(int i = 0; i < vivos.size(); i++) {
			Vivo e = vivos.get(i);
			e.rodar();
		}
		
		for(int i = 0; i < balas.size(); i++) {
			balas.get(i).rodar();
		}
		
		for(int i = 0; i < balasl.size(); i++) {
			balasl.get(i).rodar();
		}
		
		if(Inimigoa.quantidade == 0) {
			gameState = "YOU_WIN";
			
			
		}
		
		}else if(gameState == "GAME_OVER" || gameState == "YOU_WIN") {
			this.framesOver++;
			if(this.framesOver == 50) {
				this.framesOver = 0;
				if(this.showMessageOver)
					this.showMessageOver = false;
					else
						this.showMessageOver = true;
			}
		}
		
		if(recomeçar) {
			this.recomeçar = false;
			this.retornar = false;
			gameState = "NORMAL";
			Faseum.restartGame();
			
		}
		
		if(retornar) {
			this.retornar = false;
			this.recomeçar = false;
			gameState = "MENU";
			Faseum.restartGame();
			
		}
		
		if(gameState == "MENU") {
			this.recomeçar = false;
			this.retornar = false;
			menu.rodar();
		}
		
	}
	
	public void renderizar () {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(tela, 0, 0, null);
		
		faseum.renderizar(g);
		for(int i = 0; i < vivos.size(); i++) {
			Vivo e = vivos.get(i);
			e.renderizar(g);
		}
		for(int i = 0; i < balas.size(); i++) {
			balas.get(i).renderizar(g);
		}
		for(int i = 0; i < balasl.size(); i++) {
			balasl.get(i).renderizar(g);
		}
		ui.renderizar(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(255, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setColor(Color.WHITE);
			g.setFont(new Font("comic sans pro", Font.BOLD, 100));
			g.drawString("GAME OVER", 150, 300);
			g.setColor(Color.WHITE);
			g.setFont(new Font("comic sans pro", Font.BOLD, 50));
			if(showMessageOver)
				g.drawString("Clique em R para Tentar de Novo", 120, 400);
			g.setColor(Color.WHITE);
			g.setFont(new Font("comic sans pro", Font.BOLD, 50));
			if(showMessageOver)
				g.drawString("Ou clique em T para Voltar ao Menu", 100, 600);
		}
		
		if(gameState == "YOU_WIN") {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 255, 0, 100));
		g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		g.setColor(Color.WHITE);
		g.setFont(new Font("comic sans pro", Font.BOLD, 100));
		g.drawString("FASE CONCLUÍDA", 150, 300);
		g.setColor(Color.WHITE);
		g.setFont(new Font("comic sans pro", Font.BOLD, 50));
		if(showMessageOver)
			g.drawString("Clique em T para Voltar ao Menu", 120, 400);
		g.setColor(Color.WHITE);
		g.setFont(new Font("comic sans pro", Font.BOLD, 50));
		if(showMessageOver)
			g.drawString("Ou clique em R para Jogar de Novo", 100, 600);
		}
	
		
		if(gameState == "MENU") {
			g.drawImage(tela, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
			menu.renderizar(g);
		}
		
		bs.show();
		
	}
	
	public void run() {
		long ultimo = System.nanoTime();
		double fps = 60.0;
		double ns = 1000000000 / fps;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(rodando) {
			long agora = System.nanoTime();
			delta+= (agora - ultimo) / ns;
			ultimo = agora;
			if (delta >= 1) {
				rodar();
				renderizar();
				frames++;
				delta--;
				
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+= 1000;
			
		}
		
		
		}
		
		
	}

	
	public void keyTyped(KeyEvent e) {
		
	}

	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			jogador.up = true;
		}else if(e.getKeyCode() == KeyEvent.VK_S) {
			jogador.down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_V) {
			jogador.atirando = true;
			jogador.transformando = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_C) {
			jogador.trocando = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_R) {
			if(gameState == "GAME_OVER" || gameState == "YOU_WIN")
				this.recomeçar = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_T) {
			if(gameState == "GAME_OVER" || gameState == "YOU_WIN")
			this.retornar = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(gameState == "MENU")
				menu.up = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(gameState == "MENU")
				menu.down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(gameState == "MENU")
				menu.enter = true;
		}
		
	}


	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			jogador.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_S) {
			jogador.down = false;
		}
		
		

		}
		
	}

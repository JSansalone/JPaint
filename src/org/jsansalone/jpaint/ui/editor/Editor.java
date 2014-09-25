package org.jsansalone.jpaint.ui.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.jsansalone.jpaint.ui.editor.EditorEnums.TipoCursor;

public class Editor extends JPanel{

	private Desenhista desenhista = new Desenhista(this);
	private EditorEstado estado = new EditorEstado();
	private EditorController mouseController = new EditorController(this);
	
	public Editor() {
		setBackground(Color.WHITE);
		setFocusable(true);
		estado.setCursor(this, TipoCursor.TIPO_CURSOR_DESENHO);
		addMouseListener(mouseController);
		addMouseMotionListener(mouseController);
	}
	
	public void setCursor(TipoCursor cursor){
		estado.setCursor(this, cursor);
	}
	
	public EditorEstado getEstado(){
		return this.estado;
	}
	
	public EditorController getController(){
		return mouseController;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		desenhista.desenha((Graphics2D) g);
	}
	
	public void novo(){
		estado.getFormas().clear();
		estado.setImagem(null);
		repaint();
	}
	
	public BufferedImage getImagem(){
		estado.setSalvar(true);
		BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    	Graphics2D g2d = img.createGraphics();
    	paintAll(g2d);
    	estado.setSalvar(false);
    	return img;
	}
	
	public void carregarImagem(BufferedImage img){
		estado.setImagem(img);
	}

}

package org.jsansalone.jpaint.ui.editor.model;

import java.awt.BasicStroke;
import java.awt.Color;

public abstract class Forma {

	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	private Color cor;
	
	private BasicStroke stroke;
	
	public boolean isDentro(int x, int y){
		return
			x >= x1 &&
		    y >= y1 &&
		    x <= x2 &&
		    y <= y2;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}

	public BasicStroke getStroke() {
		return stroke;
	}

	public void setStroke(BasicStroke stroke) {
		this.stroke = stroke;
	}
	
}

package org.jsansalone.jpaint.ui.editor.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Lapis extends Retangulo{

	private int rx1;
	private int ry1;
	private int rx2;
	private int ry2;
	
	private List<Point> pontos = new ArrayList<Point>();

	public List<Point> getPontos() {
		return pontos;
	}
	
	public void addPonto(int x, int y){
		pontos.add(new Point(x, y));
	}
	
	@Override
	public boolean isDentro(int x, int y) {
		calculaPerimetro();
		return
				x >= rx1 &&
			    y >= ry1 &&
			    x <= rx2 &&
			    y <= ry2;
	}
	
	private void calculaPerimetro(){
		rx1 = pontos.get(0).x;
		ry1 = pontos.get(0).y;
		rx2 = pontos.get(0).x;
		ry2 = pontos.get(0).y;
		for (Point p : pontos) {
			if(rx1 > p.x)
				rx1 = p.x;
			if(ry1 > p.y)
				ry1 = p.y;
			if(rx2 < p.x)
				rx2 = p.x;
			if(ry2 < p.y)
				ry2 = p.y;
		}
	}

}

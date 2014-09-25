package org.jsansalone.jpaint.ui.editor.model;

public class Linha extends Retangulo{

	@Override
	public boolean isDentro(int x, int y){
		int x1 = getX1();
		int y1 = getY1();
		int x2 = getX2();
		int y2 = getY2();
		int aux;
		if(x1 > x2){
			aux = x1;
			x1 = x2;
			x2 = aux;
		}
		if(y1 > y2){
			aux = y1;
			y1 = y2;
			y2 = aux;
		}
		return
			x >= x1 &&
		    y >= y1 &&
		    x <= x2 &&
		    y <= y2;
	}
	
}

/*


10,10


100,100

20,50


 */



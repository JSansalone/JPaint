package org.jsansalone.jpaint.ui.editor;

import org.jsansalone.jpaint.ui.editor.EditorEnums.TipoCoordenada;

public interface CoordenadaListener {

	public TipoCoordenada getTipoCoordenada();
	
	public void trataCoordenada(int x, int y);
	
}

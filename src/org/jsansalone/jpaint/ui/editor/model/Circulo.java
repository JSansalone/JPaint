package org.jsansalone.jpaint.ui.editor.model;

import java.awt.Color;

import org.jsansalone.jpaint.ui.editor.EditorEnums.TipoPreenchimento;

public class Circulo extends Forma{

	private int raio;
	
	private Color corPreenchimento;

	private TipoPreenchimento preenchimento;
	
	@Override
	public boolean isDentro(int x, int y) {
		return super.isDentro(x, y);
	}

	public Color getCorPreenchimento() {
		return corPreenchimento;
	}

	public void setCorPreenchimento(Color corPreenchimento) {
		this.corPreenchimento = corPreenchimento;
	}

	public int getRaio() {
		return raio;
	}

	public void setRaio(int raio) {
		this.raio = raio;
	}
	
	public TipoPreenchimento getPreenchimento() {
		return preenchimento;
	}

	public void setPreenchimento(TipoPreenchimento preenchimento) {
		this.preenchimento = preenchimento;
	}
	
}

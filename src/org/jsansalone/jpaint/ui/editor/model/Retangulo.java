package org.jsansalone.jpaint.ui.editor.model;

import java.awt.Color;

import org.jsansalone.jpaint.ui.editor.EditorEnums.TipoPreenchimento;

public class Retangulo extends Forma{

	private Color corPreenchimento;
	private TipoPreenchimento preenchimento;

	public Color getCorPreenchimento() {
		return corPreenchimento;
	}

	public void setCorPreenchimento(Color corPreenchimento) {
		this.corPreenchimento = corPreenchimento;
	}

	public TipoPreenchimento getPreenchimento() {
		return preenchimento;
	}

	public void setPreenchimento(TipoPreenchimento preenchimento) {
		this.preenchimento = preenchimento;
	}
	
}

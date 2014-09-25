package org.jsansalone.jpaint.ui.editor;

public final class EditorEnums {

	public enum TipoCursor{
		TIPO_CURSOR_DESENHO,
		TIPO_CURSOR_LAPIS,
		TIPO_CURSOR_TEXTO,
		TIPO_CURSOR_BORRACHA
	}
	
	public enum TipoForma{
		FORMA_RETANGULO,
		FORMA_CIRCULO,
		FORMA_LINHA,
		FORMA_LAPIS,
		FORMA_TEXTO,
		FORMA_BORRACHA
	}
	
	public enum TipoEspessura{
		LINHA_FINA,
		LINHA_MEDIA,
		LINHA_GROSSA
	}
	
	public enum TipoPreenchimento{
		FORMA_VAZIA,
		FORMA_PREENCHIDA
	}
	
	public enum TipoCoordenada{
		INICIAL,
		FINAL,
		MOUSE
	}
	
	public enum TipoTraco{
		CONTINUO,
		TRACEJADO
	}
	
}

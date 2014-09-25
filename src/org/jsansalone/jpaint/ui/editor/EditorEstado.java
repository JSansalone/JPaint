package org.jsansalone.jpaint.ui.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.jsansalone.jpaint.ui.editor.EditorEnums.TipoCursor;
import org.jsansalone.jpaint.ui.editor.EditorEnums.TipoEspessura;
import org.jsansalone.jpaint.ui.editor.EditorEnums.TipoForma;
import org.jsansalone.jpaint.ui.editor.EditorEnums.TipoPreenchimento;
import org.jsansalone.jpaint.ui.editor.EditorEnums.TipoTraco;
import org.jsansalone.jpaint.ui.editor.model.Forma;
import org.jsansalone.jpaint.ui.editor.model.Lapis;

public class EditorEstado {

	private static final Cursor CURSOR_DESENHO = new Cursor(Cursor.CROSSHAIR_CURSOR);
	private static final Cursor CURSOR_LAPIS = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().getImage(Editor.class.getResource("/org/jsansalone/jpaint/ui/images/cur_pencil_64x64.png")), new Point(0, 30), "pencil");
	private static final Cursor CURSOR_TEXTO = new Cursor(Cursor.TEXT_CURSOR);
	private static final Cursor CURSOR_BORRACHA = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().getImage(Editor.class.getResource("/org/jsansalone/jpaint/ui/images/cur_erase_64x72.png")), new Point(5, 30), "eraser");
	
	private boolean regua = false;
	private boolean guias = false;
	private boolean grade = false;
	
	private TipoForma forma = TipoForma.FORMA_RETANGULO;
	private TipoEspessura espessura = TipoEspessura.LINHA_FINA;
	private TipoPreenchimento preenchimento = TipoPreenchimento.FORMA_VAZIA;
	private TipoTraco traco = TipoTraco.CONTINUO;
	
	private boolean formaConcluida = false;
	
	private List<Forma> formas = new ArrayList<Forma>();
	
	private Color corDaLinha = Color.BLACK;
	private Color corDoPreenchimento = Color.WHITE;
	
	private BasicStroke stroke;
	
	private boolean seleciona;
	
	private Lapis formaLapisAtual = new Lapis();
	
	private String textoAtual;
	
	private boolean salvar;
	
	private BufferedImage imagem;
	
	public EditorEstado() {
		atualizaStroke();
	}
	
	void setCursor(Editor editor, TipoCursor cursor){
		seleciona = false;
		switch (cursor) {
		case TIPO_CURSOR_DESENHO:
			editor.setCursor(CURSOR_DESENHO);
			break;
		case TIPO_CURSOR_LAPIS:
			editor.setCursor(CURSOR_LAPIS);
			break;
		case TIPO_CURSOR_TEXTO:
			editor.setCursor(CURSOR_TEXTO);
			break;
		case TIPO_CURSOR_BORRACHA:
			editor.setCursor(CURSOR_BORRACHA);
			seleciona = true;
			break;
		}
	}
	
	public boolean isRegua() {
		return regua;
	}

	public void setRegua(boolean regua) {
		this.regua = regua;
	}

	public boolean isGuias() {
		return guias;
	}

	public void setGuias(boolean guias) {
		this.guias = guias;
	}

	public boolean isGrade() {
		return grade;
	}

	public void setGrade(boolean grade) {
		this.grade = grade;
	}

	public TipoForma getForma() {
		return forma;
	}

	public void setForma(TipoForma forma) {
		this.forma = forma;
	}

	public TipoEspessura getEspessura() {
		return espessura;
	}

	public void setEspessura(TipoEspessura espessura) {
		this.espessura = espessura;
		atualizaStroke();
	}

	public TipoPreenchimento getPreenchimento() {
		return preenchimento;
	}

	public void setPreenchimento(TipoPreenchimento preenchimento) {
		this.preenchimento = preenchimento;
	}

	public boolean isFormaConcluida() {
		return formaConcluida;
	}

	public void setFormaConcluida(boolean formaConcluida) {
		this.formaConcluida = formaConcluida;
	}
	
	public List<Forma> getFormas(){
		return this.formas;
	}

	public Color getCorDaLinha() {
		return corDaLinha;
	}

	public void setCorDaLinha(Color corDaLinha) {
		this.corDaLinha = corDaLinha;
	}

	public Color getCorDoPreenchimento() {
		return corDoPreenchimento;
	}

	public void setCorDoPreenchimento(Color corDoPreenchimento) {
		this.corDoPreenchimento = corDoPreenchimento;
	}

	public TipoTraco getTraco() {
		return traco;
	}

	public void setTraco(TipoTraco traco) {
		this.traco = traco;
		atualizaStroke();
	}

	public BasicStroke getStroke() {
		return stroke;
	}

	private void atualizaStroke(){
		BasicStroke str = null;
		int esp = 1;
		if(espessura == TipoEspessura.LINHA_FINA){
			esp = 1;
		}else if(espessura == TipoEspessura.LINHA_MEDIA){
			esp = 3;
		}else if(espessura == TipoEspessura.LINHA_GROSSA){
			esp = 5;
		}
		if(traco == TipoTraco.TRACEJADO){
			str = new BasicStroke(esp, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[]{5.0f}, 0.0f);
		}else{
			str = new BasicStroke(esp, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
		}
		stroke = str;
	}

	public boolean isSeleciona() {
		return seleciona;
	}

	public void setSeleciona(boolean seleciona) {
		this.seleciona = seleciona;
	}

	public Lapis getFormaLapisAtual() {
		return formaLapisAtual;
	}

	public void setFormaLapisAtual(Lapis formaLapisAtual) {
		this.formaLapisAtual = formaLapisAtual;
	}

	public String getTextoAtual() {
		return textoAtual;
	}

	public void setTextoAtual(String textoAtual) {
		this.textoAtual = textoAtual;
	}

	public boolean isSalvar() {
		return salvar;
	}

	public void setSalvar(boolean salvar) {
		this.salvar = salvar;
	}

	public BufferedImage getImagem() {
		return imagem;
	}

	public void setImagem(BufferedImage imagem) {
		this.imagem = imagem;
	}
	
}

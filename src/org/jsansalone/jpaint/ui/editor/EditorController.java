package org.jsansalone.jpaint.ui.editor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.jsansalone.jpaint.ui.editor.EditorEnums.TipoForma;
import org.jsansalone.jpaint.ui.editor.model.Circulo;
import org.jsansalone.jpaint.ui.editor.model.Forma;
import org.jsansalone.jpaint.ui.editor.model.Lapis;
import org.jsansalone.jpaint.ui.editor.model.Linha;
import org.jsansalone.jpaint.ui.editor.model.Retangulo;
import org.jsansalone.jpaint.ui.editor.model.Texto;

public class EditorController implements MouseListener, MouseMotionListener{

	private int mouseX;
	private int mouseY;
	
	private int initialX;
	private int initialY;
	private int finalX;
	private int finalY;
	
	private List<CoordenadaListener> coordenadaListeners = new ArrayList<CoordenadaListener>();
	private Editor editor;
	
	public EditorController(Editor editor) {
		this.editor = editor;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		getCoordMouse(e);
		getCoordFinal(e);
		if(editor.getEstado().getForma() == TipoForma.FORMA_LAPIS){
			editor.getEstado().getFormaLapisAtual().addPonto(mouseX, mouseY);
		}
		notifyEditor();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		getCoordMouse(e);
		notifyEditor();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		getCoordInicial(e);
		editor.getEstado().setFormaConcluida(false);
		if(editor.getEstado().getForma() == TipoForma.FORMA_BORRACHA){
			Forma forma = getMouseDentro();
			if(forma != null){
				editor.getEstado().getFormas().remove(forma);
			}
		}else if(editor.getEstado().getForma() == TipoForma.FORMA_LAPIS){
			editor.getEstado().setFormaLapisAtual(new Lapis());
			editor.getEstado().getFormaLapisAtual().addPonto(mouseX, mouseY);
			editor.getEstado().getFormaLapisAtual().addPonto(mouseX, mouseY);
		}else if(editor.getEstado().getForma() == TipoForma.FORMA_TEXTO){
			String texto = JOptionPane.showInputDialog("Digite o texto");
			if(texto != null && texto.length() > 0){
				editor.getEstado().setTextoAtual(texto);
				adicionaForma();
			}
		}
		notifyEditor();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(editor.getEstado().getForma() != TipoForma.FORMA_BORRACHA){
			getCoordFinal(e);
			adicionaForma();
			editor.getEstado().setFormaConcluida(true);
		}
		notifyEditor();
	}
	
	private void getCoordInicial(MouseEvent e){
		initialX = e.getX();
		initialY = e.getY();
		finalX = initialX;
		finalY = initialY;
	}
	
	private void getCoordFinal(MouseEvent e){
		finalX = e.getX();
		finalY = e.getY();
	}
	
	private void getCoordMouse(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();
	}

	private void notifyEditor() {
		notifyListeners();
		editor.repaint();
	}
	
	public void addCoordenadaListener(CoordenadaListener listener){
		if(listener != null){
			this.coordenadaListeners.add(listener);
		}
	}
	
	public void removeCoordenadaListener(CoordenadaListener listener){
		this.coordenadaListeners.remove(listener);	
	}
	
	private void notifyListeners(){
		for (CoordenadaListener listener : coordenadaListeners) {
			switch (listener.getTipoCoordenada()) {
			case INICIAL:
				listener.trataCoordenada(initialX, initialY);
				break;
			case FINAL:
				listener.trataCoordenada(finalX, finalY);
				break;
			case MOUSE:
				listener.trataCoordenada(mouseX, mouseY);
				break;
			default:
				break;
			}
		}
	}
	
	private void adicionaForma(){
		int aux;
		if(editor.getEstado().getForma() != TipoForma.FORMA_LINHA){
			if(initialX > finalX){
				aux = initialX;
				initialX = finalX;
				finalX = aux;
			}
			if(initialY > finalY){
				aux = initialY;
				initialY = finalY;
				finalY = aux;
			}
		}
		Forma f = null;
		if(editor.getEstado().getForma() == TipoForma.FORMA_RETANGULO){
			f = new Retangulo();
			f.setX1(initialX);
			f.setY1(initialY);
			f.setX2(finalX);
			f.setY2(finalY);
			((Retangulo)f).setCorPreenchimento(editor.getEstado().getCorDoPreenchimento());
			((Retangulo)f).setPreenchimento(editor.getEstado().getPreenchimento());
			((Retangulo)f).setStroke(editor.getEstado().getStroke());
		}else if(editor.getEstado().getForma() == TipoForma.FORMA_LINHA){
			f = new Linha();
			f.setX1(initialX);
			f.setY1(initialY);
			f.setX2(finalX);
			f.setY2(finalY);
			((Linha)f).setStroke(editor.getEstado().getStroke());
		}else if(editor.getEstado().getForma() == TipoForma.FORMA_CIRCULO){
			f = new Circulo();
			f.setX1(initialX);
			f.setY1(initialY);
			f.setX2(finalX);
			f.setY2(finalY);
			((Circulo)f).setCorPreenchimento(editor.getEstado().getCorDoPreenchimento());
			((Circulo)f).setPreenchimento(editor.getEstado().getPreenchimento());
			((Circulo)f).setStroke(editor.getEstado().getStroke());
		}else if(editor.getEstado().getForma() == TipoForma.FORMA_LAPIS){
			f = editor.getEstado().getFormaLapisAtual();
			((Lapis)f).setCorPreenchimento(editor.getEstado().getCorDoPreenchimento());
			((Lapis)f).setPreenchimento(editor.getEstado().getPreenchimento());
			((Lapis)f).setStroke(editor.getEstado().getStroke());
		}else if(editor.getEstado().getForma() == TipoForma.FORMA_TEXTO){
			f = new Texto();
			f.setX1(initialX);
			f.setY1(initialY);
			f.setX2(initialX + calculaTamanhoTexto(editor.getEstado().getTextoAtual()));
			f.setY2(initialY + 25);
			((Texto)f).setCorPreenchimento(editor.getEstado().getCorDoPreenchimento());
			((Texto)f).setPreenchimento(editor.getEstado().getPreenchimento());
			((Texto)f).setStroke(editor.getEstado().getStroke());
			((Texto)f).setTexto(editor.getEstado().getTextoAtual());
		}
		f.setCor(editor.getEstado().getCorDaLinha());
		editor.getEstado().getFormas().add(f);
		initialX = 0;
		initialY = 0;
		finalX = 0;
		finalY = 0;
	}
	
	public Forma getMouseDentro(){
		for (Forma frm : editor.getEstado().getFormas()) {
			if(frm.isDentro(mouseX, mouseY)){
				return frm;
			}
		}
		return null;
	}
	
	private int calculaTamanhoTexto(String texto){
		int len = texto.length();
		return (len * 7);
	}
	
	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getInitialX() {
		return initialX;
	}

	public int getInitialY() {
		return initialY;
	}

	public int getFinalX() {
		return finalX;
	}

	public int getFinalY() {
		return finalY;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}

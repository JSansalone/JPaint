package org.jsansalone.jpaint.ui.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import org.jsansalone.jpaint.ui.editor.EditorEnums.TipoForma;
import org.jsansalone.jpaint.ui.editor.EditorEnums.TipoPreenchimento;
import org.jsansalone.jpaint.ui.editor.model.Circulo;
import org.jsansalone.jpaint.ui.editor.model.Forma;
import org.jsansalone.jpaint.ui.editor.model.Lapis;
import org.jsansalone.jpaint.ui.editor.model.Linha;
import org.jsansalone.jpaint.ui.editor.model.Retangulo;
import org.jsansalone.jpaint.ui.editor.model.Texto;

public class Desenhista {

	// Constantes padrao
	private static Font FONTE_PADRAO;
	private static Stroke STROKE_PADRAO;
	
	// Constantes da regua
	private static final Stroke STROKE_BORDA_REGUA = new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
	private static final Color COR_BORDA_REGUA = new Color(180,180,180);
	private static final Color COR_REGUA = Color.BLACK;
	private static final Color COR_REGUA_MARCA_100 = Color.RED;
	private static final Color COR_FONTE_REGUA = Color.BLACK;
	private static final Font FONTE_REGUA = new Font("Arial", Font.PLAIN, 10);
	
	// Constantes da grade
	private static final Color COR_GRADE = new Color(240,240,240);
	private static final int DISTANCIA_GRADE = 20;
	
	private Editor editor;

	public Desenhista(Editor editor) {
		this.editor = editor;
	}
	
	public void desenha(Graphics2D g){
		getValoresPadrao(g);
		desenhaGrade(g);
		desenhaRegua(g);
		desenhaGuias(g);
		if(editor.getEstado().getImagem() != null){
			g.drawImage(editor.getEstado().getImagem(), 0, 0, null);
		}
		desenhaFormas(g);
	}

	private void getValoresPadrao(Graphics2D g) {
		if(FONTE_PADRAO == null){
			FONTE_PADRAO = g.getFont();
		}
		if(STROKE_PADRAO == null){
			STROKE_PADRAO = g.getStroke();
		}
	}
	
	private void desenhaGuias(Graphics2D g){
		if(editor.getEstado().isGuias() && !editor.getEstado().isSalvar()){
			int x1i = 0;
			int y1i = editor.getController().getMouseY();
			int x1f = editor.getWidth();
			int y1f = editor.getController().getMouseY();
			int x2i = editor.getController().getMouseX();
			int y2i = 0;
			int x2f = editor.getController().getMouseX();
			int y2f = editor.getHeight();
			g.setColor(COR_REGUA);
			g.drawLine(x1i, y1i, x1f, y1f);
			g.drawLine(x2i, y2i, x2f, y2f);
		}
	}
	
	private void desenhaGrade(Graphics2D g){
		if(editor.getEstado().isGrade() && !editor.getEstado().isSalvar()){
			int maxX = editor.getWidth();
	        int maxY = editor.getHeight();
	        g.setColor(COR_GRADE);
	        for (int x = 0; x < maxX; x += DISTANCIA_GRADE) {
	            g.drawLine(x, 0, x, maxY);
	        }
	        for (int y = 0; y < maxY; y += DISTANCIA_GRADE) {
	            g.drawLine(0, y, maxX, y);
	        }
		}
	}
	
	private void desenhaRegua(Graphics2D g){
		if(editor.getEstado().isRegua() && !editor.getEstado().isSalvar()){
			int w = editor.getWidth();
			int h = editor.getHeight();
			
			// borda superior
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, w, 20);
			g.setColor(COR_BORDA_REGUA);
			g.setStroke(STROKE_BORDA_REGUA);
			g.drawLine(0, 0, w, 0);
			g.drawLine(0, 20, w, 20);
			// borda inferior
			g.setColor(Color.WHITE);
			g.fillRect(0, h-23, w, 25);
			g.setColor(COR_BORDA_REGUA);
			g.setStroke(STROKE_BORDA_REGUA);
			g.drawLine(0, h-3, w, h-3);
			g.drawLine(0, h-23, w, h-23);
			g.setStroke(STROKE_PADRAO);
			g.setFont(FONTE_REGUA);
			// marcacoes superiores
			for(int x = 5; x < w; x += 5){
				g.setColor(COR_REGUA);
				if(x % 20 != 0){
					g.drawLine(x, 11, x, 16);
				}else if(x % 100 == 0){
					g.setColor(COR_REGUA_MARCA_100);
					g.drawLine(x, 3, x, 16);
				}else{
					g.drawLine(x, 3, x, 16);
				}
				if(x % 100 == 0){
					g.setColor(COR_FONTE_REGUA);
					g.drawString(""+x, x+2, 10);
				}
			}
			// marcacoes inferiores
			for(int x = 5; x < w; x += 5){
				g.setColor(COR_REGUA);
				if(x % 20 != 0){
					g.drawLine(x, h-7, x, h-12);
				}else if(x % 100 == 0){
					g.setColor(COR_REGUA_MARCA_100);
					g.drawLine(x, h-7, x, h-20);
				}else{
					g.drawLine(x, h-7, x, h-20);
				}
				if(x % 100 == 0){
					g.setColor(COR_FONTE_REGUA);
					g.drawString(""+x, x+2, h-13);
				}
			}
			g.setFont(FONTE_PADRAO);
		}
	}
	
	private void desenhaFormas(Graphics2D g){
		for(Forma f : editor.getEstado().getFormas()){
			int x1 = f.getX1();
			int y1 = f.getY1();
			int x2 = f.getX2();
			int y2 = f.getY2();
			int w = distancia(x2,x1);
			int h = distancia(y2,y1);
			g.setStroke(f.getStroke());
			if(f instanceof Lapis){
				if(((Lapis)f).getPontos().size() > 0){
					g.setColor(f.getCor());
					Point a = ((Lapis)f).getPontos().get(0);
					for(int i=1; i<((Lapis)f).getPontos().size(); i++){
						Point p = ((Lapis)f).getPontos().get(i);
						g.drawLine(a.x, a.y, p.x, p.y);
						a = p;
					}
				}
			}else if(f instanceof Linha){
				g.setColor(f.getCor());
				g.drawLine(x1, y1, x2, y2);
			}else if(f instanceof Texto){
				if(((Texto) f).getPreenchimento() == TipoPreenchimento.FORMA_PREENCHIDA){
					g.setColor(((Texto) f).getCorPreenchimento());
					g.fillRect(x1, y1, w, h);
				}
				g.setColor(f.getCor());
				g.drawString(((Texto) f).getTexto(), x1, y1+12);
			}else if(f instanceof Retangulo){
				if(((Retangulo) f).getPreenchimento() == TipoPreenchimento.FORMA_PREENCHIDA){
					g.setColor(((Retangulo) f).getCorPreenchimento());
					g.fillRect(x1, y1, w, h);
				}
				g.setColor(f.getCor());
				g.drawRect(x1, y1, w, h);
			}else if(f instanceof Circulo){
				if(((Circulo) f).getPreenchimento() == TipoPreenchimento.FORMA_PREENCHIDA){
					g.setColor(((Circulo) f).getCorPreenchimento());
					g.fillArc(x1, y1, w, h, 0, 360);
				}
				g.setColor(f.getCor());
				g.drawArc(x1, y1, w, h, 0, 360);
			}
		}
		desenhaFormaAtual(g);
		desenhaFormaSelecionada(g);
	}
	
	private void desenhaFormaAtual(Graphics2D g){
		int x1 = editor.getController().getInitialX();
		int y1 = editor.getController().getInitialY();
		int x2 = editor.getController().getFinalX();
		int y2 = editor.getController().getFinalY();
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
		int w = distancia(x2,x1);
		int h = distancia(y2,y1);
		g.setStroke(editor.getEstado().getStroke());
		if(editor.getEstado().getForma() == TipoForma.FORMA_RETANGULO){
			if(editor.getEstado().getPreenchimento() == TipoPreenchimento.FORMA_PREENCHIDA){
				g.setColor(editor.getEstado().getCorDoPreenchimento());
				g.fillRect(x1, y1, w, h);
			}
			g.setColor(editor.getEstado().getCorDaLinha());
			g.drawRect(x1, y1, w, h);
		}else if(editor.getEstado().getForma() == TipoForma.FORMA_CIRCULO){
			if(editor.getEstado().getPreenchimento() == TipoPreenchimento.FORMA_PREENCHIDA){
				g.setColor(editor.getEstado().getCorDoPreenchimento());
				g.fillArc(x1, y1, w, h, 0, 360);
			}
			g.setColor(editor.getEstado().getCorDaLinha());
			g.drawArc(x1, y1, w, h, 0, 360);
		}else if(editor.getEstado().getForma() == TipoForma.FORMA_LAPIS){
			if(editor.getEstado().getFormaLapisAtual().getPontos().size() > 0){
				Point a = editor.getEstado().getFormaLapisAtual().getPontos().get(0);
				for(int i=1; i<editor.getEstado().getFormaLapisAtual().getPontos().size(); i++){
					Point p = editor.getEstado().getFormaLapisAtual().getPontos().get(i);
					g.drawLine(a.x, a.y, p.x, p.y);
					a = p;
				}
			}
		}else if(editor.getEstado().getForma() == TipoForma.FORMA_LINHA){
			g.setColor(editor.getEstado().getCorDaLinha());
			g.drawLine(editor.getController().getInitialX(), editor.getController().getInitialY(), editor.getController().getFinalX(), editor.getController().getFinalY());
		}
	}
	
	private void desenhaFormaSelecionada(Graphics2D g){
		if(editor.getEstado().isSeleciona()){
			Forma f = editor.getController().getMouseDentro();
			if(f != null){
				g.setStroke(f.getStroke());
				int x1 = f.getX1();
				int y1 = f.getY1();
				int x2 = f.getX2();
				int y2 = f.getY2();
				int w = distancia(x2,x1);
				int h = distancia(y2,y1);
				g.setColor(Color.ORANGE);
				if(f instanceof Lapis){
					Point a = ((Lapis)f).getPontos().get(0);
					for(int i=1; i<((Lapis)f).getPontos().size(); i++){
						Point p = ((Lapis)f).getPontos().get(i);
						g.drawLine(a.x, a.y, p.x, p.y);
						a = p;
					}
				}else if(f instanceof Linha){
					g.drawLine(x1, y1, x2, y2);
				}else if(f instanceof Retangulo){
					g.drawRect(x1, y1, w, h);
				}else if(f instanceof Circulo){
					g.drawOval(x1, y1, w, h);
				}else if(f instanceof Texto){
					if(((Texto) f).getPreenchimento() == TipoPreenchimento.FORMA_PREENCHIDA){
						g.setColor(((Texto) f).getCorPreenchimento());
						g.fillRect(x1, y1, w, h);
					}
					g.setColor(f.getCor());
					g.drawString(((Texto) f).getTexto(), x1, y1);
					g.drawRect(x1, y1, w, h);
				}
			}
		}
	}
	
	private int distancia(int c1, int c2){
		int r = c1 - c2;
		return r < 0 ? -r : r;
	}
	
}

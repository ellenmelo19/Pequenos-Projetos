package br.com.coder.cm.visao;

import java.awt.Color;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.w3c.dom.events.MouseEvent;

import br.com.coder.cm.modelo.Campo;
import br.com.coder.cm.modelo.CampoEvento;
import br.com.coder.cm.modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservador, MouseListener{
	
	private final Color BG_PADRAO = new Color(184, 184, 184);//cinza
	private final Color BG_MARCAR = new Color(8, 179, 247);
	private final Color BG_EXPLODIR = new Color(189, 66, 68); // vermelho
	private final Color TEXTO_VERDE = new Color(0, 100, 0); // verde

	
	private Campo campo;
	
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO); //seta a dos campos para cinza
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0)); //ajeitar as bordas
		
		addMouseListener(this);
		campo.registrarObservador(this);
	
	}
	
	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		switch(evento) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
		case MARCAR:
			aplicarEstiloMarcar();
			break;
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
		default:
			aplicarEstiloPadrao();
		}
		
		//forçar que o componente seja redesenhado na tela
		SwingUtilities.invokeLater(() -> {
			repaint();
			validate();
		});
	}

	private void aplicarEstiloPadrao() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0)); 
		setText("");
	}

	private void aplicarEstiloExplodir() {
		setBackground(BG_EXPLODIR);
		setForeground(Color.WHITE);
		setText("X");
		
	}

	private void aplicarEstiloMarcar() {
		setBackground(BG_MARCAR);
		setForeground(Color.BLACK);
		setText("M");
		
	}

	private void aplicarEstiloAbrir() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if(campo.isMinado()){
			setBackground(BG_EXPLODIR);
			return;
		}
		
		setBackground(BG_PADRAO);
	
		switch (campo.minasNaVizinhança()) {
		case 1: 
			//cor da letra
			setForeground(TEXTO_VERDE);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		
		//teste
		String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhança() + "":"";
		setText(valor);
	}
	
	// Interface que implementa os eventos do mouse
	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		if(e.getButton() == 1) {
			campo.abrir();
		}else {
			campo.alternarMarcacao();
		}
		
	}
	
	public void mouseClicked(java.awt.event.MouseEvent e) {}
	public void mouseEntered(java.awt.event.MouseEvent e) {}
	public void mouseExited(java.awt.event.MouseEvent e) {}
	public void mouseReleased(java.awt.event.MouseEvent e) {}
}

package br.com.coder.cm.visao;

import javax.swing.JFrame;
import br.com.coder.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame{

	public TelaPrincipal() {
		
		Tabuleiro tabuleiro = new Tabuleiro(16,30, 50);
	
		add(new PainelTabuleiro(tabuleiro));
		
		setTitle("Campo Minado da Ellen");
		setSize(690, 438);
		setLocationRelativeTo(null); // centraliza a tela
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
		public static void main(String[] args) {
			new TelaPrincipal();
		}
}

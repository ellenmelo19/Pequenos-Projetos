package br.com.coder.cm;

import br.com.coder.cm.modelo.Tabuleiro;
import br.com.coder.cm.visao.TabuleiroConsole;

public class Jogo {

		public static void main(String[] args) {
			Tabuleiro tabuleiro = new Tabuleiro(6, 6, 3);
			new TabuleiroConsole(tabuleiro);
		}
}

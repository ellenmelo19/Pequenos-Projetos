package br.com.coder.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.coder.cm.excessao.ExplosaoException;
import br.com.coder.cm.excessao.SairException;
import br.com.coder.cm.modelo.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}

	private void executarJogo() {
		try {
			
			boolean continuar = true;
			
			while(continuar) {
				cicloDoJogo();
				
				System.out.println("Outra partida? (S/n) ");
				String resposta = entrada.nextLine();
				
				//teste
				if("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				}else {
					tabuleiro.reiniciar();
				}
			}
			
		}catch(SairException e) {
			System.out.println("Tchau!!!");
		} finally {
			entrada.close();
		}
		
	}

	private void cicloDoJogo() {
		try {
			while(!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);
				
				String digitado = capturarValorDigitado("Digite (x, y): ");
				
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
					.map(e -> Integer.parseInt(e.trim())).iterator(); //O trim elimina os espaços caso o usuário escreva ex: 1,   2
				
				digitado =  capturarValorDigitado("1 -> Abrir ou 2 -> (Des)Marcar: " );
				
				//teste
				if("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
					
				}else if ("2".equals(digitado)){
					tabuleiro.alternarMarcacao(xy.next(), xy.next());
				}
			}
			
			System.out.println("Vitória!!!");
		}catch(ExplosaoException e){
			System.out.println(tabuleiro);
			System.out.println("Derrota");
		}
		
	}
	
	private String capturarValorDigitado(String texto) {
		System.out.println(texto);
		String digitado = entrada.nextLine();
		
		if("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		
		return digitado;
	}
}

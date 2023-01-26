package br.com.coder.cm.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import br.com.coder.cm.excessao.ExplosaoException;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>();
	
	//Construtor
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
		try {
			campos.parallelStream()
					.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
					.findFirst()
					.ifPresent(c -> c.abrir());
		}catch(ExplosaoException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}
	
	public void alternarMarcacao (int linha, int coluna) {
		campos.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.alternarMarcacao());
	}
	
	
  // métodos de inicialização do tabuleiro
	private void gerarCampos() {
		//a primeira linha do jogo tem índice 0
		for (int l = 0; l < linhas; l++) {
			for (int c = 0; c < colunas; c++) {
				campos.add(new Campo(l, c));
			}
		}
	}
	
	private void associarVizinhos() {
		for(Campo c1: campos) {
			for (Campo c2: campos) {
				c1.adicionarVizinho(c2);  //Define quem pode ser vizinho de acordo com a proximidade
			}
		}
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado(); //Interface
		
		do {
			//índice que vai conter a bomba
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
			//condição de parada do loop - até a qtd de minas limite
		}while(minasArmadas < minas);	
	}
	
	public boolean objetivoAlcancado() {
		//Significa que você ganhou o jogo
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}

	//inicializar o jogo
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
			sb.append("i |");
			sb.append(" ");
		for (int c = 0; c < colunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
			
		}
		sb.append("\n");
		//barra debaixo do indice das colunas
		sb.append("---");
		for (int c = 0; c < colunas; c++) {
			sb.append("---");	
		}
		
		sb.append("\n");
		
		int i = 0;
		for (int l = 0; l < linhas; l++) {
			sb.append(l + " |");
			sb.append(" ");
			for (int c = 0; c < colunas; c++) {
				sb.append(" ");
				sb.append(campos.get(i)); //imprime o valor do campo
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}

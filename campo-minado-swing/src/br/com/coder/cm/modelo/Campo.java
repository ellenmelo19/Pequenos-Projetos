package br.com.coder.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Campo {

	private final int linha;
	private final int coluna;
	
	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	private List<CampoObservador> observadores = new ArrayList<>();
	
	
	
	Campo(int linha, int coluna){
		this.linha = linha; 
		this.coluna = coluna;
	}
	
	public void registrarObservador(CampoObservador observador) {
		observadores.add(observador); 
	}
	
	private void notificarObservadores(CampoEvento evento) {
		observadores.stream()
		.forEach(o -> o.eventoOcorreu(this, evento));
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha!= vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		//calcula a diferença absoluta da distância do campo
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		//Cenários
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
			
		}else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else {
			return false;
		}
		
	}
	
	
	
	public void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
			
			if(marcado) {
				notificarObservadores(CampoEvento.MARCAR);
			}else {
				notificarObservadores(CampoEvento.DESMARCAR);
			}
			
		}
	}
	
	public boolean abrir() {
		if(!aberto && !marcado) {
			if(minado) {
				notificarObservadores(CampoEvento.EXPLODIR);
				return true;
			}
			
			setAberto(true);	
			
			if(vizinhancaSegura()) {
				//abre os vizinhos seguros
				vizinhos.forEach(v -> v.abrir());
			}
			
			return true;
		}else {
		return false;
		}
	}
	
	public boolean vizinhancaSegura() {
		//checa os vizinhos pra saber se é minado
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	void minar() {
			minado = true;
	}
	
	public boolean isMinado(){ //getter
		return minado;
	}
	
	public boolean isMarcado(){ //funciona como um get 
		return marcado;
	}
	
	public boolean isAberto() {
		return aberto;
	}
	
	
	 void setAberto(boolean aberto) {
		this.aberto = aberto;
		
		if(aberto) {
			notificarObservadores(CampoEvento.ABRIR);
		}
	}

	public boolean isFechado() {
		return !isAberto();
	}
	
////get e set das linhas e colunas////
	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	//quantidade de minas na vizinhança
	
	public int minasNaVizinhança() {
		return (int)vizinhos.stream().filter(v -> v.minado).count();
	}

	//reiniciar os atributos
	void reiniciar() {
		aberto =false;
		minado = false;
		marcado = false;
		notificarObservadores(CampoEvento.REINICIAR);
	}
	
}

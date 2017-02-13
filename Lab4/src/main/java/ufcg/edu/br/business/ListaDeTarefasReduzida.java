package ufcg.edu.br.business;

/**
 * Classe ListaDeTarefaReduzida, serve para encapsular algumas informacoes de
 * ListaDeTarefa necessarias para a APIRest, assim melhorando o desempenho da 
 * APIRest.
 * 
 * @author davi laerte
 *
 */
public class ListaDeTarefasReduzida {

	private Long id;
	private String nome;
	private int tamanho;

	public ListaDeTarefasReduzida(Long id, String nome, int tamanho) {
		this.id = id;
		this.nome = nome;
		this.tamanho = tamanho;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public Long getId() {
		return id;
	}

}

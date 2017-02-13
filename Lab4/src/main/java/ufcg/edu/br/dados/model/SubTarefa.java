package ufcg.edu.br.dados.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Classe SubTarefa, classe mapeada para BD.
 * 
 * @author davi laerte
 *
 */
@Entity(name="SubTarefa")
public class SubTarefa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private boolean estaConcluida;
	
	public SubTarefa(String nome, boolean estaConcluida) {
		this.nome = nome;
		this.estaConcluida = estaConcluida;
	}
	
	//para o JPA
	public SubTarefa(){}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isEstaConcluida() {
		return estaConcluida;
	}

	public void setEstaConcluida(boolean estaConcluida) {
		this.estaConcluida = estaConcluida;
	}

}

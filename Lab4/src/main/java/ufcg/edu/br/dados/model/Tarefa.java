package ufcg.edu.br.dados.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * Classe Tarefa, classe mapeada para BD;
 * 
 * @author davi laerte
 *
 */
@Entity(name = "Tarefa")
public class Tarefa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private String categoria;
	
	@Column
	private String comentarios;
	
	@Column
	private String prioridade;

	
	@Column
	private boolean estaConcluida;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<SubTarefa> subtarefas;
	
	public Tarefa(String nome, String categoria, String comentarios, String prioridade, boolean estaConcluida) {
		this.nome = nome;
		this.categoria = categoria;
		this.comentarios = comentarios;
		this.prioridade = prioridade;
		this.estaConcluida = estaConcluida;
		this.subtarefas = new ArrayList<SubTarefa>();
	}
	
	//para o JPA
	public Tarefa(){}
	
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
	
	

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public boolean isEstaConcluida() {
		return estaConcluida;
	}
	
	public void setEstaConcluida(boolean estaConcluida) {
		this.estaConcluida = estaConcluida;
	}
	
	public List<SubTarefa> getSubtarefas() {
		return subtarefas;
	}

	public void setSubtarefas(List<SubTarefa> subtarefas) {
		this.subtarefas = subtarefas;
	}
	
	//Metodos com Logica
	public void addSubTarefa(SubTarefa subTarefa) {
		this.subtarefas.add(subTarefa);
	}
	
	@Transient
	public SubTarefa obterSubTarefaId(Long id) {
		
		for (int i = 0; i < this.subtarefas.size(); i++) {
			
			if(this.subtarefas.get(i).getId().equals(id)) {
				return this.subtarefas.get(i);
			}
				
		}
		
		return null;
	}
	
	@Transient
	public SubTarefa obterUltimaSubTarefa() {
		if(this.subtarefas.isEmpty()) {
			return null;
		}
		
		return this.subtarefas.get(this.subtarefas.size() - 1 );
	}
	
	public void removeSubTarefaId(Long id) {
		
		for (int i = 0; i < this.subtarefas.size(); i++) {
			
			if(this.subtarefas.get(i).getId().equals(id)) {
				this.subtarefas.remove(i);
	
				return;
			}
				
		}
	}

}

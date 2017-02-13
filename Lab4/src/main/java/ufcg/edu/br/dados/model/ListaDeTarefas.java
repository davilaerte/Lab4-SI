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
 * Classe ListaDeTarefas, classe mapeada para BD.
 * 
 * @author davi laerte
 *
 */
@Entity(name = "ListaDeTarefa")
public class ListaDeTarefas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String nome;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Tarefa> listaDeTarefas;
	
	public ListaDeTarefas(String nome) {
		this.nome = nome;
		this.listaDeTarefas = new ArrayList<Tarefa>();
	}
	
	//para o JPA
	public ListaDeTarefas() {}
	
	
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
	public List<Tarefa> getListaDeTarefas() {
		return listaDeTarefas;
	}
	public void setListaDeTarefas(List<Tarefa> listaDeTarefas) {
		this.listaDeTarefas = listaDeTarefas;
	}
	
	//Metodos com Logica
	public void addTarefa(Tarefa tarefa) {
		this.listaDeTarefas.add(tarefa);
	}
	
	@Transient
	public Tarefa obterTarefaId(Long id) {
		
		for (int i = 0; i < this.listaDeTarefas.size(); i++) {
			
			if(this.listaDeTarefas.get(i).getId().equals(id)) {
				return this.listaDeTarefas.get(i);
			}
		}
		return null;
	}
	
	@Transient
	public Tarefa obterUltimaTarefa() {
		if(this.listaDeTarefas.isEmpty()) {
			return null;
		}
		
		return this.listaDeTarefas.get(this.listaDeTarefas.size() - 1 );
	}
	
	public void removeTarefaId(Long id) {
		
		for (int i = 0; i < this.listaDeTarefas.size(); i++) {
			
			if(this.listaDeTarefas.get(i).getId().equals(id)) {
				this.listaDeTarefas.remove(i);
	
				return;
			}
		}
	}
	
	public void removeTodasAsTarefas() {
		
		for (int i = this.listaDeTarefas.size()-1 ; i >= 0 ; i--) {
			this.listaDeTarefas.remove(i);
		}
	}
	

	
}

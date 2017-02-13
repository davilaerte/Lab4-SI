package ufcg.edu.br.business;

import java.util.List;

import ufcg.edu.br.dados.model.ListaDeTarefas;
import ufcg.edu.br.dados.model.SubTarefa;
import ufcg.edu.br.dados.model.Tarefa;

/**
 * Classe Validador, valida os dados recebidos pela APIRest.
 * 
 * @author davi laerte
 *
 */
public class Validador {
	
	private static final String PRIORIDADE_ALTA = "Alta";
	private static final String PRIORIDADE_MEDIA = "Media";
	private static final String PRIORIDADE_BAIXA = "Baixa";
	
	private static final int TAMANHO_MAXIMO_ENTRADA = 40;
	private static final int TAMANHO_MAXIMO_TEXTO = 200;
	
	public static boolean validarListaDeTarefa(ListaDeTarefas listaDeTarefas) {
		return validarObjeto(listaDeTarefas) && validarString(listaDeTarefas.getNome()) && validarTamanhoString(listaDeTarefas.getNome(), TAMANHO_MAXIMO_ENTRADA) 
				&& validarObjeto(listaDeTarefas.getListaDeTarefas());
	}
	
	
	public static boolean validarTarefa(Tarefa tarefa) {
		
		if(validarObjeto(tarefa) && validarString(tarefa.getNome()) && validarTamanhoString(tarefa.getNome(), TAMANHO_MAXIMO_ENTRADA)
				&& validarObjeto(tarefa.getCategoria())&& validarTamanhoString(tarefa.getCategoria(), TAMANHO_MAXIMO_ENTRADA) &&
				validarObjeto(tarefa.getComentarios()) && validarTamanhoString(tarefa.getComentarios(), TAMANHO_MAXIMO_TEXTO) 
				&& validarPrioridadeTarefa(tarefa.getPrioridade()) && validarObjeto(tarefa.getSubtarefas())&& validarObjeto(tarefa.isEstaConcluida())) {
			return true;
		}
		return false;
	}
	
	public static boolean validarSubTarefa(SubTarefa subTarefa) {
		
		if(validarObjeto(subTarefa) && validarString(subTarefa.getNome()) && validarTamanhoString(subTarefa.getNome(), TAMANHO_MAXIMO_ENTRADA) 
				&& validarObjeto(subTarefa.isEstaConcluida())) {
			return true;
		}
		return false;
	}
	
	public static boolean validarListaInicial(List<?> lista) {
		
		if(lista != null && lista.isEmpty()) {
			return true;
		}
		return false;
	}
	
	private static boolean validarPrioridadeTarefa(String prioridade) {
		if(validarString(prioridade)){
			
			if(prioridade.equals(PRIORIDADE_BAIXA) || prioridade.equals(PRIORIDADE_MEDIA) 
					|| prioridade.equals(PRIORIDADE_ALTA)) {
				return true;
			}
		}	
		return false;
	}
	
	
	private static boolean validarString(String string) {
		if(string != null && !string.trim().isEmpty()) {
			return true;
		}
		return false;
	}
	
	private static boolean validarObjeto(Object objeto ) {
	
		if(objeto != null) {
			return true;
		}
		return false;
	}
	
	private static boolean validarTamanhoString(String string, int tamanhoMaximo) {
		if(string.length() <= tamanhoMaximo) {
			return true;
		}
		return false;
	}
	
}

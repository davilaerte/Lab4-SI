package ufcg.edu.br.dados;

import org.springframework.data.repository.CrudRepository;

import ufcg.edu.br.dados.model.ListaDeTarefas;

/**
 * @author davi laerte
 */
public interface ListaDeTarefaRepository extends CrudRepository<ListaDeTarefas, Long> {

}

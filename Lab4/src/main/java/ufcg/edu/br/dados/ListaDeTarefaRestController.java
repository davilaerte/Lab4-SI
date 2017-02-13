package ufcg.edu.br.dados;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ufcg.edu.br.business.ListaDeTarefasReduzida;
import ufcg.edu.br.business.Validador;
import ufcg.edu.br.dados.model.ListaDeTarefas;
import ufcg.edu.br.dados.model.SubTarefa;
import ufcg.edu.br.dados.model.Tarefa;

/**
 * Classe ListaDeTarefaRestController, controla os endPoints da APIRest.
 * 
 * @author davi laerte
 *
 */
@RestController
@RequestMapping("/ListaDeTarefa")
public class ListaDeTarefaRestController {

	@Autowired
	private ListaDeTarefaRepository repositorio;
	
	
	@RequestMapping(method = RequestMethod.GET)
	 public ResponseEntity<List<ListaDeTarefas>> listarListaDeTarefas() {
		return new ResponseEntity<List<ListaDeTarefas>>((List<ListaDeTarefas>)repositorio.findAll(), HttpStatus.OK);
	 }
	 
	 /*
	  * Retorna as listas de tarefas como Objeto ListaDeTarefaReduzida, assim aumentando
	  * a velocidade de resposta.
	  */
	 @RequestMapping(value="/ListaDeTarefaReduzida", method = RequestMethod.GET)
	 public ResponseEntity<List<ListaDeTarefasReduzida>> listarListaDeTarefasReduzida() {
		 List<ListaDeTarefas> minhasListas = (List<ListaDeTarefas>)repositorio.findAll();
		 List<ListaDeTarefasReduzida> minhasListasReduzidas = new ArrayList<ListaDeTarefasReduzida>();
		 
		 for(ListaDeTarefas minhaLista : minhasListas) {
			 
			 ListaDeTarefasReduzida novaListaReduzida = new ListaDeTarefasReduzida(minhaLista.getId(),
					 minhaLista.getNome(), minhaLista.getListaDeTarefas().size());
			 
			 minhasListasReduzidas.add(novaListaReduzida);
		 }
		 
		 return new ResponseEntity<List<ListaDeTarefasReduzida>>(minhasListasReduzidas, HttpStatus.OK);
	 }
	 
	 @RequestMapping(value="/TotalTarefas", method = RequestMethod.GET)
	 public ResponseEntity<Integer> obterNumeroTotalDeTarefas() {
		List<ListaDeTarefas> minhasListas = (List<ListaDeTarefas>)repositorio.findAll();
		int totalDeTarefas = 0;
		
		for(ListaDeTarefas minhaLista : minhasListas) {
			totalDeTarefas += minhaLista.getListaDeTarefas().size(); 
		}
		return new ResponseEntity<Integer>(totalDeTarefas, HttpStatus.OK);
	 }
		 
	 
	 @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	 public ResponseEntity<ListaDeTarefas> obterListaDeTarefaPorId(@PathVariable("id") Long id) {
		 
		 ListaDeTarefas listaDeTarefas = repositorio.findOne(id);
		 
		 //verifica se a lista existe.
		 if(listaDeTarefas == null){
			 return new ResponseEntity<ListaDeTarefas>(HttpStatus.NOT_FOUND);
		 
		 } else {
			 return new ResponseEntity<ListaDeTarefas>(listaDeTarefas, HttpStatus.OK);
		 }
	 }
	 
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	 public ResponseEntity<?> deletarListaDeTarefaPorId(@PathVariable("id") Long id) {
		 repositorio.delete(id);
		 
		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 
	 }
	 
	 @RequestMapping(value = "/allTarefa", method = RequestMethod.DELETE)
	 public ResponseEntity<?> deletarTodasAsTarefas() {
		 
		 List<ListaDeTarefas> listasDeTarefas = (List<ListaDeTarefas>)repositorio.findAll();
		 
		 for (int i = 0; i < listasDeTarefas.size(); i++) {
			listasDeTarefas.get(i).removeTodasAsTarefas();
			repositorio.save(listasDeTarefas.get(i));
		 }
		 
		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 
	 }
	 
	 
	 @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	 public ResponseEntity<ListaDeTarefas> updateListaDeTarefas(@PathVariable("id") Long id, @RequestBody ListaDeTarefas listaDeTarefas) {
		
		 if(!Validador.validarListaDeTarefa(listaDeTarefas)) {
			 return new ResponseEntity<ListaDeTarefas>(HttpStatus.NOT_ACCEPTABLE);
		 }
		 
		 ListaDeTarefas update = repositorio.findOne(id);
		
		//verifica se a lista existe.
		 if(update != null) {
				 
			 update.setNome(listaDeTarefas.getNome());
		 	 
			 return new ResponseEntity<ListaDeTarefas>(repositorio.save(update), HttpStatus.OK);
		}
		return new ResponseEntity<ListaDeTarefas>(HttpStatus.NOT_FOUND);
	 }
	 
	 
	 
	 @RequestMapping(method = RequestMethod.POST)
	 public ResponseEntity<?> addListaDeTarefa(@RequestBody ListaDeTarefas listaDeTarefas) {
		 if(!Validador.validarListaDeTarefa(listaDeTarefas) 
				 || !Validador.validarListaInicial(listaDeTarefas.getListaDeTarefas())) {
			 
			 return new ResponseEntity<ListaDeTarefas>(HttpStatus.NOT_ACCEPTABLE);
		 }
		 return new ResponseEntity<>(repositorio.save(listaDeTarefas), HttpStatus.CREATED);
	 }
	 
	 @RequestMapping(value = "/{id}/tarefa", method = RequestMethod.PUT)
	 public ResponseEntity<Tarefa> addTarefaNaLista(@PathVariable("id") Long id, @RequestBody Tarefa tarefa) {
		
		 if(!Validador.validarTarefa(tarefa) || !Validador.validarListaInicial(tarefa.getSubtarefas())) {
			 return new ResponseEntity<Tarefa>(HttpStatus.NOT_ACCEPTABLE);
		 }
		 
		 ListaDeTarefas update = repositorio.findOne(id);
		
		//verifica se a lista existe.
		 if(update != null) {
			 
			 update.addTarefa(tarefa);
			 
			 
			 
			 return new ResponseEntity<Tarefa>(repositorio.save(update).obterUltimaTarefa(), HttpStatus.OK);
		 }
		 		 
		 return new ResponseEntity<Tarefa>(HttpStatus.NOT_FOUND);
	 }
	 
	 @RequestMapping(value = "/{idLista}/tarefa/{idTarefa}", method = RequestMethod.PUT)
	 public ResponseEntity<Tarefa> updateTarefaNaLista(@PathVariable("idLista") Long idLista, @PathVariable("idTarefa") Long idTarefa, @RequestBody Tarefa tarefa) {
		 
		 if(!Validador.validarTarefa(tarefa)) {
			 return new ResponseEntity<Tarefa>(HttpStatus.NOT_ACCEPTABLE);
		 }
		 
		 ListaDeTarefas updateLista = repositorio.findOne(idLista);
		 
		//verifica se a lista existe.
		 if(updateLista != null) {
			 Tarefa updateTarefa = updateLista.obterTarefaId(idTarefa);
			 
			//verifica se a tarefa existe.
			 if(updateTarefa != null) {
				 
				 updateTarefa.setNome(tarefa.getNome());
				 updateTarefa.setCategoria(tarefa.getCategoria());
				 updateTarefa.setComentarios(tarefa.getComentarios());
				 updateTarefa.setPrioridade(tarefa.getPrioridade());
				 updateTarefa.setEstaConcluida(tarefa.isEstaConcluida());
				 
				 return new ResponseEntity<Tarefa>(repositorio.save(updateLista).obterTarefaId(idTarefa), HttpStatus.OK);
			 }
		 }
		 
		 
				 
		 return new ResponseEntity<Tarefa>(HttpStatus.NOT_FOUND);
	 }
	 
	 @RequestMapping(value = "/{idLista}/tarefa/{idTarefa}", method = RequestMethod.DELETE)
	 public ResponseEntity<?> deletarTarefaDaLista(@PathVariable("idLista") Long idLista, @PathVariable("idTarefa") Long idTarefa) {
		 ListaDeTarefas listaDeTarefas = repositorio.findOne(idLista);
		 
		//verifica se a lista existe.
		 if(listaDeTarefas != null) {
			 listaDeTarefas.removeTarefaId(idTarefa);
			 repositorio.save(listaDeTarefas);
			 
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }
		 		 
				 
		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 
	 }
	 
	 @RequestMapping(value = "/{idLista}/tarefa/all", method = RequestMethod.DELETE)
	 public ResponseEntity<?> deletarTodasAsTarefasDaLista(@PathVariable("idLista") Long idLista) {
		 ListaDeTarefas listaDeTarefas = repositorio.findOne(idLista);
		 
		//verifica se a lista existe.
		 if(listaDeTarefas != null) {
			
			 listaDeTarefas.removeTodasAsTarefas();
				
			 repositorio.save(listaDeTarefas);
			 
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }
		
		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 
	 }

	 @RequestMapping(value = "/{idLista}/tarefa/{idTarefa}/subtarefa", method = RequestMethod.PUT)
	 public ResponseEntity<SubTarefa> addSubTarefaEmTarefaDaLista(@PathVariable("idLista") Long idLista, @PathVariable("idTarefa") Long idTarefa, @RequestBody SubTarefa subTarefa) {
		 
		 if(!Validador.validarSubTarefa(subTarefa)) {
			 return new ResponseEntity<SubTarefa>(HttpStatus.NOT_ACCEPTABLE);
		 }
		 
		 ListaDeTarefas listaDeTarefas = repositorio.findOne(idLista);
		 
		//verifica se a lista existe.
		 if(listaDeTarefas != null) {
			 Tarefa tarefa = listaDeTarefas.obterTarefaId(idTarefa);
			 
			//verifica se a tarefa existe.
			 if(tarefa != null) {
				 tarefa.addSubTarefa(subTarefa);
				 return new ResponseEntity<SubTarefa>(repositorio.save(listaDeTarefas).obterTarefaId(idTarefa).obterUltimaSubTarefa()
						 , HttpStatus.OK);
			 }
		 }
		 
		 return new ResponseEntity<SubTarefa>(HttpStatus.NOT_FOUND);
	 }
	 
	 @RequestMapping(value = "/{idLista}/tarefa/{idTarefa}/subtarefa/{idSubTarefa}", method = RequestMethod.PUT)
	 public ResponseEntity<SubTarefa> updateSubTarefaEmTarefaDaLista(@PathVariable("idLista") Long idLista, @PathVariable("idTarefa") Long idTarefa, @PathVariable("idSubTarefa") Long idSubTarefa, @RequestBody SubTarefa subTarefa) {
		 
		 if(!Validador.validarSubTarefa(subTarefa)) {
			 return new ResponseEntity<SubTarefa>(HttpStatus.NOT_ACCEPTABLE);
		 }
		 
		 ListaDeTarefas listaDeTarefas = repositorio.findOne(idLista);
		 
		//verifica se a lista existe.
		 if(listaDeTarefas != null) {
			 Tarefa tarefa = listaDeTarefas.obterTarefaId(idTarefa);
			 
			//verifica se a tarefa existe.
			 if(tarefa != null) {
				 SubTarefa updateSubTarefa = tarefa.obterSubTarefaId(idSubTarefa);
				 
				//verifica se a subtarefa existe.
				 if(updateSubTarefa != null) {
					 
					 updateSubTarefa.setNome(subTarefa.getNome());
					 updateSubTarefa.setEstaConcluida(subTarefa.isEstaConcluida());
					 
					 return new ResponseEntity<SubTarefa>(repositorio.save(listaDeTarefas).obterTarefaId(idTarefa).obterSubTarefaId(idSubTarefa)
							 , HttpStatus.OK);
				 }
			 }
		 }
		 
		 return new ResponseEntity<SubTarefa>(HttpStatus.NOT_FOUND);
	 }
	 
	 
	 @RequestMapping(value = "/{idLista}/tarefa/{idTarefa}/subtarefa/{idSubTarefa}", method = RequestMethod.DELETE)
	 public ResponseEntity<?> deletarSubTarefaDeTarefaDaLista(@PathVariable("idLista") Long idLista, @PathVariable("idTarefa") Long idTarefa, @PathVariable("idSubTarefa") Long idSubTarefa) {
		 ListaDeTarefas listaDeTarefas = repositorio.findOne(idLista);
		 
		//verifica se a lista existe.
		 if(listaDeTarefas != null) {
			 Tarefa tarefa = listaDeTarefas.obterTarefaId(idTarefa);
			 
			//verifica se a tarefa existe.
			 if(tarefa != null) {
				 tarefa.removeSubTarefaId(idSubTarefa);
				 
				 repositorio.save(listaDeTarefas);
				 
				 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			 }
		 }
		 
		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 
	 }
	 
}

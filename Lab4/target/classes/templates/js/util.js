

/*
 * Metodos gerais usados na aplicacao.
 * 
 * Criado por Davi Laerte
 */
var obterIndexPorId = function(lista, id) {
	
	for (i = 0; i < lista.length; i++) { 
		
		if(lista[i].id === id) {
			return i;
		}
	}
	return -1;
}

var obterNumeroDeAtividadeFeitas = function(listaTarefas) {
	var contador = 0;
	
	for(i=0; i < listaTarefas.length; i++) {
		if(listaTarefas[i].estaConcluida) {
			contador += 1;
		}
	}
	return contador;
}


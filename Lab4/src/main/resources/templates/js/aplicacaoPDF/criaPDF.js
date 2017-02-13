/*
 * Cria o texto do PDF a partir de uma Lista de Tarefa, usando o pdfmake. 
 * 
 * Criado por Davi Laerte
 */
var criarTextoPDF = function(listaDeTarefa) {
	var pulaLinha = "\n";
	var textoPrincipal = "Lista De Tarefa: "+listaDeTarefa.nome+pulaLinha+pulaLinha;
	var textoTarefas = " Tarefas:"+pulaLinha+pulaLinha;
	var textoDaTarefa = [];
	
	var textoPDF = { footer: { text: 'Copyright © Davi Laerte 2017. All right reserved.',fontSize: 10, bold:true, alignment: 'center'},
			content:[{ text: textoPrincipal, fontSize: 20, bold:true, alignment: 'center' },
	                   { text: textoTarefas, fontSize: 18, bold:true, alignment: 'left' },
	                   { ol: textoDaTarefa }]};
	

	
	var tarefas = listaDeTarefa.listaDeTarefas;
	
	for(i = 0; i < tarefas.length; i++){
		
		var textoLinha1 = [{text: " Nome: ", fontSize: 15, italics: true},tarefas[i].nome,{text:", Categoria: ", fontSize: 15, italics: true},tarefas[i].categoria,
		                   {text:", Prioridade: ", fontSize: 15, italics: true},tarefas[i].prioridade+","+pulaLinha];
		
		var textoComentariosSubTarefas = [{text:" Comentários: ", fontSize: 15, italics: true},
		                                  {text:tarefas[i].comentarios+pulaLinha, alignment: 'justify'},
		                                  {text:" SubTarefas:"+pulaLinha, fontSize: 15, italics: true}];
		var textoSubTarefas = [];
		
		var textoTarefa = [{text:textoLinha1 }, 
		                   {text:textoComentariosSubTarefas },
		                   {ol:textoSubTarefas },
		                   pulaLinha+pulaLinha];
		
		var subTarefas = tarefas[i].subtarefas;
		
		for(j = 0; j < subTarefas.length; j++){
			var textoSubTarefa = [{text:[{text:" Nome: ", fontSize: 13, italics: true},subTarefas[j].nome+pulaLinha]}];
			textoSubTarefas.push(textoSubTarefa);
		}
		
		textoDaTarefa.push(textoTarefa);
	}
	 
	return textoPDF;
}

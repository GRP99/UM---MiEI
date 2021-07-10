var express = require("express");
var router = express.Router();

var axios = require("axios");
var gdb = require("../utils/graphdb");

// #################### GETS ####################
// GET /api/emd - Devolve a lista de EMD apenas com os campos "id", "nome", "data" e "resultado";
// GET /api/emd?res=OK - Devolve a lista de EMD com resultado "true";
router.get("/emd", async function (req, res, next) {
	if (req.query.res == 'OK') {
		var myquery = `SELECT ?emd ?primeiro_nome ?ultimo_nome ?data WHERE {
			?emd a :EMD .
			?emd :data ?data .
			?emd :resultado "True" .
			?atleta :efetuaEMD ?emd .
			?atleta :primeiro_nome ?primeiro_nome .
			?atleta :ultimo_nome ?ultimo_nome .
		} `;
		var result = await gdb.execQuery(myquery);
		var dados = result.results.bindings.map((emd) => {
			return {
				id: emd.emd.value.split("#")[1].split('emd')[1],
				nome: emd.primeiro_nome.value + " " +emd.ultimo_nome.value,
				data: emd.data.value,
			};
		});
		res.jsonp(dados);
	}
	else{
		var myquery = `SELECT ?emd ?primeiro_nome ?ultimo_nome ?data ?resultado WHERE {
			?emd a :EMD .
			?emd :data ?data .
			?emd :resultado ?resultado .
			?atleta :efetuaEMD ?emd .
			?atleta :primeiro_nome ?primeiro_nome .
			?atleta :ultimo_nome ?ultimo_nome .
		} `;
		var result = await gdb.execQuery(myquery);
		var dados = result.results.bindings.map((emd) => {
			return {
				id: emd.emd.value.split("#")[1].split('emd')[1],
				nome: emd.primeiro_nome.value + " " +emd.ultimo_nome.value,
				data: emd.data.value,
				resultado: emd.resultado.value,
			};
		});
		res.jsonp(dados);
	}	
});

// GET /api/emd/:id - Devolve a informação completa de um EMD;
router.get("/emd/:id", async function (req, res, next) {
		var query = `SELECT ?id ?data ?resultado ?primeiro_nome ?ultimo_nome 
		?idade ?genero ?morada ?modalidade_nome ?clube_nome ?email ?federado WHERE {
			:emd${req.params.id} a :EMD ;
				:id ?id ;
				:data ?data ;
				:resultado ?resultado .
			?atleta :efetuaEMD :emd${req.params.id} ;
				:primeiro_nome ?primeiro_nome ;
				:ultimo_nome ?ultimo_nome ;
				:idade ?idade ;
				:genero ?genero ;
				:morada ?morada ;
				:praticaModalidade ?modalidade ;
				:temClube ?clube ;
				:email ?email ;
				:federado ?federado .
			?modalidade :designacao ?modalidade_nome.
			?clube :designacao ?clube_nome.		
		} `;
		var result = await gdb.execQuery(query);
		var dados = result.results.bindings.map((emd) => {
			return {
				_id: req.params.id,
				index: emd.id.value,
				dataEMD: emd.data.value,
				nome:{
					primeiro: emd.primeiro_nome.value,
					último: emd.ultimo_nome.value
				},
				idade: emd.idade.value,
				género: emd.genero.value,
				morada: emd.morada.value,
				modalidade: emd.modalidade_nome.value,
				clube: emd.clube_nome.value,
				email: emd.email.value,
				federado: emd.federado.value,
				resultado: emd.resultado.value			
			};
		});
		res.jsonp(dados[0]);	
});

// GET /api/modalidades - Devolve a lista de modalidades, sem repetições;
router.get("/modalidades", async function (req, res, next) {
	var myquery = `SELECT DISTINCT ?modalidade ?designacao WHERE {
		?modalidade a :Modalidade .
		?modalidade :designacao ?designacao .
	}`;
	var result = await gdb.execQuery(myquery);
	var dados = result.results.bindings.map((emd) => {
		return {
			id_modalidade : emd.modalidade.value.split("#")[1],
			modalidade: emd.designacao.value 			
		};
	});
	res.jsonp(dados);
});

// GET /api/modalidades/:id - Devolve a lista de EMD referentes à modalidade passada como parâmetro;
router.get("/modalidades/:id", async function (req, res, next) {
	var query = `SELECT ?emd ?id ?data ?resultado ?primeiro_nome ?ultimo_nome 
	?idade ?genero ?morada ?modalidade_nome ?clube_nome ?email ?federado WHERE {
		?emd a :EMD ;
			:id ?id ;
			:data ?data ;
			:resultado ?resultado .
		?atleta :efetuaEMD ?emd ;
			:primeiro_nome ?primeiro_nome ;
			:ultimo_nome ?ultimo_nome ;
			:idade ?idade ;
			:genero ?genero ;
			:morada ?morada ;
			:praticaModalidade :${req.params.id} ;
			:temClube ?clube ;
			:email ?email ;
			:federado ?federado .
		:${req.params.id} :designacao ?modalidade_nome.
		?clube :designacao ?clube_nome.		
	} `;
	var result = await gdb.execQuery(query);
	var dados = result.results.bindings.map((emd) => {
		return {
			_id: emd.emd.value.split("#")[1],
			index: emd.id.value,
			dataEMD: emd.data.value,
			nome:{
				primeiro: emd.primeiro_nome.value,
				último: emd.ultimo_nome.value
			},
			idade: emd.idade.value,
			género: emd.genero.value,
			morada: emd.morada.value,
			modalidade: emd.modalidade_nome.value,
			clube: emd.clube_nome.value,
			email: emd.email.value,
			federado: emd.federado.value,
			resultado: emd.resultado.value			
		};
	});
	res.jsonp(dados);	
});

// GET /api/atletas?gen=F - Devolve uma lista ordenada alfabeticamente com os nomes dos atletas de género feminino;
// GET /api/atletas?clube=X - Devolve uma lista ordenada alfabeticamente com os nomes dos atletas do clube X.
router.get("/atletas", async function (req, res, next) {
	if(req.query.gen){
		var myquery = `SELECT ?primeiro_nome ?ultimo_nome WHERE {
			?atleta a :Atleta .
			?atleta :genero "${req.query.gen}" .
			?atleta :primeiro_nome ?primeiro_nome .
			?atleta :ultimo_nome ?ultimo_nome .
		} ORDER BY ?primeiro_nome ?ultimo_nome`;
		var result = await gdb.execQuery(myquery);
		var dados = result.results.bindings.map((emd) => {
			return {
				atleta : emd.primeiro_nome.value + " " +emd.ultimo_nome.value,			
			};
		});
		res.jsonp(dados);
	}
	else{
		if (req.query.clube) {
			var myquery = `SELECT ?primeiro_nome ?ultimo_nome WHERE {
				?atleta a :Atleta .
				?atleta :temClube ?clube . 
				?clube :designacao "${req.query.clube}" .
				?atleta :primeiro_nome ?primeiro_nome .
				?atleta :ultimo_nome ?ultimo_nome .
			} ORDER BY ?primeiro_nome ?ultimo_nome`;
			var result = await gdb.execQuery(myquery);
			var dados = result.results.bindings.map((emd) => {
				return {
					atleta : emd.primeiro_nome.value + " " +emd.ultimo_nome.value,			
				};
			});
			res.jsonp(dados);
		}
		else{
			var myquery = `SELECT ?primeiro_nome ?ultimo_nome ?genero ?designacao_clube WHERE {
				?atleta a :Atleta .
				?atleta :genero ?genero .
				?atleta :temClube ?clube . 
				?clube :designacao ?designacao_clube .
				?atleta :primeiro_nome ?primeiro_nome .
				?atleta :ultimo_nome ?ultimo_nome .
			} ORDER BY ?primeiro_nome ?ultimo_nome`;
			var result = await gdb.execQuery(myquery);
			var dados = result.results.bindings.map((emd) => {
				return {
					atleta : emd.primeiro_nome.value + " " +emd.ultimo_nome.value,
					genero: emd.genero.value,
					clube: emd.designacao_clube.value		
				};
			});
			res.jsonp(dados);
		}
	}
});

module.exports = router;
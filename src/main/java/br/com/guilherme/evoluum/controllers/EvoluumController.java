package br.com.guilherme.evoluum.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.evoluum.dominio.Municipio;
import br.com.guilherme.evoluum.service.LocalidadesService;

@Controller
public class EvoluumController {

	@GetMapping("/")
	public String index() {
		List<Municipio> municipios = localidadesService.getAllMunicipiosFromIBGE();
		for(Municipio municipio : municipios){
			cidadesCache.put(municipio.getNome(), municipio.getId());
		}
		return "home/index";
	}

	private Map<String, Number> cidadesCache = new HashMap<>();

	@Autowired
	LocalidadesService localidadesService;

	@GetMapping("/localidadesJson")
	public void getAllLocalidadesJson(HttpServletResponse response) throws Exception {
		response.getWriter().write(localidadesService.getAllMunicipiosFromIBGEJson(2));
	}
	
	@GetMapping("/localidadesCsv")
	public void getAllLocalidadesJsonCsv(HttpServletResponse response) throws Exception {
		response.getWriter().write(localidadesService.getAllMunicipiosFromIBGEJson(1));
	}

	@GetMapping("/municipios/download/dadosCsv")
	public void downloadDadosCsv(HttpServletResponse response) throws Exception {
		 response.setContentType("text/csv");
	     response.setHeader("Content-Disposition", "attachment; file=dados.csv");
		 String listaFormatada = localidadesService.getAllMunicipiosFromIBGEJson(1);
		 response.getWriter().write(listaFormatada);
	}
	
	@GetMapping("/municipios/download/dadosJson")
	public void downloadDadosJson(HttpServletResponse response) throws Exception {
		response.setContentType("application/json");
		response.setHeader("Content-Disposition", "attachment; file=dados.json");
		String listaFormatada = localidadesService.getAllMunicipiosFromIBGEJson(2);
		response.getWriter().write(listaFormatada);
	}
	
	@GetMapping("/municipios/findIdByName/{nomeCidade}")
	public void obterIdCidadeByName(@PathVariable("nomeCidade") String nomeCidade, HttpServletResponse response) throws Exception {
		if(cidadesCache.containsKey(nomeCidade)){
			response.getWriter().write(cidadesCache.get(nomeCidade).toString());
		}else{
			List<Municipio> municipios = localidadesService.getAllMunicipiosFromIBGE();
			for(Municipio municipio : municipios){
				if(municipio.getNome().toLowerCase().equals(nomeCidade.toLowerCase())){
					response.getWriter().write(municipio.getId().toString());
					cidadesCache.put(municipio.getNome(), municipio.getId());
					break;
				}
			}
		}
	}

}

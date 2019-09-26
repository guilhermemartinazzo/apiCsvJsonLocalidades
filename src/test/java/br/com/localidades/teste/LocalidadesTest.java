package br.com.localidades.teste;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.localidades.dominio.Municipio;
import br.com.localidades.dominio.UF;
import br.com.localidades.service.LocalidadesService;

public class LocalidadesTest {

	
	LocalidadesService service;
	
	@Before
	public void setUp(){
		service = new LocalidadesService();
	}
	
	@Test
	public void getAllMunicipiosFromIBGETest() {
		List<Municipio> lstMunicipios = service.getAllMunicipiosFromIBGE();
		Assert.assertNotNull(lstMunicipios);
		Assert.assertFalse(lstMunicipios.isEmpty());
	}
	
	@Test
	public void getAllUFsFromIBGE() {
		List<UF> lstUF = service.getAllUFsFromIBGE();
		Assert.assertNotNull(lstUF);
		Assert.assertFalse(lstUF.isEmpty());
	}
	
	@Test
	public void getAllMunicipiosFromIBGEJsonTest() throws JSONException {
		String jsonString = service.getAllMunicipiosFromIBGE(LocalidadesService.TIPO_EXIBICAO_JSON);
		JSONArray jsonArray = new JSONArray(jsonString);
		Assert.assertTrue(jsonArray.length() > 0);
	}
	
	@Test
	public void getAllMunicipiosFromIBGECsvTest() throws JSONException {
		String dadosCsv = service.getAllMunicipiosFromIBGE(LocalidadesService.TIPO_EXIBICAO_CSV);
		Assert.assertNotNull(dadosCsv);
		Assert.assertFalse(dadosCsv.isEmpty());
	}
	
}

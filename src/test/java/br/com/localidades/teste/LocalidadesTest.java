package br.com.localidades.teste;

import java.util.List;

import org.junit.Assert;
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

	
	
	@Test
	public void test() {
		LocalidadesService service = new LocalidadesService();
		List<Municipio> lstMunicipios = service.getAllMunicipiosFromIBGE();
		Assert.assertNotNull(lstMunicipios);
		Assert.assertFalse(lstMunicipios.isEmpty());
	}
	@Test
	public void test2() {
		RestTemplate template = new RestTemplate();
		template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		UriComponents uri = UriComponentsBuilder.newInstance().scheme("https").host("servicodados.ibge.gov.br").path("api/v1/localidades/estados").build();
		ResponseEntity<List<UF>> response = template.exchange(uri.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<UF>>(){});
		List<UF> lstUF = response.getBody();
	}

}

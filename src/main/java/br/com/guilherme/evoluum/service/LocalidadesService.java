package br.com.guilherme.evoluum.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.evoluum.dominio.Municipio;
import br.com.evoluum.dominio.UF;

@Component
public class LocalidadesService {
	private static final String HOST = "servicodados.ibge.gov.br";
	private static final String PATH_MUNICIPIOS = "api/v1/localidades/municipios";
	private static final String PATH_UFS = "api/v1/localidades/estados";
	private static final String HTTPS = "https"; 
	
	public List<Municipio> getAllMunicipiosFromIBGE(){
		RestTemplate template = getRestTemplate();
		String uriMunicipios = getUriByParametersSchemeHostPath(HTTPS, HOST, PATH_MUNICIPIOS);
		ResponseEntity<List<Municipio>> response = template.exchange(uriMunicipios, HttpMethod.GET, null, new ParameterizedTypeReference<List<Municipio>>(){});
		return response.getBody();
	}
	
	public String getAllMunicipiosFromIBGEJson(int tipoExibicao){
		RestTemplate template = getRestTemplate();
		
		String uriMunicipios = getUriByParametersSchemeHostPath(HTTPS, HOST, PATH_MUNICIPIOS);
		ResponseEntity<String> response = template.exchange(uriMunicipios, HttpMethod.GET, null, new ParameterizedTypeReference<String>(){});
		String dadosParaExibicao = obterDadosParaExibicao(response.getBody(),tipoExibicao);
		return dadosParaExibicao;
	}
	
	public List<UF> getAllUFsFromIBGE(){
		RestTemplate template = getRestTemplate();
		String uriUFs = getUriByParametersSchemeHostPath(HTTPS, HOST, PATH_UFS);
		ResponseEntity<List<UF>> response = template.exchange(uriUFs, HttpMethod.GET, null, new ParameterizedTypeReference<List<UF>>(){});
		return response.getBody();
	}
	
	public String getAllUFsFromIBGEJson(){
		RestTemplate template = getRestTemplate();
		String uriUFs = getUriByParametersSchemeHostPath(HTTPS, HOST, PATH_UFS);
		ResponseEntity<String> response = template.exchange(uriUFs, HttpMethod.GET, null, new ParameterizedTypeReference<String>(){});
		String ttttt = response.getBody();
		String dadosParaExibicao = obterDadosParaExibicao(ttttt,2);
		return dadosParaExibicao;
	}
	
	//tipoExibicao 1 = CSV, tipoExibicao 2 = Json
	private String obterDadosParaExibicao(String json, int tipoExibicao){
		StringBuilder sb = new StringBuilder("");
		if(tipoExibicao == 1){
			sb.append("idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado");
		}
		JSONArray jsonArrayRetorno = new JSONArray();		
		try{
			 JSONArray jsonArray = new JSONArray(json);
			 for(int i = 0 ; i < jsonArray.length(); i++){
				 //
				 JSONObject municipio = jsonArray.getJSONObject(i);
				 JSONObject microrregiao = municipio.getJSONObject("microrregiao");
				 JSONObject mesorregiao = microrregiao.getJSONObject("mesorregiao");
				 JSONObject uf = mesorregiao.getJSONObject("UF");
				 JSONObject regiaoUF = uf.getJSONObject("regiao");
				 //
				 Number idEstado = uf.getInt("id"); 
				 String siglaEstado = uf.getString("sigla");
				 String regiaoNome = regiaoUF.getString("nome");
				 String nomeCidade = municipio.getString("nome");
				 String nomeMesorregiao = mesorregiao.getString("nome");
				 String nomeFormatado = nomeCidade+"/"+uf.getString("nome");
				 //
				 if(tipoExibicao == 1){
					 appendTipoExibicaoCSV(sb, idEstado, siglaEstado, regiaoNome, nomeCidade, nomeMesorregiao, nomeFormatado);
				 }else if(tipoExibicao == 2){
					 appendTipoExibicaoJson(idEstado, siglaEstado, regiaoNome, nomeCidade, nomeMesorregiao, nomeFormatado, jsonArrayRetorno);
				 }
				 
			 }
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		return tipoExibicao == 1 ? sb.toString() : jsonArrayRetorno.toString();
	}

	private void appendTipoExibicaoJson(Number idEstado, String siglaEstado, String regiaoNome,
			String nomeCidade, String nomeMesorregiao, String nomeFormatado, JSONArray jsonArray) throws JSONException {
		JSONObject json= new JSONObject();
		json.put("idEstado", idEstado);
		json.put("siglaEstado", siglaEstado);
		json.put("regiaoNome", regiaoNome);
		json.put("nomeCidade", nomeCidade);
		json.put("siglaEstado", siglaEstado);
		json.put("nomeMesorregiao", nomeMesorregiao);
		json.put("nomeFormatado", nomeFormatado);
		jsonArray.put(json);
	}

	private void appendTipoExibicaoCSV(StringBuilder sb, Number idEstado, String siglaEstado, String regiaoNome, String nomeCidade,
			String nomeMesorregiao, String nomeFormatado) {
		sb.append("\n").append(idEstado).append(",")
		 .append(siglaEstado).append(",")
		 .append(regiaoNome).append(",")
		 .append(nomeCidade).append(",")
		 .append(nomeMesorregiao).append(",")
		 .append(nomeFormatado).append("");
	}
	
	private RestTemplate getRestTemplate() {
		RestTemplate template = new RestTemplate();
		template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		return template;
	}
	
	private String getUriByParametersSchemeHostPath(String scheme, String host, String path){
		UriComponents uri = UriComponentsBuilder.newInstance().scheme(scheme).host(host).path(path).build();
		return uri.toUriString();
	}
}
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API Guilherme</title>
</head>
<br/>
<a href="/localidadesJson" >Listar todas as informacoes formato Json</a>
<br/>
<a href="/localidadesCsv" >Listar todas as informacoes formato CSV</a>
<br/>
<a href="/municipios/download/dadosCsv" >Download arquivo CSV</a>
<br/>
<a href="/municipios/download/dadosJson" >Download arquivo Json</a>

<div>
	<h3>Endpoint para consultar id de cidade passando o nome como parametro:</h3>
	<h4>Ex: localhost:8080/municipios/findIdByName/Florianópolis</h4>
	<br/>
	<h3>Endpoint que retorna um json com todos os dados:</h3>
	<h4>Ex: localhost:8080/localidadesJson</h4>
	<br/>
	<h3>Endpoint que retorna um csv com todos os dados:</h3>
	<h4>Ex: localhost:8080/localidadesCsv</h4>
	<br/>
</div>
</body>
</html>
package es.isoco.trie.io;

import java.io.IOException;
import java.net.URI;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class QueryRodl {
	
	public static final URI DEFAULT_BASE = URI.create("http:/repos-wf4ever.isoco.com/sparql/");
	
	Client client = Client.create();
	ObjectMapper mapper = new ObjectMapper();

	private WebResource base;
	
	public QueryRodl() {
		base = client.resource(DEFAULT_BASE);
	}
	
	public QueryRodl(URI baseURI) {
		base = client.resource(baseURI);
	}

	public static void main(String[] args) throws Exception {
		URI baseURI;
		if (args.length > 0) {
			baseURI = URI.create(args[0]);
		} else {
			baseURI = DEFAULT_BASE;
		}
		
		new QueryRodl(baseURI).annotateT2flows();
		
	}
	
	public void annotateT2flows() throws JsonParseException, JsonMappingException, IOException {
		
		String query = "PREFIX wfdesc: <http://purl.org/wf4ever/wfdesc#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "SELECT ?wf ?proc ?procType ?procLabel "
				+ "WHERE {"
				+ "	?wf a wfdesc:Workflow;"
				+ "       wfdesc:hasSubProcess ?proc. "
				+ " ?proc rdfs:label ?procLabel ."
				// Ignore non-specific types
				+ "OPTIONAL { ?proc a ?procType . FILTER (?procType != wfdesc:Description && ?procType != wfdesc:Process && ?procType != owl:Thing) }"
				+ "} " + "ORDER BY ?wf ";
		
		
	/*	for (JsonNode binding : sparql(query)) {
			System.out.print( binding.path("wf").path("value").asText());
			System.out.print( binding.path("proc").path("value").asText());
			System.out.print( binding.path("procType").path("value").asText());
			System.out.println( binding.path("procLabel").path("value").asText());
		}
		
		query = "PREFIX wfdesc: <http://purl.org/wf4ever/wfdesc#> "
		+ " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
		+ " PREFIX owl: <http://www.w3.org/2002/07/owl#> "
		+ " SELECT ?wf ?fromProc ?toProc ?fromProcLabel ?toProcLabel "
		+ " WHERE {" + "	?wf a wfdesc:Workflow;"
		+ "       wfdesc:hasSubProcess ?fromProc,?toProc ;"
		+ "       wfdesc:hasDataLink ?link . "
		+ " ?link wfdesc:hasSource ?fromPort; "
		+ "      wfdesc:hasSink ?toPort ."
		+ " ?fromProc wfdesc:hasOutput ?fromPort ;"
		+ "           rdfs:label ?fromProcLabel ."
		+ " ?toProc wfdesc:hasInput ?toPort ;"
		+ "         rdfs:label ?toProcLabel ." + "} "
		+ "ORDER BY ?wf ";
		
		for (JsonNode binding : sparql(query)) {
			System.out.print( binding.path("wf").path("value").asText());
			System.out.print( binding.path("fromProc").path("value").asText());
			System.out.print( binding.path("toProc").path("value").asText());
			System.out.println( binding.path("fromProcLabel").path("value").asText());
			System.out.println( binding.path("toProcLabel").path("value").asText());
		}
		*/
		
		
		query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "PREFIX wfprov: <http://purl.org/wf4ever/wfprov#>"
			+ "PREFIX wfdesc: <http://purl.org/wf4ever/wfdesc#>"
			+ "SELECT DISTINCT ?process WHERE {?process wfprov:wasPartOfWorkflowRun <http://wings.isi.edu/opmexport/resource/Account/ACCOUNT1332778606534> .}" ;

		query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "SELECT * WHERE { ?s ?p ?o} LIMIT 10"; 
		
		for (JsonNode binding : sparql(query)) {
			System.out.print( binding.path("process").path("value").asText());
		}
	}

	public JsonNode sparql(String query) throws JsonParseException, JsonMappingException, IOException {
		WebResource sparql = base.path("sparql");		
		//String json = sparql.queryParam("query", query).accept("application/sparql-results+json", "application/json").get(String.class);
		String json = sparql.queryParam("query", query).accept("application/sparql-results+xml").get(String.class);
		System.out.println(json);
		JsonNode jsonNode = mapper.readValue(json, JsonNode.class);
		return jsonNode.path("results").path("bindings");		
	}
	
}

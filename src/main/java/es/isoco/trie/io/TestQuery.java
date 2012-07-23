package es.isoco.trie.io;
import java.io.IOException;
import java.net.MalformedURLException;


public class TestQuery {

/**
* @param args
*/
public static void main(String[] args) {

Store store;
try {
	System.out.println("Start");
	store = new Store("http://test-wf4ever.isoco.com/");
	String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX wfprov: <http://purl.org/wf4ever/wfprov#>"
				+ "PREFIX wfdesc: <http://purl.org/wf4ever/wfdesc#>"
				+ "SELECT DISTINCT ?process WHERE {?process wfprov:wasPartOfWorkflowRun <http://wings.isi.edu/opmexport/resource/Account/ACCOUNT1332778606534> .}" ;
	
	//simple query
	String response1 = store.query(query);
	System.out.println(response1);
	//specifying outputformat
	String response2 = store.query(query,Store.OutputFormat.JSON);
	System.out.println(response2);
	//specifying softlimit and default output format
	String response3 = store.query(query,5);
	System.out.println(response3);
	//specifying outputformat and soft limit
	String response4 = store.query(query,Store.OutputFormat.TAB_SEPARATED, 22);
	System.out.println(response4);
	System.out.println("Done");
	} catch (MalformedURLException e) {
	e.printStackTrace();
	} catch (IOException e) {
	e.printStackTrace();
	}
	}
}
package es.isoco.trie.io;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class XMLTrieSaver {
	
	public void save(String url, Document doc) throws IOException{
		
	try {
	      // Write the DOM document to the file
	      // Prepare the output file
	      Source source = new DOMSource(doc);
	      File file = new File(url);
	      Result result = new StreamResult(file);
	      Transformer xformer;
		  xformer = TransformerFactory.newInstance().newTransformer();			
	      xformer.transform(source, result);
	      
	} catch (TransformerConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TransformerFactoryConfigurationError e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TransformerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}
}

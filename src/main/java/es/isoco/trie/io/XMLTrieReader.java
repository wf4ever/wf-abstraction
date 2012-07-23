package es.isoco.trie.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;



public  class XMLTrieReader {

	public Document read(String url, String type){
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();		
			
			//File reader
			if (type.compareTo("file") == 0){
				doc = docBuilder.parse (new File(url));
			}
			//Url reader
			else if(type.compareTo("url") == 0){
				doc = docBuilder.parse (new ByteArrayInputStream(url.getBytes("UTF-8")));
			}
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}	
		return doc;
	}
}

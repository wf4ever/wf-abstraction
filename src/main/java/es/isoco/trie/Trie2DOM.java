package es.isoco.trie;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Trie2DOM {

	public static Document buildDOM(Trie t) 
	{
	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
	try {
		docBuilder = docBuilderFactory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    Document document = docBuilder.newDocument();
    // Create from whole cloth
	    Element root = (Element)document.createElement("Trie"); 
	    document.appendChild(root);
	    root.setAttribute("depth", "0");
	   /* root.appendChild( 
	      document.createTextNode("Some") );
	    root.appendChild( 
	      document.createTextNode(" ")    );
	    root.appendChild( 
	      document.createTextNode("text") );*/
	    Element node = (Element)document.createElement("Node");
	    root.appendChild(node);
	    node.setAttribute("NAME", "process1");
	    node.setAttribute("LEVEL", "1");
	    node.setAttribute("PROB", "0.1");
	    node.setAttribute("FREQ", "10");
	    return document;
    } 
    
    
}

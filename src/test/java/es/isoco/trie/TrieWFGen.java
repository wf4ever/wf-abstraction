package es.isoco.trie;


import java.io.IOException;
import java.util.LinkedList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.isoco.processOrder.main.RunAll;
import es.isoco.trie.io.XMLTrieReader;
import es.isoco.trie.io.XMLTrieSaver;

public class TrieWFGen {

	public static Trie trieDSA;
	final String inputType = "file"; //Input stream : file or URL SPARQL endpoint
	
	 public static void main(String[] args) {
	  TrieWFGen trieTest = new TrieWFGen();
	  trieTest.load();	  
	 }	

	 /**
	 * @throws  
	 * @throws InterruptedException 
	 * 
	 */
	public void load() {
	  trieDSA = new Trie();
	  boolean subChains = true;
	  
	  XMLTrieReader tReader = new XMLTrieReader();
	  Document doc = null;
	  XMLTrieSaver tSaver = new XMLTrieSaver();
	  
	  if (inputType.compareToIgnoreCase("file") == 0){
		  doc = tReader.read("actions.xml", "file");
	  }
	  else{
		  RunAll r= new RunAll();
		  doc = tReader.read(r.run(), "url");
	  }
	  
	  doc.getDocumentElement().normalize();
	  
      NodeList nListWFs = doc.getElementsByTagName("workflow");
            
      for (int temp = 0; temp < nListWFs.getLength(); temp++)
      {    	 	 
   		 Node nNode = nListWFs.item(temp);
   		 if (nNode.getNodeType() == Node.ELEMENT_NODE)
   		 {
   			Element eNode = (Element)nNode;
   			NodeList pNodes = eNode.getElementsByTagName("process");
   			LinkedList<String> sList = new LinkedList<String>();

   			if (subChains == false)
   			{
	   			//Inserting WFs as they are
	   			for (int i = 0; i < pNodes.getLength(); i++)
	   			{
	   				sList.add(pNodes.item(i).getChildNodes().item(0).getNodeValue());
	   			}
	   			trieDSA.insert(sList);
	   			}
   			else
   			{
   				//Inserting WFs and their sub-chains
   				for (int i = 0; i < pNodes.getLength(); i++)
	   			{
   					int maxInd = Math.min(pNodes.getLength(), i+3);
   					for (int j = i; j < maxInd; j++)
   					{
   						sList.add(pNodes.item(j).getChildNodes().item(0).getNodeValue());
   						trieDSA.insert(sList);
   					}
   					sList.clear();
	   			}	   			
	   		}
   		}
   			
   	 }
      
	   LinkedList<Integer> fLevel = new LinkedList<Integer>();
	   for (int i = 0; i <= trieDSA.getDepth(); i++)
	   {
		   fLevel.add(trieDSA.getTotalFreqByLevel(i));
	   }
	   
	   Document document = trieDSA.getDOM(fLevel);
	   try {
		tSaver.save("./wfTrie.xml", document);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
}


	public static void process(Document doc) {
		    
	        NodeList listOfWFs = doc.getElementsByTagName("process");
            int totalWFs = listOfWFs.getLength();
            System.out.println("Total no of WFs : " + totalWFs);
            
            for(int s=0; s<listOfWFs.getLength() ; s++){
 
            	Node WFNode = listOfWFs.item(s);
            	if (WFNode.getNodeType() == Node.ELEMENT_NODE) {
            		Element eElement= (Element)WFNode;	
            		System.out.println(getTagValue("workflow", eElement));
            	}
            }
	}
	 
	 private static String getTagValue(String sTag, Element eElement) {
		    NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		    Node nValue = (Node) nlList.item(0);
		 
			return nValue.getNodeValue();
		  }
}

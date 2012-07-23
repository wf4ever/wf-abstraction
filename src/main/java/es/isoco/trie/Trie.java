package es.isoco.trie;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import es.isoco.trie.io.Constants;
import es.isoco.trie.io.GenericMethods;
import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;


/* The different types for trie traversals can be found at 
 * http://en.wikipedia.org/wiki/Tree_traversal
 */

public class Trie {

	public static float[][] percByLevel = new float[Constants.numActions][Constants.numLevels];
	
	private TNode root;
	private int depth;
   
	 
	 public Trie(){
	  root = new TNode(" ");
	  root.freq = 0;
	  depth = 0;	  
	 }
	 
	 public void insert(LinkedList<String> sList){
	  
	  String s = null;
	  int sSize = sList.size();	  
	  TNode current = root;
	  int level = 0;
	  	  
	  if(sSize==0) //For an empty list make it root		  
		  current.marker = true;
	  if(sSize > depth)
		  depth = sSize;
		
	  for(int i=0;i<sSize;i++){
	   level++;
	   if (level == 1) {root.freq++; root.updateProb();}
	   s = sList.get(i);
	   TNode child = current.subNode(s);
	   if(child!=null){  //There is a child with that content
		   child.freq++;	
		   current.updateProb();
		   current = child;
	   }
	   else{
		TNode newNode = new TNode(s);
		newNode.level = level;
		newNode.freq++;
		newNode.prob = 1;      //There is only 1 child
		newNode.leaf = true;  //Change if the search has to be over the complete 
			// set of actions
		
	    current.child.add(newNode);
	    
	    current.updateProb();
	    current = current.subNode(s);	   
	   }
	   
	   
	   // Set marker to indicate end of the word
	   if(i==s.length()-1)
	    current.marker = true;
	  }	  
	 }
	 
	 
	 public void display()
	 {	 
		 preorderDisplay(root);
		 levelOrderDisplay(root);		 
	 }
	 
	 	
	 protected void preorderDisplay(TNode n)
	 {
		 
		 if (n == null)
		 {
			 return;
		 }
		 
		 String sGuion = new String("");
		 if (n.level == 1){sGuion = "-";}
		 if (n.level == 2){sGuion = "---";}
		 if (n.level == 3){sGuion = "-----";}
		 if (n.level == 4){sGuion = "--------";}
		 if (n.level == 5){sGuion = "-----------";}
		 if (n.level == 6){sGuion = "--------------";}
		 
		 
		 
		 System.out.println(sGuion + n.content + "  " + n.freq + " " + n.level + " " + n.prob);

		 		 
		 Collection<TNode> childs = n.getChilds();
			  
		 Iterator<TNode> iteNode = childs.iterator();
		 while (iteNode.hasNext())
		 {
			 preorderDisplay(iteNode.next());
		 }
	 }
	 
	 protected void levelOrderDisplay(TNode root)
	 {
		 
		 if (root == null) 
		 {
			 return;
		 }
		 System.out.println("++++++++++Level Order Display+++++++");
		 LinkedList<TNode> queue = new LinkedList<TNode>();
		 queue.add(root);
		 while ( !queue.isEmpty()) {
			 TNode n = queue.removeFirst();	
			 
			 System.out.println("Process: " + n.getContent() + "Freq: " + n.getFreq() + "level: "  + n.getLevel());
			 Collection<TNode> childs = n.getChilds();
			 Iterator<TNode> iteNode = childs.iterator();
			 while (iteNode.hasNext())
			 {
				 queue.add(iteNode.next());
			 }
		 }
		 
	 }
	 
	 protected void levelOrderDisplay(LinkedList<Integer> fLevel)
	 {
		 
		 if (root == null) 
		 {
			 return;
		 }
		 System.out.println("++++++++++++++++++++++++++++++++");
		 LinkedList<TNode> queue = new LinkedList<TNode>();
		 queue.add(root);
		 while ( !queue.isEmpty()) {
			 TNode n = queue.removeFirst();	
			 float aux =(float)n.getFreq()/(float)(fLevel.get(n.getLevel()));
			 System.out.println("Process: " + n.getContent() + "Freq: " +n.getFreq()+ "level: "  + n.getLevel() + "Prob. by level: " + aux);
			 Collection<TNode> childs = n.getChilds();
			 Iterator<TNode> iteNode = childs.iterator();
			 while (iteNode.hasNext())
			 {
				 queue.add(iteNode.next());
			 }
		 }
		 
	 }
	 protected int getTotalFreqByLevel(int nLevel)
	 {
		 int total = 0;
		 
		 if (root == null) 
		 {
			 return 0;
		 }
		 
		 LinkedList<TNode> queue = new LinkedList<TNode>();
		 queue.add(root);
		 while ( !queue.isEmpty()) {
			 TNode n = queue.removeFirst();	
			 if (nLevel == n.getLevel()){
			//	 System.out.println("Process: " + n.getContent() + "Freq: " + n.getFreq() + "level: "  + n.getLevel());
				 total = total + n.getFreq();
			 }
			 
			 Collection<TNode> childs = n.getChilds();
			 Iterator<TNode> iteNode = childs.iterator();
			 while (iteNode.hasNext())
			 {
				 queue.add(iteNode.next());
			 }
		 }
		 return total;
	 }
	 
	 public TNode search(LinkedList<String> sList){
	  TNode current = root;
	  
	  while(current != null){
	   for(int i=0;i<sList.size();i++){   
	    if(current.subNode(sList.get(i)) == null)
	     return null;
	    else
	     current = current.subNode(sList.get(i));
	   }
	   /*
	    * This means that a string exists, but make sure its
	    * a word by checking its 'marker' flag
	    */
	   if (current.leaf == true)
	    return current;
	   else
	    return null;
	  }
	  return null;
	 }
	 
	 public void traverse(TNode n)
	 {
		 Collection<TNode> childs = n.getChilds();	
		 if (n.level > 0)
		 {
		     percByLevel[GenericMethods.transform(n.content)-1][n.level-1] =
		     percByLevel[GenericMethods.transform(n.content)-1][n.level-1] + n.freq;
		 }	     
		 
		 Iterator<TNode> iteNode = childs.iterator();
		 while (iteNode.hasNext())
		 {
			 traverse(iteNode.next());
		 }
}
	 
	 
	 
	 public float[][] getPerByLevel()
	 {
		 traverse(root);
		 float[] totalLevel = GenericMethods.sumLevels(percByLevel);
		 
		 for (int i = 0; i < Constants.numLevels; i++)
		 {
			 for (int j = 0; j < Constants.numActions; j++)
			 {
				 percByLevel[j][i] = percByLevel[j][i] / totalLevel[i];
			 }
		 }
		 return percByLevel;
	 }
	 
	 
	 public TNode nexTNode (TNode target)
	 {
		 Collection<TNode> childs = target.getChilds();
		 int maxFreq = 0;
		 		 
		 TNode current = null, nextN = null;
		 
		 Iterator<TNode> iteNode = childs.iterator();
		 
		 while (iteNode.hasNext())
		 {
			 current = iteNode.next();
			 if (current.getFreq() > maxFreq)
			 {
				 maxFreq = current.getFreq();
				 nextN = current;
			 }
		 }
		 return nextN;
	 }
	 
	 public int getDepth()
	 {
		 return depth;
	 }
	 
	 public void save(String url){

		 	Document doc = null;
		 	 try {
			        // Prepare the DOM document for writing
			        Source source = new DOMSource(doc);

			        // Prepare the output file
			        File file = new File(url);
			        Result result = new StreamResult(file);

			        // Write the DOM document to the file
			        Transformer xformer = TransformerFactory.newInstance().newTransformer();
			        xformer.transform(source, result);
			    } catch (TransformerConfigurationException e) {
			  } catch (TransformerException e) {
			 }
	 }
	 
	 //ByLevel representation in a DOM document 
	 public Document getDOM(LinkedList<Integer> fLevel) 
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
	    
		    Element root = (Element)document.createElement("TRIE"); 
		    document.appendChild(root);
		    root.setAttribute("DEPTH", "0");
		   /* root.appendChild( 
		      document.createTextNode("Some") );
		    root.appendChild( 
		      document.createTextNode(" ")    );
		    root.appendChild( 
		      document.createTextNode("text") );*/
		    
		    
			 LinkedList<TNode> queue = new LinkedList<TNode>();
			 queue.add(this.root);
			 while ( !queue.isEmpty()) {
				 TNode n = queue.removeFirst();	
				 float prob =(float)n.getFreq()/(float)(fLevel.get(n.getLevel()));
			
				 Element node = (Element)document.createElement("NODE");
				 root.appendChild(node);
				 node.setAttribute("PROCESS", n.getContent());
				 node.setAttribute("LEVEL", Integer.toString(n.getLevel()));
				 node.setAttribute("PROB", Float.toString(prob));
				 node.setAttribute("FREQ", Float.toString(n.getFreq()));
				    
				 Collection<TNode> childs = n.getChilds();
				 Iterator<TNode> iteNode = childs.iterator();
				 while (iteNode.hasNext())
				 {
					 queue.add(iteNode.next());
				 }
			 }		 
		    return document;
	    }
	    
	/*  
	   protected Document getDOMPre(TNode n)
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
		    
		    Element node = (Element)document.createElement("Node");
			root.appendChild(node);
			document = getDOMPreorder(root, document);
			
	   }
	   
	   protected Document getDOMPreorder(TNode n, Document document)
	   {		   
			node.setAttribute("NAME", n.getContent());
			node.setAttribute("LEVEL", Integer.toString(n.getLevel()));
			node.setAttribute("PROB", Float.toString(prob));
			node.setAttribute("FREQ", Float.toString(n.getFreq()));
			
			 if (n == null)
			 {
				 return null;
			 }
			 
			 Element root = (Element)document.createElement("Trie"); 
			 document.appendChild(root);
			 root.setAttribute("depth", "0");
			 		 
			 Collection<TNode> childs = n.getChilds();
				  
			 Iterator<TNode> iteNode = childs.iterator();
			 while (iteNode.hasNext())
			 {
				 preorderDisplay(iteNode.next());
			 }
			 return document;
		 }
	   */
	   public TNode getRoot(){
		   return this.root;
		   
	   }
}

package es.isoco.trie;

import java.util.Collection;
import java.util.LinkedList;

public class TNode {

	
	String content;
	boolean marker;
	boolean leaf;
	Collection<TNode> child;
	float prob;
	int freq, level;
	
	
	public TNode (String sValue){
		  child = new LinkedList<TNode>();
		  marker = false;
		  leaf = true;
		  content = sValue;	
		  level = 0;
		  prob = 1;
		  freq = 0;
		 }
		  
	public void updateProb()
	{
		if(child!=null){
			for(TNode eachChild:child){
				eachChild.prob = (float)((float)eachChild.freq / (float)this.freq);
			}
		}			
	}
	
	public TNode subNode(String sValue){
	  if(child!=null){
	   for(TNode eachChild:child){
	   if(eachChild.content.equalsIgnoreCase(sValue)){
	     return eachChild;
	   }
	  }
	 }
	 return null;
	}
	
	public Collection<TNode> getChilds()
	{
		return child;
	}
	
	public int getFreq()
	{
		return freq;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setLevel(int nLevel){
		level = nLevel;
	}
	
	public int getLevel()
	{
		return level;
	}
	
}

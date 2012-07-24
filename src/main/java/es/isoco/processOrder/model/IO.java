package es.isoco.processOrder.model;

import java.util.ArrayList;

public class IO {
	private String name;
	private boolean available;
	private ArrayList<Process> outputFrom;
	private ArrayList<Process> inputIn;
	
	public IO(String name){
		this.name=name;
		outputFrom = new ArrayList<Process>();
		inputIn = new ArrayList<Process>();
		available=false;
	}
	
	public boolean isOutput(){
		return outputFrom.size()!=0;
	}
	
	public boolean isInput(){
		return inputIn.size()!=0;
	}
	
	public int getNumOutputs(){
		return outputFrom.size();
	}
	
	public int getNumOInputs(){
		return inputIn.size();
	}
	
	public String getName(){
		return name;
	}
	
	public void setAvailable(boolean b){
		available =b;
	}
	
	public boolean isAvailable(){
		return available;
	}
	
	public void addInputIn(Process p){
		inputIn.add(p);
	}
	
	public void addOutputFrom(Process p){
		outputFrom.add(p);
	}

}

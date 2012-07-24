package es.isoco.processOrder.model;

import java.util.ArrayList;

public class Process {
	
	private String name;
	private String template;
	private Boolean executed;
	private ArrayList<IO> inputs;
	private ArrayList<IO> outputs;
	
	public Process(String name){
		this.name=name;
		template=null;
		inputs = new ArrayList<IO>();
		outputs = new ArrayList<IO>();
		executed=false;	
	}
	
	public void setTemplate(String template){
		this.template=template;
	}
	
	public String getTemplate(){
		return template;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isExecutable(){
		boolean executable=true;
		int i=0;
		while (executable && i<inputs.size()){
			if (!inputs.get(i).isAvailable()) executable=false;
			i++;
		}
		return executable;
	}
	
	public boolean isExecuted(){
		return executed;
	}
	
	public void execute(){
		executed=true;
		for (IO o: outputs)
			o.setAvailable(true);
	}

	public void addInputUsage(IO i) {
		inputs.add(i);
	}
	
	public void addOutputUsage(IO o) {
		outputs.add(o);
	}
	
	public int getNumInputs(){
		return inputs.size();
	}
	
	public int getNumOutputs(){
		return outputs.size();
	}
	
	public String getInputName(int n){
		return inputs.get(n).getName();
	}
	
	public String getOutputName(int n){
		return outputs.get(n).getName();
	}
	

}

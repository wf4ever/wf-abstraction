package es.isoco.processOrder.model;

import java.util.ArrayList;

public class Content {
	
	private ArrayList<Process> listProcess;
	private ArrayList<IO> listIO;
	private ArrayList<Process> executionTrace;
	
	public Content(){
		listProcess=new ArrayList<Process>();
		listIO=new ArrayList<IO>();
		executionTrace=new ArrayList<Process>();
	}
	
	public Process addProcess(Process p,String name){
		boolean found=false;
		int i=0;
		int j=0;
		while (i<listProcess.size() && !found){
			if (listProcess.get(i).getName().equals(name))found=true;
			if (listProcess.get(i).getTemplate().compareTo(p.getTemplate())<0)j++;
			i++;
		}
		if (!found){
			//p=new Process(name);
			listProcess.add(j,p);
		}
		return p;
	}
	
	public void addIO(String name){
		boolean found=false;
		int i=0;
		while (i<listIO.size() && !found){
			if (listIO.get(i).getName().equals(name))found=true;
			i++;
		}
		if (!found){
			IO io=new IO(name);
			listIO.add(io);
		}
	}
	
	public void inicializeContent(){
		for (IO i: listIO){
			if (!i.isOutput()) i.setAvailable(true);
		}
	}
	
	public void addInputUsage(String in, String pr){
		//buscamos el IO
		IO i= this.getIO(in);
		//buscamos el proceso
		Process p=this.getProcess(pr);
		//link
		i.addInputIn(p);
		p.addInputUsage(i);
	}
	
	public void addOutputUsage(String ou, String pr){
		//buscamos el IO
		IO o= this.getIO(ou);
		//buscamos el proceso
		Process p=this.getProcess(pr);
		//link
		o.addOutputFrom(p);
		p.addOutputUsage(o);
	}
	
	private IO getIO(String name){
		boolean found=false;
		int i=0;
		while (i<listIO.size() && !found){
			if (listIO.get(i).getName().equals(name))found=true;
			else
			i++;
		}
		
		return listIO.get(i);
	}
	
	private Process getProcess(String name){
		boolean found=false;
		int i=0;
		while (i<listProcess.size() && !found){
			if (listProcess.get(i).getName().equals(name))found=true;
			else
			i++;
		}
		
		return listProcess.get(i);
	}
	
	public ArrayList<Process> getProcessList(){
		return listProcess;
	}

	public void createTrace() {
		while (remaining()){	
			for(Process p:listProcess){
				if (!p.isExecuted())
					if (p.isExecutable()){
						p.execute();
						executionTrace.add(p);
					}
			}
		}
		
	}

	
	//depurar esta mierda de negacions
	private boolean remaining() {
		boolean remaining=false;
		int i=0;
		while (!remaining && i<listProcess.size()){
			if (!listProcess.get(i).isExecuted()) remaining=true;
			i++;
		}
		return remaining;
	}
	
	
	public void printTrace(){
		int i=1;
		for (Process p:executionTrace){
			System.out.println("Step "+i+":");
			System.out.println("PROCESS: "+p.getName());
			printInputs(p);
			printOutputs(p);
			System.out.println();
			i++;
		}
	}
	
	public String getTrace(){
		String result="<workflow>\n";
		
		for (Process p:executionTrace){
			result = result + ("<process>"+p.getTemplate()+"</process>\n");
		}
		result=result+"</workflow>\n";
		return result;
	}

	private void printOutputs(Process p) {
		int num=p.getNumOutputs();
		System.out.println("OUTPUTS:");
		for (int i=0; i<num; i++){
			System.out.println(p.getOutputName(i));
		}
	}

	private void printInputs(Process p) {
		int num=p.getNumInputs();
		System.out.println("INPUTS:");
		for (int i=0; i<num; i++){
			System.out.println(p.getInputName(i));
		}
	}
	
	public ArrayList<Process> getExecutionTrace(){
		return this.executionTrace;
	}

}

package es.isoco.processOrder.main;


public class Ppal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//RunExample r;
		/////////////////////////////////////
		//NO EXISTE r=new RunExample("http://wings.isi.edu/opmexport/resource/Account/ACCOUNT1319047718714");
		//NO EXISTE r=new RunExample("http://wings.isi.edu/opmexport/resource/Account/ACCOUNT1332336097755");
		/////////////////////////////////////
		
		//r=new RunExample("http://ns.taverna.org.uk/2011/run/479c9612-4862-4bcb-ad09-315b7b864260/");
		//r=new RunExample("http://wings.isi.edu/opmexport/resource/Account/ACCOUNT1332778606534");
		//r=new RunExample("http://wings.isi.edu/opmexport/resource/Account/ACCOUNT1332778615941");
		//UserInterface u= new UserInterface();
		
		//String wfRun1="http://wings.isi.edu/opmexport/resource/Account/ACCOUNT1332778606534";
		//String wfRun2="http://wings.isi.edu/opmexport/resource/Account/ACCOUNT1332778615941";
		//CompareProvenance cp=new CompareProvenance(wfRun1,wfRun2);
			
		//esto era lo de esteban
		RunAll r= new RunAll();
		r.run();
		
	}

}

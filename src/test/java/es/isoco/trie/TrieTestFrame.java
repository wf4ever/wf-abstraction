package es.isoco.trie;

import java.awt.Dimension;
import java.util.Date;
import java.util.LinkedList;
import java.util.StringTokenizer;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
public class TrieTestFrame extends JFrame {
 
 private static final long serialVersionUID = 1L;
  
 private JPanel basePanel = new JPanel();
 private JTextField textField = new JTextField(20);
 private JButton button = new JButton("Check");
 private JButton buttonI = new JButton("Insert");
 
 public TrieTestFrame(){
  designUI();
 }
  
 private void designUI(){
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.setTitle("TRIE Test");
  
   
  button.addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e) {
	
	LinkedList<String> target = new LinkedList<String>();
			
   StringTokenizer tokens = new StringTokenizer(String.valueOf(textField.getText()));
   while(tokens.hasMoreTokens()){			   
		   target.add(tokens.nextToken());			   
   }
     
	//target.add(String.valueOf(textField.getText()));
	//float[][] perc = TrieTest.trieDSA.getPerByLevel();
	
	
	TNode nTarget = TrieTest.trieDSA.search(target); 
	

	if(nTarget != null){
      TNode nNode = TrieTest.trieDSA.nexTNode(nTarget);
      if (nNode != null){
      //JOptionPane.showMessageDialog(basePanel, "The word you entered exist!!" + 
    //		  String.valueOf(nNode.getContent()) + " " + nNode.prob);
      System.out.println("The word you entered exist!!" + String.valueOf(nNode.getContent()) + " " + nNode.prob);
      }
      else{System.out.println("The node does not have next item");}
    }else{
     //JOptionPane.showMessageDialog(basePanel, "The word you entered does not exist!!","Error",JOptionPane.ERROR_MESSAGE);
    	System.out.println("The word you entered does not exist!!");
    }
   }
  
  });
  
  buttonI.addActionListener(new ActionListener() {	  
	   
	  @Override
	  public void actionPerformed(ActionEvent e) {
		   
	  Date start = new java.util.Date();
	  Date end = null;
		   
	   LinkedList<String> target = new LinkedList<String>();
			
	   StringTokenizer tokens = new StringTokenizer(String.valueOf(textField.getText()));
	   while(tokens.hasMoreTokens()){			   
			   target.add(tokens.nextToken());			   
	   }

	   TrieTest.trieDSA.insert(target);
	   TrieTest.trieDSA.display();
	   end = new java.util.Date(); 
	   long diffMils = end.getTime() - start.getTime();
	   long secs = diffMils / 1000;
	   System.out.println("Time:" + secs + " " + diffMils);
  }
  });
   
  
  basePanel.add(textField);
  basePanel.add(button);
  basePanel.add(buttonI);
   
  add(basePanel);
   
  this.setSize(400,100);
   
     Toolkit tk = Toolkit.getDefaultToolkit();
     Dimension screenSize = tk.getScreenSize();
     int screenHeight = screenSize.height;
     int screenWidth = screenSize.width;
     setLocation(screenWidth / 2 - 200, screenHeight / 2 - 50);
   
  this.setVisible(true);
  
 
 }
}
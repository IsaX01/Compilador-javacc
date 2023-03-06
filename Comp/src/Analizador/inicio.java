package Analizador;

import java.awt.EventQueue;
import Analizador.Control;
import Analizador.ParseException;
import Analizador.TokenMgrError;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.io.ByteArrayInputStream;
import java.io.Console;

public class inicio extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;
	private JTextArea textArea_1;
	private JTextArea textArea_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					inicio frame = new inicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public inicio() {
		setType(Type.POPUP);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 492);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("Compilar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				analizar();
				
			}
		});
		
		textArea = new JTextArea();
		textArea.setColumns(25);
		textArea.setRows(24);
		contentPane.add(textArea);
		contentPane.add(btnNewButton);
		
		textArea_1 = new JTextArea();
		textArea_1.setRows(24);
		textArea_1.setColumns(25);
		contentPane.add(textArea_1);
		
		textArea_2 = new JTextArea();
		textArea_2.setRows(24);
		textArea_2.setColumns(25);
		contentPane.add(textArea_2);

		
	}
	
	  void analizar(){
		  
			String textoAnalizar=textArea.getText();
	        ByteArrayInputStream bais=new ByteArrayInputStream(textoAnalizar.getBytes());
	        Control ae=new Control(bais);
	        try {
	        	String text;
	            Control.gramatica();
	      
	            textArea_1.setText("Codigo analizado, sin errores");
	        } catch (ParseException ex) {
	        	textArea_1.setText(ex.getMessage());
	        } catch (TokenMgrError tme){
	        	textArea_1.setText(tme.getMessage());
	        }
	        
	        
	        
	    }
	  
	  

}

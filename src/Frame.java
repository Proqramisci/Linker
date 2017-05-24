import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Frame extends JFrame implements KeyListener, ActionListener {

	private int height = 500;
	private int width = 500;
	private JFrame frame;
	private JPanel panel;
	private JPanel secPanel;
	private JPanel thirdPanel;
	private JTextField fullLinkTextField;
	private JTextArea trimmedLinkTextArea;
	private JButton trimButton;
	private JScrollPane scrollPane;
	
	public Frame(){
	String[] options = {"Tak","Nie"};
	
	frame = new JFrame("Linker");
	frame.setPreferredSize(new Dimension(width, height));
	frame.setMaximumSize(new Dimension(width, height));
	frame.setMinimumSize(new Dimension(width, height));
	frame.setResizable(true);
	frame.setLocationRelativeTo(null);
	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	frame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent event){
			int confirmed = JOptionPane.showOptionDialog(null, "Chcesz zamknąć?",
					"Uwaga",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
				    options, options[0]);
			if(confirmed == JOptionPane.YES_OPTION){
				dispose();
				System.exit(0);
			}
		}}
	);
	
	panel = new JPanel(new BorderLayout());
	secPanel = new JPanel(new BorderLayout());
	thirdPanel = new JPanel(new FlowLayout());
	
	fullLinkTextField = new JTextField("http://www.quadgraphics.pl/tresc");
	fullLinkTextField.addKeyListener(this);
	
	trimButton = new JButton("Tnij!");
	trimButton.addActionListener(this);
	trimButton.addKeyListener(this);
	
	trimmedLinkTextArea = new JTextArea();
	scrollPane = new JScrollPane(trimmedLinkTextArea); 
	trimmedLinkTextArea.setEditable(false);
	trimmedLinkTextArea.addKeyListener(this);

	thirdPanel.add(trimButton, FlowLayout.LEFT);
	secPanel.add(thirdPanel, BorderLayout.NORTH);
	//secPanel.add(trimmedLinkTextArea, BorderLayout.CENTER);
	secPanel.add(scrollPane, BorderLayout.CENTER);
	panel.add(fullLinkTextField, BorderLayout.NORTH);
	panel.add(secPanel, BorderLayout.CENTER);
	
	frame.add(panel);
	frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
			if(event.getSource() == trimButton){
				readHTMLandReturnLinks();
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
			readHTMLandReturnLinks();
		}
	}
	
	public void readHTMLandReturnLinks(){
		trimmedLinkTextArea.setText("");
		try{
			URL url = new URL(fullLinkTextField.getText());
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			 while ((line=in.readLine())!=null){
				 if(Main.containsWWW(line)){
					 line = Main.getLinksFromHTML(line);
					 if(Main.isTextLongEnough(line)){
						 trimmedLinkTextArea.append(line + "\n");
					 }
				 }
			}
			 in.close();
		}catch(Exception e){
			String message;
			String http = "http";
			if(fullLinkTextField.getText().isEmpty()){
				message = "Nie podano URL";
			}
			else if(!fullLinkTextField.getText().contains(http)){
				message = "URL powinien zawierać protokół http";
			}
			else{
				message = "Podano nieprawidłowy link URL lub nie ma połączenia z podaną stroną.";
			}
			JOptionPane.showMessageDialog(frame, message,"Uwaga",JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}

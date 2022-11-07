package ups.edu.ec.vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ups.edu.ec.modelo.Receptor;
import ups.edu.ec.modelo.Remitente;

public class VistaRemitenteReceptor {

	private JFrame frame;
	private JTextArea msgReceivedLeft;
	private JTextField textSendLeft;
	private JTextField textSendLeft1;
	private JTextArea msgReceivedRight;
	private JTextField textSendRight;
	private JTextField textSendRight1;
	private JButton btnSendRight;

	// Init Sender and Receiver

	Remitente senderLeft = new Remitente("sentMsgQueue");
	Remitente senderRight = new Remitente("recMsgQueue");

	Receptor recieverLeft = new Receptor("recMsgQueue");
	Receptor recieverRight = new Receptor("sentMsgQueue");
	Thread tRecieverLeft = new Thread(recieverLeft);
	Thread tRecieverRight = new Thread(recieverRight);

	// Queue sender for Left ->> recMsgQueue <<- Receive queue for right
	// Queue sender for Right ->> sentMsgQueue <<- Receive queue for left

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaRemitenteReceptor window = new VistaRemitenteReceptor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VistaRemitenteReceptor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 701, 385);
		frame.getContentPane().setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textSendLeft1 = new JTextField();
		textSendLeft1.setText("Remitente: Bryan");
		textSendLeft1.setEditable(false);
		textSendLeft1.setBounds(10, 5, 308, 20);
		textSendLeft1.setBackground(Color.GREEN);
		frame.getContentPane().add(textSendLeft1);
		textSendLeft1.setColumns(10);
		
		textSendLeft = new JTextField();
		textSendLeft.setBounds(10, 30, 308, 20);
		textSendLeft.setBackground(Color.GREEN);
		frame.getContentPane().add(textSendLeft);
		textSendLeft.setColumns(10);
		
		msgReceivedLeft = new JTextArea();
		msgReceivedLeft.setEditable(false);
		msgReceivedLeft.setBounds(10, 85, 308, 248);
		msgReceivedLeft.setBackground(Color.GREEN);
		frame.getContentPane().add(msgReceivedLeft);
		msgReceivedLeft.setColumns(10);

		

		JButton btnSendLeft = new JButton("Enviar");
		btnSendLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Left Sender
				String msg = textSendLeft.getText();
				if (msg != "" || msg != null) {
					senderLeft.enviarMensaje(msg);
					msgReceivedLeft.setText(msgReceivedLeft.getText() +"\n"+ msg +" ==> Braulio"
							);
					textSendLeft.setText("");
				}
			}
		});
		btnSendLeft.setBounds(121, 55, 89, 23);
		frame.getContentPane().add(btnSendLeft);

		textSendRight1 = new JTextField();
		textSendRight1.setText("Receptor: Braulio");
		textSendRight1.setEditable(false);
		textSendRight1.setColumns(10);
		textSendRight1.setBounds(367, 5, 308, 20);
		textSendRight1.setBackground(Color.CYAN);
		frame.getContentPane().add(textSendRight1);
		
		textSendRight = new JTextField();
		textSendRight.setColumns(10);
		textSendRight.setBounds(367, 30, 308, 20);
		textSendRight.setBackground(Color.CYAN);
		frame.getContentPane().add(textSendRight);
		
		msgReceivedRight = new JTextArea();
		msgReceivedRight.setEditable(false);
		msgReceivedRight.setColumns(10);
		msgReceivedRight.setBounds(367, 85, 308, 248);
		msgReceivedRight.setBackground(Color.CYAN);
		frame.getContentPane().add(msgReceivedRight);

		

		btnSendRight = new JButton("Enviar");
		btnSendRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Right Sender
				String msg = textSendRight.getText();
				if (msg != "" || msg != null) {
					senderRight.enviarMensaje(msg);
					msgReceivedRight.setText(msgReceivedRight.getText()
							+"\n"+msg+ " ==> Bryan");
					textSendRight.setText("");
				}
			}
		});
		btnSendRight.setBounds(478, 55, 89, 23);
		frame.getContentPane().add(btnSendRight);

		// Init Callback Asynchronous functions
		try {
			recieverLeft.receptor.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message msg) {
					TextMessage tmsg = (TextMessage) msg;
					try {
						msgReceivedLeft.setText(msgReceivedLeft.getText()
								+"\n"+tmsg.getText()+ " <== Braulio" );
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});

			recieverRight.receptor.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message msg) {
					TextMessage tmsg = (TextMessage) msg;
					try {
						msgReceivedRight.setText(msgReceivedRight.getText()
								+"\n"+tmsg.getText()+ " <== Bryan");
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
	}

}

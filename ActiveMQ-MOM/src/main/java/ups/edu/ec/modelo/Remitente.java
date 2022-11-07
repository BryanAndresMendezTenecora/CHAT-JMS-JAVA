package ups.edu.ec.modelo;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Remitente {
	QueueConnection con = null;
	QueueSession session = null;
	QueueSender remitente = null;
	private String logicalTestQ;

	public Remitente(String logicalTestQ) {
		super();
		this.setLogicalTestQ(logicalTestQ);
		InitialContext context;
		QueueConnectionFactory factory;
		try {
			context = new InitialContext();
			factory = (QueueConnectionFactory) context
					.lookup("QueueConnectionFactory");
			Queue queue = (Queue) context.lookup(logicalTestQ);
			con = factory.createQueueConnection();
			session = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			remitente = session.createSender(queue);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public Remitente() {
		super();

	}

	public void enviarMensaje(String msg) {
		try {
			TextMessage message = session.createTextMessage();
			message.setText(msg);
			remitente.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	public void close() {
		try {
			remitente.close();
			session.close();
			con.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	public String getLogicalTestQ() {
		return logicalTestQ;
	}

	public void setLogicalTestQ(String logicalTestQ) {
		this.logicalTestQ = logicalTestQ;
	}
}

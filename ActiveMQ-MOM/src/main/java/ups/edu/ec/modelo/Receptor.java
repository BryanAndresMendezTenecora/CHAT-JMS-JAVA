package ups.edu.ec.modelo;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Receptor implements Runnable{
	public QueueReceiver receptor = null;
	private String queueName;

	public QueueReceiver getReceiver() {
		return receptor;
	}

	public void setReceiver(QueueReceiver receiver) {
		this.receptor = receiver;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public Receptor() {
		super();
	}

	public Receptor(String queueName) {
		super();
		this.queueName = queueName;
		QueueConnection con = null;
		QueueSession session = null;

		try {
			InitialContext context = new InitialContext();
			QueueConnectionFactory factory = (QueueConnectionFactory) context
					.lookup("QueueConnectionFactory");
			Queue queue = (Queue) context.lookup(queueName);
			con = factory.createQueueConnection();
			session = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			receptor = session.createReceiver(queue);

			con.start();
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			
			
		}
	}

	@Override
	public void run() {
		while (true) {
			// keep Recieving
		}
		/*
		 * try {
				receiver.close();
				session.close();
				con.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		 */
	}


}

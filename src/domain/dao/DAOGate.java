package domain.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.Gate;
import domain.model.Lane;
import hibernate.HibernateConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOGate.
 */
public class DAOGate {
	
	/** The session. */
	@SuppressWarnings("unused")
	private static Session session;
	
	private static final String PARAMETER_TERMINAL_ID = "terminalId";

	
	private static final String QUERY_FREE_GATES = "select g from Gate as g join g.terminal as t "
			+ "where g.free is true and t.id = :" + PARAMETER_TERMINAL_ID;
	


	public static List<Gate> loadFreeGatesFromTerminal(int terminalId) {
		List<Gate> gateList = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_FREE_GATES);
			query.setParameter(PARAMETER_TERMINAL_ID, terminalId);
			if (query.getResultList().size() > 0) {
				gateList = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return gateList;
	}

}

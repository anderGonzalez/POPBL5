package domain.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.Gate;
import hibernate.HibernateConnection;

/**
 * The Class DAOGate.
 */
public class DAOGate {

	/** The Constant QUERY_GATES_FROM_TERMINAL_FROM_AIRPORT. */
	private static final String QUERY_GATES_FROM_TERMINAL_FROM_AIRPORT = "from Gate as g where g.terminal.airport.id = :";

	/** The session. */
	private static Session session;

	/** The Constant PARAMETER_TERMINAL_ID. */
	private static final String PARAMETER_TERMINAL_ID = "terminalId";

	/** The Constant QUERY_FREE_GATES. */
	private static final String QUERY_FREE_GATES = "select g from Gate as g join g.terminal as t "
			+ "where g.free is true and t.id = :" + PARAMETER_TERMINAL_ID;

	/** The Constant PARAMETER_AIRPORT_ID. */
	private static final String PARAMETER_AIRPORT_ID = "airportId";

	/** The Constant LOAD_TABLE_GATES. */
	private static final String LOAD_TABLE_GATES = QUERY_GATES_FROM_TERMINAL_FROM_AIRPORT
			+ PARAMETER_AIRPORT_ID + " order by g.";

	/** The Constant LOAD_ALL_GATES. */
	private static final String LOAD_ALL_GATES = QUERY_GATES_FROM_TERMINAL_FROM_AIRPORT
			+ PARAMETER_AIRPORT_ID;

	/** The Constant PARAMETER_POSX. */
	private static final String PARAMETER_POSX = "posX";

	/** The Constant PARAMETER_POSY. */
	private static final String PARAMETER_POSY = "posY";
	
	/** The Constant QUERY_GATE_FROM_POSITION. */
	private static final String QUERY_GATE_FROM_POSITION = "from Gate as g where g.positionNode.positionX = :"
			+ PARAMETER_POSX + " and g.positionNode.positionY = :" + PARAMETER_POSY;

	/**
	 * Load free gates from terminal.
	 *
	 * @param terminalId the terminal id
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public static List<Gate> loadFreeGatesFromTerminal(int terminalId) {
		List<Gate> gateList = null;
		try {

			session = HibernateConnection.getSession();
			Query query = session.createQuery(QUERY_FREE_GATES);
			query.setParameter(PARAMETER_TERMINAL_ID, terminalId);
			if (query.getResultList().size() > 0) {
				gateList = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			HibernateConnection.closeSession(session);
		}
		return gateList;
	}

	/**
	 * Load gates for table.
	 *
	 * @param airportId the airport id
	 * @param orderCol the order col
	 * @param orderDir the order dir
	 * @param start the start
	 * @param length the length
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public static List<Gate> loadGatesForTable(int airportId, String orderCol, String orderDir, int start, int length) {
		List<Gate> gateList = null;
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_TABLE_GATES + orderCol + " " + orderDir);
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);
			if (query.getResultList().size() > 0) {
				query.setFirstResult(start);
				query.setMaxResults(length);
				gateList = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		return gateList;
	}

	/**
	 * Load all gates from airport.
	 *
	 * @param airportId the airport id
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public static List<Gate> loadAllGatesFromAirport(int airportId) {
		List<Gate> gateList = null;
		try {
			session  = HibernateConnection.getSession();

			Query query = session.createQuery(LOAD_ALL_GATES);
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);
			if (query.getResultList().size() > 0) {
				gateList = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		return gateList;
	}
	
	/**
	 * Gets the node from pos X pos Y.
	 *
	 * @param posX the pos X
	 * @param posY the pos Y
	 * @return the node from pos X pos Y
	 */
	public static Gate getNodeFromPosXPosY(double posX, double posY) {
		Gate gate = null;
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(QUERY_GATE_FROM_POSITION);
			query.setParameter(PARAMETER_POSX, posX);
			query.setParameter(PARAMETER_POSY, posY);
			if (query.getResultList().size() > 0) {
				gate = (Gate) query.getResultList().get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}
		return gate;
	}
	
	

}

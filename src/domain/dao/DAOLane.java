package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Lane;
import hibernate.HibernateConnection;

public class DAOLane {
	private static Session session;

	public static boolean insertLane(Lane lane) {
		boolean result = true;
		try {
			
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.save(lane);
			session.getTransaction().commit();

		} catch (Exception e) {

			session.getTransaction().rollback();
			result = false;
		}

		return result;

	}

	public static boolean deleteLane(Lane lane) {
		boolean result = true;
		try {
			
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.delete(lane);
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			result = false;
		} finally {
			
		}

		return result;
	}

	public static List<Lane> loadAllLanes() {
		List<Lane> laneList = null;
		try {
			
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<Lane> query = session.createQuery("from Lane");
			laneList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return laneList;
	}


}

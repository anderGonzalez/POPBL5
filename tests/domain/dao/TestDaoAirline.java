package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import domain.model.Airline;
import domain.model.Delay;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.PlaneMovement;
import domain.model.User;

public class TestDaoAirline {

	private static final String NARANAIR = "Naranair";
	private static final String ERROR_LOAD = "Error load all airlines from database";
	private static final String INSERT_ERROR = "Error insert airline into database";
	private static final String REMOVE_ERROR = "Error removing one airline from database";
	private static final String USERNAME = "naranair";
	private static final String PASSWORD = "Nestor123";

	@Test
	public void testInsertAirlineWithoutRoutes() {
		Airline airline = initAirline();
		deleteAllUsers();

		boolean result = HibernateGeneric.insertObject(airline);
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testInsertAirlineWithRoutes() {

		assertEquals(INSERT_ERROR, true, HibernateGeneric.insertObject(initCompleteAirline()));
	}

	@Test
	public void testLoadAllAirlines() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Airline()));

	}

	@Test
	public void testRemoveOneSpecificAirline() {
		/* Create airline */

		HibernateGeneric.insertObject(initCompleteAirline());
		Airline airline = (Airline) HibernateGeneric.loadAllObjects(new Airline()).get(0);
		boolean result = HibernateGeneric.deleteObject(airline);
		assertEquals(REMOVE_ERROR, true, result);
	}

	private Airline initCompleteAirline() {
		deleteAllPlaneMovements();
		deleteAllDelays();
		deleteAllFlights();
		deleteAllPlanes();
		deleteAllUsers();

		Airline airline = initAirline();
		DAOUser.deleteUserWithUsername(airline);

		return airline;
	}

	public static Airline initAirline() {
		Airline airline = new Airline();
		airline.setName(NARANAIR);
		airline.setUsername(USERNAME);
		airline.setPassword(PASSWORD);
		airline.setBirthDate(new Date());
		return airline;

	}

	private void deleteAllFlights() {
		List<Object> listFlight = HibernateGeneric.loadAllObjects(new Flight());
		for (Object flight : listFlight) {
			HibernateGeneric.deleteObject((Flight) flight);
		}
	}

	private void deleteAllUsers() {
		List<Object> listUser = HibernateGeneric.loadAllObjects(new User());
		for (Object user : listUser) {
			HibernateGeneric.deleteObject((User) user);
		}
	}

	private void deleteAllPlanes() {
		List<Object> listPlanes = HibernateGeneric.loadAllObjects(new Plane());
		for (Object plane : listPlanes) {
			HibernateGeneric.deleteObject((Plane) plane);
		}
	}

	private void deleteAllDelays() {
		List<Object> listDelays = HibernateGeneric.loadAllObjects(new Delay());
		for (Object delay : listDelays) {
			HibernateGeneric.deleteObject((Delay) delay);
		}
	}

	private void deleteAllPlaneMovements() {
		List<Object> planeMovementList = HibernateGeneric.loadAllObjects(new PlaneMovement());
		for (Object planeMovements : planeMovementList) {
			HibernateGeneric.deleteObject((PlaneMovement) planeMovements);
		}
	}

}

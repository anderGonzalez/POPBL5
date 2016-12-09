package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Airport;
import domain.model.City;
import domain.model.Gate;
import domain.model.Route;
import domain.model.State;
import domain.model.Terminal;

public class TestDaoRoute {

	private static final String ERROR_LOAD = "Error load all routes from database";
	private static final String INSERT_ERROR = "Error insert route into database";
	private static final String REMOVE_ERROR = "Error removing one route from database";

	@Test
	public void testInsertRouteWithoutGateIntoDB() {
		Route route = new Route();
		boolean result = HibernateGeneric.insertObject(route);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertRouteWithGatesIntoDB() {

		boolean result = HibernateGeneric.insertObject(initCompleteRoute());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllRoutes() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Route()));

	}

	@Test
	public void testRemoveOneSpecificRoute() {

		HibernateGeneric.insertObject(initCompleteRoute());
		// TODO Hemen gero loadAll biharrian load bakarra einbiko litzake
		boolean result = HibernateGeneric.deleteObject((Route) HibernateGeneric.loadAllObjects(new Route()).get(0));
		assertEquals(REMOVE_ERROR, true, result);
	}

	@Test
	public void testGetListOfArrivalRoutesOfAirportByAirportId() {

		State state = TestDaoState.initState();
		HibernateGeneric.insertObject(state);

		City city = TestDaoCity.initCity(state);
		HibernateGeneric.insertObject(city);

		Airport airport = TestDaoAirport.initAirport(city);
		HibernateGeneric.insertObject(airport);

		Terminal terminal = TestDaoTerminal.initTerminal(airport);
		HibernateGeneric.insertObject(terminal);

		Gate gate = TestDaoGate.initGate(terminal);
		HibernateGeneric.insertObject(gate);

		Route expectedRoute = initRoute(gate, gate);
		HibernateGeneric.insertObject(expectedRoute);

		Route actualRoute = HibernateGeneric.getRandomArrivalRouteFromAirport(airport.getId()).get(0);

		assertEquals(expectedRoute.getId(), actualRoute.getId());

	}

	private Route initCompleteRoute() {

		State state = TestDaoState.initState();
		HibernateGeneric.insertObject(state);

		City city = TestDaoCity.initCity(state);
		HibernateGeneric.insertObject(city);

		Airport airport = TestDaoAirport.initAirport(city);
		HibernateGeneric.insertObject(airport);

		Terminal terminal = TestDaoTerminal.initTerminal(airport);
		HibernateGeneric.insertObject(terminal);

		Gate gate = TestDaoGate.initGate(terminal);
		HibernateGeneric.insertObject(gate);

		return initRoute(gate, gate);
	}

	public static Route initRoute(Gate arrivalGate, Gate departureGate) {
		Route route = new Route();
		route.setArrivalGate(arrivalGate);
		route.setDepartureGate(departureGate);
		return route;
	}

}

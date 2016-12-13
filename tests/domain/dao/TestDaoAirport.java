package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Address;
import domain.model.Airport;

public class TestDaoAirport {

	private static final String ERROR_LOAD = "Error load all airports from database";
	private static final String INSERT_ERROR = "Error insert airport into database";

	@Test
	public void testInsertAirportWithoutCityAndTerminalIntoDB() {
		Airport airport = Initializer.initAirport();
		boolean result = HibernateGeneric.saveOrUpdateObject(airport);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertAirportWithCityAndWithoutTerminalIntoDB() {

		Address address = Initializer.initAddress();
		HibernateGeneric.saveOrUpdateObject(address);

		Airport airport = Initializer.initAirport(address);
		boolean result = HibernateGeneric.saveOrUpdateObject(airport);
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testInsertAirportWithTerminalAndWithCityIntoDB() {

		boolean result = HibernateGeneric.saveOrUpdateObject(Initializer.initCompleteAirport());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllAirports() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Airport()));

	}

	
}

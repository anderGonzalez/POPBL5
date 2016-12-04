package domain.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import domain.model.City;
import domain.model.State;

public class TestDaoCity {

	private static final String EUSKAL_HERRIA = "Euskal herria";
	private static final String ERROR_LOAD = "Error load all cities from database";
	private static final String BERGARA = "Bergara";
	private static final String INSERT_ERROR = "Error insert city into database";
	private static final String REMOVE_ERROR = "Error removing one city from database";

	@Test
	public void testInsertCityWithoutStateIntoDB() {
		City city = new City();
		city.setName(BERGARA);
		boolean result = HibernateGeneric.insertObject(city);
		assertEquals(INSERT_ERROR, false, result);
	}

	@Test
	public void testInsertCityWithStateIntoDB() {

		boolean result = HibernateGeneric.insertObject(initCompleteCity());
		assertEquals(INSERT_ERROR, true, result);
	}

	@Test
	public void testLoadAllCities() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new City()));

	}

	@Test
	public void testRemoveOneSpecificCity() {

		HibernateGeneric.insertObject(initCompleteCity());
		boolean result = HibernateGeneric.deleteObject((City) HibernateGeneric.loadAllObjects(new City()).get(0));

		assertEquals(REMOVE_ERROR, true, result);
	}

	private City initCompleteCity() {
		State state = new State();
		state.setName(EUSKAL_HERRIA);
		HibernateGeneric.insertObject(state);

		City city = new City();
		city.setName(BERGARA);
		city.setState(state);

		return city;
	}
}

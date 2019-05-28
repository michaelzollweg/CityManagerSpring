package at.fh.swenga.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class CityService {

	private List<CityModel> cities = new ArrayList<CityModel>();

	/**
	 * Add city to List
	 * 
	 * @param city
	 */
	public void addCity(CityModel city) {
		cities.add(city);
	}

	/**
	 * Verify if list contains city with same id
	 * 
	 * @param city
	 * @return
	 */
	public boolean contains(CityModel city) {
		return cities.contains(city);
	}

	/**
	 * convenient method: true if list is empty
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return cities.isEmpty();
	}

	/**
	 * try to find CityModel with given id return model, otherwise null
	 * 
	 * @param id
	 * @return
	 */
	public CityModel getCityByID(int id) {

		for (CityModel cityModel : cities) {
			if (cityModel.getId() == id) {
				return cityModel;
			}
		}
		return null;
	}

	/**
	 * return list with all cities
	 * 
	 * @return
	 */
	public List<CityModel> getAllCities() {
		return cities;
	}

	/**
	 * return a new list with all cities where name or country contains search
	 * string
	 * 
	 * @param searchString
	 * @return
	 */
	public List<CityModel> getFilteredCities(String searchString) {

		if (searchString == null || searchString.equals("")) {
			return cities;
		}

		// List for results
		List<CityModel> filteredList = new ArrayList<CityModel>();

		// check every city
		for (CityModel cityModel : cities) {

			if ((cityModel.getName() != null && cityModel.getName().contains(searchString))
					|| (cityModel.getCountry() != null && cityModel.getCountry().contains(searchString))) {
				filteredList.add(cityModel);
			}
		}
		return filteredList;
	}

	/**
	 * remove cities with same id
	 * 
	 * @param id
	 * @return
	 */
	public boolean remove(int id) {
		return cities.remove(new CityModel(id, null, null, null, 0));
	}
}
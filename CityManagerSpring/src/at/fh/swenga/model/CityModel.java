package at.fh.swenga.model;

public class CityModel {

	private int id;

	private String name;

	private String country;

	private String state;

	private int population;

	public CityModel() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public CityModel(int id, String name, String country, String state, int population) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
		this.state = state;
		this.population = population;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CityModel other = (CityModel) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}

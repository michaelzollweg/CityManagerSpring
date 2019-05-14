package at.fh.swenga.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.model.CityModel;
import at.fh.swenga.model.CityService;

@Controller
public class CityManagerController {

	@Autowired
	private CityService cityService;

	@RequestMapping(value = { "/", "listCities" })
	public String showAllCities(Model model) {
		model.addAttribute("cities", cityService.getAllCities());
		return "listEmployees";
	}

	@RequestMapping("/fillCityList")
	public String fillCityList(Model model) {

		cityService.addCity(new CityModel(1, "Graz", "Austria", "Styria", 800000));
		cityService.addCity(new CityModel(2, "St.Johann", "Austria", "Salzburg", 12000));
		cityService.addCity(new CityModel(3, "New York", "USA", "New York", 19000000));

		model.addAttribute("cities", cityService.getAllCities());
		return "listCities";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}

	// Spring 4: @RequestMapping(value = "/deleteCity", method = RequestMethod.GET)
	@GetMapping("/deleteCity")
	public String delete(Model model, @RequestParam int id) {
		boolean isRemoved = cityService.remove(id);

		if (isRemoved) {
			model.addAttribute("warningMessage", "City " + id + " deleted");
		} else {
			model.addAttribute("errorMessage", "There is no City " + id);
		}

		// Multiple ways to "forward"
		// return "forward:/listCities";
		return showAllCities(model);
	}

	// Spring 4: @RequestMapping(value = "/searchCities", method =
	// RequestMethod.POST)
	@PostMapping("/searchCities")
	public String search(Model model, @RequestParam String searchString) {
		model.addAttribute("cities", cityService.getFilteredCities(searchString));
		return "listCities";
	}

	// Spring 4: @RequestMapping(value = "/addCity", method = RequestMethod.GET)
	@GetMapping("/addCity")
	public String showAddCityForm(Model model) {
		return "editCity";
	}
	
	// Spring 4: @RequestMapping(value = "/addCity", method = RequestMethod.POST)
	@PostMapping("/addCity")
	public String addCity(@Valid CityModel newCityModel, BindingResult bindingResult,
			Model model) {

		// Any errors? -> Create a String out of all errors and return to the page
		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid: "+fieldError.getCode()+"<br>";
			}
			model.addAttribute("errorMessage", errorMessage);

			// Multiple ways to "forward"
			return "forward:/listCities";
		}

		// Look for city in the List. One available -> Error 
		CityModel city = cityService.getCityByID(newCityModel.getId());

		if (city != null) {
			model.addAttribute("errorMessage", "City already exists!<br>");
		} else {
			cityService.addCity(newCityModel);
			model.addAttribute("message", "New city " + newCityModel.getId() + " added.");
		}

		return "forward:/listCities";
	}
	
	// Spring 4: @RequestMapping(value = "/editCity", method = RequestMethod.GET)
	@GetMapping("/editCity")
	public String showChangeCityForm(Model model, @RequestParam int id) {
 
		CityModel city = cityService.getCityByID(id);
 
		if (city != null) {
			model.addAttribute("city", city);
			return "editCity";
		} else {
			model.addAttribute("errorMessage", "Couldn't find city " + id);
			return "forward:/listCities";
		}
	}
	
	// Spring 4: @RequestMapping(value = "/editCity", method = RequestMethod.POST)
	@PostMapping("/editCity")
	public String editCity(@Valid CityModel changedCityModel, BindingResult bindingResult,
			Model model) {

		// Any errors? -> Create a String out of all errors and return to the page
		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid: "+fieldError.getCode()+"<br>";
			}
			model.addAttribute("errorMessage", errorMessage);
			return "forward:/listCities";
		}

		// Get the city we want to change
		CityModel city = cityService.getCityByID(changedCityModel.getId());

		if (city == null) {
			model.addAttribute("errorMessage", "City does not exist!<br>");
		} else {
			// Change the attributes
			city.setId(changedCityModel.getId());
			city.setName(changedCityModel.getName());
			city.setCountry(changedCityModel.getCountry());
			city.setState(changedCityModel.getState());
			city.setPopulation(changedCityModel.getPopulation());

			// Save a message for the web page
			model.addAttribute("message", "Changed city " + changedCityModel.getId());
		}

		return "forward:/listCities";
	}
}

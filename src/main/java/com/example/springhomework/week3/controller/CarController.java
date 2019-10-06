package com.example.springhomework.week3.controller;

import com.example.springhomework.week3.model.Car;
import com.example.springhomework.week3.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cars")
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public String getAllCars(Model model) {
        model.addAttribute("title", "Car - Rest API");
        model.addAttribute("cars", carService.getAllCars());
        return "cars";
    }

    @GetMapping(value = "/{carId}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    public String showCarItem(@PathVariable("carId") Long carId, Model model) {
        model.addAttribute("title", "Car - Rest API - opcje");
        model.addAttribute("car", carService.getCarById(carId));
        return "options";
    }

    @GetMapping(value = "/color",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    public String showCarsByColor(@RequestParam("color") String color, Model model) {
        if (color.isEmpty()) {
            model.addAttribute("info", "Parametr kolor jest wymagany");
            return "cars";
        } else {
            model.addAttribute("cars", carService.getCarsByColor(color));
            return "cars";
        }
    }


    @GetMapping("/add")
    public String getPageForAddNewCar(Model model) {
        model.addAttribute("title", "Car - Rest API - dodawanie auta");
        model.addAttribute("car", new Car());
        model.addAttribute("carId", carService.getNextCarId());
        return "add";
    }

    @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public String addNewCar(@ModelAttribute("car") Car car) {
        if (carService.addCar(car)) {
            return "redirect:/cars";
        } else {
            return "add";
        }
    }

    @GetMapping(value = "/edit/{carId}", produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public String getPageForModifyCar(@PathVariable("carId") Long carId, Model model) {
        model.addAttribute("title", "Car - Rest API - edycja auta");
        model.addAttribute("modifyCar", carService.getCarById(carId));
        return "edit";
    }

    @PutMapping(produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public String modifyCar(@ModelAttribute("modifyCar") Car modifyCar) {
        if (carService.getCarAndModifyHim(modifyCar)) {
            return "redirect:/cars";
        } else {
            return "cars";
        }
    }

    @GetMapping(value = "/color/{carId}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    public String getPageForChangeColorOfCar(@PathVariable("carId") Long carId, Model model) {
        model.addAttribute("title", "Car - Rest API - edycja koloru auta");
        model.addAttribute("modifyCar", carService.getCarById(carId));
        return "edit-color";
    }

    @PatchMapping(value = "/{carId}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    public String modifyColorOfCar(@PathVariable("carId") Long carId, @RequestParam("color") String newCarColor) {
        if (carService.getCarAndModifyHisColor(newCarColor, carId)) {
            return "redirect:/cars/" + carId;
        } else {
            return "cars/color/" + carId;
        }
    }

    @GetMapping(value = "/delete/{carId}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    public String deleteCar(@PathVariable("carId") Long carId) {
        if (carService.deleteCar(carId)) {
            return "redirect:/cars";
        } else {
            return "cars/" + carId;
        }
    }
}

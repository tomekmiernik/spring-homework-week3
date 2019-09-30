package com.example.springhomework.week3.controller;

import com.example.springhomework.week3.model.Car;
import com.example.springhomework.week3.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> allCars = carService.getAllCars();
        return new ResponseEntity<>(allCars, HttpStatus.OK);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable("carId") Long carId) {
        Optional<Car> car = carService.getCarById(carId);
        return car.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/color")
    public ResponseEntity<List<Car>> getCarByColor(@RequestParam String color) {
        List<Car> cars = carService.getCarsByColor(color);
        if (cars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity addNewCar(@RequestBody Car car) {
        if (carService.addCar(car)){
            return new ResponseEntity(HttpStatus.CREATED);
        }else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity modifyCar(@RequestBody Car modifyCar){
        if (carService.getCarAndModifyHim(modifyCar)) {
            return new ResponseEntity(HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{carId}")
    public ResponseEntity modifyFieldOfCar(@RequestParam("color") String newCarColor,
                                           @PathVariable("carId") Long carId){
        if (carService.getCarAndModifyHim(newCarColor,carId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity removeCar(@PathVariable Long carId){
        if (carService.deleteCar(carId)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}

package com.example.springhomework.week3.service;

import com.example.springhomework.week3.model.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private
    List<Car> carList;


    public CarService() {
        carList = new ArrayList<>();
        init();
    }

    private void init() {
        carList.add(new Car(1L, "Opel", "Astra", "czerwony"));
        carList.add(new Car(2L, "Ford", "Kuga", "niebieski"));
        carList.add(new Car(3L, "Skoda", "Superb", "zielony"));
    }


    public List<Car> getAllCars() {
        return carList;
    }

    public Optional<Car> getCarById(Long carId) {
        return carList.stream().filter(car -> car.getId().equals(carId)).findFirst();
    }

    public List<Car> getCarsByColor(String color) {
        return carList.stream()
                .filter(car -> car.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public boolean addCar(Car car) {
        return carList.add(car);
    }

    public boolean getCarAndModifyHim(Car modifyCar) {
        Optional<Car> car = carList.stream()
                .filter(c-> c.getId().equals(modifyCar.getId()))
                .findFirst();
        if(car.isPresent()){
            carList.remove(car.get());
            carList.add(modifyCar);
            return true;
        }else {
            return false;
        }
    }

    public boolean getCarAndModifyHim(String newCarColor, Long carId) {
        Optional<Car> car = carList.stream()
                .filter(c-> c.getId().equals(carId))
                .findFirst();
        if (car.isPresent()){
            car.get().setColor(newCarColor);
            carList.remove(car.get());
            carList.add(car.get());
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteCar(Long carId) {
        Optional<Car> car = carList.stream().filter(c-> c.getId().equals(carId)).findFirst();
        if(car.isPresent()){
            carList.remove(car.get());
            return true;
        }else {
            return false;
        }
     }
}

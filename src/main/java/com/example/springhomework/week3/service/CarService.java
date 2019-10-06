package com.example.springhomework.week3.service;

import com.example.springhomework.week3.model.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        carList.add(new Car(1L, "Opel", "Astra", "czarny"));
        carList.add(new Car(2L, "Opel", "Insignia", "niebieski"));
        carList.add(new Car(3L, "Opel", "Corsa", "zielony"));
        carList.add(new Car(4L, "Skoda", "Superb", "biały"));
        carList.add(new Car(5L, "Skoda", "Fabia", "grafitowy"));
        carList.add(new Car(6L, "Skoda", "Octavia", "czerwony"));
        carList.add(new Car(7L, "Ford", "Focus", "czarny"));
        carList.add(new Car(8L, "Ford", "Kuga", "szary"));
        carList.add(new Car(9L, "Ford", "Mondeo", "zielony"));
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
        autoIncrementCarId(car);
        return carList.add(car);
    }

    public boolean getCarAndModifyHim(Car modifyCar) {
        Optional<Car> car = carList.stream()
                .filter(c -> c.getId().equals(modifyCar.getId()))
                .findFirst();
        if (car.isPresent()) {
            car.get().setMark(modifyCar.getMark());
            car.get().setModel(modifyCar.getModel());
            car.get().setColor(modifyCar.getColor());
            carList.remove(car.get());
            carList.add(modifyCar);
            return true;
        } else {
            return false;
        }
    }

    public boolean getCarAndModifyHisColor(String newCarColor, Long carId) {
        Optional<Car> car = carList.stream()
                .filter(c -> c.getId().equals(carId))
                .findFirst();
        if (car.isPresent()) {
            car.get().setColor(newCarColor);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteCar(Long carId) {
        Optional<Car> car = carList.stream()
                .filter(c -> c.getId().equals(carId))
                .findFirst();
        if (car.isPresent()) {
            carList.remove(car.get());
            return true;
        } else {
            return false;
        }
    }

    private void autoIncrementCarId(Car car) {
        long lastCarId = carList.get(carList.size()-1).getId();
        if(lastCarId > carList.size()){
            car.setId(lastCarId + 1);
        }
    }

    public long getNextCarId() {
        return Long.parseLong(String.valueOf(carList.size()+1));
    }
}

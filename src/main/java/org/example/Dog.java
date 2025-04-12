package org.example;
import java.time.LocalDate;

public class Dog extends HomeAnimal {
    public Dog(int id, String name, LocalDate birthDate) {
        super(id, name, birthDate);
    }

    @Override
    public String getType() {
        return "Собака";
    }
}
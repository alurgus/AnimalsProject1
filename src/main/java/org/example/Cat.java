package org.example;
import java.time.LocalDate;

public class Cat extends HomeAnimal {
    public Cat(int id, String name, LocalDate birthDate) {
        super(id, name, birthDate);
    }

    @Override
    public String getType() {
        return "Кошка";
    }
}

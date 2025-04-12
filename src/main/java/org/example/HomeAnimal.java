package org.example;
import java.time.LocalDate;

public abstract class HomeAnimal extends Animal {
    public HomeAnimal(int id, String name, LocalDate birthDate) {
        super(id, name, birthDate);
    }
}
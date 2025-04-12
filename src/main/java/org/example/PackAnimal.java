package org.example;
import java.time.LocalDate;

public abstract class PackAnimal extends Animal {
    public PackAnimal(int id, String name, LocalDate birthDate) {
        super(id, name, birthDate);
    }
}
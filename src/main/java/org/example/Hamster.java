package org.example;
import java.time.LocalDate;

public class Hamster extends HomeAnimal {
    public Hamster(int id, String name, LocalDate birthDate) {
        super(id, name, birthDate);
    }

    @Override
    public String getType() {
        return "Хомяк";
    }
}

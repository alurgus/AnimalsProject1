package org.example;
import java.time.LocalDate;

public class Donkey extends PackAnimal {
    public Donkey(int id, String name, LocalDate birthDate) {
        super(id, name, birthDate);
    }

    @Override
    public String getType() {
        return "Осёл";
    }
}
package org.example;
import java.time.LocalDate;

public class Camel extends PackAnimal {
    public Camel(int id, String name, LocalDate birthDate) {
        super(id, name, birthDate);
    }

    @Override
    public String getType() {
        return "Верблюд";
    }
}
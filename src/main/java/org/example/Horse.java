package org.example;
import java.time.LocalDate;

public class Horse extends PackAnimal {
    public Horse(int id, String name, LocalDate birthDate) {
        super(id, name, birthDate);
    }

    @Override
    public String getType() {
        return "Лошадь";
    }
}

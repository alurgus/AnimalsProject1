package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Animal {
    private int id;
    private String name;
    private LocalDate birthDate;
    private List<String> commands = new ArrayList<>();

    public Animal(int id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public void showCommands() {
        System.out.println("Команды " + name + ": " + String.join(", ", commands));
    }

    public abstract String getType(); // для определения типа животного
}

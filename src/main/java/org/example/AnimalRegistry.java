package org.example;

import java.time.LocalDate;
import java.util.*;
import java.sql.*;

public class AnimalRegistry {
    private final List<Animal> animals = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private int nextId = 1;

    public void start() {
        while (true) {
            System.out.println("\n=== Реестр животных ===");
            System.out.println("1. Завести новое животное");
            System.out.println("2. Показать команды животного");
            System.out.println("3. Обучить животное новой команде");
            System.out.println("4. Показать всех животных");
            System.out.println("0. Выход");
            System.out.print("Выбор: ");

            switch (scanner.nextLine()) {
                case "1" -> addAnimal();
                case "2" -> showAnimalCommands();
                case "3" -> teachAnimal();
                case "4" -> showAllAnimals();
                case "0" -> {
                    System.out.println("До свидания!");
                    return;
                }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private void addAnimal() {
        System.out.print("Имя животного: ");
        String name = scanner.nextLine();

        System.out.print("Дата рождения (ГГГГ-ММ-ДД): ");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());

        System.out.println("Тип животного: 1-Собака, 2-Кошка, 3-Хомяк, 4-Лошадь, 5-Верблюд, 6-Осёл");
        int type = Integer.parseInt(scanner.nextLine());

        Animal animal = switch (type) {
            case 1 -> new Dog(nextId++, name, birthDate);
            case 2 -> new Cat(nextId++, name, birthDate);
            case 3 -> new Hamster(nextId++, name, birthDate);
            case 4 -> new Horse(nextId++, name, birthDate);
            case 5 -> new Camel(nextId++, name, birthDate);
            case 6 -> new Donkey(nextId++, name, birthDate);
            default -> null;
        };

        if (animal != null) {
            animals.add(animal);
            saveAnimalToDB(animal);  // Сохраняем животное в БД
            System.out.println(animal.getType() + " по имени " + name + " добавлен(а)!");
        } else {
            System.out.println("Неверный тип.");
        }
    }

    private void saveAnimalToDB(Animal animal) {
        String insertAnimalQuery = "INSERT INTO Animals (type, name, birthDate) VALUES (?, ?, ?)";
        String insertCommandQuery = "INSERT INTO Commands (animal_id, command) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement insertAnimalStmt = connection.prepareStatement(insertAnimalQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertCommandStmt = connection.prepareStatement(insertCommandQuery)) {

            // Сохраняем животное в таблицу Animals
            insertAnimalStmt.setString(1, animal.getType());
            insertAnimalStmt.setString(2, animal.getName());
            insertAnimalStmt.setDate(3, Date.valueOf(animal.getBirthDate()));
            insertAnimalStmt.executeUpdate();

            // Получаем сгенерированный ID для животного
            try (ResultSet generatedKeys = insertAnimalStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int animalId = generatedKeys.getInt(1);

                    // Сохраняем команды для этого животного
                    for (String command : animal.getCommands()) {
                        insertCommandStmt.setInt(1, animalId);
                        insertCommandStmt.setString(2, command);
                        insertCommandStmt.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAnimalCommands() {
        Animal animal = findAnimalByName();
        if (animal != null) {
            animal.showCommands();
        }
    }

    private void teachAnimal() {
        Animal animal = findAnimalByName();
        if (animal != null) {
            System.out.print("Введите новую команду: ");
            String command = scanner.nextLine();
            animal.addCommand(command);
            saveCommandToDB(animal, command);
            System.out.println("Команда добавлена.");
        }
    }

    private void saveCommandToDB(Animal animal, String command) {
        String insertCommandQuery = "INSERT INTO Commands (animal_id, command) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement insertCommandStmt = connection.prepareStatement(insertCommandQuery)) {

            insertCommandStmt.setInt(1, animal.getId());
            insertCommandStmt.setString(2, command);
            insertCommandStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAllAnimals() {
        if (animals.isEmpty()) {
            System.out.println("Животных пока нет.");
            return;
        }

        for (Animal a : animals) {
            System.out.println(a.getId() + ": " + a.getType() + " - " + a.getName() + " (" + a.getBirthDate() + ")");
        }
    }

    private Animal findAnimalByName() {
        System.out.print("Введите имя животного: ");
        String name = scanner.nextLine();

        for (Animal a : animals) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        System.out.println("Животное не найдено.");
        return null;
    }
}

/*public class AnimalRegistry {
    private final List<Animal> animals = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private int nextId = 1;

    public void start() {
        while (true) {
            System.out.println("\n=== Реестр животных ===");
            System.out.println("1. Завести новое животное");
            System.out.println("2. Показать команды животного");
            System.out.println("3. Обучить животное новой команде");
            System.out.println("4. Показать всех животных");
            System.out.println("0. Выход");
            System.out.print("Выбор: ");

            switch (scanner.nextLine()) {
                case "1" -> addAnimal();
                case "2" -> showAnimalCommands();
                case "3" -> teachAnimal();
                case "4" -> showAllAnimals();
                case "0" -> {
                    System.out.println("До свидания!");
                    return;
                }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private void addAnimal() {
        System.out.print("Имя животного: ");
        String name = scanner.nextLine();

        System.out.print("Дата рождения (ГГГГ-ММ-ДД): ");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());

        System.out.println("Тип животного: 1-Собака, 2-Кошка, 3-Хомяк, 4-Лошадь, 5-Верблюд, 6-Осёл");
        int type = Integer.parseInt(scanner.nextLine());

        Animal animal = switch (type) {
            case 1 -> new Dog(nextId++, name, birthDate);
            case 2 -> new Cat(nextId++, name, birthDate);
            case 3 -> new Hamster(nextId++, name, birthDate);
            case 4 -> new Horse(nextId++, name, birthDate);
            case 5 -> new Camel(nextId++, name, birthDate);
            case 6 -> new Donkey(nextId++, name, birthDate);
            default -> null;
        };

        if (animal != null) {
            animals.add(animal);
            System.out.println(animal.getType() + " по имени " + name + " добавлен(а)!");
        } else {
            System.out.println("Неверный тип.");
        }

        // Использование Counter с try-with-resources
        try (Counter counter = new Counter()) {
            counter.add(); // увеличиваем счетчик
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showAnimalCommands() {
        Animal animal = findAnimalByName();
        if (animal != null) {
            animal.showCommands();
        }
    }

    private void teachAnimal() {
        Animal animal = findAnimalByName();
        if (animal != null) {
            System.out.print("Введите новую команду: ");
            String command = scanner.nextLine();
            animal.addCommand(command);
            System.out.println("Команда добавлена.");
        }
    }

    private void showAllAnimals() {
        if (animals.isEmpty()) {
            System.out.println("Животных пока нет.");
            return;
        }

        for (Animal a : animals) {
            System.out.println(a.getId() + ": " + a.getType() + " - " + a.getName() + " (" + a.getBirthDate() + ")");
        }
    }

    private Animal findAnimalByName() {
        System.out.print("Введите имя животного: ");
        String name = scanner.nextLine();

        for (Animal a : animals) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        System.out.println("Животное не найдено.");
        return null;
    }
}*/

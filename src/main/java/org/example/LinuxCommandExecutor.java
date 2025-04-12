package org.example;

import java.io.*;

public class LinuxCommandExecutor {

    private final boolean isLinux;
    private final boolean isWSL;
    private final BufferedWriter logWriter;

    public LinuxCommandExecutor() {
        isLinux = detectLinux();
        isWSL = detectWSL();
        logWriter = initLogger();

        if (!isLinux && !isWSL) {
            System.err.println("⛔ Эта программа работает только в Linux или через WSL на Windows!");
            closeLogger();
            return;
        }

        log("ОС обнаружена: " + (isWSL ? "Windows (через WSL)" : "Linux"));
        executeTasks();
        closeLogger();
    }

    private boolean detectLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    private boolean detectWSL() {
        try (BufferedReader reader = new BufferedReader(new FileReader("/proc/version"))) {
            String line = reader.readLine();
            return line != null && line.toLowerCase().contains("microsoft");
        } catch (IOException e) {
            return false;
        }
    }

    private BufferedWriter initLogger() {
        try {
            return new BufferedWriter(new FileWriter("executor_log.txt", true));
        } catch (IOException e) {
            System.err.println("⚠ Не удалось создать лог-файл.");
            return null;
        }
    }

    private void log(String message) {
        try {
            if (logWriter != null) {
                logWriter.write(message + "\n");
                logWriter.flush();
            }
        } catch (IOException e) {
            System.err.println("⚠ Ошибка логирования: " + e.getMessage());
        }
    }

    private void closeLogger() {
        try {
            if (logWriter != null) {
                logWriter.close();
            }
        } catch (IOException ignored) {}
    }

    private void runCommand(String command) {
        try {
            log("▶ Команда: " + command);
            System.out.println("▶ Выполняю: " + command);
            Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});

            BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = output.readLine()) != null) {
                log("✔ " + line);
                System.out.println("✔ " + line);
            }

            while ((line = error.readLine()) != null) {
                log("⚠ " + line);
                System.err.println("⚠ " + line);
            }

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            log("❌ Ошибка: " + e.getMessage());
            System.err.println("❌ Ошибка: " + e.getMessage());
        }
    }

    private void executeTasks() {
        runCommand("echo -e \"Собака\\nКошка\\nХомяк\" > home_animals.txt");
        runCommand("echo -e \"Лошадь\\nВерблюд\\nОсел\" > pack_animals.txt");
        runCommand("cat home_animals.txt pack_animals.txt > all_animals.txt");
        runCommand("cat all_animals.txt");
        runCommand("mv all_animals.txt \"Друзья_человека.txt\"");
        runCommand("mkdir -p animals_dir");
        runCommand("mv \"Друзья_человека.txt\" animals_dir/");

        // Работа с репозиториями и .deb-пакетами
        runCommand("wget -q https://dev.mysql.com/get/mysql-apt-config_0.8.24-1_all.deb");
        runCommand("sudo dpkg -i mysql-apt-config_0.8.24-1_all.deb");
        runCommand("sudo apt update");
        runCommand("sudo apt install -y mysql-workbench");

        runCommand("wget -q http://archive.ubuntu.com/ubuntu/pool/universe/h/htop/htop_3.0.5-7build1_amd64.deb");
        runCommand("sudo dpkg -i htop_3.0.5-7build1_amd64.deb");
        runCommand("sudo dpkg -r htop");

        runCommand("history > terminal_history.txt");
    }

    public static void main(String[] args) {
        new LinuxCommandExecutor();
    }
}
package rvt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TodoList {
    private ArrayList<String> tasks;
    private final String[] filePaths = {
        "src/main/java/rvt/todo.csv",
        "data/todo.csv"
    };

    public TodoList() {
        this.tasks = new ArrayList<>();
        this.loadFromFile();
        this.updateFile();
    }

    private void loadFromFile() {
        for (String filePath : this.filePaths) {
            File file = new File(filePath);
            if (!file.exists()) {
                continue;
            }

            ArrayList<String> loadedTasks = new ArrayList<>();

            try (Scanner fileReader = new Scanner(file)) {
                if (fileReader.hasNextLine()) {
                    fileReader.nextLine();
                }

                while (fileReader.hasNextLine()) {
                    String line = fileReader.nextLine();
                    String[] parts = line.split(",", 2);
                    if (parts.length >= 2) {
                        loadedTasks.add(parts[1].trim());
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }

            if (!loadedTasks.isEmpty()) {
                this.tasks.addAll(loadedTasks);
                return;
            }
        }
    }

    private int getLastId() {
        return this.tasks.size();
    }

    public void add(String task) {
        this.tasks.add(task);

        for (String filePath : this.filePaths) {
            File file = new File(filePath);
            try {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }

                boolean writeHeader = !file.exists() || file.length() == 0;

                try (FileWriter fw = new FileWriter(file, true);
                     PrintWriter pw = new PrintWriter(fw)) {

                    if (writeHeader) {
                        pw.println("id, task");
                    }

                    pw.println(this.getLastId() + "," + task);
                }
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }

    private boolean updateFile() {
        boolean updated = true;

        for (String filePath : this.filePaths) {
            File file = new File(filePath);
            try {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }

                try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                    pw.println("id, task");
                    for (int i = 0; i < this.tasks.size(); i++) {
                        pw.println((i + 1) + "," + this.tasks.get(i));
                    }
                }
            } catch (IOException e) {
                updated = false;
            }
        }

        return updated;
    }

    public void remove(int id) {
        if (id > 0 && id <= this.tasks.size()) {
            this.tasks.remove(id - 1);
            this.updateFile();
        }
    }

    public void print() {
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println((i + 1) + ": " + this.tasks.get(i));
        }
    }

    public boolean checkEventString(String value) {
        if (value == null || value.length() < 3) {
            return false;
        }
        return value.matches("^[a-zA-Z0-9āčēģīķļņšūžĀČĒĢĪĶĻŅŠŪŽ ]+$");
    }

    public static void main(String[] args) {
        TodoList list = new TodoList();
        Scanner scanner = new Scanner(System.in);

        UserInterface ui = new UserInterface(list, scanner);
        ui.start();
    }
}

class UserInterface {
    private TodoList todoList;
    private Scanner scanner;

    public UserInterface(TodoList todoList, Scanner scanner) {
        this.todoList = todoList;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.print("Command: ");
            String command = this.scanner.nextLine();

            if (command.equals("stop")) {
                break;
            }

            if (command.equals("add")) {
                System.out.print("To add: ");
                String task = this.scanner.nextLine();
                
                if (this.todoList.checkEventString(task)) {
                    this.todoList.add(task);
                } else {
                    System.out.println("Invalid task! Must be at least 3 characters and contain only letters, numbers, or spaces.");
                }
                
            } else if (command.equals("list")) {
                this.todoList.print();
            } else if (command.equals("remove")) {
                System.out.print("Which one is removed? ");
                try {
                    int id = Integer.parseInt(this.scanner.nextLine());
                    this.todoList.remove(id);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number ID.");
                }
            }
        }
    }
}
package rvt;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ToDoList {
    private ArrayList<String> tasks;
    private final String filePath = "todo.csv";
    public Object print;
    
    public void TodoList() {
        this.tasks = new ArrayList<>();
        loadFromFile();
    }

    private int getLastId() {
       return tasks.size() + 1;
    }

    private void loadFromFile() {
        try {
            File file = new File(filePath);

            if (!file.exists()) {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write("id,task\n");
                writer.close();
            }

                Scanner scanner = new Scanner(file);
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",", 2);

                    if (parts.length == 2) {
                        tasks.add(parts[1]);
                    }
                }
                scanner.close();
        } catch (IOException e) {
            System.out.println("Kļūda lasot failu.");
            }
    }


public void add(String task) {
    if (!checkEventString(task)) {
        System.out.println("Nepareizs ievades formāts!");
        return;
    }
    tasks.add(task);
    updateFile();
}

private boolean updateFile() {
    try {
        FileWriter writer = new FileWriter(filePath);
        writer.write("id,task\n");
        for (int i = 0; i < tasks.size(); i++) {
            writer.write((i + 1) + "," + tasks.get(i) + "\n");
        }
        writer.close();
        return true;

    } catch (IOException e) {
        System.out.println("Kļūda saglabājot failu.");
        return false;
    }
}

public void remove(int id) {
    if (id <= 0 || id > tasks.size()) {
        System.out.println("Nederīgs ID!");
        return;
    }
    tasks.remove(id - 1);
    updateFile();
}

public void print() {
    for (int i = 0; i < tasks.size(); i++) {
        System.out.println((i + 1) + ": " + tasks.get(i));
    }
}

public boolean checkEventString(String value) {
    return value.matches("[A-Za-z0-9 ]+");
}


}



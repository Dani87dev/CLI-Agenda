package com.itacademy.cliagenda.task.cli;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.service.EventService;
import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.service.NotesService;
import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.service.TaskService;

import java.util.List;
import java.util.Scanner;

public class TaskCli {

    private final TaskService service;
    private final NotesService notesService;
    private final EventService eventService;
    private final Scanner scanner = new Scanner(System.in);

    public TaskCli(TaskService service, NotesService notesService, EventService eventService) {
        this.service = service;
        this.notesService = notesService;
        this.eventService = eventService;
    }

    public void showMenu() {
        int option = -1;
        do {
            System.out.println("<< TASKS MENU >>");
            System.out.println("1 - Create task");
            System.out.println("2 - List all tasks");
            System.out.println("3 - List incomplete tasks");
            System.out.println("4 - List completed tasks");
            System.out.println("5 - Find task");
            System.out.println("6 - Update task");
            System.out.println("7 - Delete task");
            System.out.println("0 - Return to App Menu");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1: createTask(); break;
                    case 2: listTasks(); break;
                    case 3: listIncompleteTasks(); break;
                    case 4: listCompletedTasks(); break;
                    case 5: findTask(); break;
                    case 6: updateTask(); break;
                    case 7: deleteTask(); break;
                    case 0: break;
                    default: System.out.println("Incorrect input, try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
                scanner.nextLine();
            }
        } while (option != 0);
    }

    public void createTask() {
        try {
            System.out.println("Introduce task:");
            String body = scanner.nextLine();

            Task task = service.createTask(body);
            System.out.println("Task \"" + task.getBody() + "\" created.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listTasks() {
        try {
            List<Task> tasks = service.getAllTasks();
            if (tasks.isEmpty()) {
                System.out.println("No tasks found.");
                return;
            }
            for (Task task : tasks) {
                System.out.println("ID: " + task.getId()
                        + " | " + task.getBody()
                        + " | Completed: " + (task.isCompleted() ? "Yes" : "No"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listIncompleteTasks() {
        try {
            List<Task> tasks = service.getTasksByCompleted(false);
            if (tasks.isEmpty()) {
                System.out.println("No incomplete tasks found.");
                return;
            }
            for (Task task : tasks) {
                System.out.println("ID: " + task.getId()
                        + " | " + task.getBody()
                        + " | Completed: No");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listCompletedTasks() {
        try {
            List<Task> tasks = service.getTasksByCompleted(true);
            if (tasks.isEmpty()) {
                System.out.println("No completed tasks found.");
                return;
            }
            for (Task task : tasks) {
                System.out.println("ID: " + task.getId()
                        + " | " + task.getBody()
                        + " | Completed: Yes");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void findTask() {
        try {
            System.out.println("Introduce task ID:");
            int id = scanner.nextInt();
            scanner.nextLine();

            Task task = service.findTaskById(id);
            if (task == null) {
                System.out.println("Task not found.");
                return;
            }

            System.out.println("ID: " + task.getId());
            System.out.println("  Body: " + task.getBody());
            System.out.println("  Completed: " + (task.isCompleted() ? "Yes" : "No"));
            System.out.println("  Event FK: " + task.getEvent_fk());

            List<Note> notes = notesService.getNotesByTaskId(id);
            if (!notes.isEmpty()) {
                System.out.println("  Associated notes:");
                for (Note note : notes) {
                    System.out.println("    - " + note.getBody());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateTask() {
        try {
            System.out.println("Introduce task ID to update:");
            int id = scanner.nextInt();
            scanner.nextLine();

            Task task = service.findTaskById(id);
            if (task == null) {
                System.out.println("Task not found.");
                return;
            }

            System.out.println("Current task:");
            System.out.println("  ID: " + task.getId());
            System.out.println("  Body: " + task.getBody());
            System.out.println("  Completed: " + (task.isCompleted() ? "Yes" : "No"));
            System.out.println("  Event FK: " + task.getEvent_fk());

            System.out.println("Do you want to modify the body? (Y/N):");
            String modifyBody = scanner.nextLine();
            if (modifyBody.equalsIgnoreCase("y")) {
                System.out.println("Introduce new body:");
                String newBody = scanner.nextLine();
                task.changeBody(newBody);
            }

            System.out.println("Do you want to mark as completed/incomplete? (Y/N):");
            String modifyCompleted = scanner.nextLine();
            if (modifyCompleted.equalsIgnoreCase("y")) {
                System.out.println("Mark as completed? (Y/N):");
                String completed = scanner.nextLine();
                task.setCompleted(completed.equalsIgnoreCase("y"));
            }

            List<Event> events = eventService.getAllEvents();
            if (!events.isEmpty()) {
                System.out.println("Do you want to modify the event association? (Y/N):");
                String modifyEvent = scanner.nextLine();
                if (modifyEvent.equalsIgnoreCase("y")) {
                    System.out.println("Available events:");
                    for (Event event : events) {
                        System.out.println("  ID: " + event.getId() + " - " + event.getTitle());
                    }
                    System.out.println("Introduce new event ID (0 for none):");
                    int newEventId = scanner.nextInt();
                    scanner.nextLine();
                    task.setEvent_fk(newEventId);
                }
            }

            service.updateTask(task);
            System.out.println("Task updated successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteTask() {
        try {
            System.out.println("Introduce task ID to delete:");
            int id = scanner.nextInt();
            scanner.nextLine();

            Task task = service.findTaskById(id);
            if (task == null) {
                System.out.println("Task not found.");
                return;
            }

            service.deleteTaskById(id);
            System.out.println("Task deleted.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
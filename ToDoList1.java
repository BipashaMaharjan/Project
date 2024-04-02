import java.util.Scanner;

class TaskNode {
    String task;
    String reminder;
    TaskNode next;

    TaskNode(String task, String reminder) {
        this.task = task;
        this.reminder = reminder;
        this.next = null;
    }
}

class ToDoList {
    private TaskNode head;

    ToDoList() {
        this.head = null;
    }

    void addTask(String task, String reminder) {
        TaskNode newNode = new TaskNode(task, reminder);
        if (head == null) {
            head = newNode;
        } else {
            TaskNode temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        System.out.println("Task added: " + task);
    }

    void displayTasks() {
        if (head == null) {
            System.out.println("No tasks in the list.");
            return;
        }
        TaskNode temp = head;
        System.out.println("Tasks:");
        while (temp != null) {
            System.out.println("- " + temp.task + " (Reminder: " + temp.reminder + ")");
            temp = temp.next;
        }
    }
}

public class ToDoList1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ToDoList toDoList = new ToDoList();

        while (true) {
            System.out.println("\n1. Add Task\n2. Display Tasks\n3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task: ");
                    String task = scanner.nextLine();
                    System.out.print("Enter reminder: ");
                    String reminder = scanner.nextLine();
                    toDoList.addTask(task, reminder);
                    break;
                case 2:
                    toDoList.displayTasks();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter again.");
            }
        }
    }
}
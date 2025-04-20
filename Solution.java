import java.util.*;
import java.util.concurrent.locks.*;

// Easy Level: Employee Management using ArrayList
class Employee {
    int id;
    String name;
    double salary;

    Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String toString() {
        return id + " - " + name + " - " + salary;
    }
}

class EmployeeManager {
    private ArrayList<Employee> employees = new ArrayList<>();

    void addEmployee(int id, String name, double salary) {
        employees.add(new Employee(id, name, salary));
    }

    void updateEmployee(int id, String name, double salary) {
        for (Employee e : employees) {
            if (e.id == id) {
                e.name = name;
                e.salary = salary;
                return;
            }
        }
    }

    void removeEmployee(int id) {
        employees.removeIf(e -> e.id == id);
    }

    Employee searchEmployee(int id) {
        for (Employee e : employees) {
            if (e.id == id) return e;
        }
        return null;
    }

    void printEmployees() {
        for (Employee e : employees) System.out.println(e);
    }
}

// Medium Level: Card Storage and Search using Collection Interface
class CardCollection {
    Map<String, List<String>> symbolToCards = new HashMap<>();

    void addCard(String symbol, String cardName) {
        symbolToCards.computeIfAbsent(symbol, k -> new ArrayList<>()).add(cardName);
    }

    List<String> getCardsBySymbol(String symbol) {
        return symbolToCards.getOrDefault(symbol, new ArrayList<>());
    }
}

// Hard Level: Ticket Booking System with Synchronized Threads
class TicketBookingSystem {
    private int seats = 10;
    private final Lock lock = new ReentrantLock();

    void bookSeat(String user) {
        lock.lock();
        try {
            if (seats > 0) {
                System.out.println(user + " booked seat no. " + (11 - seats));
                seats--;
            } else {
                System.out.println(user + " tried booking but no seats available.");
            }
        } finally {
            lock.unlock();
        }
    }
}

class BookingThread extends Thread {
    TicketBookingSystem system;
    String user;

    BookingThread(TicketBookingSystem system, String user, int priority) {
        this.system = system;
        this.user = user;
        setPriority(priority);
    }

    public void run() {
        system.bookSeat(user);
    }
}

// Main Class
public class CombinedProgram {
    public static void main(String[] args) {
        System.out.println("=== Easy Level: Employee Manager ===");
        EmployeeManager em = new EmployeeManager();
        em.addEmployee(1, "Alice", 50000);
        em.addEmployee(2, "Bob", 60000);
        em.updateEmployee(1, "Alice Smith", 55000);
        em.printEmployees();
        em.removeEmployee(2);
        System.out.println("After removing Bob:");
        em.printEmployees();

        System.out.println("\n=== Medium Level: Card Collection ===");
        CardCollection cc = new CardCollection();
        cc.addCard("♠", "Ace of Spades");
        cc.addCard("♠", "King of Spades");
        cc.addCard("♥", "Queen of Hearts");
        System.out.println("Cards with ♠: " + cc.getCardsBySymbol("♠"));
        System.out.println("Cards with ♥: " + cc.getCardsBySymbol("♥"));

        System.out.println("\n=== Hard Level: Ticket Booking System ===");
        TicketBookingSystem system = new TicketBookingSystem();
        for (int i = 1; i <= 12; i++) {
            int priority = (i % 3 == 0) ? Thread.MAX_PRIORITY : Thread.NORM_PRIORITY;
            new BookingThread(system, "User" + i, priority).start();
        }
    }
}

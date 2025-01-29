# Synchronization in Java

Synchronization is a mechanism in Java to control access to shared resources in a multithreaded environment. It prevents thread interference and ensures consistency of data by allowing only one thread to access a critical section at a time.

---

## 📖 Table of Contents
1. [Why Synchronization is Needed](#why-synchronization-is-needed)
2. [Types of Synchronization](#types-of-synchronization)
3. [Synchronized Methods](#synchronized-methods)
4. [Synchronized Blocks](#synchronized-blocks)
5. [Static Synchronization](#static-synchronization)
6. [Deadlocks](#deadlocks)
7. [ReentrantLock](#reentrantlock)
8. [Best Practices](#best-practices)

---

## 🤔 Why Synchronization is Needed
In a multithreaded environment, multiple threads may access shared resources concurrently, leading to data inconsistency. Synchronization helps:
- Prevent thread interference.
- Maintain data integrity.
- Avoid race conditions.

### Example of a Race Condition
```java
class Counter {
    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

public class RaceConditionExample {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final Count: " + counter.getCount()); // Inconsistent result
    }
}
```

---

## 🔒 Types of Synchronization
1. **Process Synchronization**: Ensures processes execute without conflicts.
2. **Thread Synchronization**: Prevents thread interference using:
    - Synchronized methods
    - Synchronized blocks
    - Locks (e.g., `ReentrantLock`)

---

## 🛠️ Synchronized Methods
Synchronizing a method ensures that only one thread can execute it at a time for a given instance.

### Example:
```java
class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
```

---

## 🧱 Synchronized Blocks
Synchronized blocks allow finer-grained control by locking only a specific part of the code.

### Example:
```java
class Counter {
    private int count = 0;

    public void increment() {
        synchronized (this) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}
```

### Why Use Synchronized Blocks?
- Improves performance by reducing the scope of the lock.
- Allows locking specific objects or sections of code.

---

## 🗂 Static Synchronization
Static synchronization is used to synchronize static methods or blocks. The lock is associated with the class, not the instance.

### Example:
```java
class Counter {
    private static int count = 0;

    public static synchronized void increment() {
        count++;
    }

    public static int getCount() {
        return count;
    }
}
```

---

## ⚠️ Deadlocks
Deadlocks occur when two or more threads are waiting for each other to release locks, causing a cycle of dependency.

### Example of Deadlock:
```java
class DeadlockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void method1() {
        synchronized (lock1) {
            System.out.println("Thread 1: Holding lock 1...");

            synchronized (lock2) {
                System.out.println("Thread 1: Holding lock 2...");
            }
        }
    }

    public void method2() {
        synchronized (lock2) {
            System.out.println("Thread 2: Holding lock 2...");

            synchronized (lock1) {
                System.out.println("Thread 2: Holding lock 1...");
            }
        }
    }
}
```

### Avoiding Deadlocks
1. Acquire locks in a consistent order.
2. Use try-lock mechanisms like `ReentrantLock` with timeouts.

---

## 🔑 ReentrantLock
`ReentrantLock` is a flexible and advanced locking mechanism available in `java.util.concurrent.locks`.

### Example:
```java
import java.util.concurrent.locks.ReentrantLock;

class Counter {
    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}
```

---

## ✅ Best Practices
- Use synchronization only when necessary to avoid performance bottlenecks.
- Prefer `ReentrantLock` for advanced locking requirements.
- Avoid locking on `String` or publicly accessible objects.
- Always release locks in a `finally` block.
- Minimize the scope of synchronized blocks.

---

Synchronization is a powerful tool for ensuring thread safety, but it must be used judiciously to avoid performance issues and deadlocks. With proper understanding and implementation, it can help create robust and efficient multithreaded applications.


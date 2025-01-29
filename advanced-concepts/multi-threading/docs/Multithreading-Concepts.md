# Multithreading Concepts in Java

This document explores the fundamental concepts of multithreading in Java. It serves as a guide for understanding how Java handles concurrent programming and the tools it provides to developers for efficient and safe multithreaded applications.

---

## 📖 Table of Contents
1. [What is Multithreading?](#what-is-multithreading)
2. [Thread Lifecycle](#thread-lifecycle)
3. [Creating Threads in Java](#creating-threads-in-java)
5. [Thread Priority](#thread-priority)
6. [Thread Safety](#thread-safety)
7. [Best Practices](#best-practices)

---

## 🤔 What is Multithreading?
Multithreading is the ability of a program to execute multiple threads concurrently. A thread is a lightweight sub-process, the smallest unit of processing. Multithreading allows:
- Efficient use of CPU resources.
- Simultaneous execution of multiple tasks.
- Faster execution of independent operations.
- Threads share the same memory space.

In Java, the `java.lang.Thread` class and the `java.util.concurrent` package provide support for multithreading.

---

## 🔄 Thread Lifecycle
A thread goes through the following states in its lifecycle:

1. **New**: The thread is created but not started yet. (`Thread t = new Thread();`)
2. **Runnable**: The thread is ready to run but is waiting for CPU time. (`t.start();`)
3. **Running**: The thread is executing its task.
4. **Blocked/Waiting**: The thread is waiting for a resource or signal.
5. **Terminated**: The thread has completed execution or has been stopped.

![Thread Lifecycle](https://example.com/thread-lifecycle-diagram)

---

## 🧵 Creating Threads in Java
There are two main ways to create threads in Java:

### 1. Extending the `Thread` Class
```java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}

public class Main {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
    }
}
```

### 2. Implementing the `Runnable` Interface
```java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable thread is running");
    }
}

public class Main {
    public static void main(String[] args) {
        Thread t = new Thread(new MyRunnable());
        t.start();
    }
}
```

---

## 🔝 Thread Priority
Threads in Java have priorities ranging from 1 (MIN_PRIORITY) to 10 (MAX_PRIORITY). By default, threads are assigned `NORM_PRIORITY` (5). Higher priority threads are more likely to be scheduled for execution, but this behavior is not guaranteed.

```java
Thread t1 = new Thread(() -> System.out.println("Thread 1"));
Thread t2 = new Thread(() -> System.out.println("Thread 2"));

t1.setPriority(Thread.MAX_PRIORITY);
t2.setPriority(Thread.MIN_PRIORITY);
```

---

## 🛡️ Thread Safety
Thread safety ensures that shared resources are accessed by one thread at a time. Techniques to achieve thread safety include:

1. **Synchronized Methods**
   ```java
   public synchronized void increment() {
       counter++;
   }
   ```

2. **Synchronized Blocks**
   ```java
   public void increment() {
       synchronized (this) {
           counter++;
       }
   }
   ```

3. **Volatile Keyword**
   Ensures visibility of changes to variables across threads.
   ```java
   private volatile boolean flag = true;
   ```

4. **Locks**
   Use `ReentrantLock` for advanced locking mechanisms.
   ```java
   Lock lock = new ReentrantLock();
   lock.lock();
   try {
       // critical section
   } finally {
       lock.unlock();
   }
   ```

---

## ✅ Best Practices
- Minimize the use of `synchronized` blocks and methods.
- Use thread-safe collections like `ConcurrentHashMap`.
- Prefer higher-level concurrency utilities from `java.util.concurrent`.
- Avoid deadlocks by acquiring locks in a consistent order.
- Use thread pools for better resource management.

---

This document provides a foundational understanding of multithreading in Java. For more examples and advanced topics, refer to the other documents in this repository.


# Advanced Topics in Java Multithreading

This document covers advanced concepts in Java multithreading to help you write efficient, safe, and optimized concurrent programs.

---

## 📖 Table of Contents
1. [Fork/Join Framework](#forkjoin-framework)
2. [Parallel Streams](#parallel-streams)
3. [CompletableFuture](#completablefuture)
4. [Deadlock Detection and Prevention](#deadlock-detection-and-prevention)
5. [ThreadLocal Variables](#threadlocal-variables)
6. [Concurrency Utilities](#concurrency-utilities)
7. [Customizing Thread Behavior](#customizing-thread-behavior)
8. [Reactive Programming](#reactive-programming)

---

## 🔀 Fork/Join Framework
The **Fork/Join Framework** is designed for parallelizing tasks that can be broken down into smaller subtasks recursively. It is ideal for divide-and-conquer algorithms.

### Key Components:
- **ForkJoinPool**: Executes tasks in a shared thread pool.
- **ForkJoinTask**: Base class for tasks; use `RecursiveTask` (returns a result) or `RecursiveAction` (does not return a result).

### Example:
```java
import java.util.concurrent.*;

class SumTask extends RecursiveTask<Integer> {
    private final int[] array;
    private final int start, end;
    private static final int THRESHOLD = 10;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            int mid = (start + end) / 2;
            SumTask leftTask = new SumTask(array, start, mid);
            SumTask rightTask = new SumTask(array, mid, end);

            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();

            return leftResult + rightResult;
        }
    }
}

public class ForkJoinExample {
    public static void main(String[] args) {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) array[i] = i + 1;

        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(array, 0, array.length);

        int result = pool.invoke(task);
        System.out.println("Sum: " + result);
    }
}
```

---

## 🚀 Parallel Streams
Java 8 introduced **parallel streams** to simplify parallel processing for collections. It splits the data source into multiple parts and processes them concurrently.

### Example:
```java
import java.util.stream.*;

public class ParallelStreamExample {
    public static void main(String[] args) {
        int sum = IntStream.range(1, 1000)
                           .parallel()
                           .filter(i -> i % 2 == 0)
                           .sum();

        System.out.println("Sum of even numbers: " + sum);
    }
}
```

---

## 🌐 CompletableFuture
**CompletableFuture** is a powerful class for asynchronous programming, allowing you to chain tasks and combine results.

### Features:
- Asynchronous execution.
- Combining multiple tasks.
- Exception handling.

### Example:
```java
import java.util.concurrent.*;

public class CompletableFutureExample {
    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task running: " + Thread.currentThread().getName());
            return 42;
        });

        future.thenApply(result -> result * 2)
              .thenAccept(result -> System.out.println("Final Result: " + result));

        Thread.sleep(1000); // Wait for completion
    }
}
```

---

## 🔒 Deadlock Detection and Prevention
Deadlocks occur when two or more threads wait indefinitely for resources held by each other.

### Prevention Techniques:
1. **Lock Ordering**: Always acquire locks in a consistent order.
2. **Try-Lock**: Use `tryLock()` with a timeout.
3. **Avoid Nested Locks**: Reduce complexity by avoiding nested locks.

### Detecting Deadlocks:
Use the `ThreadMXBean` class to monitor thread states.

```java
import java.lang.management.*;

public class DeadlockDetection {
    public static void main(String[] args) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = bean.findDeadlockedThreads();

        if (deadlockedThreads != null) {
            System.out.println("Deadlock detected!");
        } else {
            System.out.println("No deadlocks detected.");
        }
    }
}
```

---

## 🌍 ThreadLocal Variables
ThreadLocal provides thread-specific variables, ensuring each thread gets its own isolated copy.

### Example:
```java
public class ThreadLocalExample {
    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 1);

    public static void main(String[] args) {
        Runnable task = () -> {
            threadLocal.set(threadLocal.get() + 1);
            System.out.println(Thread.currentThread().getName() + " -> " + threadLocal.get());
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();
    }
}
```

---

## 🔧 Concurrency Utilities
Java's `java.util.concurrent` package includes advanced utilities:
1. **ConcurrentHashMap**: A thread-safe hash map.
2. **CountDownLatch**: Synchronizes threads by blocking until a count reaches zero.
3. **CyclicBarrier**: Allows a group of threads to wait for each other at a common point.
4. **Semaphore**: Manages access to resources with permits.

### Example of CountDownLatch:
```java
import java.util.concurrent.*;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + " completed.");
            latch.countDown();
        };

        new Thread(task).start();
        new Thread(task).start();
        new Thread(task).start();

        latch.await();
        System.out.println("All threads completed.");
    }
}
```

---

## 🎨 Customizing Thread Behavior
Java allows customizing thread pools and behavior through:
1. **ThreadFactory**: Customizes thread creation.
2. **UncaughtExceptionHandler**: Handles uncaught exceptions in threads.

### Example of ThreadFactory:
```java
import java.util.concurrent.*;

public class CustomThreadFactoryExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2, runnable -> {
            Thread thread = new Thread(runnable);
            thread.setName("CustomThread");
            return thread;
        });

        executor.execute(() -> System.out.println(Thread.currentThread().getName() + " running"));
        executor.shutdown();
    }
}
```

---

## 📡 Reactive Programming
Reactive programming models asynchronous streams of data. Frameworks like **Project Reactor** and **RxJava** simplify building non-blocking applications.

### Example using Reactor:
```java
import reactor.core.publisher.*;

public class ReactiveExample {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.range(1, 5)
                                 .map(i -> i * 2)
                                 .doOnNext(System.out::println);

        flux.subscribe();
    }
}
```

---

These advanced multithreading concepts and tools empower you to build scalable and high-performance applications in Java. Explore and experiment to master them!


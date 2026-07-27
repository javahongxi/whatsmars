# Java 并发编程核心知识

## 线程创建方式

### 1. 继承 Thread 类
```java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("线程运行");
    }
}
```

### 2. 实现 Runnable 接口
```java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("线程运行");
    }
}
new Thread(new MyRunnable()).start();
```

### 3. 实现 Callable 接口（有返回值）
```java
Callable<String> task = () -> "结果";
FutureTask<String> future = new FutureTask<>(task);
new Thread(future).start();
String result = future.get();
```

## 线程池（Executor 框架）

### 核心参数（ThreadPoolExecutor）
| 参数 | 说明 |
|------|------|
| corePoolSize | 核心线程数，即使空闲也不会被回收 |
| maximumPoolSize | 最大线程数 |
| keepAliveTime | 非核心线程空闲存活时间 |
| workQueue | 任务队列（LinkedBlockingQueue / ArrayBlockingQueue / SynchronousQueue） |
| handler | 拒绝策略（AbortPolicy / CallerRunsPolicy / DiscardPolicy / DiscardOldestPolicy） |

### 常用线程池（Executors 工厂方法）
- `newFixedThreadPool(n)`：固定 n 个核心线程
- `newCachedThreadPool()`：按需创建，空闲 60s 回收
- `newSingleThreadExecutor()`：单线程，保证任务顺序执行
- `newScheduledThreadPool(n)`：支持定时和周期性任务

> **阿里巴巴规范**：不允许使用 Executors 创建线程池，应通过 ThreadPoolExecutor 构造参数显式指定，避免 OOM 风险。

## 并发工具类（JUC）

### CountDownLatch
允许一个或多个线程等待其他线程完成操作。
```java
CountDownLatch latch = new CountDownLatch(3);
// 每个子任务完成后 latch.countDown()
// 主线程 latch.await() 等待所有完成
```

### CyclicBarrier
让一组线程到达一个屏障时被阻塞，直到最后一个线程到达。
```java
CyclicBarrier barrier = new CyclicBarrier(3);
barrier.await(); // 等待所有线程到达屏障
```

### Semaphore
控制同时访问特定资源的线程数量。
```java
Semaphore semaphore = new Semaphore(5); // 最多 5 个线程同时访问
semaphore.acquire();
try { /* 访问资源 */ } finally { semaphore.release(); }
```

## 锁机制

### synchronized
- 修饰实例方法：锁当前对象实例
- 修饰静态方法：锁当前 Class 对象
- 修饰代码块：锁指定对象

### ReentrantLock
可重入锁，功能比 synchronized 更丰富：
- `lock()` / `unlock()`：加锁/解锁
- `tryLock()`：尝试获取锁（非阻塞）
- `lockInterruptibly()`：可响应中断的锁获取

### ReadWriteLock
读写锁：读读不互斥、读写互斥、写写互斥。适合读多写少场景。

## 并发集合

| 集合 | 说明 |
|------|------|
| ConcurrentHashMap | 线程安全的 HashMap，JDK8+ 使用 CAS + synchronized |
| CopyOnWriteArrayList | 写时复制，适合读多写少场景 |
| BlockingQueue | 阻塞队列，生产者-消费者模式核心组件 |
| ConcurrentLinkedQueue | 无界非阻塞线程安全队列 |

## 虚拟线程（Java 21+）

虚拟线程是轻量级线程，由 JVM 调度而非操作系统：
```java
// 创建虚拟线程
Thread.startVirtualThread(() -> System.out.println("Hello"));

// 使用虚拟线程执行器
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    executor.submit(() -> System.out.println("任务1"));
    executor.submit(() -> System.out.println("任务2"));
}
```

虚拟线程的优势：
- 创建成本极低（约几 KB 内存 vs 平台线程约 1MB）
- 可轻松创建百万级虚拟线程
- 适合 I/O 密集型任务（网络请求、数据库查询等）

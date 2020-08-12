package com.tikivn.presentation.viewmodel

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import java.util.ArrayDeque
import java.util.concurrent.*


const val NUMBER_OF_THREADS = 2

class MyAsyncScheduler<T>(val onTasksCompletedListener: (results: List<T>) -> Unit) : MyScheduler<Callable<T>> {

    private val tasks: MutableList<Callable<T>> = mutableListOf()
    private val executor = SimpleExecutor()
    private val executorService: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

    override fun addTask(task: Callable<T>) {
        tasks.add(task)
    }

    override fun executeTasks() {
        executor.execute {
            val futures: List<Future<T>> = executorService.invokeAll(tasks)
            postToMainThread {
                onTasksCompletedListener(futures.map { it.get() })
            }
            executorService.shutdown()
        }
    }

    override fun postToMainThread(task: () -> Unit) {
        if (isMainThread()) {
            task()
        } else {
            val mainThreadHandler = Handler(Looper.getMainLooper())
            mainThreadHandler.post(task)
        }
    }

    override fun terminate() {
        executorService.shutdown()
        executor.terminate()
    }

    private fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }
}

class MySyncScheduler(val onTasksCompletedListener: (results: List<Any>) -> Unit) : MyScheduler<Runnable> {

    private val tasks: MutableList<Runnable> = mutableListOf()
    private val executor = SerialExecutor()

    override fun addTask(task: Runnable) {
        tasks.add(task)
    }

    override fun executeTasks() {
        for (task in tasks) {
            executor.execute(task)
        }
    }

    override fun postToMainThread(task: () -> Unit) {
        if (isMainThread()) {
            task()
        } else {
            val mainThreadHandler = Handler(Looper.getMainLooper())
            mainThreadHandler.post(task)
        }
    }

    override fun terminate() {
        executor.terminate()
    }

    private fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }
}

interface MyScheduler<T> {

    fun addTask(task: T)

    fun executeTasks()

    fun postToMainThread(task: () -> Unit)

    fun terminate()
}

class SimpleExecutor : Executor {

    private var mThread: Thread? = null

    override fun execute(runnable: Runnable) {
        mThread = Thread(runnable)
        mThread?.start()
        mThread?.interrupt()
    }

    fun terminate() {
        mThread?.interrupt()
    }
}

class SerialExecutor : Executor {
    private val mTasks = ArrayDeque<Runnable>()
    private var mActive: Runnable? = null

    @Synchronized
    override fun execute(r: Runnable) {
        mTasks.offer(Runnable {
            try {
                r.run()
            } finally {
                scheduleNext()
            }
        })
        if (mActive == null) {
            scheduleNext()
        }
    }

    fun terminate() {
        (AsyncTask.THREAD_POOL_EXECUTOR as ThreadPoolExecutor).shutdown()
    }

    @Synchronized
    private fun scheduleNext() {
        if (mTasks.poll().also { mActive = it } != null) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(mActive!!)
        }
    }
}
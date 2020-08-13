package com.tikivn.presentation.viewmodel

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import java.util.ArrayDeque
import java.util.concurrent.*

const val NUMBER_OF_THREADS = 2

class AsyncScheduler<T>(val onTasksCompletedListener: (results: List<T?>) -> Unit) :
    Scheduler<Callable<T?>> {

    private val tasks: MutableList<Callable<T?>> = mutableListOf()
    private val executor = SimpleExecutor()
    private val executorService: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

    override fun addTask(task: Callable<T?>) {
        tasks.add(task)
    }

    override fun executeTasks() {
        executor.execute {
            try {
                val futures: List<Future<T?>> = executorService.invokeAll(tasks)
                postToMainThread {
                    onTasksCompletedListener(futures.map { it.get() })
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }
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
}

class SyncScheduler<T>(private val onTasksCompletedListener: (results: List<T?>) -> Unit) :
    Scheduler<Callable<T?>> {

    private val tasks: MutableList<Callable<T?>> = mutableListOf()
    private val executor = SerialExecutor(this::onTasksCompleted)

    override fun addTask(task: Callable<T?>) {
        tasks.add(task)
    }

    override fun executeTasks() {
        executor.reset()
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

    private fun onTasksCompleted(results: List<T?>) {
        postToMainThread {
            onTasksCompletedListener(results)
        }
    }
}

interface Scheduler<T> {

    fun addTask(task: T)

    fun executeTasks()

    fun postToMainThread(task: () -> Unit)

    fun terminate()

    fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }
}

class SimpleExecutor : Executor {

    private var mThread: Thread? = null

    override fun execute(runnable: Runnable) {
        mThread = Thread(runnable)
        mThread?.start()
    }

    fun terminate() {
        if (mThread?.isAlive == true) {
            mThread?.interrupt()
        }
    }
}

class SerialExecutor<T>(private val onTasksCompletedListener: (results: List<T?>) -> Unit) {
    private val mTasks = ArrayDeque<Runnable>()
    private var mActive: Runnable? = null
    private val results = arrayListOf<T?>()
    private val mThreadPoolExecutor = AsyncTask.THREAD_POOL_EXECUTOR as ThreadPoolExecutor

    @Synchronized
    fun execute(callable: Callable<T?>) {
        mTasks.offer(Runnable {
            try {
                results.add(callable.call())
            } finally {
                scheduleNext()
            }
        })
        if (mActive == null) {
            scheduleNext()
        }
    }

    fun reset() {
        mTasks.clear()
        results.clear()
    }

    fun terminate() {
        reset()
        mActive?.let { mThreadPoolExecutor.remove(it) }
    }

    @Synchronized
    private fun scheduleNext() {
        if (mTasks.poll().also { mActive = it } != null) {
            mThreadPoolExecutor.execute(mActive!!)
        } else {
            onTasksCompletedListener(results)
        }
    }
}
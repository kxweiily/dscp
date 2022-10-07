package com.topideal.dscp.common.util;

import java.util.concurrent.*;

/**
 * 全局异步线程池 工具类
 * @Author: kongxj
 * @Date: 2022/8/9 18:27
 */
public class ExecutorUtils {

    private static Integer CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static Integer MAX_IMUM_POOL_SIZE;
    private static ThreadPoolExecutor threadPool;

    static {
        MAX_IMUM_POOL_SIZE = CORE_POOL_SIZE > 10 ? CORE_POOL_SIZE : 10;
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_IMUM_POOL_SIZE, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue());
    }

    public ExecutorUtils() {
    }

    public static void execute(Runnable runnable) {
        threadPool.execute(runnable);
    }


    public static <V> Future<V> submit(Callable<V> task) {
        return threadPool.submit(task);
    }

}

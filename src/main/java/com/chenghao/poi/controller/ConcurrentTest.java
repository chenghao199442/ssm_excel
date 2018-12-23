package com.chenghao.poi.controller;

import com.chenghao.poi.util.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 模拟用户的并发请求，检测用户乐观锁的性能问题
 */
public class ConcurrentTest {
    final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);
        Thread[] ths = new Thread[5];
        for (int j = 0; j < 5; j++) {
            ths[j] = new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    HttpRequest.sendPost("http://localhost:8080/excel/distribution.do", "name=" + "user"+i);
                    latch.countDown();
                }
            });
        }
        Arrays.asList(ths).forEach(t -> t.start());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有模拟请求结束  at " + sdf.format(new Date()));

    }

    static class AnalogUser extends Thread {
        String name;
        CountDownLatch latch;

        public AnalogUser(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                latch.await(); //一直阻塞当前线程，直到计时器的值为0
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            post();//发送post 请求
        }

        public void post() {
            String result = "";
            System.out.println("模拟用户： " + name + " 开始发送模拟请求  at " + sdf.format(new Date()));
            result = HttpRequest.sendPost("http://localhost:8080/excel/distribution.do", "name=" + name);
            System.out.println("操作结果：" + result);
            System.out.println("模拟用户： " + name + " 模拟请求结束  at " + sdf.format(new Date()));

        }
    }
}


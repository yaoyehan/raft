package com.nanami.www;

public class TestThread {


    static volatile int status;
    static Object object = new Object();

    public static void main(String[] args) {

        class DoSomething implements Runnable {

            int s;

            public DoSomething(int s){
                this.s = s;
            }

            @Override
            public void run() {
                synchronized (object){
                    status = s;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(status);
//                    object.notifyAll();
                }
            }
        }


        Thread thread1 = new Thread(new DoSomething(1));
        Thread thread2 = new Thread(new DoSomething(2));
        thread1.start();
        thread2.start();
    }


}

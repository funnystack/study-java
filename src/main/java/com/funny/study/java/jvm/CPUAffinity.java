package com.funny.study.java.jvm;

import net.openhft.affinity.AffinityLock;

import static net.openhft.affinity.AffinityStrategies.SAME_CORE;
import static net.openhft.affinity.AffinityStrategies.SAME_SOCKET;

/**
 * CPU亲和性
 */
public class CPUAffinity {


    public static void main(String... args) throws InterruptedException {
        AffinityLock al = AffinityLock.acquireLock();
        try {
            // search a cpu on a different socket, otherwise a different core.
            System.out.println("al id " + al.cpuId());
            AffinityLock readerLock = al.acquireLock(SAME_CORE, SAME_SOCKET);
            System.out.println("reader id " + readerLock.cpuId());
            new Thread(new SleepRunnable(readerLock, true), "reader").start();

            // search a cpu on the same core, or the same socket, or any free cpu.
            //AffinityLock writerLock = al.acquireLock(SAME_CORE, SAME_SOCKET);
            System.out.println("writerLock id " + readerLock.cpuId());
            new Thread(new SleepRunnable(readerLock, true), "writer").start();

            Thread.sleep(200);
        } finally {
            al.release();
        }

        // allocate a whole core to the engine so it doesn't have to compete for resources.
        al = AffinityLock.acquireCore(false);
        System.out.println("engine id " + al.cpuId());
        new Thread(new SleepRunnable(al, true), "engine").start();

        Thread.sleep(200);
        System.out.println("\nThe assignment of CPUs is\n" + AffinityLock.dumpLocks());
    }
    static class SleepRunnable implements Runnable {
        private final AffinityLock affinityLock;
        private final boolean wholeCore;

        SleepRunnable(AffinityLock affinityLock, boolean wholeCore) {
            this.affinityLock = affinityLock;
            this.wholeCore = wholeCore;
        }

        public void run() {
            affinityLock.bind(wholeCore);
            try {
                Thread.sleep(1000);
                Thread t = Thread.currentThread();
                String name = t.getName();
                System.out.println("name=" + name);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                affinityLock.release();
            }
        }
    }
}

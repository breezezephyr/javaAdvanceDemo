package com.sean.jstack;

import com.google.common.base.Objects;

/**
 * Created with IntelliJ IDEA.
 * Author: xiappeng.cai
 * Date: 14-4-25
 * Time: 上午11:03
 */
public class ThreadInfo {
    private String threadId;
    private String threadName;
    private String threadCondition;
    private String waitThreadId;

    public ThreadInfo() {
    }

    public ThreadInfo(String threadId, String threadName, String threadCondition, String waitThreadId) {
        this.threadId = threadId;
        this.threadName = threadName;
        this.threadCondition = threadCondition;
        this.waitThreadId = waitThreadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public void setThreadCondition(String threadCondition) {
        this.threadCondition = threadCondition;
    }

    public void setWaitThreadId(String waitThreadId) {
        this.waitThreadId = waitThreadId;
    }

    public String getThreadId() {
        return threadId;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getThreadCondition() {
        return threadCondition;
    }

    public String getWaitThreadId() {

        return waitThreadId;
    }

    @Override
    public String toString() {
        return threadId + "| " + threadName + "| " + threadCondition;

    }
}

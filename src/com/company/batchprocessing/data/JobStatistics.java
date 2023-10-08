package com.company.batchprocessing.data;

public class JobStatistics {

    private int readCount;
    private int processedCount;
    private int writeCount;
    private int total;

    public void setTotal(int total) {
        this.total = total;
    }

    public void addReadCount(int count) {
        this.readCount += count;
    }

    public void addProcessedCount(int count) {
        this.processedCount += count;
    }

    public void addWriteCount(int count) {
        this.writeCount += count;
    }

    public int getReadCount() {
        return readCount;
    }

    public int getProcessedCount() {
        return processedCount;
    }

    public int getWriteCount() {
        return writeCount;
    }

    public int getTotal() {
        return total;
    }
}

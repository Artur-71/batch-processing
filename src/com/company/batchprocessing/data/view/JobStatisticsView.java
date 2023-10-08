package com.company.batchprocessing.data.view;

import com.company.batchprocessing.data.JobStatistics;

public class JobStatisticsView {

    private final int readCount;
    private final int processedCount;
    private final int writeCount;
    private final int total;

    public JobStatisticsView(int readCount, int processedCount, int writeCount, int total) {
        this.readCount = readCount;
        this.processedCount = processedCount;
        this.writeCount = writeCount;
        this.total = total;
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

    public static JobStatisticsView fromJobStatistics(JobStatistics statistics) {
        return new JobStatisticsView(
                statistics.getReadCount(),
                statistics.getProcessedCount(),
                statistics.getWriteCount(),
                statistics.getTotal()
        );
    }

    @Override
    public String toString() {
        return "JobStatisticsView{" +
                "readCount=" + readCount +
                ", processedCount=" + processedCount +
                ", writeCount=" + writeCount +
                ", total=" + total +
                '}';
    }
}

package com.company.batchprocessing;

import com.company.batchprocessing.data.JobParameters;
import com.company.batchprocessing.data.view.JobStatisticsView;

public class Job<T> {

    private final JobInstance<T> jobInstance;

    public Job(Reader<T> reader,
               Processor<T> processor,
               Writer<T> writer,
               JobParameters jobParameters) {
        this.jobInstance = new JobInstance<>(reader, processor, writer, jobParameters);
    }

    public void start() {
        jobInstance.start();
    }

    public void cancel() {
        jobInstance.cancel();
    }

    public JobStatus getStatus() {
        return jobInstance.getStatus();
    }

    public JobStatisticsView getStatistics() {
        return jobInstance.getStatistics();
    }
}

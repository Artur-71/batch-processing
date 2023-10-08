package com.company.batchprocessing;

import com.company.batchprocessing.data.JobParameters;
import com.company.batchprocessing.data.JobStatistics;
import com.company.batchprocessing.data.view.JobStatisticsView;
import com.company.batchprocessing.exception.JobException;

import java.time.LocalDateTime;

public class JobInstance<T> {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private JobStatus status = JobStatus.CREATED;
    private final JobStatistics statistics = new JobStatistics();
    private final JobExecutor<T> jobExecutor;
    private final JobParameters parameters;

    public JobInstance(Reader<T> reader,
                       Processor<T> processor,
                       Writer<T> writer,
                       JobParameters parameters) {
        this.jobExecutor = new JobExecutor<>(this, statistics, reader, processor, writer, parameters);
        this.parameters = parameters;
    }

    public void start() {
        if (status == JobStatus.STARTED) {
            throw new JobException("Job has already been started");
        }

        status = JobStatus.STARTED;
        startDate = LocalDateTime.now();
        jobExecutor.execute();
    }

    public void cancel() {
        jobExecutor.cancel();
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public JobStatus getStatus() {
        return status;
    }

    public JobStatisticsView getStatistics() {
        return JobStatisticsView.fromJobStatistics(statistics);
    }
}

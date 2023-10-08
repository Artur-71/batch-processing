package com.company.batchprocessing;

import com.company.batchprocessing.data.JobParameters;
import com.company.batchprocessing.data.JobStatistics;
import com.company.batchprocessing.exception.JobExecutorException;
import com.company.batchprocessing.exception.ProcessDataException;
import com.company.batchprocessing.exception.ReadDataException;
import com.company.batchprocessing.exception.WriteDataException;
import com.company.batchprocessing.util.ListUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class JobExecutor<T> {

    private final JobInstance<T> jobInstance;
    private final JobStatistics jobStatistics;
    private final Reader<T> reader;
    private final Processor<T> processor;
    private final Writer<T> writer;
    private final JobParameters jobParameters;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private volatile Future<?> currentTask;

    public JobExecutor(JobInstance<T> jobInstance,
                       JobStatistics jobStatistics,
                       Reader<T> reader,
                       Processor<T> processor,
                       Writer<T> writer,
                       JobParameters jobParameters) {
        this.jobInstance = jobInstance;
        this.jobStatistics = jobStatistics;
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
        this.jobParameters = jobParameters;
    }

    public void execute() {
        // TODO: Implement synchronization for currentTask
        if (currentTask != null && !currentTask.isDone()) {
            throw new JobExecutorException("The Job Executor is currently in the process of executing a task. " +
                    "You can cancel the existing task and then try to execute a new one.");
        }

        currentTask = executorService.submit(this::executeJob);
    }

    public void cancel() {
        // TODO: Implement synchronization for currentTask
        if (currentTask == null) {
            throw new JobExecutorException("The Job Executor doesn't have any task.");
        }

        if (currentTask.isDone()) {
            throw new JobExecutorException("The Job Executor has been already finished task.");
        }

        currentTask.cancel(true);
        currentTask = null;
    }

    // TODO: Process partitioned data with executor service
    private void executeJob() {
        final Collection<T> dataToProcess = this.readData();
        final List<List<T>> partitionedDataToProcess = ListUtil.partition(dataToProcess, jobParameters.getChunkSize());

        final List<Collection<T>> processedData = partitionedDataToProcess.stream()
                .map(this::processData)
                .toList();

        final List<Collection<T>> persistedData = processedData.stream()
                .map(ArrayList::new)
                .map(this::writeData)
                .toList();

        jobInstance.setStatus(JobStatus.COMPLETED);
        jobInstance.setEndDate(LocalDateTime.now());
        currentTask = null;
    }

    private Collection<T> readData() {
        try {
            final Collection<T> dataToProcess = reader.readData();
            jobStatistics.setTotal(dataToProcess.size());
            jobStatistics.addReadCount(dataToProcess.size());

            return dataToProcess;
        } catch (Throwable e) {
            jobInstance.setStatus(JobStatus.FAILED);
            throw new ReadDataException("Job Executor failed to read data. Exception message: " + e.getMessage());
        }
    }

    private Collection<T> processData(List<T> dataToProcess) {
        try {
            final Collection<T> processedData = processor.processData(dataToProcess);
            jobStatistics.addProcessedCount(processedData.size());

            return processedData;
        } catch (Throwable e) {
            jobInstance.setStatus(JobStatus.FAILED);
            throw new ProcessDataException("Job Executor failed to process data. Exception message: " + e.getMessage());
        }
    }

    public Collection<T> writeData(List<T> dataToWrite) {
        try {
            final Collection<T> persistedData = writer.writeData(dataToWrite);
            jobStatistics.addWriteCount(persistedData.size());

            return persistedData;
        } catch (Throwable e) {
            jobInstance.setStatus(JobStatus.FAILED);
            throw new WriteDataException("Job Executor failed to write data. Exception message: " + e.getMessage());
        }
    }
}

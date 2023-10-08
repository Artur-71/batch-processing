package com.company;

import com.company.batchprocessing.Job;
import com.company.batchprocessing.Processor;
import com.company.batchprocessing.Reader;
import com.company.batchprocessing.Writer;
import com.company.batchprocessing.data.JobParameters;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final Job<Integer> job = new Job<>(
                defaultReader(),
                defaultProcessor(),
                defaultWriter(),
                new JobParameters(20)
        );

        System.out.println(job.getStatus());
        job.start();

        System.out.println(job.getStatus());
        System.out.println(job.getStatistics());

        Thread.sleep(3000L);

        System.out.println(job.getStatistics());

        Thread.sleep(3000L);

        System.out.println(job.getStatus());
        System.out.println(job.getStatistics());
    }

    public static Reader<Integer> defaultReader() {
        return () -> IntStream.range(1, 201)
                .boxed()
                .collect(Collectors.toList());
    }

    public static Processor<Integer> defaultProcessor() {
        return data -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return data.stream()
                    .peek(item -> System.out.println("Item value: " + item))
                    .collect(Collectors.toList());
        };
    }

    public static Writer<Integer> defaultWriter() {
        return data -> data;
    }
}

package com.company.batchprocessing.data;

import java.util.Map;

public class JobParameters {

    private final Map<String, Object> params;
    private final int chunkSize;

    public JobParameters(int chunkSize) {
        this(null, chunkSize);
    }

    public JobParameters(Map<String, Object> params, int chunkSize) {
        this.params = params;
        this.chunkSize = chunkSize;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public int getChunkSize() {
        return chunkSize;
    }
}

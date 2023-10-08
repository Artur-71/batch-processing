package com.company.batchprocessing;

import java.util.Collection;

public interface Processor<T> {

    Collection<T> processData(Collection<T> data);
}

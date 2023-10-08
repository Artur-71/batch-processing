package com.company.batchprocessing;

import java.util.Collection;

public interface Writer<T> {

    Collection<T> writeData(Collection<T> data);
}

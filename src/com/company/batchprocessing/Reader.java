package com.company.batchprocessing;

import java.util.Collection;

public interface Reader<T> {

    Collection<T> readData();
}

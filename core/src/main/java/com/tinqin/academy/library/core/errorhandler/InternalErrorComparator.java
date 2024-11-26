package com.tinqin.academy.library.core.errorhandler;

import com.tinqin.academy.library.core.errorhandler.base.ErrorHandlerComponent;
import com.tinqin.academy.library.core.errorhandler.components.InternalErrorHandlerComponent;

import java.util.Comparator;

public class InternalErrorComparator implements Comparator<ErrorHandlerComponent> {

    @Override
    public int compare(ErrorHandlerComponent o1, ErrorHandlerComponent o2) {

        if (o1.getClass() == InternalErrorHandlerComponent.class) {
            return 1;
        }

        if (o2.getClass() == InternalErrorHandlerComponent.class) {
            return -1;
        }

        return 0;
    }
}
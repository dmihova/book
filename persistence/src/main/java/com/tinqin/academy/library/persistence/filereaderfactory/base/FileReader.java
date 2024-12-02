package com.tinqin.academy.library.persistence.filereaderfactory.base;

import com.tinqin.academy.library.persistence.filereaderfactory.models.BookSeederModel;

import java.util.List;

public interface FileReader {
    List<BookSeederModel> getBatch();
}

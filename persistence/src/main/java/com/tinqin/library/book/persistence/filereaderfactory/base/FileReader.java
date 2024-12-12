package com.tinqin.library.book.persistence.filereaderfactory.base;

import com.tinqin.library.book.persistence.filereaderfactory.models.BookSeederModel;

import java.util.List;

public interface FileReader {
    List<BookSeederModel> getBatch();
}

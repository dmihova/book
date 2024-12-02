package com.tinqin.academy.library.persistence.filereaderfactory;

import com.tinqin.academy.library.persistence.filereaderfactory.base.FileReader;
import com.tinqin.academy.library.persistence.filereaderfactory.readers.CsvV1FileReader;
import com.tinqin.academy.library.persistence.filereaderfactory.readers.JsonV3FileReader;
import org.springframework.stereotype.Component;

@Component
public class FileReaderFactory {
    public FileReader createCsvFileReader(String path, Integer batchSize) {
        return new CsvV1FileReader(path, batchSize);
    }

     public FileReader createJsonFileReader(String path, Integer batchSize) {
         return new JsonV3FileReader(path, batchSize);
      }
}
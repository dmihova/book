package com.tinqin.library.book.persistence.filereaderfactory.readers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.tinqin.library.book.persistence.filereaderfactory.base.FileReader;
import com.tinqin.library.book.persistence.filereaderfactory.models.BookSeederModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JsonV3FileReader implements FileReader {
    private final Integer batchSize;
    private final JsonParser jsonParser;

    public JsonV3FileReader(String path, Integer batchSize) {
        this.batchSize = batchSize;
        this.jsonParser = initParser(path);
    }

    private JsonParser initParser(String path) {
        try {
            InputStream pathResource = new ClassPathResource(path).getInputStream();
            JsonFactory jsonFactory = new JsonFactory();
            return jsonFactory.createParser(pathResource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BookSeederModel> getBatch() {

        ArrayList<BookSeederModel> batch = new ArrayList<>();

        try {
            JsonToken currentToken = jsonParser.nextToken();
            while (currentToken!= null&&
                    currentToken != JsonToken.END_ARRAY && batch.size() < batchSize) {
                if (jsonParser.currentToken() == JsonToken.START_OBJECT) {
                    Optional<BookSeederModel> book = parseObject();
                    book.ifPresent(batch::add);
                }
                currentToken =jsonParser.nextToken();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return batch;
    }
    private Optional<BookSeederModel> parseObject() {
        BookSeederModel.BookSeederModelBuilder builder = BookSeederModel.builder();

        try {
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = jsonParser.currentName();
                jsonParser.nextToken();

                switch (fieldName) {
                    case "title" -> builder.title(jsonParser.getText());
                    case "pages" -> builder.pages(jsonParser.getIntValue());
                    case "price" -> builder.price(jsonParser.getDoubleValue());
                    case "authorFirstName" -> builder.authorFirstName(jsonParser.getText());
                    case "authorLastName" -> builder.authorLastName(jsonParser.getText());
                    default -> jsonParser.skipChildren();
                }
            }

            BookSeederModel book = builder.build();
            return Optional.of(book);
        }catch (IOException e) {
            log.warn("Error parsing JSON " + e);
            return Optional.empty();
        }
    }
}

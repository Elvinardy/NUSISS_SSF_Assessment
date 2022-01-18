package iss.ssf_assessment_book_search.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import iss.ssf_assessment_book_search.Constants;
import iss.ssf_assessment_book_search.model.BookModel;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


@Service
public class BookService {

    public static final Logger logger = LoggerFactory.getLogger(BookService.class.getName());
    @Autowired
    private BookCheck bookcheck;
    // method to perfom the book search
    public List<BookModel> getBook(String bookTitle) {
        
        String url = UriComponentsBuilder
            .fromUriString(Constants.OPEN_LIBRARY_URL)
            .queryParam("title", bookTitle.trim().replace(" ", "+"))
            .queryParam("fields", "title+key")
            .queryParam("limit", "20")
            .toUriString();
            logger.info("url: " + url);

        final RequestEntity<Void> req = RequestEntity.get(url).build();
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String> resp = template.exchange(req, String.class);
        // if(resp.getStatusCode() != HttpStatus.OK) {
        //     throw new IllegalArgumentException(
        //         "Error: status code " + resp.getStatusCode().toString());
        // }
        final String body = resp.getBody();
        // logger.info("payload: " + body);
        try (InputStream is = new ByteArrayInputStream(body.getBytes())) {

            final JsonReader reader = Json.createReader(is);
            final JsonObject obj = reader.readObject();
            final JsonArray docs = obj.getJsonArray("docs");

            return docs.stream()
                .map(v -> (JsonObject)v)    // map each Json object into an array
                .map(BookModel::create)
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
        }
                
        public BookModel getBookDetails(String worksId) {
            
            String url = Constants.OPEN_LIBRARY_BOOK_ENDPOINT + worksId + (".json");
            
            try (InputStream is = new ByteArrayInputStream(body.getBytes())) {
            
            JsonReader reader = Json.createReader(is);
            RestTemplate template = new RestTemplate();
            RequestEntity<Void> req = RequestEntity.get(url).build();
            ResponseEntity<String> resp = template.exchange(req, String.class);
            JsonObject jsonObj = reader.readObject();
            
            return convertToBook(jsonObj);
        }

    }

    private BookModel convertToBook(JsonObject obj) {
        BookModel book = new BookModel();
        book.setTitle(obj.getString("title"));

        String desc = bookcheck.checkForDesc(obj);
        if(!desc.isEmpty()) {
            book.setDescription(desc);
        }
        String excerpt = bookcheck.checkForExcerpt(obj);
        if(!excerpt.isEmpty()) {
            book.setExcerpt(excerpt);
        }
        if(obj.containsKey("covers")) {
            book.setThumbnail(obj.getJsonArray("covers").get(0).toString());
        }
     return book;
    }
}


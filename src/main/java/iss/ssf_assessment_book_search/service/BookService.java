package iss.ssf_assessment_book_search.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                
    }


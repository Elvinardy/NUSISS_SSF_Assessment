package iss.ssf_assessment_book_search.service;

import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import iss.ssf_assessment_book_search.Constants;
import iss.ssf_assessment_book_search.model.BookModel;

public class BookService {
    
    // method to perfom the book search
    public List<BookModel> getBook(String title) {
       
        String url = UriComponentsBuilder
            .fromUriString(Constants.OPEN_LIBRARY_URL)
            .queryParam("q", title.trim().replace(" ", "+"))
            .queryParam("field", "title+key")
            .queryParam("init", "20")
            .toUriString();

        final RequestEntity<Void> req = RequestEntity.get(url).build();
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String< resp = template.exchange(req, String.class);

        if(resp.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException(
                "Error: status code " + resp.getStatusCode().toString());
        }
        final String body = resp.getBody();

        logger.info("payload: " + body);

        try (InputStream is = new ByteArrayInputStream(body.getBytes())) {

            final JsonReader reader = Json.createReader(is);
            final JsonObject obj = reader.readObject();
            final JsonArray docs = docs.getJsonArray("docs");
            final String key = obj.getString("key");
            final String title = obj.getString("title");

            return docs.stream()
                .map(v -> (JsonObject)v)    // map each Json object into an array
                .map(BookModel::create)
                .map(b ->{
                    b.setTitle(title);
                    b.setBookKey(bookKey);
                }).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
        }
                
    }


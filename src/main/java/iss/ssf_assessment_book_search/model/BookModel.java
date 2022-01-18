package iss.ssf_assessment_book_search.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class BookModel {
    
    private String title;
    private String description;
    private String excerpt;
    private String thumbnail;
    private String bookKey;
    private boolean inCache = false;

    public BookModel() {

    }

    public BookModel(String[] bookParams) {
        this.title = bookParams[0];
        this.description = bookParams[1];
        this.excerpt = bookParams[2];
        this.inCache = true;
    }

    
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExcerpt() {
        return this.excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getBookKey() {
        return this.bookKey;
    }

    public void setBookKey(String bookKey) {
        this.bookKey = bookKey;
    }

    public boolean getInCache() {
        return this.inCache;
    }

    public void setInCache (boolean inCache) {
        this.inCache = inCache;
    }

    public static BookModel create(JsonObject obj) {
       final BookModel bm = new BookModel();
       bm.setBookKey(obj.getString("key").replace("/works/",""));
       bm.setTitle(obj.getString("title"));
       return bm;
        }

    public static BookModel create(String jsonString) {
        try(InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
            final JsonReader reader = Json.createReader(is);
            return create(reader.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BookModel();
    }

    @Override
    public String toString() {
        return String.join("|", this.title, this.description, this.excerpt, this.thumbnail);
    }
    }

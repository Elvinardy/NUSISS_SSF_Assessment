package iss.ssf_assessment_book_search.model;

import jakarta.json.JsonObject;

public class BookModel {
    
    private String title;
    private String description;
    private String excerpt;
    private String thumbnail;
    private String bookKey;
    private boolean inCache;

    
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
       bm.setBookKey(obj.getString("key"));
       bm.setTitle(obj.getString("title"));
       return bm;
       
            
        }
    }

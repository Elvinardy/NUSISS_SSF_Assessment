package iss.ssf_assessment_book_search.model;

public class BookModel {
    
    private String title;
    private String description;
    private String excerpt;
    private String thumbnail;
    private boolean inCache = false;

    
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

    public boolean inCache() {
        return this.inCache = false;
    }







}

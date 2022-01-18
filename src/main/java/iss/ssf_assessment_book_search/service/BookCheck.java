package iss.ssf_assessment_book_search.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import iss.ssf_assessment_book_search.controllers.SearchController;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@Component
public class BookCheck {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class.getName());

    public String checkForDesc(JsonObject obj) {
        String description = "";
        if(obj.containsKey("description")) {
            try {
                description = obj.getString("description");
            } catch(ClassCastException e) {
                if(obj.getJsonObject("description").containsKey("value")) {
                    JsonObject descObj = obj.getJsonObject("description");
                    description = descObj.getString("value");
                }
            }
        }
        return description;
    }

    public String checkForExcerpt(JsonObject obj) {
        if(obj.containsKey("excerpts") && obj.getJsonArray("excerpts").size() >= 1) {
            JsonArray excerpts = obj.getJsonArray("excerpts");
            logger.info("excerpts is " + excerpts.toString());
            JsonObject firstExcerpts = excerpts.getJsonObject(0);
            return firstExcerpts.getString("excerpts");
    }
    return "";
}
}

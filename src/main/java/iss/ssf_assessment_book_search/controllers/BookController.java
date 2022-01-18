package iss.ssf_assessment_book_search.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import iss.ssf_assessment_book_search.Constants;
import iss.ssf_assessment_book_search.model.BookModel;
import iss.ssf_assessment_book_search.service.BookService;

@Controller
@RequestMapping(path="/book", produces = MediaType.TEXT_HTML_VALUE)
public class BookController {

    @Autowired
    @Qualifier(Constants.BEAN_BOOK_CACHE)
    private BookService bookSvc;


    // Controller to handle retrieving and displaying of book details
    @GetMapping(path = "{worksId}")
    public String getBookById(@PathVariable("worksId") String id, Model model) {
        try {
            BookModel book = bookSvc.getBook(id);
            model.addAttribute("book", book);
            return "book";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }
    




}

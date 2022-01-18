package iss.ssf_assessment_book_search.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import iss.ssf_assessment_book_search.model.BookModel;
import iss.ssf_assessment_book_search.service.BookService;

@Controller
@RequestMapping(path="/book", produces = MediaType.TEXT_HTML_VALUE)
public class BookController {

    @Autowired
    private BookService bookSvc;


    // Controller to handle retrieving and displaying of book details
    @GetMapping(path = "{worksId}")
    public String getBookById(@PathVariable("worksId") String worksId, Model model) {
        try {
            BookModel book = bookSvc.getBookDetails(worksId);
            model.addAttribute("worksId", worksId);
            model.addAttribute("book", book);
            return "book";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    




}

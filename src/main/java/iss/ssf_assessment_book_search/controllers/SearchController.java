package iss.ssf_assessment_book_search.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import iss.ssf_assessment_book_search.model.BookModel;
import iss.ssf_assessment_book_search.service.BookService;


@Controller
@RequestMapping(path="/search" , produces = MediaType.TEXT_HTML_VALUE)
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class.getName());

    @Autowired
    private BookService bookSvc;

    // Controller to handle submissions in view 1
    @GetMapping
    public String BookSearch(@RequestParam(name = "title") String bookTitle, Model model) {
        logger.info(">>> Title searched: " + bookTitle);

        List<BookModel> bookList;
        try {
            bookList = bookSvc.getBook(bookTitle.trim().replaceAll(" ", "+"));
            model.addAttribute("title", bookTitle);
            model.addAttribute("results", bookList);
            return "result";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

}

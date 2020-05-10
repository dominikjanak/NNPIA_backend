package cz.janakdom.controller.rest;

import cz.janakdom.service.QuoteService;
import cz.janakdom.model.database.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/quotes")
@CrossOrigin
public class RestQuoteController {

    private final QuoteService quoteService;

    @Autowired
    public RestQuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public List<Quote> getAllQuotes() {
        return quoteService.findAll();
    }

    @GetMapping("{quoteId}")
    public Quote getQuoteById(@PathVariable Integer quoteId) {
        return quoteService.findById(quoteId);
    }

}

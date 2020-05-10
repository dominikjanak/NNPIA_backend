package cz.janakdom.controller.rest;

import cz.janakdom.model.ApiResponse;
import cz.janakdom.model.database.Quote;
import cz.janakdom.model.dto.QuoteDto;
import cz.janakdom.service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/quote")
@CrossOrigin
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/")
    public ApiResponse<Quote> saveQuote(@RequestBody QuoteDto quote){

        if(quoteService.findByQuote(quote.getQuote()) != null){
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "ALREADY-EXISTS",null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", quoteService.save(quote));
    }

    @GetMapping("/")
    public ApiResponse<List<Quote>> listQuote(){
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", quoteService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Quote> getOneQuote(@PathVariable int id){
        Quote quote = quoteService.findById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), quote == null ? "NOT-EXISTS" : "SUCCESS", quote);
    }

    @PutMapping("/{id}")
    public ApiResponse<QuoteDto> updateQuote(@RequestBody QuoteDto quoteDto, @PathVariable int id) {
        if(!quoteService.canBeUpdate(id, quoteDto.getQuote())){
            return new ApiResponse<>(HttpStatus.OK.value(), "ALREADY-EXISTS", null);
        }

        QuoteDto quote = quoteService.update(id, quoteDto);
        return new ApiResponse<>(HttpStatus.OK.value(), quote==null ? "NOT-FOUND" : "SUCCESS", quote);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteQuote(@PathVariable int id) {
        Quote quote = quoteService.findById(id);
        if(quote != null) quoteService.delete(id);
        return new ApiResponse<Void>(HttpStatus.NOT_ACCEPTABLE.value(), quote == null ? "NOT-FOUND" : "SUCCESS", null);
    }
}

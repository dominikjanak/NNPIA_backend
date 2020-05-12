package cz.janakdom.backend.controller.rest;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Quote;
import cz.janakdom.backend.model.dto.OutputQuoteDto;
import cz.janakdom.backend.model.dto.QuoteDto;
import cz.janakdom.backend.security.JwtUtil;
import cz.janakdom.backend.service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static cz.janakdom.backend.config.Constants.HEADER_STRING;


@RestController
@RequestMapping(value = "/api/quote")
@CrossOrigin
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private JwtUtil jwtUtil;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/")
    public ApiResponse<Quote> saveQuote(@RequestHeader(HEADER_STRING) String token, @RequestBody QuoteDto quote){
        String username = jwtUtil.extractUsername(jwtUtil.extractToken(token));

        if(quoteService.findByQuote(quote.getQuote()) != null){
            return new ApiResponse<>(HttpStatus.OK.value(), "ALREADY-EXISTS",null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", quoteService.save(quote, username));
    }

    @GetMapping("/")
    public ApiResponse<Page<OutputQuoteDto>> listQuotes(@RequestHeader(HEADER_STRING) String token, Pageable pageable){
        String username = jwtUtil.extractUsername(jwtUtil.extractToken(token));
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", quoteService.findAll(username, pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<OutputQuoteDto> getOneQuote(@RequestHeader(HEADER_STRING) String token, @PathVariable int id, Pageable pageable){
        String username = jwtUtil.extractUsername(jwtUtil.extractToken(token));
        OutputQuoteDto quote = quoteService.convertQuote(quoteService.findById(id, username), username);
        return new ApiResponse<>(HttpStatus.OK.value(), quote == null ? "NOT-EXISTS" : "SUCCESS", quote);
    }

    @PutMapping("/{id}")
    public ApiResponse<QuoteDto> updateQuote(@RequestHeader(HEADER_STRING) String token, @RequestBody QuoteDto quoteDto, @PathVariable int id) {
        String username = jwtUtil.extractUsername(jwtUtil.extractToken(token));

        if(!quoteService.canBeUpdate(id, quoteDto.getQuote())){
            return new ApiResponse<>(HttpStatus.OK.value(), "ALREADY-EXISTS", null);
        }

        QuoteDto quote = quoteService.update(id, username, quoteDto);
        return new ApiResponse<>(HttpStatus.OK.value(), quote==null ? "NOT-FOUND" : "SUCCESS", quote);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteQuote(@RequestHeader(HEADER_STRING) String token, @PathVariable int id) {
        String username = jwtUtil.extractUsername(jwtUtil.extractToken(token));
        Quote quote = quoteService.findById(id, username);
        if(quote != null) quoteService.delete(quote.getId());
        return new ApiResponse<Void>(HttpStatus.OK.value(), quote == null ? "NOT-FOUND" : "SUCCESS", null);
    }
}

package cz.janakdom.controller.rest;

import cz.janakdom.model.ApiResponse;
import cz.janakdom.model.database.Quote;
import cz.janakdom.model.database.QuoteScore;
import cz.janakdom.model.dto.QuoteDto;
import cz.janakdom.model.dto.QuoteScoreDto;
import cz.janakdom.service.QuoteScoreService;
import cz.janakdom.service.QuoteService;
import cz.janakdom.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/score")
@CrossOrigin
public class QuoteScoreController {

    @Autowired
    private QuoteScoreService quoteScoreService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuoteService quoteService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/")
    public ApiResponse<QuoteScore> saveQuoteScore(@RequestBody QuoteScoreDto quoteScore){

        if(quoteScoreService.findById(quoteScore) != null){
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "ALREADY-EXISTS",null);
        }

        if(userService.findById(quoteScore.getUserId()) == null || quoteService.findById(quoteScore.getQuoteId()) == null){
            return new ApiResponse<>(HttpStatus.OK.value(), "USER-QUOTE-NOT-EXISTS", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", quoteScoreService.save(quoteScore));
    }

    @GetMapping("/")
    public ApiResponse<List<QuoteScore>> listAllScores(){
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", quoteScoreService.findAll());
    }

    @GetMapping("/quote/{quoteId}")
    public ApiResponse<List<QuoteScore>> listQuoteScores(@PathVariable int quoteId){
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", quoteScoreService.findQuoteAll(quoteId));
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<QuoteScore>> listUserScores(@PathVariable int userId){
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", quoteScoreService.findUserAll(userId));
    }

    @GetMapping("/{quoteId}/{userId}")
    public ApiResponse<QuoteScore> getOneQuoteScore(@PathVariable int quoteId, @PathVariable int userId){
        QuoteScore quoteScore = quoteScoreService.findById(quoteId, userId);
        return new ApiResponse<>(HttpStatus.OK.value(), quoteScore == null ? "NOT-EXISTS" : "SUCCESS", quoteScore);
    }

    @PutMapping("/")
    public ApiResponse<QuoteScore> updateQuoteScore(@RequestBody QuoteScoreDto quoteScoreDto) {
        QuoteScore quote = quoteScoreService.update(quoteScoreDto);
        return new ApiResponse<>(HttpStatus.OK.value(), quote==null ? "NOT-FOUND" : "SUCCESS", quote);
    }

    @DeleteMapping("/{quoteId}/{userId}")
    public ApiResponse<Void> deleteQuoteScore(@PathVariable int quoteId, @PathVariable int userId) {
        QuoteScore quoteScore = quoteScoreService.findById(quoteId, userId);
        if(quoteScore != null) quoteScoreService.delete(quoteScore);
        return new ApiResponse<Void>(HttpStatus.NOT_ACCEPTABLE.value(), quoteScore == null ? "NOT-FOUND" : "SUCCESS", null);
    }
}

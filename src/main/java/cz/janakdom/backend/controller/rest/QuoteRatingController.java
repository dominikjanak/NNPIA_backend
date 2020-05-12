package cz.janakdom.backend.controller.rest;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.QuoteRating;
import cz.janakdom.backend.model.database.User;
import cz.janakdom.backend.model.dto.QuoteRatingDto;
import cz.janakdom.backend.security.JwtUtil;
import cz.janakdom.backend.service.QuoteRatingService;
import cz.janakdom.backend.service.QuoteService;
import cz.janakdom.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static cz.janakdom.backend.config.Constants.HEADER_STRING;

@RestController
@RequestMapping(value = "/api/rating")
@CrossOrigin
public class QuoteRatingController {

    @Autowired
    private QuoteRatingService quoteRatingService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuoteService quoteService;
    @Autowired
    private JwtUtil jwtUtil;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/")
    public ApiResponse<QuoteRating> saveQuoteRating(@RequestHeader(HEADER_STRING) String token, @RequestBody QuoteRatingDto quoteRating){
        String username = jwtUtil.extractUsername(jwtUtil.extractToken(token));
        User user = userService.findByUsername(username);

        if(quoteRatingService.findById(quoteRating, user.getId()) != null){
            return new ApiResponse<>(HttpStatus.OK.value(), "ALREADY-EXISTS",null);
        }

        if(userService.findById(user.getId()) == null || quoteService.findById(quoteRating.getQuoteId()) == null){
            return new ApiResponse<>(HttpStatus.OK.value(), "USER-QUOTE-NOT-EXISTS", null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", quoteRatingService.save(quoteRating, user.getId()));
    }

    @GetMapping("/")
    public ApiResponse<List<QuoteRating>> listAllRating(){
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", quoteRatingService.findAll());
    }

    @GetMapping("/quote/{quoteId}")
    public ApiResponse<List<QuoteRating>> listQuoteRating(@PathVariable int quoteId){
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", quoteRatingService.findQuoteAll(quoteId));
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<QuoteRating>> listUserRating(@PathVariable int userId){
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", quoteRatingService.findUserAll(userId));
    }

    @GetMapping("/{quoteId}/{userId}")
    public ApiResponse<QuoteRating> getOneQuoteRating(@PathVariable int quoteId, @PathVariable int userId){
        QuoteRating quoteRating = quoteRatingService.findById(quoteId, userId);
        return new ApiResponse<>(HttpStatus.OK.value(), quoteRating == null ? "NOT-EXISTS" : "SUCCESS", quoteRating);
    }

    @PutMapping("/")
    public ApiResponse<QuoteRating> updateQuoteRating(@RequestHeader(HEADER_STRING) String token, @RequestBody QuoteRatingDto quoteRatingDto) {
        String username = jwtUtil.extractUsername(jwtUtil.extractToken(token));
        User user = userService.findByUsername(username);

        QuoteRating quote = quoteRatingService.update(quoteRatingDto, user.getId());
        return new ApiResponse<>(HttpStatus.OK.value(), quote==null ? "NOT-FOUND" : "SUCCESS", quote);
    }

    @DeleteMapping("/{quoteId}/{userId}")
    public ApiResponse<Void> deleteQuoteRating(@PathVariable int quoteId, @PathVariable int userId) {
        QuoteRating quoteRating = quoteRatingService.findById(quoteId, userId);
        if(quoteRating != null) quoteRatingService.delete(quoteRating);
        return new ApiResponse<Void>(HttpStatus.OK.value(), quoteRating == null ? "NOT-FOUND" : "SUCCESS", null);
    }
}

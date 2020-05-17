package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.QuoteRatingDao;
import cz.janakdom.backend.model.database.Quote;
import cz.janakdom.backend.model.database.QuoteRating;
import cz.janakdom.backend.model.database.QuoteScoreKey;
import cz.janakdom.backend.model.database.User;
import cz.janakdom.backend.model.dto.QuoteRatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "quoteScoreService")
public class QuoteRatingService {

    private final QuoteRatingDao dao;

    @Autowired private UserService userService;
    @Autowired private QuoteService quoteService;

    @Autowired
    public QuoteRatingService(QuoteRatingDao dao) {
        this.dao = dao;
    }

    public List<QuoteRating> findAll(){
        return dao.findAll();
    }

    public List<QuoteRating> findQuoteAll(int quoteId){
        return dao.findAllByQuoteId(quoteId);
    }

    public List<QuoteRating> findUserAll(int userId){
        return dao.findAllByUserId(userId);
    }

    public QuoteRating findById(int quoteId, int userId) {
        Optional<QuoteRating> optional = Optional.ofNullable(dao.findByQuoteIdAndUserId(quoteId, userId));
        return optional.orElse(null);
    }

    public QuoteRating findById(QuoteRatingDto quoteRatingDto, int userId) {
        Optional<QuoteRating> optional = Optional.ofNullable(dao.findByQuoteIdAndUserId(quoteRatingDto.getQuoteId(), userId));
        return optional.orElse(null);
    }

    public void delete(QuoteRatingDto quoteRatingDto, int userId) {
        dao.deleteByQuoteIdAndUserId(quoteRatingDto.getQuoteId(), userId);
    }

    public void delete(QuoteRating quoteRating) {
        dao.delete(quoteRating);
    }

    public void delete(int quoteId, int userId) {
        dao.deleteByQuoteIdAndUserId(quoteId, userId);
    }

    public QuoteRating update(QuoteRatingDto quoteRatingDto, int userId) {
        QuoteRating quoteRating = findById(quoteRatingDto, userId);
        if(quoteRating != null) {
            quoteRating.setScore(quoteRatingDto.getScore());
            dao.save(quoteRating);
            return quoteRating;
        }
        return null;
    }

    public QuoteRating save(QuoteRatingDto quoteRatingDto, int userId) {
        QuoteRating newScore = new QuoteRating();
        newScore.setId(new QuoteScoreKey(quoteRatingDto.getQuoteId(), userId));

        User user = userService.findById(userId);
        Quote quote = quoteService.findById(quoteRatingDto.getQuoteId());

        if(user == null || quote == null){
            return null;
        }

        newScore.setUser(user);
        newScore.setQuote(quote);
        newScore.setScore(quoteRatingDto.getScore());

        return dao.save(newScore);
    }

    public QuoteRating save(QuoteRating quoteRating) {
        return dao.save(quoteRating);
    }

    public boolean canBeUpdate(QuoteRatingDto quoteRatingDto, int userId) {
        QuoteRating find = findById(quoteRatingDto, userId);
        if(find == null) return true;
        return find.getQuote().getId().equals(quoteRatingDto.getQuoteId()) && find.getUser().getId().equals(userId);
    }
}

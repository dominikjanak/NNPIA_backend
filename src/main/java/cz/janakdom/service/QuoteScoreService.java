package cz.janakdom.service;

import cz.janakdom.dao.QuoteScoreDao;
import cz.janakdom.model.database.Quote;
import cz.janakdom.model.database.QuoteScore;
import cz.janakdom.model.database.QuoteScoreKey;
import cz.janakdom.model.database.User;
import cz.janakdom.model.dto.QuoteScoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "quoteScoreService")
public class QuoteScoreService {

    private final QuoteScoreDao dao;

    @Autowired private UserService userService;
    @Autowired private QuoteService quoteService;

    @Autowired
    public QuoteScoreService(QuoteScoreDao dao) {
        this.dao = dao;
    }

    public List<QuoteScore> findAll(){
        return dao.findAll();
    }

    public List<QuoteScore> findQuoteAll(int quoteId){
        return dao.findAllByQuoteId(quoteId);
    }

    public List<QuoteScore> findUserAll(int userId){
        return dao.findAllByUserId(userId);
    }

    public QuoteScore findById(int quoteId, int userId) {
        Optional<QuoteScore> optional = Optional.ofNullable(dao.findByQuoteIdAndUserId(quoteId, userId));
        return optional.orElse(null);
    }

    public QuoteScore findById(QuoteScoreDto quoteScoreDto) {
        Optional<QuoteScore> optional = Optional.ofNullable(dao.findByQuoteIdAndUserId(quoteScoreDto.getQuoteId(), quoteScoreDto.getUserId()));
        return optional.orElse(null);
    }

    public void delete(QuoteScoreDto quoteScoreDto) {
        dao.deleteByQuoteIdAndUserId(quoteScoreDto.getQuoteId(), quoteScoreDto.getUserId());
    }

    public void delete(QuoteScore quoteScore) {
        dao.delete(quoteScore);
    }

    public void delete(int quoteId, int userId) {
        dao.deleteByQuoteIdAndUserId(quoteId, userId);
    }

    public QuoteScore update(QuoteScoreDto quoteScoreDto) {
        QuoteScore quoteScore = findById(quoteScoreDto);
        if(quoteScore != null) {
            quoteScore.setScore(quoteScoreDto.getScore());
            dao.save(quoteScore);
            return quoteScore;
        }
        return null;
    }

    public QuoteScore save(QuoteScoreDto quoteScoreDto) {
        QuoteScore newScore = new QuoteScore();
        newScore.setId(new QuoteScoreKey(quoteScoreDto.getQuoteId(), quoteScoreDto.getUserId()));

        User user = userService.findById(quoteScoreDto.getUserId());
        Quote quote = quoteService.findById(quoteScoreDto.getQuoteId());

        if(user == null || quote == null){
            return null;
        }

        newScore.setUser(user);
        newScore.setQuote(quote);
        newScore.setScore(quoteScoreDto.getScore());

        return dao.save(newScore);
    }

    public QuoteScore save(QuoteScore quoteScore) {
        return dao.save(quoteScore);
    }

    public boolean canBeUpdate(QuoteScoreDto quoteScoreDto) {
        QuoteScore find = findById(quoteScoreDto);
        if(find == null) return true;
        return find.getQuote().getId().equals(quoteScoreDto.getQuoteId()) && find.getUser().getId().equals(quoteScoreDto.getUserId());
    }
}

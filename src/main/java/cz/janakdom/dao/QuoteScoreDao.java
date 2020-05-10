package cz.janakdom.dao;

import cz.janakdom.model.database.QuoteScore;
import cz.janakdom.model.database.QuoteScoreKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteScoreDao extends JpaRepository<QuoteScore, QuoteScoreKey> {
    QuoteScore findByQuoteIdAndUserId(Integer quote_id, Integer user_id);
    List<QuoteScore> findAllByQuoteId(Integer quote_id);
    List<QuoteScore> findAllByUserId(Integer user_id);
    void deleteByQuoteIdAndUserId(Integer quote_id, Integer user_id);
}

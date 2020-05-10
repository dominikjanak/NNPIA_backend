package cz.janakdom.dao;

import cz.janakdom.model.database.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteScoreDao extends JpaRepository<QuoteScore, QuoteScoreKey> {

}

package sofa.internetbankieren.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import sofa.internetbankieren.model.Bedrijf;
import sofa.internetbankieren.model.Medewerker;

import java.util.ArrayList;
import java.util.List;

public class BedrijfDAO {
// public class BedrijfDAO implements GenericDAO<Medewerker> {

    private JdbcTemplate jdbcTemplate;

    public BedrijfDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Bedrijf> getAllByIdAccountmanager(int personeelsnummer) {
        // TODO
        return new ArrayList<>();
    }
}

package sofa.internetbankieren.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sofa.internetbankieren.model.Bedrijf;

import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class BedrijfsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BedrijfsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // todo
    public List<Bedrijf> getAllByIdAccountmanager(int personeelsnummer) { return new ArrayList<>(); }

    // todo
    public Bedrijf getOneByID(int idBedrijf){
        return null;
    }
}

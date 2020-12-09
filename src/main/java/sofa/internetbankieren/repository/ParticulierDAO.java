package sofa.internetbankieren.repository;

/**
 * @author Taco Jongkind, 09-12-2020
 *
 * */

import org.springframework.jdbc.core.JdbcTemplate;
import sofa.internetbankieren.model.Bedrijfsrekening;
import sofa.internetbankieren.model.Particulier;

import java.util.List;

public class ParticulierDAO {

    private JdbcTemplate jdbcTemplate;

    public ParticulierDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //get All
    public List<Particulier> getAll() {
        final String sql = "select * from particulier";
        return jdbcTemplate.query(sql, new ParticulierRowMapper(), null);
    }
    // get One by Id
    public Particulier getOneByID(int idKlant){
        final String sql = "select * from particulier where idKlant=?";
        return jdbcTemplate.queryForObject(sql, new ParticulierRowMapper(), idKlant);
    }

    //get All by naam
    public Particulier getByNaam(String voornaam, String achternaam) {
        final String sql = "select * from particulier where voornaam=? and achternaam=?";
        return jdbcTemplate
                .queryForObject(sql,
                        new ParticulierRowMapper(), voornaam, achternaam);
    }
    //get All by BSN
    public Particulier getByBSN(int BSN) {
        final String sql = "select * from particulier where BSN=?";
        return jdbcTemplate
                .queryForObject(sql,
                        new ParticulierRowMapper(), BSN);
    }

}

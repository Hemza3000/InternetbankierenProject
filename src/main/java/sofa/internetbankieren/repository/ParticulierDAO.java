package sofa.internetbankieren.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import sofa.internetbankieren.model.Particulier;

import java.util.List;

public class ParticulierDAO {

    private JdbcTemplate jdbcTemplate;

    public ParticulierDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Particulier> list() {
        final String sql = "select * from particulier";
        return jdbcTemplate.query(sql, new ParticulierRowMapper(), null);
    }

    public Particulier getByNaam(String voornaam, String achternaam) {
        final String sql = "select * from particulier where voornaam=? and achternaam=?";
        return jdbcTemplate
                .queryForObject(sql,
                        new ParticulierRowMapper(), voornaam, achternaam);
    }
}

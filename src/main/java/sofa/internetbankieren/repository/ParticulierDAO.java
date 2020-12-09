package sofa.internetbankieren.repository;

/**
 * @author Taco Jongkind, 09-12-2020
 *
 * */

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sofa.internetbankieren.model.Bedrijfsrekening;
import sofa.internetbankieren.model.Medewerker;
import sofa.internetbankieren.model.Particulier;

import java.sql.*;
import java.util.List;

@Repository
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

    // store One
    public void storeOne(Particulier particulier) {
        final String sql = "INSERT INTO particulier(voornaam, tussenvoegsels, achternaam, BSN, geboortedatum," +
                "straat, huisnummer, postcode, woonplaats ) values (?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"idKlant"});
                ps.setString(1, particulier.getVoornaam());
                ps.setString(2, particulier.getTussenvoegsels());
                ps.setString(3, particulier.getAchternaam());
                ps.setInt(4, particulier.getBSN());
                ps.setDate(5, (Date) particulier.getGeboortedatum());
                ps.setString(6, particulier.getStraatnaam());
                ps.setInt(8, particulier.getHuisnummer());
                ps.setString(9, particulier.getPostcode());
                ps.setString(10, particulier.getWoonplaats());
                return ps;
            }
        }, keyHolder);
        particulier.setIdKlant((Integer) keyHolder.getKey());
    }
    //update one
    public int updateOne(Particulier particulier) {
        return jdbcTemplate.update("update particulier set voornaam=?, tussenvoegsels=?, achternaam=?, BSN=?," +
                        "geboortedatum=?, straatnaam=?, huisnummer=?, postcode=?, woonplaats=? " +
                        "where idKlant=?",
                particulier.getVoornaam(),
                particulier.getTussenvoegsels(),
                particulier.getAchternaam(),
                particulier.getBSN(),
                particulier.getGeboortedatum(),
                particulier.getStraatnaam(),
                particulier.getHuisnummer(),
                particulier.getPostcode(),
                particulier.getWoonplaats(),
                particulier.getIdKlant());
    }

    public int deleteOne(Particulier particulier) {
        return jdbcTemplate.update("delete from particulier where idKlant=?",
                particulier.getIdKlant());
    }

    //RowMapper

    public class ParticulierRowMapper implements RowMapper<Particulier> {

        @Override
        public Particulier mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Particulier(resultSet.getInt("idKlant"),
                    resultSet.getString("straatnaam"),
                    resultSet.getInt("huisnummer"),
                    resultSet.getString("postcode"),
                    resultSet.getString("woonplaats"),
                    resultSet.getString("voornaam"),
                    resultSet.getString("tussenvoegsel"),
                    resultSet.getString("achternaam"),
                    resultSet.getDate("geboortedatum"),
                    resultSet.getInt("BSN"));
        }
    }

}

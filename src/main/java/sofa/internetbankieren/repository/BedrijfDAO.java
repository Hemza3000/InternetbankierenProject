package sofa.internetbankieren.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sofa.internetbankieren.model.Bedrijf;
import sofa.internetbankieren.model.Bedrijfsrekening;
import sofa.internetbankieren.model.Medewerker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hemza Lasri, 10-12-2020
 *
 * */

@Repository
@Primary
public class BedrijfDAO implements GenericDAO<Bedrijf> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BedrijfDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    // Retrieves all customers
    public List<Bedrijf> getAll() {
        final String sql = "SELECT * FROM bedrijf";
        return jdbcTemplate.query(sql, new BedrijfsMapper());
    }

    // Retrieves all corporate customers by accountmanager ID
    public List<Bedrijf> getAllByIdAccountmanager(int idAccountmanager) {
        final String sql = "SELECT * FROM bedrijf WHERE idaccountmanager=?";
        return jdbcTemplate.query(sql, new BedrijfsMapper());
    }

    // Retrieves one corporate customer by ID
    public Bedrijf getOneByID(int idBedrijf) {
        final String sql = "SELECT * FROM bedrijf WHERE idbedrijf=?";
        return jdbcTemplate.queryForObject(sql, new BedrijfsMapper(), idBedrijf);
    }

    @Override

    //Stores new customer in database
    public void storeOne(Bedrijf bedrijf) {
        String sql = "insert into bedrijf (gebruikersnaam, wachtwoord, straat, huisnummer, postcode, woonplaats, bedrijfsnaam, kvknummer, sector, btwnummer) values (?,?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"idBedrijf"});
                ps.setString(1, bedrijf.getGebruikersnaam());
                ps.setString(2, bedrijf.getWachtwoord());
                ps.setString(3, bedrijf.getStraatnaam());
                ps.setInt(4, bedrijf.getHuisnummer());
                ps.setString(5, bedrijf.getPostcode());
                ps.setString(6, bedrijf.getWoonplaats());
                ps.setString(7, bedrijf.getBedrijfsnaam());
                ps.setInt(8, bedrijf.getKVKNummer());
                ps.setString(9, bedrijf.getSector());
                ps.setString(10, bedrijf.getBTWNummer());
                return ps;
            }
        }, keyHolder);
        bedrijf.setIdKlant(keyHolder.getKey().intValue());
    }

    //Updates customer in database by ID
    @Override
    public void updateOne(Bedrijf bedrijf) {
        jdbcTemplate.update("update bedrijf set gebruikersnaam=?, wachtwoord=?, straatnaam=?, huisnummer=?, postcode=?, woonplaats=?, bedrijfsnaam=?, KVKnummer=?, sector=?, BTWnummer=? + where idBedrijf=?",
                bedrijf.getGebruikersnaam(),
                bedrijf.getWachtwoord(),
                bedrijf.getStraatnaam(),
                bedrijf.getHuisnummer(),
                bedrijf.getPostcode(),
                bedrijf.getWoonplaats(),
                bedrijf.getBedrijfsnaam(),
                bedrijf. getKVKNummer(),
                bedrijf.getSector(),
                bedrijf.getBTWNummer());
    }

    //Deletes customer by ID
    public void deleteOne(Bedrijf bedrijf) {
         jdbcTemplate.update("DELETE FROM bedrijf WHERE idBedrijf=?",
                bedrijf.getIdKlant());
    }


    //Rowmapper

    class BedrijfsMapper implements RowMapper<Bedrijf> {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Override
        public Bedrijf mapRow(ResultSet resultSet, int i) throws SQLException {
            BedrijfsrekeningDAO bedrijfsrekeningDAO = new BedrijfsrekeningDAO(jdbcTemplate);
            MedewerkerDAO medewerkerDAO = new MedewerkerDAO(jdbcTemplate);
            return new Bedrijf(
                    resultSet.getInt("idBedrijf"),
                    resultSet.getString("gebruikersnaam"),
                    resultSet.getString("wachtwoord"),
                    resultSet.getString("straatnaam"),
                    resultSet.getInt("huisnummer"),
                    resultSet.getString("postcode"),
                    resultSet.getString("woonplaats"),
                    resultSet.getString("bedrijfsnaam"),
                    resultSet.getInt("KVKNummer"),
                    resultSet.getString("sector"),
                    resultSet.getString("BTWnummer"),
                    medewerkerDAO.getOneByID(resultSet.getInt("idAccountmanager")),
                    bedrijfsrekeningDAO.getAllByBedrijf(resultSet.getInt("idBedrijf")));
        }

    }
}


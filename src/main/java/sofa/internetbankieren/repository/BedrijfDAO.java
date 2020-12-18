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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Hemza Lasri, 10-12-2020
 *
 * */

@Repository
public class BedrijfDAO implements GenericDAO<Bedrijf> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BedrijfDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    // Retrieves all customers
    public List<Bedrijf> getAll() {
        final String sql = "select * from bedrijf";
        return jdbcTemplate.query(sql, new BedrijfsMapper());
    }

    // Retrieves all corporate customers by accountmanager ID
    public List<Bedrijf> getAllByIdAccountmanager(int idAccountmanager) {
        final String sql = "select * from bedrijf where idaccountmanager=?";
        return jdbcTemplate.query(sql, new BedrijfsMapper(), idAccountmanager);
    }

    public List<Integer> getAllIDsByIdAccountmanager(int idAccountmanager) {
        final String sql = "select idbedrijf from bedrijf where idaccountmanager=?";
        return jdbcTemplate.query(sql, new BedrijfsIDMapper(), idAccountmanager);
    }

    // Retrieves one corporate customer by ID
    public Bedrijf getOneByID(int idBedrijf) {
        final String sql = "select * from bedrijf where idbedrijf=?";
        return jdbcTemplate.queryForObject(sql, new BedrijfsMapper(), idBedrijf);
    }

    @Override

    //Stores new customer in database
    public void storeOne(Bedrijf bedrijf) {
        String sql = "insert into bedrijf (gebruikersnaam, wachtwoord, straat, huisnummer, postcode, woonplaats, " +
                "bedrijfsnaam, kvknummer, sector, btwnummer, idaccountmanager) values (?,?,?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"idBedrijf"});
                ps.setString(1, bedrijf.getGebruikersnaam());
                ps.setString(2, bedrijf.getWachtwoord());
                ps.setString(3, bedrijf.getStraat());
                ps.setInt(4, bedrijf.getHuisnummer());
                ps.setString(5, bedrijf.getPostcode());
                ps.setString(6, bedrijf.getWoonplaats());
                ps.setString(7, bedrijf.getBedrijfsnaam());
                ps.setInt(8, bedrijf.getKVKNummer());
                ps.setString(9, bedrijf.getSector());
                ps.setString(10, bedrijf.getBTWNummer());
                ps.setInt(11, bedrijf.getAccountmanager().getPersoneelsnummer());
                return ps;
            }
        }, keyHolder);
        bedrijf.setIdKlant(keyHolder.getKey().intValue());
    }

    //Updates customer in database by ID
    @Override
    public void updateOne(Bedrijf bedrijf) {
        jdbcTemplate.update("update bedrijf set gebruikersnaam=?, wachtwoord=?, straat=?, huisnummer=?, postcode=?, woonplaats=?, bedrijfsnaam=?, KVKnummer=?, sector=?, BTWnummer=? + where idBedrijf=?",
                bedrijf.getGebruikersnaam(),
                bedrijf.getWachtwoord(),
                bedrijf.getStraat(),
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
         jdbcTemplate.update("delete from bedrijf where idBedrijf=?",
                bedrijf.getIdKlant());
    }


    //Rowmapper

    private final class BedrijfsMapper implements RowMapper<Bedrijf> {

        @Override
        public Bedrijf mapRow(ResultSet resultSet, int i) throws SQLException {
            BedrijfsrekeningDAO bedrijfsrekeningDAO = new BedrijfsrekeningDAO(jdbcTemplate);
            MedewerkerDAO medewerkerDAO = new MedewerkerDAO(jdbcTemplate);
            return new Bedrijf(
                    resultSet.getInt("idBedrijf"),
                    resultSet.getString("gebruikersnaam"),
                    resultSet.getString("wachtwoord"),
                    resultSet.getString("straat"),
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

    private final class BedrijfsIDMapper implements RowMapper<Integer> {
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt("idBedrijf");
        }
    }
}


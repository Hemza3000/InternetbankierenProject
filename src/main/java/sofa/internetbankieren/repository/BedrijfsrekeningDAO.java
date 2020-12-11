package sofa.internetbankieren.repository;

/**
 * @Author Wichert Tjerkstra aangemaakt op 8 dec
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sofa.internetbankieren.model.Bedrijfsrekening;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Primary
public class BedrijfsrekeningDAO implements GenericDAO<Bedrijfsrekening> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BedrijfsrekeningDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    // get One by Id
    public Bedrijfsrekening getOneByID(int idBedrijfsrekening){
        final String sql = "SELECT * FROM bedrijfsrekening WHERE idbedrijfsrekening=?";
            return jdbcTemplate.queryForObject(sql, new BedrijfsrekeningRowMapper(), idBedrijfsrekening);
    }

    // get All
    public List<Bedrijfsrekening> getAll() {
        final String sql = "SELECT * FROM bedrijfsrekening";
        return jdbcTemplate.query(sql, new BedrijfsrekeningRowMapper(), null);
    }

    // get All By Rekeninghouder (Bedrijf)
    public List<Bedrijfsrekening> getAllByBedrijf(int idRekeninghouder) {
        final String sql = "SELECT * FROM bedrijfsrekening WHERE idbedrijf=?";
        return jdbcTemplate.query(sql, new BedrijfsrekeningRowMapper(), idRekeninghouder);
    }

    // get All By Contactpersoon (Contactpersoon)
    public List<Bedrijfsrekening> getAllByContactpersoon(int idContactpersoon) {
        final String sql = "SELECT * FROM bedrijfsrekening WHERE idcontactpersoon=?";
        return jdbcTemplate.query(sql, new BedrijfsrekeningRowMapper(), idContactpersoon);
    }

    // update One
    public void updateOne(Bedrijfsrekening bedrijfsrekening) {
         jdbcTemplate.update("UPDATE bedrijfsrekening SET idbedrijf=?, " +
                " idcontactpersoon=?, saldo=?, IBAN=? WHERE idbedrijfsrekening=?",
                bedrijfsrekening.getRekeninghouder().getIdKlant(),
                bedrijfsrekening.getContactpersoon().getIdKlant(),
                bedrijfsrekening.getSaldo(),
                bedrijfsrekening.getIBAN(),
                bedrijfsrekening.getIdRekening());
    }

    // store One
    public void storeOne(Bedrijfsrekening bedrijfsrekening) {
        final String sql = "INSERT INTO bedrijfsrekening (idbedrijf, idcontactpersoon, saldo, iban) values (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"idbedrijfsrekening"});
                ps.setInt(1, bedrijfsrekening.getRekeninghouder().getIdKlant());
                ps.setInt(2, bedrijfsrekening.getContactpersoon().getIdKlant());
                ps.setDouble(3, bedrijfsrekening.getSaldo());
                ps.setString(4, bedrijfsrekening.getIBAN());
                return ps;
            }
        }, keyHolder);
        bedrijfsrekening.setIdRekening(keyHolder.getKey().intValue());   // TODO wil jij dit uitleggen wat dit doet?
    }


    // delete One
    public void deleteOne(Bedrijfsrekening bedrijfsrekening) {
        jdbcTemplate.update("DELETE FROM bedrijfsrekening WHERE idrekening=?",
                bedrijfsrekening.getIdRekening());
    }

}


class BedrijfsrekeningRowMapper implements RowMapper<Bedrijfsrekening> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Bedrijfsrekening mapRow(ResultSet resultSet, int i) throws SQLException {
        ParticulierDAO particulierDAO = new ParticulierDAO(jdbcTemplate);
        BedrijfDAO bedrijfDAO = new BedrijfDAO(jdbcTemplate);
        return new Bedrijfsrekening(resultSet.getInt("idbedrijfsrekening"),
                                    resultSet.getString("iban"),
                                    resultSet.getDouble("saldo"),
                (particulierDAO.getOneByID(resultSet.getInt("idcontactpersoon"))),
                    (bedrijfDAO.getOneByID(resultSet.getInt("idbedrijf"))));
    }
}
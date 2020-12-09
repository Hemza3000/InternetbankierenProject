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
import sofa.internetbankieren.model.Bedrijf;
import sofa.internetbankieren.model.Bedrijfsrekening;
import sofa.internetbankieren.model.Particulier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Primary
public class BedrijfsrekeningDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BedrijfsrekeningDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    // get One by Id
    public Bedrijfsrekening getOneByID(int idBedrijfsrekening){
        final String sql = "SELECT * FROM Bedrijfsrekening WHERE idBedrijfsrekening=?";
            return jdbcTemplate.queryForObject(sql, new BedrijfsrekeningRowMapper(), idBedrijfsrekening);
    }

    // get All
    public List<Bedrijfsrekening> getAll() {
        final String sql = "SELECT * FROM Bedrijfsrekening";
        return jdbcTemplate.query(sql, new BedrijfsrekeningRowMapper(), null);
    }

    // get All By Rekeninghouder (Bedrijf)
    public List<Bedrijfsrekening> getAllByBedrijf(int idRekeninghouder) {
        final String sql = "SELECT * FROM Bedrijfsrekening WHERE idBedrijf=?";
        return jdbcTemplate.query(sql, new BedrijfsrekeningRowMapper(), idRekeninghouder);
    }

    // get All By Contactpersoon (Contactpersoon)
    public List<Bedrijfsrekening> getAllByContactpersoon(int idContactpersoon) {
        final String sql = "SELECT * FROM Bedrijfsrekening WHERE idContactpersoon=?";
        return jdbcTemplate.query(sql, new BedrijfsrekeningRowMapper(), idContactpersoon);
    }

    // update One
    public int updateOne(Bedrijfsrekening bedrijfsrekening) {
        return jdbcTemplate.update("UPDATE bedrijfsrekening SET idBedrijf=?, " +
                " idContactpersoon=?, saldo=?, IBAN=? WHERE idBedrijfsrekening=?",
                bedrijfsrekening.getRekeninghouder().getIdKlant(),
                bedrijfsrekening.getContactpersoon().getIdKlant(),
                bedrijfsrekening.getSaldo(),
                bedrijfsrekening.getIBAN(),
                bedrijfsrekening.getIdRekening());
    }

    // store One
/*    public Number storeOne(Bedrijfsrekening bedrijfsrekening) {
        final String sql = "INSERT INTO bedrijfsrekening (idBedrijf, idContactpersoon, Saldo, IBAN) values (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"idBedrijfsrekening"});
                ps.setInt(1, bedrijfsrekening.getRekeninghouder().getIdKlant());
                ps.setInt(2, bedrijfsrekening.getContactpersoon().getIdKlant());
                ps.setDouble(3, bedrijfsrekening.getSaldo());
                ps.setString(4, bedrijfsrekening.getIBAN());
                return ps;
            }
        }, KeyHolder);
        return KeyHolder.getKey();
    }

    // delete One
    public int deleteOne(Bedrijfsrekening bedrijfsrekening) {
        return jdbcTemplate.update("DELETE FROM bedrijfsrekening WHERE idRekening=?",
                bedrijfsrekening.getIdRekening());
    }*/

}


class BedrijfsrekeningRowMapper implements RowMapper<Bedrijfsrekening> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Bedrijfsrekening mapRow(ResultSet resultSet, int i) throws SQLException {
        ParticulierDAO particulierDAO = new ParticulierDAO(jdbcTemplate);
        BedrijfsDAO bedrijfsDAO = new BedrijfsDAO(jdbcTemplate);
        return new Bedrijfsrekening(resultSet.getInt("idBedrijfsrekening"),
                                    resultSet.getString("IBAN"),
                                    resultSet.getDouble("saldo"),
                (particulierDAO.getOneByID(resultSet.getInt("idContactpersoon"))),
                    (bedrijfsDAO.getOneByID(resultSet.getInt("idBedrijf"))));
    }
}
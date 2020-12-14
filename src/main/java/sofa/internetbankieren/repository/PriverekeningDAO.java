package sofa.internetbankieren.repository;

/**
 * @Author Wichert Tjerkstra aangemaakt op 9 dec
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sofa.internetbankieren.model.Priverekening;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PriverekeningDAO implements GenericDAO<Priverekening> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PriverekeningDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

        // get One by Id
    public Priverekening getOneByID(int idPriverekening){
        final String sql = "SELECT * FROM priverekening WHERE idpriverekening=?";
        return jdbcTemplate.queryForObject(sql, new PriverekeningRowMapper(), idPriverekening);
    }

        // get All
    public List<Priverekening> getAll() {
        final String sql = "SELECT * FROM priverekening";
        return jdbcTemplate.query(sql, new PriverekeningRowMapper(), null);
    }

    // get All By Rekeninghouder
    public List<Priverekening> getAllByRekeninghouder(int idRekeninghouder) {
        final String sql = "SELECT * FROM priverekening WHERE idrekeninghouder=?";
        return jdbcTemplate.query(sql, new PriverekeningRowMapper(), idRekeninghouder);
    }

    // update One
    public void updateOne(Priverekening priverekening) {
         jdbcTemplate.update("UPDATE priverekening SET idrekeninghouder=?, " +
                        " saldo=?, iban=? WHERE idpriverekening=?",
                priverekening.getRekeninghouder().getIdKlant(),
                priverekening.getSaldo(),
                priverekening.getIBAN(),
                priverekening.getIdRekening());
    }

    // store One
    public void storeOne(Priverekening priverekening) {
        final String sql = "INSERT INTO priverekening (idrekeninghouder, saldo, iban) values (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"idpriverekening"});
                ps.setInt(1, priverekening.getRekeninghouder().getIdKlant());
                ps.setDouble(2, priverekening.getSaldo());
                ps.setString(3, priverekening.getIBAN());
                return ps;
            }
        }, keyHolder);
        priverekening.setIdRekening(keyHolder.getKey().intValue());
    }

    // delete One
    public void deleteOne(Priverekening priverekening) {
        jdbcTemplate.update("DELETE FROM priverekening WHERE idrekening=?",
                priverekening.getIdRekening());
    }

}

final class PriverekeningRowMapper implements RowMapper<Priverekening> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Priverekening mapRow(ResultSet resultSet, int i) throws SQLException {
        ParticulierDAO particulierDAO = new ParticulierDAO(jdbcTemplate);
        return new Priverekening(resultSet.getInt("idPriverekening"),
                                resultSet.getString("IBAN"),
                                resultSet.getDouble("Saldo"),
                (particulierDAO.getOneByID(resultSet.getInt("idRekeninghouder"))));
    }
}


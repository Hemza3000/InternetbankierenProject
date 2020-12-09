package sofa.internetbankieren.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.model.Priverekening;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Primary
public class PriverekeningDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PriverekeningDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

        // get One by Id
    public Priverekening getOneByID(int idPriverekening){
        final String sql = "SELECT * FROM priverekening WHERE idPriverekening=?";
        return jdbcTemplate.queryForObject(sql, new PriverekeningRowMapper(), idPriverekening);
    }

        // get All
    public List<Priverekening> getAll() {
        final String sql = "SELECT * FROM priverekening";
        return jdbcTemplate.query(sql, new PriverekeningRowMapper(), null);
    }

    // get All By Rekeninghouder
    public List<Priverekening> getAllByRekeninghouder(int idRekeninghouder) {
        final String sql = "SELECT * FROM priverekening WHERE idRekeninghouder=?";
        return jdbcTemplate.query(sql, new PriverekeningRowMapper(), idRekeninghouder);
    }


    // update One
    public int updateOne(Priverekening priverekening) {
        return jdbcTemplate.update("UPDATE priverekening SET idRekeninghouder=?, " +
                        " saldo=?, IBAN=? WHERE idPriverekening=?",
                priverekening.getRekeninghouder().getIdKlant(),
                priverekening.getSaldo(),
                priverekening.getIBAN(),
                priverekening.getIdRekening());
    }

//    // store One
//    public Number storeOne(Bedrijfsrekening bedrijfsrekening) {
//        ParticulierDAO particulierDAO = new ParticulierDAO(JdbcTemplate jdbcTemplate);
//        BedrijfsDAO bedrijfsDAO = new BedrijfsDAO(JdbcTemplate jdbcTemplate);
//        Bedrijf bedrijf = new Bedrijf(bedrijfsDAO.getOneByID(bedrijfsrekening.getRekeninghouder()));
//        Particulier particulier = new Particulier(particulierDAO.getOneByID(bedrijfsrekening.getContactpersoon());
//
//        final String sql = "INSERT INTO bedrijfsrekening (idBedrijf, idContactpersoon, Saldo, IBAN) values (?,?,?,?)";
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"idBedrijfsrekening"});
//                ps.setInt(1, bedrijf.getIdKlant());
//                ps.setInt(2, particulier.getIdKlant());
//                ps.setDouble(3, bedrijfsrekening.getSaldo());
//                ps.setString(4, bedrijfsrekening.getIBAN());
//                return ps;
//            }
//        }, KeyHolder);
//        return KeyHolder.getKey();
//    }

    // delete One
    public int deleteOne(Priverekening priverekening) {
        return jdbcTemplate.update("DELETE FROM Priverekening WHERE idRekening=?",
                priverekening.getIdRekening());
    }

}


class PriverekeningRowMapper implements RowMapper<Priverekening> {

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


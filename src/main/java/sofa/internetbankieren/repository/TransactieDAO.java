package sofa.internetbankieren.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sofa.internetbankieren.model.*;

import java.sql.*;
import java.util.List;

/**
 * @author Wendy Ellens
 *
 * Voegt transactiegegevens toe aan de SQL-database of haalt deze eruit op.
 */
@Repository
public class TransactieDAO implements GenericDAO<Transactie> {

    private JdbcTemplate jdbcTemplate;
    private PriverekeningDAO priverekeningDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;

    public TransactieDAO(JdbcTemplate jdbcTemplate, PriverekeningDAO priverekeningDAO,
                         BedrijfsrekeningDAO bedrijfsrekeningDAO) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.priverekeningDAO = priverekeningDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
    }

    @Override
    public List<Transactie> getAll() {
        String sql = "select * from transactie";
        return jdbcTemplate.query(sql, new TransactieRowMapper());
    }

    public List<Transactie> getAllByIDPriverekening(int idRekening) {
        String sql = "select * from transactie where idpriverekening = ?";
        return jdbcTemplate.query(sql, new TransactieRowMapper(), idRekening);
    }

    public List<Transactie> getAllByIDBedrijfsrekening(int idRekening) {
        String sql = "select * from transactie where idbedrijfsrekening = ?";
        return jdbcTemplate.query(sql, new TransactieRowMapper(), idRekening);
    }

    @Override
    public Transactie getOneByID(int idTransactie) {
        String sql = "select * from transactie where idtransactie = ?";
        return jdbcTemplate.queryForObject(sql, new TransactieRowMapper(), idTransactie);
    }

    @Override
    public void storeOne(Transactie transactie) {
        String sql = "insert into transactie (bedrag, transactiebeschrijving, datum, bijschrijving, " +
                "idbedrijfsrekening, idpriverekening) values (?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"idtransactie"});
            ps.setDouble(1, transactie.getBedrag());
            ps.setString(2, transactie.getOmschrijving());
            ps.setTimestamp(3, Timestamp.valueOf(transactie.getDatum()));
            ps.setBoolean(4, transactie.isBijschrijving());
            if (transactie.getRekening() instanceof Bedrijfsrekening) {
                ps.setInt(5, ((Bedrijfsrekening) transactie.getRekening()).getIdRekening());
                ps.setNull(6, Types.INTEGER); //todo werkt het ook zonder deze regel?
            }
            else {
                ps.setNull(5, Types.INTEGER); //todo werkt het ook zonder deze regel?
                ps.setInt(6, ((Priverekening) transactie.getRekening()).getIdRekening());
            }
            return ps;
        }, keyHolder);
        transactie.setIdTransactie(keyHolder.getKey().intValue());
    }

    @Override
    public void updateOne(Transactie transactie) {
        jdbcTemplate.update("update transactie set bedrag = ?, transactiebeschrijving = ?, datum = ?, " +
                        "bijschrijving = ?, idbedrijfsrekening = ?, idpriverekening = ? where idtransactie = ?",
            transactie.getBedrag(),
            transactie.getOmschrijving(),
            Timestamp.valueOf(transactie.getDatum()),
            transactie.isBijschrijving(),
            transactie.getRekening() instanceof Bedrijfsrekening ?
                ((Bedrijfsrekening) transactie.getRekening()).getIdRekening() : null,
            transactie.getRekening() instanceof Priverekening ?
                ((Priverekening) transactie.getRekening()).getIdRekening() : null,
            transactie.getIdTransactie()
            );
    }

    @Override
    public void deleteOne(Transactie transactie) {
        jdbcTemplate.update("delete from transactie where idtransactie = ?",
                transactie.getIdTransactie());
    }

    private final class TransactieRowMapper implements RowMapper<Transactie> {
        @Override
        public Transactie mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Transactie(
                resultSet.getInt("idtransactie"),
                resultSet.getInt("idbedrijfsrekening") == 0 ?
                    priverekeningDAO.getOneByID(resultSet.getInt("idpriverekening")) :
                    bedrijfsrekeningDAO.getOneByID(resultSet.getInt("idbedrijfsrekening")),
                // todo controleren of precies een van beide rekeningvelden is gevuld?
                resultSet.getBoolean("bijschrijving"),
                // todo controleren of bijschrijving 0 of 1 is?
                resultSet.getDouble("bedrag"),
                resultSet.getTimestamp("datum").toLocalDateTime(),
                resultSet.getString("transactiebeschrijving")
                );
        }
    }
}

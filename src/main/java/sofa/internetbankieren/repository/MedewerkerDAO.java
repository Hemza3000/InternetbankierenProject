package sofa.internetbankieren.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sofa.internetbankieren.model.Medewerker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Wendy Ellens
 *
 * Voegt medewerkergegevens toe aan de SQL-database of haalt deze eruit op.
 */
@Repository
public class MedewerkerDAO implements GenericDAO<Medewerker>{

    private JdbcTemplate jdbcTemplate;

    public MedewerkerDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Medewerker> getAll() {
        String sql = "select * from medewerker";
        return jdbcTemplate.query(sql, new MedewerkerRowMapper());
    }

    @Override
    public Medewerker getOneByID(int personeelsnummer) {
        String sql = "select * from medewerker where personeelsnummer=?";
        return jdbcTemplate.queryForObject(sql, new MedewerkerRowMapper(), personeelsnummer);
    }

    @Override
    public void storeOne(Medewerker medewerker) {
        String sql = "insert into medewerker (voornaam, tussenvoegsels, achternaam, rol) values (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"personeelsnummer"});
                ps.setString(1, medewerker.getVoornaam());
                ps.setString(2, medewerker.getTussenvoegsels());
                ps.setString(3, medewerker.getAchternaam());
                ps.setString(4, medewerker.getRol().name());
                return ps;
            }
        }, keyHolder);
        medewerker.setPersoneelsnummer(keyHolder.getKey().intValue());
    }

    @Override
    public void updateOne(Medewerker medewerker) {
        jdbcTemplate.update("update medewerker set voornaam=?, tussenvoegsels=?, achternaam=?, rol=? " +
                        "where personeelsnummer=?",
                medewerker.getVoornaam(),
                medewerker.getTussenvoegsels(),
                medewerker.getAchternaam(),
                medewerker.getRol().name(),
                medewerker.getPersoneelsnummer());
    }

    @Override
    public void deleteOne(Medewerker medewerker) {
        jdbcTemplate.update("delete from medewerker where personeelsnummer = ?",
                medewerker.getPersoneelsnummer());
    }

    private class MedewerkerRowMapper implements RowMapper<Medewerker> {
        @Override
        public Medewerker mapRow(ResultSet resultSet, int i) throws SQLException {
            BedrijfDAO bedrijfDAO = new BedrijfDAO(jdbcTemplate);
            return new Medewerker(
                    resultSet.getInt("personeelsnummer"),
                    resultSet.getString("voornaam"),
                    resultSet.getString("tussenvoegsels"),
                    resultSet.getString("achternaam"),
                    Medewerker.Rol.valueOf(resultSet.getString("rol")),
                    // TODO IllegalArgumentException afvangen?
                    bedrijfDAO.getAllByIdAccountmanager(resultSet.getInt("personeelsnummer"))
            );
        }
    }
}



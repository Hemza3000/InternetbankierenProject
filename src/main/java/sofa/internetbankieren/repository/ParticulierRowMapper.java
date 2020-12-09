package sofa.internetbankieren.repository;


/**
 * @author Taco Jongkind, 09-12-2020
 *
 * */

import sofa.internetbankieren.model.Particulier;

import org.springframework.jdbc.core.RowMapper;
//import javax.swing.tree.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

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

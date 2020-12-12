package sofa.internetbankieren.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sofa.internetbankieren.model.Transactie;

import javax.sql.rowset.JdbcRowSet;
import java.util.List;

/**
 * @author Wendy Ellens
 * <p>
 * Voegt transactiegegevens toe aan de SQL-database of haalt deze eruit op.
 */
@Repository
public class TransactieDAO implements GenericDAO<Transactie> {

    private JdbcTemplate jdbcTemplate;

    public TransactieDAO(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transactie> getAll() {
        return null;
    }

    @Override
    public Transactie getOneByID(int id) {
        return null;
    }

    @Override
    public void storeOne(Transactie type) {

    }

    @Override
    public void updateOne(Transactie type) {

    }

    @Override
    public void deleteOne(Transactie type) {

    }
}

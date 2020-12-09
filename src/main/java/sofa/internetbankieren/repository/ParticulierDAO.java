package sofa.internetbankieren.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sofa.internetbankieren.model.Particulier;


@Repository
@Primary
public class ParticulierDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ParticulierDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Particulier getOneByID(int idRekeninghouder){
        return null;
    }


}

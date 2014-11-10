package bo.net.tigo.dao;

import bo.net.tigo.model.FTPParameter;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aralco on 11/9/14.
 */
@Repository
public class FTPParameterDaoImpl implements FTPParameterDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(FTPParameter ftpParameter) {

    }

    @Override
    public void update(FTPParameter ftpParameter) {

    }

    @Override
    public FTPParameter findLast(Long userId) {
        return null;
    }

    @Override
    public List<FTPParameter> findAll() {
        return null;
    }
}

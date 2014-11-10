package bo.net.tigo.dao;

import bo.net.tigo.model.City;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aralco on 11/9/14.
 */
@Repository
public class CityDaoImpl implements CityDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(City city) {

    }

    @Override
    public void update(City city) {

    }

    @Override
    public City findOne(Long cityId) {
        return null;
    }

    @Override
    public List<City> findAll() {
        return null;
    }
}

package bo.net.tigo.service;

import bo.net.tigo.dao.AccessLogDao;
import bo.net.tigo.dao.CityDao;
import bo.net.tigo.dao.ContactDao;
import bo.net.tigo.dao.UserDao;
import bo.net.tigo.model.AccessLog;
import bo.net.tigo.model.City;
import bo.net.tigo.model.Contact;
import bo.net.tigo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by aralco on 10/22/14.
 */
@Service
@Transactional
public class ConfigurationService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private AccessLogDao accessLogDao;

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationService.class);

    @Transactional
    public User createUser(User user)  {
        userDao.save(user);
        return user;
    }

    @Transactional
    public User getUser(Long userId)  {
        return userDao.findOne(userId);
    }

    @Transactional
    public User updateUser(User user)  {
        userDao.update(user);
        return user;
    }

    @Transactional
    public List<User> getUsers()  {
        return userDao.findAll();
    }

    @Transactional
    public Contact createContact(Contact contact)  {
        contactDao.save(contact);
        return contact;
    }

    @Transactional
    public Contact getContact(Long contactId)  {
        return contactDao.findOne(contactId);
    }

    @Transactional
    public Contact updateContact(Contact contact)  {
        contactDao.update(contact);
        return contact;
    }

    @Transactional
    public List<Contact> getContacts()  {
        return contactDao.findAll();
    }

    @Transactional
    public City createCity(City city)  {
        cityDao.save(city);
        return city;
    }

    @Transactional
    public List<City> getCities()  {
        return cityDao.findAll();
    }

    @Transactional
    public List<AccessLog> getAccessLogs()  {
        return accessLogDao.findAll();
    }

}

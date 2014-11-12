package bo.net.tigo.service;

import bo.net.tigo.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by aralco on 10/22/14.
 */
@Service
public class ConfigurationService {
    @Autowired
    private UserDao userDao;

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationService.class);

}

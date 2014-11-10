package bo.net.tigo.rest;

import bo.net.tigo.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by aralco on 11/9/14.
 */
@Controller
@RequestMapping(value = "/config",
        consumes = "application/json",
        produces = "application/json")
public class ConfigurationResource {

    @RequestMapping(value = "/city", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<City> createCity(@RequestBody City city)   {
        return null;
    }

    @RequestMapping(value = "/city", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<City>> viewTask()   {
        return null;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact)   {
        return null;
    }

    @RequestMapping(value = "/contact/{contactId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Contact> viewContact(@PathVariable Long contactId)   {
        return null;
    }


    @RequestMapping(value = "/contact/{contactId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Contact> updateContact(@PathVariable Long contactId, @RequestBody Contact contact)   {
        return null;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<City>> viewContacts()   {
        return null;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> createUser(@RequestBody User user)   {
        return null;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> viewUser(@PathVariable Long userId)   {
        return null;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> updateUser(@PathVariable Long contactId, @RequestBody User user)   {
        return null;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> viewUsers()   {
        return null;
    }

    @RequestMapping(value = "/accesslog", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<AccessLog>> viewAccessLogs()   {
        return null;
    }

    @RequestMapping(value = "/ftp", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<FTPParameter> viewFTPParameters()   {
        return null;
    }

    @RequestMapping(value = "/ftp", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<FTPParameter> updateFTPParameters(@RequestBody FTPParameter ftpParameter)   {
        return null;
    }

}

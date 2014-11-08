package bo.net.tigo.security;

import org.springframework.ldap.core.LdapTemplate;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by aralco on 11/7/14.
 */
public class MyAuthenticator {
    private LdapTemplate ldapTemplate = null;

//    public MyAuthenticator(LdapTemplate ldapTemplate) {
//        this.ldapTemplate = ldapTemplate;
//    }

    public LdapTemplate getLdapTemplate() {
        return ldapTemplate;
    }

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

//    public boolean login(String username, String password)  {
//        AndFilter filter = new AndFilter();
//        filter.and(new EqualsFilter("objectclass","user")).
//                and(new EqualsFilter("sAMAccountName", username));
//        return ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password);
//    }
    public boolean login(String user, String pass) {

        String LDAPSearchBase = "DC=tigo,DC=net,DC=bo";
        String LDAPUrl = "ldap://73.20.0.6:389";
        String LDAPSecurityAuthentication = "simple";
        String LDAPDominio= "TIGOBO";


        boolean respuesta = true;
        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user + "))";
        String[] attribs = {"sAMAccountName"};
        //Create the search controls
        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(attribs);
        //Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAPUrl);
        env.put(Context.SECURITY_AUTHENTICATION, LDAPSecurityAuthentication);
        env.put(Context.SECURITY_PRINCIPAL, LDAPDominio + "\\" + user);
        env.put(Context.SECURITY_CREDENTIALS, pass);

        LdapContext ctxGC = null;
        try {
            ctxGC = new InitialLdapContext(env, null);
            //Search objects in GC using filters
            NamingEnumeration answer = ctxGC.search(LDAPSearchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes attrs = sr.getAttributes();
                Map amap = null;
                if (attrs != null) {
                    amap = new HashMap();
                    NamingEnumeration ne = attrs.getAll();
                    while (ne.hasMore()) {
                        Attribute attr = (Attribute) ne.next();
                        amap.put(attr.getID(), attr.getAll());
                    }
                    ne.close();
                }
            }
        } catch (NamingException ne) {
            //Loguear el error
            System.out.println("ERROR: " + ne.getExplanation());
            respuesta = false;
        } finally {
            try {
                if (ctxGC != null) {
                    ctxGC.close();
                }
            } catch (NamingException ne) {
                //Loguear el error
                System.out.println("Error al cerrar la conexion con LDAP: " + ne.getMessage());
            }
        }
        return respuesta;
    }
}

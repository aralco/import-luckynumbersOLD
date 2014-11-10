package bo.net.tigo.dao;

import bo.net.tigo.model.FTPParameter;

import java.util.List;

/**
 * Created by aralco on 11/9/14.
 */
public interface FTPParameterDao {
    public void save(FTPParameter ftpParameter);
    public void update(FTPParameter ftpParameter);
    public FTPParameter findLast(Long userId);
    public List<FTPParameter> findAll();

}

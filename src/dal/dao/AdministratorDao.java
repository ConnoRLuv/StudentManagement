package dal.dao;

import dal.Entity.Administrator;

import java.util.List;

public interface AdministratorDao {
    boolean certifyAdministrator(String Id, String Password);

    List<Administrator> selectAdministrator(String sql);

    List<Administrator> selectAllAdministrator();

    String selectAdministratorByAno(String Ano);
}

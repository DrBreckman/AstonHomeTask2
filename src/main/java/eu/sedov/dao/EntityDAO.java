package eu.sedov.dao;

public interface EntityDAO {
    String createTableIfNotExist();
    String getAll();
    String getById();
    String insert();
    String delete();
    String update();

}

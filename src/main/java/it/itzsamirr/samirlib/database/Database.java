package it.itzsamirr.samirlib.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author ItzSamirr
 * Created at 23.06.2022
 **/
public interface Database {
    Connection getConnection();
}

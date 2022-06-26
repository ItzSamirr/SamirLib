package it.itzsamirr.samirlib.database;

import com.google.common.collect.Maps;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.RowSetFactoryImpl;
import it.itzsamirr.samirlib.SamirLib;
import it.itzsamirr.samirlib.event.database.DatabaseSetupEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @author ItzSamirr
 * Created at 24.06.2022
 **/
public abstract class Database<T extends JavaPlugin> {
    private T plugin;
    private Connection connection;
    private String classname, url, user, password;
    private HashMap<String, List<DefaultValue<?>>> defaultValues = new HashMap<>();

    public Database(T plugin, String url, String classname, String user, String password, String[] defaultTables, DefaultValue<?>[]... defaultValues) {
        this.plugin = plugin;
        this.url = url;
        this.classname = classname;
        this.user = user;
        this.password = password;
        for (int i = 0; i < defaultTables.length; i++) {
            List<DefaultValue<?>> list = Arrays.asList(defaultValues[i]);
            this.defaultValues.put(defaultTables[i], list);
        }
    }

    public HashMap<String, List<DefaultValue<?>>> getDefaultValues(){
        return this.defaultValues;
    }
    @SuppressWarnings("unchecked cast")
    public <V> DefaultValue<V> getDefaultValue(String table, String name, Class<V> clazz){
        return defaultValues.get(table).stream().filter(d -> d.name.equalsIgnoreCase(name))
                .findFirst()
                .map(d -> (DefaultValue<V>)d)
                .orElse(null);
    }


    public T getPlugin() {
        return plugin;
    }

    public void setPlugin(T plugin) {
        this.plugin = plugin;
    }

    public Connection getConnection() {
        if(!isClosed())return connection;
        try {
            Class.forName(classname);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(String sql, Object... objects){
        PreparedStatement statement = prepareStatement(sql, objects);
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> false);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                statement.executeUpdate();
                future.complete(true);
            } catch (SQLException e) {
                e.printStackTrace();
                future.complete(false);
            }
        });
        return future.join();
    }

    public void setup(){
        DatabaseSetupEvent<T> event = new DatabaseSetupEvent<>(this);
        SamirLib.getInstance().getEventManager().call(event);
        if(event.isCancelled())return;
        createDefaultTables();
        testConnection();
    }

    public void testConnection(){
        List<String> list = new ArrayList<>();
        defaultValues.forEach((key, value) -> list.add(key + "|(" + value.toString().replace("[", "").replace("]", "") + ")"));
        list.forEach(s -> {
            String sql = "SELECT * FROM " + s.split("\\|")[0];
            PreparedStatement statement = this.prepareStatement(sql);
            close(null, statement, null, null);
        });
    }

    public void createDefaultTables(){
        List<String> list = new ArrayList<>();
        defaultValues.forEach((key, value) -> list.add(key + "|(" + value.toString().replace("[", "").replace("]", "") + ")"));
        list.forEach(s -> {
            String sql = "CREATE TABLE IF NOT EXISTS " + s.split("\\|")[0] + " " + s.split("\\|")[1];
            this.update(sql);
        });
    }

    public PreparedStatement prepareStatement(String sql, Object... objects){
        CompletableFuture<PreparedStatement> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(plugin ,() -> {
            try{
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql);
                for (int i = 0; i < objects.length; i++) {
                    statement.setObject(i+1, objects[i]);
                }
                future.complete(statement);
            }catch(Exception e){
                e.printStackTrace();
                future.completeExceptionally(e);
            }
            close(connection, null, null, null);
        });
        return future.join();
    }

    public ResultSet resultSet(String sql, Object... objects){
        PreparedStatement statement = prepareStatement(sql, objects);
        CompletableFuture<ResultSet> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try{
                ResultSet rs = statement.executeQuery();
                future.complete(rs);
            }catch (Exception e){
                e.printStackTrace();
                future.completeExceptionally(e);
            }
            close(null, statement, null, null);
        });
        return future.join();
    }

    public CachedRowSet rowSet(String sql, Object... objects){
        CompletableFuture<CachedRowSet> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            ResultSet rs = resultSet(sql, objects);
            try{
                CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet();
                rowSet.populate(rs);
                future.complete(rowSet);
            }catch(Exception e){
                e.printStackTrace();
                future.completeExceptionally(e);
            }
            close(null, null, rs, null);
        });
        return future.join();
    }

    public void close(Connection connection, PreparedStatement statement, ResultSet set, CachedRowSet row){
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(set != null) {
            try {
                set.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(row != null) {
            try {
                row.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isClosed(){
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Data
    @AllArgsConstructor
    public static class Value<V>{
        protected String name;
        protected String type;
        protected V object;
    }

    public static final class DefaultValue<E> extends Value<E>{
        public DefaultValue(String name, String type) {
            super(name, type, null);
        }
    }
}

package model.dao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.connection.ConnectionFactory;

/**
 * Class AbstractDAO defines the common operations for accessing a table
 */
public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;


    @SuppressWarnings("unchecked")
    /**
     * Constructor for the abstractDAO class
     */
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Creates a query for selecting everything from a field
     *
     * @param field
     * @return
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Creates a query in order to find all the object from a field
     *
     * @return
     */
    public String findAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("*");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * Find all function
     *
     * @return
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = findAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Find by ID function
     *
     * @param id
     * @return
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            tryConnection(query);

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            endConnection();
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Creates a JTable
     *
     * @return
     */
    public JTable createTable() {
        List<T> objects = findAll();
        if (!objects.isEmpty()) {
            int tableSize = objects.get(0).getClass().getDeclaredFields().length;
            String[] columnNames = new String[tableSize];
            int columnIndex = 0;
            for (Field currentField : objects.get(0).getClass().getDeclaredFields()) {
                currentField.setAccessible(true);
                try {
                    columnNames[columnIndex] = currentField.getName();
                    columnIndex++;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            DefaultTableModel myModel = new DefaultTableModel();
            myModel.setColumnIdentifiers(columnNames);
            for (Object o : objects) {
                Object[] obj = new Object[tableSize];
                int col = 0;
                for (Field currentField : o.getClass().getDeclaredFields()) {
                    currentField.setAccessible(true);
                    try {
                        obj[col] = currentField.get(o);
                        col++;
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                myModel.addRow(obj);
            }
            JTable table = new JTable(myModel);
            table.setEnabled(true);
            table.setVisible(true);
            return table;
        }
        return null;
    }

    public void tryConnection(String query) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        connection = ConnectionFactory.getConnection();
        statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.executeUpdate();
    }

    public void endConnection() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ConnectionFactory.close(resultSet);
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
    }

    private PreparedStatement createStatement(PreparedStatement st, T t) throws InvocationTargetException, IllegalAccessException, IntrospectionException, SQLException {
        int index = 1;
        for (Field field : type.getDeclaredFields()) {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
            Method method = propertyDescriptor.getReadMethod();
            st.setObject(index, method.invoke(t));
            index++;
        }
        return st;
    }

    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder("INSERT INTO " + type.getSimpleName().toLowerCase(Locale.ROOT) + "");
        sb.append(" VALUES (");
        int columns = 0;
        for (Field field : type.getDeclaredFields()) {
            columns++;
        }
        int i = 0;
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            sb.append("?");
            if (columns - 1 != i)
                sb.append(",");
            i++;
        }
        sb.append(")");
        return sb.toString();
    }

    public T insert(T t) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        ResultSet rs = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(createInsertQuery(), Statement.RETURN_GENERATED_KEYS);
            insertStatement = createStatement(insertStatement, t);
            System.out.println(insertStatement);
            insertStatement.executeUpdate();
            rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor("id", type);
            Method method = propertyDescriptor.getWriteMethod();
            method.invoke(t, insertedId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid Data");
        } finally {
            ConnectionFactory.close(dbConnection);
        }
        return t;
    }


    public void Update(Object genericObject) {
        String tableName = genericObject.getClass().getSimpleName().toLowerCase(Locale.ROOT);
        StringBuilder query = new StringBuilder();
        query.append("update " + tableName + " set ");
        Field[] allFields = genericObject.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < allFields.length - 1; i++) {
                allFields[i].setAccessible(true);
                query.append(allFields[i].getName());
                query.append(" = ");
                Object value = allFields[i].get(genericObject);
                String fieldType = allFields[i].getType().getSimpleName();
                if (fieldType.equals("String"))
                    query.append("\'" + value + "\'");
                else
                    query.append(value);
                query.append(", ");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at updating");
        }
        int lastFieldIndex = allFields.length - 1;
        allFields[lastFieldIndex].setAccessible(true);
        query.append(allFields[lastFieldIndex].getName());
        query.append(" = ");
        try {
            Object value = allFields[lastFieldIndex].get(genericObject);
            String fieldType = allFields[lastFieldIndex].getType().getSimpleName();
            if (fieldType.equals("String"))
                query.append("\'" + value + "\'");
            else
                query.append(value);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at updating");
        }

        query.append(" where ");
        Field firstField = allFields[0];
        firstField.setAccessible(true);
        String fieldName = firstField.getName();

        query.append(fieldName).append(" = ");

        try {
            Object value = firstField.get(genericObject);
            query.append(value);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at geting id value");
        }
        try {
            System.out.println(query);
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement prepUpdateStatement = con.prepareStatement(query.toString());
            prepUpdateStatement.executeUpdate();
            con.close();
            prepUpdateStatement.close();
            JOptionPane.showMessageDialog(null, "Record updated succesfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception when executing update query");
        }
    }


    /**
     * Creates a query in order to delete from a field
     *
     * @param delete
     * @return
     */
    private String createDeleteQuery(String delete) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append("FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        sb.append(delete);
        return sb.toString();
    }

    /**
     * delete function
     *
     * @param id
     * @return
     */
    public String delete(String id) {
        String query = createDeleteQuery("id = " + id);
        try {
            tryConnection(query);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
            return "Bad find.";
        } finally {
            endConnection();
        }
        return null;
    }
}


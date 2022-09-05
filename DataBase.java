/*
This class is for all the database basic operation
Dec.23rd.2019
 */
//package comsci;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBase
{
//declear attributes

    private String dbName;
    private Connection dbConnection;
    private ArrayList<ArrayList<String>> data;

    public DataBase()
    {//initinal
        this.dbName = "";
        this.dbConnection = null;
        this.data = null;
    }

    public DataBase(String dbName)
    {//connect
        setDbName(dbName);
        createDb(dbName);
        this.data = null;
    }

    //set & get
    public void setDbName(String dbName)
    {
        this.dbName = dbName;
    }

    public void setDbConnection(Connection dbConnection)
    {
        this.dbConnection = dbConnection;
    }

    public void setData(ArrayList<ArrayList<String>> data)
    {
        this.data = data;
    }

    public String getDbName()
    {
        return this.dbName;
    }

    public Connection getDbConnection()
    {
        return this.dbConnection;
    }

    public ArrayList<ArrayList<String>> getData()
    {
        return this.data;
    }

    public ArrayList<String> getOneDimentionalData(String tableName, String tableHeaders)
    {
        ResultSet rs;
        ArrayList<String> data;
        data = new ArrayList<>();
        try
        {//get result set
            rs = dbConnection.createStatement().executeQuery("SELECT * FROM " + tableName);
            while (rs.next())
            {
                data.add(rs.getString(tableHeaders));
            }
        }
        catch (SQLException e)
        {
            new ErrorFrame(e.getMessage());
        }
        return data;
    }

    public ArrayList<ArrayList<String>> getData(String tableName, String[] tableHeaders)
    {
        ResultSet rs;
        int columnCount = tableHeaders.length;
        data = new ArrayList<>();
        try
        {//get result set
            rs = dbConnection.createStatement().executeQuery("SELECT * FROM " + tableName);
            ArrayList<String> oneLineData;
            while (rs.next())
            {
                oneLineData = new ArrayList<>(columnCount);
                for (int i = 0; i < columnCount; i++)
                {
                    oneLineData.add(rs.getString(tableHeaders[i]));
                }
                this.data.add(oneLineData);
            }
        }
        catch (SQLException e)
        {
            new ErrorFrame(e.getMessage());
        }
        // System.out.println("Got data");
        return data;
    }

    public ArrayList<ArrayList<String>> joinAndGetData(String firstTableName, String secondTableName,
            String thirdTableName, String firstPrimaryKey, String firstPrimaryKey2,
            String secondPrimaryKey, String thirdPrimaryKey,
            String[] requestHeaders)
    {
//        SELECT article.aid,article.title,user.username,type.typename FROM article INNER JOIN user 
//ON article.uid=user.uid INNER JOIN type ON article.tid=type.tid
        ResultSet rs;
        int columnCount = requestHeaders.length;
        data = new ArrayList<>();
        String headers = "";
        for (int i = 0; i < requestHeaders.length; i++)
        {
            headers += requestHeaders[i];
        }
        try
        {//get result set
            rs = dbConnection.createStatement().executeQuery("SELECT * FROM " + firstTableName
                    + " INNER JOIN " + secondTableName + " ON " + firstTableName + "." + firstPrimaryKey
                    + "=" + secondTableName + "." + secondPrimaryKey + " INNER JOIN " + thirdTableName
                    + " ON " + firstTableName + "." + firstPrimaryKey2 + "=" + thirdTableName + "."
                    + thirdPrimaryKey);
            ArrayList<String> oneLineData;
            while (rs.next())
            {
                oneLineData = new ArrayList<>(columnCount);
                for (int i = 0; i < columnCount; i++)
                {
                    oneLineData.add(rs.getString(requestHeaders[i]));
                }
                this.data.add(oneLineData);
            }
        }
        catch (SQLException e)
        {
            new ErrorFrame(e.getMessage());
        }
        // System.out.println("Got data");
        return data;
    }

    public void deleteBaseOnTwoVariablesTable(String tableName,
            String variable1, String value1, String variable2, String value2)
    {
        dbRunStatement("DELETE FROM " + tableName + " WHERE " + variable1 + " = " + value1
                + " and " + variable2 + "=" + value2);
    }

    private void createDb(String dbName)//create or connect
    {
        setDbName(dbName);
        String connectUrl = "jdbc:derby:" + this.dbName + ";create=true";
        try
        {//connect
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            this.dbConnection = DriverManager.getConnection(connectUrl);
        }
        catch (ClassNotFoundException e)
        {//show error
            new ErrorFrame("DataBase error: data base connect error");
        }
        catch (SQLException e)
        {
            new ErrorFrame("DataBase Connect error");
        }
    }

    public void dbRunStatement(String statement)
    {
        try
        {//run statement
            dbConnection.createStatement().execute(statement);
        }
        catch (SQLException e)
        {//show error
            new ErrorFrame("run sql statement error " + e.getMessage());
        }
    }

    public void createTable(String tableName, String attributes)
    {//creat a table in db base on name and attribute
        dbRunStatement("CREATE TABLE " + tableName + "(" + attributes + ")");
    }

    public void insertIntoTable(String tableName, Object[] values)
    {
        //construct the statement
        String statement = "";
        for (int i = 0; i < values.length; i++)
        {
            if (values[i].getClass().getName().equals("java.lang.String"))
            {
                statement = statement + "\'" + values[i] + "\'";
            }
            else
            {
                statement = statement + values[i];
            }
            if (i + 1 < values.length)
            {
                statement += ",";
            }
            else
            {
                statement += "";
            }
        }
        dbRunStatement("INSERT INTO " + tableName + " VALUES (" + statement + ")");
    }

    public void deleteFromTable(String tableName, String variable, String value)
    {
        dbRunStatement("DELETE FROM " + tableName + " WHERE " + variable + " = " + value);
    }

    public void updateInTable(String tableName, String variable, Object searchValue, Object[] column, Object[] values)
    {//construct the statement
        String statement = "UPDATE " + tableName + " " + "\nSET ";
        for (int i = 0; i < column.length && i < values.length; i++)
        {
            if (values[i].getClass().getName().equals("java.lang.String"))
            {
                statement += column[i] + "=\'" + values[i] + "\'";
            }
            else
            {
                statement += column[i] + "=" + values[i];
            }
            if (i + 1 < column.length && i + 1 < values.length)
            {
                statement += ",";
            }
            else
            {
                statement += " ";
            }
        }//run it
        if (searchValue.getClass().getName().equals("java.lang.String"))
        {
            statement += "\'" + searchValue + "\'";
        }
        else
        {
            statement += searchValue;
        }
        statement += " Where " + variable + "=" + searchValue;
        //System.out.println(statement);
        dbRunStatement(statement);
    }

    public boolean isTableEmpty(String tableName, String tableHeader)
    {
        ResultSet rs;
        ArrayList<String> data = new ArrayList<>();
        try
        {//get result set
            rs = dbConnection.createStatement().executeQuery("SELECT * FROM " + tableName);
            while (rs.next())
            {
                data.add(rs.getString(tableHeader));
            }
        }
        catch (SQLException e)
        {
            new ErrorFrame(e.getMessage());
        }
        return data.size() > 0;
    }

    @Override
    public String toString()
    {
        return "dataBase{" + "dbName=" + dbName + ", dbConnection=" + dbConnection + ", data=" + data + '}';
    }

}

/*
This class is used to install database 
Nov.17.2019
 */
//package comsci;

public class InstallDb
{
//connect and create db
    private static DataBase install = new DataBase("StudentInfo");

    public static void main(String[] args)
    {
        //create and initial tables
        install.createTable("studentTable", "name varchar(128), id varchar(128)"
                + ",gradeLevel int,height double, primary key(id)");
        install.createTable("assignmentTable", "assignmentId int, assignmentName varchar(128),"
                + "assignmentGrade int," + "primary key(assignmentId)");
        install.createTable("gradeTable", "assignmentId int, studentId varchar(128),assignmentGrade double");
        install.createTable("loginInfo", "userName varchar(128), password varchar(128)");
        install.createTable("Appointment", "time varchar(128), date varchar(128), people varchar(128)"
                + ", location varchar(128)");
        install.createTable("assignmentId", "id int");
        install.insertIntoTable("assignmentId", new Object[]
        {
            0
        });

    }
}

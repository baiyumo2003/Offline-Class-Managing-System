/*
Class for the test
Nov.19th.2019
 */
//package comsci;

import java.util.ArrayList;

public class TestTable
{

    public static void main(String arg[])
    {
        DataBase tepo = new DataBase("StudentInfo");
        ArrayList<ArrayList<String>> Object = tepo.joinAndGetData("gradeTable",
                "studentTable", "assignmentTable", "studentId", "assignmentId", "id",
                "assignmentId", new String[]
                {
                    "name", "assignmentName", "assignmentGrade"
                });
        int tepo1;

//        for (int i = 0; i < Object.size(); i++)
//        {
//            for (int j = 0; j < Object.size() - 1; j++)
//            {
//                tepo1 = Object.get(j).get(0).compareTo(Object.get(j + 1).get(0));
//                if (tepo1 > 0)
//                {
//                    ArrayList<String> r = Object.get(j);
//                    Object.set(j, Object.get(j + 1));
//                    Object.set(j + 1, r);
//                }
//            }
//        }

        for (int i = 0; i < Object.size(); i++)
        {
            for (int j = 0; j < Object.get(0).size(); j++)
            {
                System.out.print(Object.get(i).get(j) + "   ");
            }
            System.out.println("");
        }

    }
}

import java.util.*;
import java.io.*;

public class Employee
{
    protected String name;
    protected String employeeNumber;
    protected String department;
    protected char type;

    //Constructors
    public Employee()
    {
        name = "";
        employeeNumber = "";
        department = "";
        type = '\0';
    }

    public Employee(String id, String empNum, String dept)
    {
        name = id;
        employeeNumber = empNum;
        department = dept;
    }
    //Accessors
    public String getName()
    {
        return name;
    }

    public String getEmployeeNumber()
    {
        return employeeNumber;
    }

    public String getDepartment()
    {
        return department;
    }

    public char getType()
    {
        return type;
    }
    //Mutators
    public void setName(String id)
    {
        name = id;
    }

    public void setEmployeeNumber(String empNum)
    {
        employeeNumber = empNum;
    }

    public void setDepartment(String dept)
    {
        department = dept;
    }
    //Helper Methods
    public boolean equals (Employee e)
    {
        boolean found = false;

        if (e.getEmployeeNumber() == employeeNumber)
        {
            found = true;
        }

        return found;
    }

    protected double calcWeeklySalary()
    {
        double salary = 0.0;
        return salary;
    } 

    public String toString()
    {
        String result;

        result = name + " " + employeeNumber + " " + department; 

        return result;
    }

    public void writeData(String fileLoc) throws IOException
    {
        PrintWriter out = new PrintWriter (new FileWriter(fileLoc, true));

        out.println();
        out.print(toString());
        System.out.println("Successfully written to file");

        out.close();
    }

    public String printDetails()
    {
        String result;

        result = "Employee Name: \t" + name + "\n" +
        "Employee Number:" + employeeNumber + "\n" +
        "Department: \t" + department + "\n";

        return result;
    }
}

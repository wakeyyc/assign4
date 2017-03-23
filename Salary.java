import java.util.*;
import java.io.*;
public class Salary extends Employee
{
    private double yearlySalary;
    private char type = 'S';

    //Constructors
    public Salary()
    {
        yearlySalary = 0.0;
    }

    public Salary(String id, String empNum, String dept, double yearSal)
    {
        super(id, empNum, dept);
        yearlySalary = yearSal;
    }

    //Accessor
    public double getYearlySalary()
    {
        return yearlySalary;
    }

    //Mutator
    public void setYearlySalary(double yearSal)
    {
        yearlySalary = yearSal;
    }
    //Helper Methods
    public double calcWeeklySalary()
    {
        double salary = yearlySalary * (1/52);

        return salary;
    }    

    public String toString()
    {
        String result;

        result = super.toString(); 
        result = result + " " + type + " " + yearlySalary;

        return result;
    }

    public void writeData(String fileLoc) throws IOException
    {
        super.writeData(fileLoc);
    }

    public String printDetails()
    {
        String result;

        result = super.printDetails();
        result = result + "Yearly Salary: \t" + yearlySalary;

        return result;
    }
}

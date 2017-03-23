import java.util.*;
import java.io.*;
public class Payroll extends Employee
{
    private ArrayList<Employee> list;
    private int numPeople;
    private String fileName;

    public Payroll ()
    {
        list = new ArrayList<Employee>();
        numPeople = 0;
        fileName = "x";
    }    

    /**
     *  Loads information from user text file
     */
    public int loadData() throws IOException
    {
        String name;
        String employeeNum;
        String department;
        char type;
        char placeHold;
        double payRate;
        double hoursWorked;
        double yearlySalary;
        int weeksWorked;
        double weeklySalary;
        double weeklySales;
        double yearlySales;
        double commission;       
        int i = 0;

        Employee emp1 = new Employee();

        Scanner input = new Scanner (System.in);

        System.out.println("Enter file name to load: ");
        try{
            fileName = input.nextLine();
            Scanner file = new Scanner (new File(fileName));
            while (file.hasNextLine())
            {
                name = file.next();
                employeeNum = file.next();
                department = file.next();
                placeHold = file.next().toUpperCase().charAt(0);

                if (placeHold == 'H')
                {
                    type = 'H';
                    payRate = file.nextDouble();
                    hoursWorked = file.nextDouble();

                    emp1 = new Hourly(name, employeeNum, department, payRate, hoursWorked);
                    list.add(emp1);
                }
                else if (placeHold == 'S')
                {
                    type = 'S';
                    yearlySalary = file.nextDouble();

                    emp1 = new Salary(name, employeeNum, department, yearlySalary);
                    list.add(emp1);
                }
                else if (placeHold == 'C')
                {
                    type = 'C';
                    weeksWorked = file.nextInt();
                    weeklySalary = file.nextDouble();
                    weeklySales = file.nextDouble();
                    yearlySales = file.nextDouble();
                    commission = file.nextDouble();

                    emp1 = new Commission(name, employeeNum, department, weeksWorked, weeklySalary, weeklySales, yearlySales, commission);
                    list.add(emp1);
                }
                i++;
            }
            file.close();
        }
        catch (FileNotFoundException a) {
            System.out.println("Invalid file name.");
        }
        switch (fileName){
            case "x": //Invalid Input repeats prompt
            break;
        }       
        numPeople += i;
        return i;
    }

    /**
     * Validates employee number and returns employee if found
     */
    private Employee validateID(String num)
    {
        Employee foundEmployee = null;
        boolean found = false;
        int i = 0;

        while (i < numPeople && !found)
        {
            if (list.get(i).getEmployeeNumber().equals(num))
            {
                foundEmployee = list.get(i);
                found = true;
            }
            i++;
        }
        return foundEmployee;
    }

    /**
     * Validates employee name and returns employee if found
     */
    private Employee validateName(String num)
    {
        Employee foundEmployee = null;
        boolean found = false;
        int i = 0;

        while (i < numPeople && !found)
        {
            if (list.get(i).getName().equals(num))
            {
                foundEmployee = list.get(i);
                found = true;
            }
            i++;
        }
        return foundEmployee;
    }

    public void addEmployee(String num) throws IOException
    {
        int i = 0;
        Employee check = null;

        check = validateID(num);

        if(check != null)
        {
            System.out.println("Employee cannot be created. Employee already exists."); 
        }
        else    
        {
            System.out.println("Employee was not found. The employee will be created.");
            newEmployeeInfo(num);
        }
    }

    /**
     * Removes employee once user confirms prompt
     */
    public void removeEmployee(String num)
    {
        char verify = '\0';
        Employee check = null;        
        check = validateID(num);

        Scanner scan = new Scanner (System.in);        
        if (check != null)
        {
            System.out.println("You are about to delete " + getName() + " ID: " + check.getEmployeeNumber() + ".\n" +
                "Enter 'Y' to delete or press any key to cancel");
            verify = scan.nextLine().toUpperCase().charAt(0);
            if (verify == 'Y')
            {
                list.remove(check);
                numPeople--;
                System.out.println("Employee: " + num + " was removed.");
                
            }
            else
            {
                System.out.println(getName() + " will not be removed.");
            }
        }
        else 
        {
            System.out.println("Employee could not be found.");  
        }
        System.out.println("Returning to Main Menu.");  
    }

    /**
     * Requests user for input on new employee 
     */
    public Employee newEmployeeInfo(String num) throws IOException
    {
        String name = "";
        String empNum = "";
        String dept = "";
        char type = '\0';
        //Hourly
        double pay = 0;
        double hoursW = 0;
        //Salary
        double yearSal = 0;
        //Commission
        int numWeeks = 0;
        double weekSal = 0;
        double wSales = 0;
        double ySales = 0;
        double comm = 0;
        boolean valid = false;
        String error = "x";

        Employee emp = null;
        Scanner scan = new Scanner(System.in);

        empNum = num;
        System.out.println("Enter Employee Name: ");
        name = scan.nextLine();
        System.out.println("Enter Department: ");
        dept = scan.nextLine();

        while (valid == false)
        {
            System.out.println("Enter employee type (H - Hourly, S - Salary, C - Commission): ");
            type = scan.nextLine().toUpperCase().charAt(0);

            if (type == 'H')
            {
                System.out.println("Enter pay rate: ");
                pay = scan.nextDouble();
                System.out.println("Enter hours worked this week: ");
                hoursW = scan.nextDouble();
                emp = new Hourly(name, empNum, dept, pay, hoursW);
                emp.writeData(fileName);

                System.out.println("Employee created successfully!"); 

                valid = true;
            }
            else if (type == 'S')
            {
                System.out.println("Enter yearly salary amount: ");
                yearSal = scan.nextDouble();
                emp = new Salary(name, empNum, dept, yearSal);
                emp.writeData(fileName);

                System.out.println("Employee created successfully!"); 

                valid = true;
            }
            else if (type == 'C')
            {
                System.out.println("Enter number of weeks worked since start of year or employment: ");
                numWeeks = scan.nextInt();
                System.out.println("Enter base weekly salary amount: ");
                weekSal = scan.nextDouble();
                System.out.println("Enter weekly sales: ");
                wSales = scan.nextDouble();
                System.out.println("Enter yearly sales: ");
                ySales = scan.nextDouble();
                System.out.println("Enter commission rate: ");
                comm = scan.nextDouble();
                emp = new Commission(name, empNum, dept, numWeeks, weekSal, wSales, ySales, comm);
                emp.writeData(fileName);

                System.out.println("Employee created successfully!"); 

                valid = true;
            }
            else
            {
                System.out.println("Invalid employee type. Please try again");
            }
        }

        list.add(emp);
        numPeople++;
        return emp;
    }

    public void printEmployee(String num)
    {
        Employee check = null;        
        check = validateID(num);

        if (check != null)
            System.out.println (check.printDetails());
        else
            System.out.println ("The employee number " + num + " does not exist.");
    }

    /**
     * Calculates and prints weekly salary of employee
     */
    public void weeklySalary(String num)
    {
        Employee check = null;        
        check = validateID(num);
        double yearlySalary = 0.0;

        if ((check != null))
            System.out.println ("Weekly Salary for " + check.getName() + " is $" + check.calcWeeklySalary());
        else
            System.out.println ("The employee number " + num + " does not exist.");
    }

    /**
     * Top Seller's Club requires member to have sales greater than $1,500 per week
     */
    public void topEmployees()
    {
        int i = 0;
        boolean check = false;
        boolean check2 = false;

        Commission com = new Commission();
        System.out.println("Top Seller Club Members:");
        while (i < numPeople)
        {
            check = false;
            if (list.get(i).getType() == 'C')
            {
                check = com.topSeller();
                if (check = true)
                {
                    System.out.println(list.get(i).getName());
                    check2 = true;
                }
            }
            i++;
        }
        if (check2 = false)
        {
            System.out.println("No employee is a member of the top sellers club.");
        }
    }

    public void salaryReport()
    {
        int i = 0;

        System.out.println("Salary Report");
        while (i < numPeople)
        {
            System.out.print(list.get(i).getName() + "\t" + list.get(i).getEmployeeNumber() + "\t" + list.get(i).getDepartment() + " \t");
            
            if (list.get(i).getType() == 'H')
                System.out.print("Hourly" + "\t\t");
            else if (list.get(i).getType() == 'C')
                System.out.print("Commission" + "\t");
            else if (list.get(i).getType() == 'S')
                System.out.print("Salary" + "\t\t");
            
            System.out.println(list.get(i).calcWeeklySalary());
            i++;
        }        
    }

    public void endOfWeek() throws IOException
    {
        System.out.println("Executing end of week process");
        reset();
        exportData();
    }

    /**
     * Resets sales and hours worked
     */
    public void reset()
    {
        int i = 0;
        double sales = 0.0;
        int hours = 0;
        
        Scanner input = new Scanner (System.in);
        while (i < numPeople)
        {
            if (list.get(i).getType() == 'C')
            {
                setWeeklySales(0.0);
            }
            else
            {
                setHoursWorked(0);
            }
            i++;
        }
    }
    
    /**
     * Requests user for current week values
     */
    public void newWeek()
    {
        int i = 0;
        double sales = 0.0;
        int hours = 0;
        
        Scanner input = new Scanner (System.in);
        
        System.out.println("New week processessing. Please enter new values.");
        while (i < numPeople)
        { 
            if (list.get(i).getType() == 'C')
            {
                System.out.print("Enter weekly sales for " + list.get(i).getName() + " : ");
                sales = input.nextDouble();
                setWeeklySales(sales);
            }            
            else if (list.get(i).getType() == 'H')
            {
                System.out.print("Enter hours worked for " + list.get(i).getName() + " : ");
                hours = input.nextInt();
                setHoursWorked(hours);
            }
            i++;
        }
    }
        
    /**
     * Exports data to new file
     */
    private void exportData() throws IOException
    {
        String confirm = "";

        int i = 0;
        Scanner input = new Scanner (System.in);

        System.out.print("Exporting payroll data. Enter new file name (include .txt extension): ");
        fileName = input.nextLine();

        PrintWriter out = new PrintWriter (fileName);
        while (i < numPeople)
        {
            out.print(list.get(i).toString());
            i++;
            if (i < numPeople)
            {
                out.println();
            }
        }
        System.out.println("Export successful. New file is called '" + fileName + "'");
        out.close();
    }
}

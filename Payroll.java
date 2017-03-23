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
        /**
        Hourly x1 = new Hourly("Sally", "333-333-333", "Finance", 'H', 15.75, 0.0);
        Salary x2 = new Salary("Arnie", "222-222-222", "Human Resources", 'S', 52000);
        Commission x3 = new Commission("Bobby", "111-111-111", "Appliances", 'C', 10, 300, 0.0, 10000, 10.0);
        Commission x4 = new Commission("Rich", "123-123-123", "Cookware", 'C', 20, 500.0, 0.0, 200000.0, 20.0);
        list.add(x1);
        list.add(x2);
        list.add(x3); 
        list.add(x4);
        numPeople = 4;
        return numPeople;
         */
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
     * NOTE: Should we combine this with addEmployee()?
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

        check = validateName(num);

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
     * -- Should the verify be in its own method? --
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

        Employee emp = null;
        Scanner scan = new Scanner(System.in);

        name = num;
        System.out.println("Enter Employee ID for " + name + " : ");
        empNum = scan.nextLine();
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

        if (check != null)
            System.out.println ("Weekly Salary for " + check.getName() + " is $" + check.calcWeeklySalary());
        else
            System.out.println ("The employee number " + num + " does not exist.");
    }

    /**
     * Top Seller's Club requires member to have sales greater than $1,500 per week
     * --TODO--
     */
    public void topEmployees()
    {
        int i = 0;
        boolean check = false;

        Commission com = new Commission();

        while (i < numPeople)
        {
            check = false;
            if (list.get(i).getType() == 'C')
            {
                check = com.topSeller();
                if (check == true)
                    System.out.println(getName());
            }
            i++;
        }
    }

    public void salaryReport()
    {
        int i = 0;

        System.out.println("Salary Report");
        while (i < numPeople)
        {
            list.get(i).printDetails();
            i++;
        }        
    }

    public void endOfWeek() throws IOException
    {
        salaryReport();
        reset();
        exportData();
    }

    /**
     * Resets sales and hours worked
     * -TODO-
     */
    public void reset()
    {
        int i = 0;

        while (i < numPeople)
        {

            i++;
        }

    }

    /**
     * Exports data to new file
     */
    private void exportData() throws IOException
    {
        String fileName = "";
        String confirm = "";

        int i = 0;
        Scanner input = new Scanner (System.in);

        System.out.print("Exporting payroll data. Enter new file name (include .txt extension): ");
        fileName = input.next();

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

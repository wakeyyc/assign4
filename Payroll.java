import java.util.*;
import java.io.*;
public class Payroll extends Employee
{
    private ArrayList<Employee> list;
    private int numPeople;
    private int empCount;
    private Scanner file;

    public Payroll ()
    {
        empCount = 0;
        list = new ArrayList<Employee>();
        numPeople = 0;

    }    

    /**
     *  Loads information from user text file
     */
    public boolean loadData() throws IOException
    {
        String name;
        String fileName;
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
        boolean valid = false;

        Employee emp1 = new Employee();

        Scanner input = new Scanner (System.in);

        System.out.println("Enter file name to load: ");

        while (valid == false) {
            try {
                fileName = input.nextLine();
                file = new Scanner (new File(fileName));
                valid = true;
            }
            catch (FileNotFoundException a) {
                System.out.println("Invalid file name.");
            }
        }

        while (file.hasNext()) {
            try{
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
            catch (NoSuchElementException a) {
                System.out.println("Error found in file, was ignored.");
            }  
        }
        numPeople = i;
        return valid;
    }

    /**
     * Validates employee number and returns employee if found
     */
    private boolean validateID(String num)
    {
        empCount = 0;
        int i = 0;
        boolean found = false;
        Employee emp = new Employee();

        emp.setEmployeeNumber(num);

        while(i < numPeople)
        {
            empCount++;
            if (list.get(i).getEmployeeNumber().equals(num) == true)
            {
                found = true;
                empCount = i;
                return found;
            }
            i++;
        }

        return found;
    }

    public void addEmployee(String num) throws IOException
    {
        if(validateID(num) != false)
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

        Scanner scan = new Scanner (System.in);        
        if (validateID(num) == true)
        {
            System.out.println("You are about to delete " + list.get(empCount).getName() + " ID: " + list.get(empCount).getEmployeeNumber() + ".\n" +
                "Enter 'Y' to delete or press any key to cancel");
            verify = scan.nextLine().toUpperCase().charAt(0);
            if (verify == 'Y')
            {
                list.remove(empCount);
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

                System.out.println("Employee created successfully!"); 

                valid = true;
            }
            else if (type == 'S')
            {
                System.out.println("Enter yearly salary amount: ");
                yearSal = scan.nextDouble();
                emp = new Salary(name, empNum, dept, yearSal);

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
        if (validateID(num) != false)
            System.out.println (list.get(empCount).printDetails());
        else
            System.out.println ("The employee number " + num + " does not exist.");
    }

    /**
     * Calculates and prints weekly salary of employee
     */
    public void weeklySalary(String num)
    {      
        double yearlySalary = 0.0;

        if (validateID(num) != false)
            System.out.println ("Weekly Salary for " + list.get(empCount).getName() + " is $" + list.get(empCount).calcWeeklySalary());
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
        int placeHold = 0;

        while (i < numPeople)
        {
            if (list.get(i).getType() == 'C')
            {
                Commission com = (Commission)list.get(i);
                com.setWeeklySales(0.0);
                placeHold = com.getWeeksWorked() + 1;
                com.setWeeksWorked(placeHold);
            }
            else if (list.get(i).getType() == 'H')
            {
                Hourly hour = (Hourly)list.get(i);
                hour.setHoursWorked(0);
            }
            i++;
        }
    }

    /**
     * Requests user for current week values
     * Increases weeks worked
     */
    public void newWeek()
    {
        int i = 0;
        double sales = 0.0;
        int hours = 0;

        System.out.println("New week processessing. Please enter new values.");
        while (i < numPeople)
        { 
            if (list.get(i).getType() == 'C')
            {
                Commission com = (Commission)list.get(i);
                System.out.print("Enter weekly sales for " + list.get(i).getName() + " : ");
                try {
                    Scanner input = new Scanner (System.in);
                    sales = input.nextDouble();
                    com.setWeeklySales(sales);
                    i++;
                }
                catch (InputMismatchException a) {
                    System.out.println("Invalid input, please input an integer.");
                }
            }            
            else if (list.get(i).getType() == 'H')
            {
                Hourly hour = (Hourly)list.get(i);
                System.out.print("Enter hours worked for " + list.get(i).getName() + " : ");
                try {
                    Scanner input = new Scanner (System.in);
                    hours = input.nextInt();
                    hour.setHoursWorked(hours);
                    i++;
                }
                catch (InputMismatchException a) {
                    System.out.println("Invalid input, please input an integer.");
                }
            }
            else if (list.get(i).getType() == 'S')
            {
                i++;
            }
        }
    }

    /**
     * Exports data to new file
     */
    private void exportData() throws IOException
    {
        String confirm = "";
        String fileName;

        int i = 0;
        Scanner input = new Scanner (System.in);

        System.out.print("Exporting payroll data. Enter new file name: ");
        fileName = input.nextLine();

        if(!(fileName.substring(fileName.length() - 4)).equals(".txt"))
        {
            fileName += ".txt";
        }

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

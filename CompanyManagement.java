import java.lang.reflect.Member;
import java.sql.*;
import java.util.Scanner;

class All{
    private static final String url = "jdbc:mysql://127.0.0.1:3306/mydb";
    private static final String password = "122436";
    private static final String username = "root";

    void add_emp(int emp_id, String name, int salary) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
//            String query = "SELECT * FROM STUDENT";
//            ResultSet resultSet  = statement.executeQuery(query);
//
//            while(resultSet.next()){
//                int id = resultSet.getInt("emp_id");
//                String name_emp = resultSet.getString("name");
//                int salary_emp = resultSet.getInt("salary");
//                System.out.println("id: "+id);
//                System.out.println("name: "+name_emp);
//                System.out.println("age: "+salary_emp);
//                System.out.println("\n");
//            }
            String query1 = String.format("INSERT INTO EMPLOYEE(emp_id, name, salary) VALUES(%o, '%s', %o)", emp_id, name, salary);
            int rowsAffected = statement.executeUpdate(query1);
            if(rowsAffected>0){
                System.out.println("Success fully updated");
            }
            else{
                System.out.println("Data Not inserted");
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void add_mang(int emp_id, String name, int salary, String dept) {
        Scanner sc = new Scanner(System.in);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            String query = String.format("INSERT INTO MANAGER(emp_id, name, salary, department) VALUES(%o, '%s', %o, %s)", emp_id, name, salary, dept);
            int rowsAffected = statement.executeUpdate(query);
            if(rowsAffected>0){
                System.out.println("Success fully updated");
            }
            else{
                System.out.println("Data Not inserted");
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void print_detail() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM EMPLOYEE";
            ResultSet resultSet  = statement.executeQuery(query);
            int i=0;
            while(resultSet.next()){
                System.out.println("Employee "+(i++)+" :");
                int id = resultSet.getInt("emp_id");
                String name_emp = resultSet.getString("name");
                int salary_emp = resultSet.getInt("salary");
                System.out.println("id: "+id);
                System.out.println("name: "+name_emp);
                System.out.println("age: "+salary_emp);
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

class Employee extends All {
    private String name;
    private int employeeId;
    private double salary = 10000;

    public Employee(String name, int employeeId, int salary) {
        this.name = name;
        this.employeeId = employeeId;
        this.salary = salary;
        add_emp(employeeId, name, salary);
    }
    void add_manager(int emp_id, String name, int salary, String dept) {
        this.name = name;
        this.employeeId = emp_id;
        this.salary = salary;
        add_mang(employeeId, name, salary, dept);
    }

    public String getName() {
        return name;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public double getSalary() {return salary;}

    public void setSalary(double salary) {
        this.salary = salary;
    }

}
class Manager extends Employee{
    private String department;
    public Manager(String name, int employeeId, int salary, String department) {
        super(name, employeeId, salary);
        this.department = department;
    }
    void manage(int emp_id, String name, int salary, String department) {
        add_manager(emp_id, name, salary, department);
    }

}

class Company {
    private Employee[] employees;
    private int employeeCount;

    public Company(int capacity) {
        employees = new Employee[capacity];
        employeeCount = 0;
    }

    public void addEmployee(Employee employee) {
        if (employeeCount < employees.length) {
            employees[employeeCount] = employee;
            employeeCount++;
            System.out.println("Employee added to the company.");
        } else {
            System.out.println("Cannot add more employees. Company at full capacity.");
        }
    }

}

public class CompanyManagement
{
    public static void main(String[] arg)
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("1 -> add employee and manager and 0 -> print old history: ");
        int quest = sc.nextInt();

        if(quest == 1){
            System.out.println("Number of employee: ");
            int size_emp = sc.nextInt();
            System.out.println("Number of manager: ");
            int size_mag = sc.nextInt();
            Company my_employee = new Company(size_emp);
            Employee[] member_emp = new Employee[size_emp];

            for (int i = 0; i < size_emp; i++) {
                System.out.println("Enter employee id: ");
                int emp_id = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter employee name: ");
                String name = sc.nextLine();
                System.out.println("Enter employee salary: ");
                int emp_sal = sc.nextInt();
                member_emp[i] = new Employee(name, emp_id, emp_sal);
                my_employee.addEmployee(member_emp[i]);
            }

            Company my_manager = new Company(size_mag);
            Manager obj_mag;

            for (int i = 0; i < size_mag; i++) {
                System.out.println("Enter manager id: ");
                int emp_id = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter manager name: ");
                String name = sc.nextLine();
                System.out.println("Enter manager salary: ");
                int emp_sal = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter manager department: ");
                String dept = sc.nextLine();
//                obj_mag.manage(emp_id, name, emp_sal, dept);
//                my_manager.addEmployee(obj_mag);
            }
        }
        else{
            All print_detail = new All();
            print_detail.print_detail();
        }

        System.out.println("1 -> print detail otherwise 0 -> exit: ");
        int question2 = sc.nextInt();
        if(question2 == 1 && quest != 0){
            All print_detail = new All();
            print_detail.print_detail();
        }

    }
}

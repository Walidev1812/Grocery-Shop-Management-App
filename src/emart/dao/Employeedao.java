/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package emart.dao;

import java.sql.Connection;
import emart.dbutil.DBconnection;
import emart.pojo.Employeepojo;
import java.util.List;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 *
 * @author LENOVO
 */
public class Employeedao {
    public static String getNextEmpId()throws SQLException
    {
       Connection con=DBconnection.getConnection();
       Statement s=con.createStatement();
       ResultSet rs=s.executeQuery("select max(empid)from employees");
       rs.next();
       String empid=rs.getString(1);
       int empno=Integer.parseInt(empid.substring(1));
       empno+=1;
       return "E"+empno;
       
    }
    public static boolean addEmployee(Employeepojo e)throws SQLException
    {
        Connection con=DBconnection.getConnection();
        PreparedStatement ps=con.prepareStatement("insert into employees values(?,?,?,?)");
        ps.setString(1,e.getEmpid());
        ps.setString(2, e.getEmpname());
        ps.setString(3, e.getJob());
        ps.setDouble(4,e.getSalary());
        int result=ps.executeUpdate();
        
        return result==1;
        
    }
    public static List<Employeepojo> viewAllEmployee()throws SQLException
    {
              Connection con=DBconnection.getConnection();
       Statement s=con.createStatement();
       ResultSet rs=s.executeQuery("select * from employees order by empid"); 
       
       ArrayList<Employeepojo> emplist=new ArrayList<>();
       while(rs.next())
       {
           Employeepojo emp=new Employeepojo();
           emp.setEmpid(rs.getString(1));
           emp.setEmpname(rs.getString(2));
           emp.setJob(rs.getString(3));
           emp.setSalary(rs.getDouble(4));
           emplist.add(emp);
       }
       return emplist;
       
       
      
       
    }
    public static List<String> getAllEmpId()throws SQLException
    {
          Connection con=DBconnection.getConnection();
       Statement s=con.createStatement();
       ResultSet rs=s.executeQuery("select empid from employees where job!='Manager' order by empid"); 
       
       ArrayList<String> a=new ArrayList<String>();
       while(rs.next())
       {
           a.add(rs.getString(1));
       }
     return a;   
    }
    
    public static Employeepojo findEmpById(String empid)throws SQLException
    {
         Connection con=DBconnection.getConnection();
        PreparedStatement ps=con.prepareStatement("select * from employees where empid=?");
        ps.setString(1, empid);
        ResultSet rs=ps.executeQuery();
        rs.next();
        Employeepojo e=new Employeepojo();
        e.setEmpid(rs.getString(1));
        e.setEmpname(rs.getString(2));
        e.setJob(rs.getString(3));
        e.setSalary(rs.getDouble(4));
        return e;
        
    }
    
    public static boolean updateEmployee(Employeepojo e)throws SQLException
    {
        
         Connection con=DBconnection.getConnection();
        PreparedStatement ps=con.prepareStatement("update employees set empname=?,job=?,salary=? where empid=?");
        ps.setString(1,e.getEmpname());
        ps.setString(2,e.getJob());
        ps.setDouble(3, e.getSalary());
        ps.setString(4,e.getEmpid());
        int x=ps.executeUpdate();
        if(x==0)
        {
            return false;
        }
        
        {
            boolean result=Userdao.isUserPresent(e.getEmpid());
            if(result==false)
                return true;
        
          ps=con.prepareStatement("update users set username=?,usertype=? where empid=?");
          ps.setString(1,e.getEmpname());
          ps.setString(2,e.getJob());
          ps.setString(3,e.getEmpid());
          int y=ps.executeUpdate();
          return y==1;
        
    }
    
}
 public static boolean deleteEmployee(String empid)throws SQLException
 {
      Connection con=DBconnection.getConnection();
        PreparedStatement ps=con.prepareStatement("delete from employees where empid=?");
        ps.setString(1,empid);
        int x=ps.executeUpdate();
        return x==1;
        
 }
  public static int getEmpCount()throws SQLException
       {
          Connection con=DBconnection.getConnection();
           Statement s=con.createStatement();
           ResultSet rs=s.executeQuery("select count (*) from employees ");
           rs.next();
           int count=rs.getInt(1);
           return count;
       }


  public static List<Employeepojo> viewAllEmployees()throws SQLException
    {
              Connection con=DBconnection.getConnection();
       Statement s=con.createStatement();
       ResultSet rs=s.executeQuery("select * from employees where job!='Manager' order by empid"); 
       
       ArrayList<Employeepojo> emplist=new ArrayList<>();
       while(rs.next())
       {
           Employeepojo emp=new Employeepojo();
           emp.setEmpid(rs.getString(1));
           emp.setEmpname(rs.getString(2));
           emp.setJob(rs.getString(3));
           emp.setSalary(rs.getDouble(4));
           emplist.add(emp);
       }
       return emplist;
       
       
      
       
    }
}
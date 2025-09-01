   /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package emart.dao;

import java.sql.Connection;
import emart.dbutil.DBconnection;
import emart.pojo.Employeepojo;
import emart.pojo.Receptionistpojo;
import emart.pojo.Userpojo;
import java.sql.Statement;
import java.util.Map;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author LENOVO
 */
public class Receptionistdao 
{
    public static Map<String,String> getNonRegisteredReceptionist()throws SQLException
    {
        Connection con=DBconnection.getConnection();
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery("select empid,empname from employees where job='Receptionist' and empid not in(select empid from users where usertype='Receptionist')");
        
        HashMap<String,String> receptionistList=new HashMap<>();
        while(rs.next())
        {
            String id=rs.getString(1);
            String name=rs.getString(2);
            receptionistList.put(id, name);
        }
        return receptionistList;
    }
    
    public static boolean addReceptionist(Userpojo u)throws SQLException
    {
         Connection con=DBconnection.getConnection();
        PreparedStatement s=con.prepareStatement("insert into users values(?,?,?,?,?)");
        s.setString(1, u.getUserid());
        s.setString(2,u.getEmpid());
        s.setString(3, u.getPassword());
        s.setString(4, u.getUsertype());
        s.setString(5,u.getUsername());
        
        int result=s.executeUpdate();
        return result==1;
        
       
    }
    
    public static List<Receptionistpojo> getAllReceptionistDetail()throws SQLException
    {
         Connection con=DBconnection.getConnection();
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery("select users.empid,userid,empname,job,salary from users,employees  where usertype='Receptionist' and users.empid=employees.empid");
         ArrayList<Receptionistpojo> al=new ArrayList<>();
       while(rs.next())
       {
           Receptionistpojo rp=new Receptionistpojo();
           rp.setEmpid(rs.getString(1));
           rp.setUserid(rs.getString(2));
           rp.setEmpname(rs.getString(3));
           rp.setJob(rs.getString(4));
           rp.setSalary(rs.getDouble(5));
           al.add(rp);
       }
       return al;
       
    }
    
    public static Map<String,String> getAllReceptionistId() throws SQLException
        {
            Connection con=DBconnection.getConnection();
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery("select userid,username from users where usertype='Receptionist' order by userid");
        
        HashMap<String,String> receptionistList=new HashMap<>();
        while(rs.next())
        {
            String id=rs.getString(1);
            String name=rs.getString(2);
            receptionistList.put(id, name);
        }
        return receptionistList; 
        }   
    public static boolean updatePassword(String userid,String pwd)throws SQLException
    {
        Connection con=DBconnection.getConnection();
       PreparedStatement ps=con.prepareStatement("update users set password=? where userid=?");
       ps.setString(1, pwd);
       ps.setString(2, userid);
       
       return ps.executeUpdate()==1;
    }
    public static List<String> getAllReceptionistUserId()throws SQLException
    {
          Connection con=DBconnection.getConnection();
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery("select userid from users where usertype='Receptionist' order by userid");
        
        List<String> receptionistList=new ArrayList<>();
        while(rs.next())
        {
            String id=rs.getString(1);
            
            receptionistList.add(id);
        }
        return receptionistList; 
    }
    public static boolean deleteReceptionist(String userid)throws SQLException
    {
       Connection con=DBconnection.getConnection();
       PreparedStatement ps=con.prepareStatement("delete from users where empid=?");
       ps.setString(1, userid);
       
       
       return ps.executeUpdate()==1;  
    }
    
    public static int getRecepCount()throws SQLException
       {
          Connection con=DBconnection.getConnection();
           Statement s=con.createStatement();
           ResultSet rs=s.executeQuery("select count (*) from users where usertype='Receptionist' ");
           rs.next();
           int count=rs.getInt(1);
           return count;
       }
    }



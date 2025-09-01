/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package emart.dao;

import emart.dbutil.DBconnection;
import emart.pojo.Employeepojo;
import emart.pojo.Productspojo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class Productsdao {
     public static String getNextProductId()throws SQLException
    {
       Connection con=DBconnection.getConnection();
       Statement s=con.createStatement();
       ResultSet rs=s.executeQuery("select max(p_id)from products");
       rs.next();
       String productid=rs.getString(1);
       if(productid==null)
           return "P101";
       int pno=Integer.parseInt(productid.substring(1));
       pno+=1;
       return "P"+pno;
       
    }
     public static boolean addProducts(Productspojo p)throws SQLException
     {
      Connection con=DBconnection.getConnection();
      PreparedStatement ps=con.prepareStatement("Insert into products values(?,?,?,?,?,?,?,'Y')");
      ps.setString(1,p.getProductId());
      ps.setString(2,p.getProductName());
      ps.setString(3,p.getProductCompany());
      ps.setDouble(4,p.getProductPrice());
      ps.setDouble(5,p.getOurPrice());
      ps.setInt(6,p.getTax());
      ps.setInt(7,p.getQuantity());
      
      return ps.executeUpdate()==1;
      
     }
     public static List<Productspojo> getProductsDetail()throws SQLException
     {
        Connection con=DBconnection.getConnection();
       Statement s=con.createStatement();
       ResultSet rs=s.executeQuery("select * from products where status='Y' order by p_id"); 
       
       ArrayList<Productspojo> plist=new ArrayList<>();
       while(rs.next())
       {
           Productspojo p=new Productspojo();
           p.setProductId(rs.getString(1));
           p.setProductName(rs.getString(2));
           p.setProductCompany(rs.getString(3));
           p.setProductPrice(rs.getDouble(4));
           p.setOurPrice(rs.getDouble(5));
          p.setTax(rs.getInt(6));
          p.setQuantity(rs.getInt(7));
           plist.add(p);
       }
       return plist;
       
     }
     public static boolean deleteProduct(String pid)throws SQLException
     {
          Connection con=DBconnection.getConnection();
      PreparedStatement ps=con.prepareStatement("update products set status='N' where p_id=?");
      ps.setString(1,pid);
      
      return ps.executeUpdate()==1;
     }
     public static boolean updateProduct(Productspojo p)throws SQLException
     {
          Connection con=DBconnection.getConnection();
      PreparedStatement ps=con.prepareStatement("update products set p_name=?,p_companyname=?,p_price=?,our_price=?,p_tax=?,quantity=? where p_id=?");
      
      ps.setString(1,p.getProductName());
      ps.setString(2,p.getProductCompany());
      ps.setDouble(3,p.getProductPrice());
      ps.setDouble(4,p.getOurPrice());
      ps.setInt(5,p.getTax());
      ps.setInt(6,p.getQuantity());
      ps.setString(7,p.getProductId());
      
      return ps.executeUpdate()==1;
     }
     public static Productspojo getProductsDetail(String id)throws SQLException
     {
            Connection con=DBconnection.getConnection();
      PreparedStatement ps=con.prepareStatement("select * from products where p_id=? and status='Y'");
      ps.setString(1,id);
      ResultSet rs=ps.executeQuery();
      Productspojo p=new Productspojo();
      if(rs.next())
      {
            p.setProductId(rs.getString(1));
           p.setProductName(rs.getString(2));
           p.setProductCompany(rs.getString(3));
           p.setProductPrice(rs.getDouble(4));
           p.setOurPrice(rs.getDouble(5));
          p.setTax(rs.getInt(6));
          p.setQuantity(rs.getInt(7));
          
      }
      return p;
     }
     public static boolean updateStock(List<Productspojo> productslist)throws SQLException
     {
               Connection con=DBconnection.getConnection();
      PreparedStatement ps=con.prepareStatement("update products set quantity=quantity-? where p_id=?");
      int x=0;
      for(Productspojo p:productslist)
      {
          ps.setInt(1,p.getQuantity());
          ps.setString(2, p.getProductId());
          int rows=ps.executeUpdate();
          if(rows!=0)
              x++;
      }
      return  x==productslist.size();
     }
     public static int getProdCount()throws SQLException
       {
          Connection con=DBconnection.getConnection();
           Statement s=con.createStatement();
           ResultSet rs=s.executeQuery("select count (*) from products where status='Y' ");
           rs.next();
           int count=rs.getInt(1);
           return count;
       }
     
}
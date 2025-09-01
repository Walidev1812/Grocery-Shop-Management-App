
package emart.dao;

import emart.dbutil.DBconnection;
import emart.pojo.Billpojo;
import emart.pojo.Productspojo;
import emart.pojo.UserProfile;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Orderdao {
       public static String getNextOrderId()throws SQLException
    {
       Connection con=DBconnection.getConnection();
       Statement s=con.createStatement();
       ResultSet rs=s.executeQuery("select max(order_id)from bill");
       rs.next();
       String ordid=rs.getString(1);
       if(ordid==null)
           return "O-101";
       int ordno=Integer.parseInt(ordid.substring(2));
       ordno++;
       return "O-"+ordno;
       
    }
       public static boolean addOrder(ArrayList<Productspojo> al,String ordid)throws SQLException
       {
            Connection con=DBconnection.getConnection();
       PreparedStatement ps=con.prepareStatement("Insert into orders values(?,?,?,?)");
       int count=0;
       for(Productspojo p:al)
       {
           ps.setString(1,ordid);
           ps.setString(2, p.getProductId());
           ps.setInt(3, p.getQuantity());
           ps.setString(4,UserProfile.getUserid());
           count=count+ps.executeUpdate();
           
       }
       return count==al.size();
       }
       public static List<String> getAllOrderId()throws SQLException
    {
          Connection con=DBconnection.getConnection();
       Statement s=con.createStatement();
       ResultSet rs=s.executeQuery("select order_id from bill order by order_id"); 
       Set<String> unique=new HashSet<>();
       List<String> a=new ArrayList<String>();
       while(rs.next())
       {
           String orderid=rs.getString(1);
           if(!unique.contains(orderid))
           {
               unique.add(orderid);
               a.add(orderid);
           }
         //  a.add(rs.getString(1));
       }
     return a;   
    }
         public static boolean addBill(ArrayList<Productspojo> al,String ordid)throws SQLException
       {
            Connection con=DBconnection.getConnection();
       PreparedStatement ps=con.prepareStatement("Insert into bill values(?,?,?,?,?,?,?,?,?)");
       int count=0;
       for(Productspojo p:al)
       {
           ps.setString(1,ordid);
           ps.setString(2, p.getProductId());
           ps.setString(3, p.getProductName());
           ps.setString(4, p.getProductCompany());
           ps.setDouble(5,p.getProductPrice());
           ps.setDouble(6,p.getOurPrice());
           ps.setInt(7, p.getQuantity());
           ps.setInt(8, p.getTax());
           ps.setDouble(9,p.getTotal());
           //ps.setString(4,UserProfile.getUserid());
           count=count+ps.executeUpdate();
           
       }
       return count==al.size();
       }
         
          public static List<Billpojo> getBillDetail(String orderid)throws SQLException
     {
        Connection con=DBconnection.getConnection();
       PreparedStatement ps=con.prepareStatement("select * from bill where order_id=?");
       ps.setString(1,orderid);
       ResultSet rs=ps.executeQuery(); 
       
       
       ArrayList<Billpojo> plist=new ArrayList<>();
       while(rs.next())
       {
           Billpojo p=new Billpojo();
          p.setOrderId(rs.getString(1));
           p.setProductId(rs.getString(2));
           p.setProductName(rs.getString(3));
           p.setProductCompany(rs.getString(4));
           p.setProductPrice(rs.getDouble(5));
           p.setOurPrice(rs.getDouble(6));
          
          p.setQuantity(rs.getInt(7));
          p.setTax(rs.getInt(8));
          p.setTotal(rs.getDouble(9));
           plist.add(p);
       }
       return plist;
       
     }
    
    
}

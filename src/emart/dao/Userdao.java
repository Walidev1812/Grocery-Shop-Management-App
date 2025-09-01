 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package emart.dao;


import emart.dbutil.DBconnection;
import emart.pojo.UserProfile;
import emart.pojo.Userpojo;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author LENOVO
 */
public class Userdao {
    public static boolean validateUser(Userpojo user)throws SQLException
            {
                Connection con=DBconnection.getConnection();
                PreparedStatement ps=con.prepareStatement("select * from users where userid=? and password=? and usertype=?");
                ps.setString(1, user.getUserid());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getUsertype());
                ResultSet rs=ps.executeQuery();
                if(rs.next())
                {
                    String username=rs.getString(5);
                    UserProfile.setUsername(username);
                    return true;
                }
                return false;
            }
    public static boolean isUserPresent(String empid)throws SQLException
    {
        Connection con=DBconnection.getConnection();
                PreparedStatement ps=con.prepareStatement("select 1 from users where empid=?");
                ps.setString(1, empid);
                ResultSet rs=ps.executeQuery();
                return rs.next();
        
    }
}

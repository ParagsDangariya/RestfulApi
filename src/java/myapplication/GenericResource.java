/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author 1894264
 */
@Path("mad311")
public class GenericResource {

    @Context
    private UriInfo context;
    static Connection conn = null;
    static Statement stm = null;
    static ResultSet rs = null;
    static String countryId, countryName, regionId;

    static JSONObject connError = new JSONObject();
    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of myapplication.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("list")
    @Produces("text/plain")
    public String getText() {
       
        JSONArray mainarrList = new JSONArray();
        JSONObject singleobjList = new JSONObject();

        conn = getConnection();

        Date date = new Date();
        long time = date.getTime();
        if (conn != null) {
            try {
                String sql = "SELECT COUNTRY_ID,COUNTRY_NAME,REGION_ID FROM COUNTRIES";
                stm = conn.createStatement();
                int i = stm.executeUpdate(sql);

                rs = stm.executeQuery(sql);

                while (rs.next()) {

                    countryId = rs.getString("country_id");
                    countryName = rs.getString("country_name");
                    regionId = rs.getString("region_id");
                    singleobjList.accumulate("countryId", countryId);
                    singleobjList.accumulate("countryName", countryName);
                    singleobjList.accumulate("regionId", regionId);
                    mainarrList.add(singleobjList);
                    singleobjList.clear();

                }
//                return mainarrList.toString();
            } catch (SQLException ex) {
                Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection();
            }
        } else {

            singleobjList.accumulate("status", " Error");
            singleobjList.accumulate("Timestamp", date);
            singleobjList.accumulate("Message", "Error in display list");
            
            ;
        }
       return mainarrList.toString();
        
        
    }

    public static Connection getConnection() {

        try {

            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conn;
    }

    public static void getDataList() {

    }

    @GET
    @Path("hr&{table}&{value1}&{value2}&{value3}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getText7(@PathParam("table")String table,
            @PathParam("value1")String value1,
            @PathParam("value2")String value2, 
            @PathParam("value3")double value3) {

        conn = getConnection();
        JSONObject singleInsert = new JSONObject();

        String sql = "INSERT INTO COUNTRIES(COUNTRY_ID,COUNTRY_NAME,REGION_ID) VALUES('BH','BHARAT',1)";

        if (conn != null) {
            try {
                stm = conn.createStatement();
                int i = stm.executeUpdate(sql);

                if (i > 0) {
                    singleInsert.accumulate("message", "Record inserted");
                    System.out.println(singleInsert);

                } else {
                    singleInsert.accumulate("message", "Record Not inserted");
                    System.out.println(singleInsert);
                }

            } catch (SQLException ex) {
                Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection();
            }
        } else {

            connError.accumulate("message", "Connection Error");
            System.out.println(connError);

        }
    }

    public static void updateData() {

        conn = getConnection();
        JSONObject singleupdate = new JSONObject();
        String sql = "UPDATE COUNTRIES SET COUNTRY_NAME='HINDUSTAN' WHERE COUNTRY_ID='BH'";

        if (conn != null) {
            try {
                stm = conn.createStatement();

                int i = stm.executeUpdate(sql);

                if (i > 0) {
                    singleupdate.accumulate("message", "Record Updated");
                    System.out.println(singleupdate);
                } else {
                    singleupdate.accumulate("message", "Record Not Updated");
                    System.out.println(singleupdate);
                }

            } catch (SQLException ex) {
                Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection();
            }
        } else {
            connError.accumulate("message", "Connection Error");
            System.out.println(connError);
        }
    }

    public static void deleteData() {

        conn = getConnection();
        JSONObject singleDelete = new JSONObject();

        String sql = "DELETE FROM COUNTRIES WHERE COUNTRY_ID = 'BH'";

        if (conn != null) {
            try {
                stm = conn.createStatement();
                int i = stm.executeUpdate(sql);
                if (i > 0) {
                    singleDelete.accumulate("message", "Record Deleted");
                    System.out.println(singleDelete);
                } else {
                    singleDelete.accumulate("message", "Record not Deleted");
                    System.out.println(singleDelete);
                }

            } catch (SQLException ex) {
                Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection();
            }
        } else {
            connError.accumulate("message", "Connection Error");
            System.out.println(connError);
        }
    }

    public static void getSingleList() {

        conn = getConnection();
        JSONObject singleList = new JSONObject();

        String sql = "SELECT * FROM COUNTRIES WHERE COUNTRY_ID='BH'";

        if (conn != null) {
            try {
                stm = conn.createStatement();
                int i = stm.executeUpdate(sql);

                rs = stm.executeQuery(sql);

                while (rs.next()) {

                    countryId = rs.getString("country_id");
                    countryName = rs.getString("country_name");
                    regionId = rs.getString("region_id");
                    singleList.accumulate("countryId", countryId);
                    singleList.accumulate("countryName", countryName);
                    singleList.accumulate("regionId", regionId);
                }
                System.out.println(singleList);

            } catch (SQLException ex) {
                Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeConnection();
            }
        } else {
            connError.accumulate("message", "Connection Error");
            System.out.println(connError);
        }
    }

    public static void closeConnection() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                /* ignored */
            }
        }
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                /* ignored */
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                /* ignored */
            }
        }
    }
}

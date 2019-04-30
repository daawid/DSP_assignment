package com.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author dawid
 */
public class Database {

    //Database connectivity details
    private static String url = "jdbc:derby://localhost:1527/dsp";
    private static String user = "root";
    private static String pass = "password";

    //Constants to be used 
    public static final String TBL_BOOKINGS = "bookings";
    public static final String TBL_BOOKINGS_ID = "id";
    public static final String TBL_BOOKINGS_MEMBER_ID = "member_id";
    public static final String TBL_BOOKINGS_DATE = "date";
    public static final String TBL_BOOKINGS_STATUS = "status";
    public static final String TBL_BOOKINGS_PRICE = "price";
    public static final String TBL_BOOKINGS_ORIGIN = "origin";
    public static final String TBL_BOOKINGS_DESTINATION = "destination";
    public static final String TBL_BOOKINGS_DISTANCE = "distance";
    public static final String TBL_BOOKINGS_DATEOFTRAVEL = "dateOfTravel";
    public static final String TBL_BOOKINGS_PASSENGERS = "passengers";
    public static final String TBL_BOOKINGS_REFERENCE = "reference";

    public static final String TBL_MEMBERS = "members";
    public static final String TBL_MEMBERS_ID = "id";
    public static final String TBL_MEMBERS_NAME = "name";
    public static final String TBL_MEMBERS_ADDRESS = "address";
    public static final String TBL_MEMBERS_EMAIL = "email";
    public static final String TBL_MEMBERS_DOB = "dob";
    public static final String TBL_MEMBERS_DOR = "dor";
    public static final String TBL_MEMBERS_STATUS = "status";
    public static final String TBL_MEMBERS_BALANCE = "balance";

    public static final String TBL_PAYMENTS = "payments";
    public static final String TBL_PAYMENTS_ID = "id";
    public static final String TBL_PAYMENTS_MEMBER_ID = "member_id";
    public static final String TBL_PAYMENTS_TYPE_OF_PAYMENT = "type_of_payment";
    public static final String TBL_PAYMENTS_AMOUNT = "amount";
    public static final String TBL_PAYMENTS_DATE = "date";
    public static final String TBL_PAYMENTS_TIME = "time";

    public static final String TBL_USERS = "users";
    public static final String TBL_USERS_ID = "id";
    public static final String TBL_USERS_PASSWORD = "password";
    public static final String TBL_USERS_STATUS = "status";
    public static final String TBL_USERS_RESET = "reset";

    //Create a new entry in members table
    protected boolean insertIntoMembers(String username, String firstName, String lastName, String address, String email, Date dob, Date dor, String status, double balance) {

        String query = "insert into members values(?,?,?,?,?,?,?,?)";

        //Create database connection
        try (Connection con = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = con.prepareStatement(query);) {
            String name = firstName + " " + lastName;

            ps.setString(1, username);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, email);
            ps.setDate(5, dob);
            ps.setDate(6, dor);
            ps.setString(7, status);
            ps.setDouble(8, balance);

            //Confirm execution
            if (ps.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Create entry in users table
    protected boolean insertIntoUsers(String username, String password, String status, Boolean reset) {

        String query = "insert into users values(?,?,?,?)";

        //Create database connection
        try (Connection con = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = con.prepareStatement(query);) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, status);
            ps.setBoolean(4, false);

            //Confirm execution
            if (ps.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Get all data from a table (tableName) and return it as ArrayList
    protected ArrayList<Object> getAllData(String tableName) {

        String query = "select * from " + tableName;
        ArrayList<Object> data = new ArrayList<>();

        //Create database connection
        try (Connection con = DriverManager.getConnection(url, user, pass);
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();) {

            //Switch matches to tableName
            switch (tableName) {
                case TBL_BOOKINGS:
                    try {
                        while (rs.next()) {
                            //Generate booking
                            BookingsBean booking = new BookingsBean();
                            booking.setID(rs.getInt(1));
                            booking.setMemberID(rs.getString(2));
                            booking.setDate(rs.getDate(3));
                            booking.setStatus(rs.getString(4));
                            booking.setPrice(rs.getDouble(5));
                            booking.setOrigin(rs.getString(6));
                            booking.setDestination(rs.getString(7));
                            booking.setDistance(rs.getInt(8));
                            booking.setDate(rs.getDate(9));
                            booking.setPassengers(rs.getInt(10));
                            booking.setReference(rs.getString(11));

                            DecimalFormat df = new DecimalFormat("#.00");
                            double price = Double.parseDouble(df.format(rs.getDouble(5)));
                            booking.setPrice(price);

                            data.add(booking);
                        }
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                case TBL_PAYMENTS:
                    try {
                        while (rs.next()) {
                            //Return a payment
                            PaymentBean payment = new PaymentBean();
                            payment.setID(rs.getInt(1));
                            payment.setMemberID(rs.getString(2));
                            payment.setType(rs.getString(3));

                            DecimalFormat df = new DecimalFormat("#.00");
                            double amount = Double.parseDouble(df.format(rs.getDouble(4)));
                            payment.setAmount(amount);
                            payment.setDate(rs.getDate(5));
                            payment.setTimestamp(rs.getTime(6));

                            data.add(payment);
                        }
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }

                case TBL_MEMBERS:
                    try {
                        while (rs.next()) {
                            //Return a user
                            User member = new User();
                            member.setUsername(rs.getString(1));
                            String names[] = rs.getString(2).split(" ");
                            member.setFirstName(names[0]);

                            //Check for space for last name
                            String lastName = "";
                            if (names.length > 2) {
                                for (int i = 1; i < names.length; i++) {
                                    lastName += names[i];
                                    if (i < names.length - 1) {
                                        lastName += " ";
                                    }
                                }
                            } else {
                                lastName = names[1];
                            }
                            member.setLastName(lastName);
                            member.setAddress(rs.getString(3));
                            member.setEmail(rs.getString(4));
                            member.setDob(rs.getDate(5));
                            member.setDor(rs.getDate(6));
                            member.setStatus(rs.getString(7));
                            member.setBalance(rs.getDouble(8));

                            data.add(member);
                        }
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }

                case TBL_USERS:
                    try {
                        while (rs.next()) {
                            //Return a user
                            User user = new User();
                            user.setUsername(rs.getString(1));
                            user.setPassword(rs.getString(2));
                            user.setStatus(rs.getString(3));
                            user.setReset(rs.getBoolean(4));

                            data.add(user);
                        }
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                default:
                    //In case the table wasn't found
                    System.out.println("Table not found");
                    return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Not added");
        return null;
    }

    //Return ArrayList based on table name and filter
    protected ArrayList<Object> getAllData(String tableName, String columnName, Object filter) {

        String query = "select * from " + tableName + " where " + columnName + "=?";

        ArrayList<Object> data = new ArrayList<>();
        ResultSet rs = null;

        //Create database connection
        try (Connection con = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = con.prepareStatement(query);) {

            ps.setObject(1, filter);
            rs = ps.executeQuery();

            //Switch based on tableName
            switch (tableName) {
                case TBL_BOOKINGS:
                    try {
                        while (rs.next()) {
                            //Return a booking
                            BookingsBean booking = new BookingsBean();
                            booking.setID(rs.getInt(1));
                            booking.setMemberID(rs.getString(2));
                            booking.setDate(rs.getDate(3));
                            booking.setStatus(rs.getString(4));
                            booking.setPrice(rs.getDouble(5));
                            booking.setOrigin(rs.getString(6));
                            booking.setDestination(rs.getString(7));
                            booking.setDistance(rs.getInt(8));
                            booking.setDateOfTravel(rs.getDate(9));
                            booking.setPassengers(rs.getInt(10));
                            booking.setReference(rs.getString(11));

                            DecimalFormat df = new DecimalFormat("#.00");
                            double price = Double.parseDouble(df.format(rs.getDouble(5)));
                            booking.setPrice(price);

                            data.add(booking);
                        }

                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }

                case TBL_PAYMENTS:
                    try {
                        while (rs.next()) {
                            //Return a payment
                            PaymentBean payment = new PaymentBean();
                            payment.setID(rs.getInt(1));
                            payment.setMemberID(rs.getString(2));
                            payment.setType(rs.getString(3));

                            DecimalFormat df = new DecimalFormat("#.00");
                            double amount = Double.parseDouble(df.format(rs.getDouble(4)));
                            payment.setAmount(amount);
                            payment.setDate(rs.getDate(5));
                            payment.setTimestamp(rs.getTime(6));

                            data.add(payment);
                        }
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }

                case TBL_MEMBERS:
                    try {
                        while (rs.next()) {
                            //Return user
                            User member = new User();
                            member.setUsername(rs.getString(1));
                            String names[] = rs.getString(2).split(" ");
                            member.setFirstName(names[0]);

                            //Check for spaces for last name
                            String lastName = "";
                            if (names.length > 2) {
                                for (int i = 1; i < names.length; i++) {
                                    lastName += names[i];
                                    if (i < names.length - 1) {
                                        lastName += " ";
                                    }
                                }
                            } else {
                                lastName = names[1];
                            }

                            member.setLastName(lastName);
                            member.setAddress(rs.getString(3));
                            member.setEmail(rs.getString(4));
                            member.setDob(rs.getDate(5));
                            member.setDor(rs.getDate(6));
                            member.setStatus(rs.getString(7));
                            member.setBalance(rs.getDouble(8));

                            data.add(member);
                        }
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }

                case TBL_USERS:
                    try {
                        while (rs.next()) {
                            //Return user
                            User user = new User();
                            user.setUsername(rs.getString(1));
                            user.setPassword(rs.getString(2));
                            user.setStatus(rs.getString(3));
                            user.setReset(rs.getBoolean(4));

                            data.add(user);
                        }
                        return data;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        break;
                    }
                default:
                    return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Not added");
        return null;
    }

    //Return a single object from tableName and using a filter
    protected Object searchData(String tableName, String columnName, Object dataSearch) {

        String query = "select * from " + tableName + " where " + columnName + "=?";
        ResultSet rs = null;
        PreparedStatement ps = null;

        //Create database connection
        try (Connection con = DriverManager.getConnection(url, user, pass);) {

            if (columnName == TBL_BOOKINGS_PRICE || columnName == TBL_MEMBERS_ADDRESS) {
                //Search for long varchar
                query = "select * from " + tableName + " where " + columnName + " like ? escape '!'";
                ps = con.prepareStatement(query);
                //If word matches then return row
                ps.setObject(1, "%" + dataSearch + "%");
            } else {
                //If not long varchar
                ps = con.prepareStatement(query);
                ps.setObject(1, dataSearch);
            }

            rs = ps.executeQuery();

            //Switch based on tableName
            switch (tableName) {
                case TBL_BOOKINGS:
                    BookingsBean booking = new BookingsBean();
                    if (rs.next()) {
                        booking.setID(rs.getInt(1));
                        booking.setMemberID(rs.getString(2));
                        booking.setDate(rs.getDate(3));
                        booking.setStatus(rs.getString(4));
                        booking.setPrice(rs.getDouble(5));
                        booking.setOrigin(rs.getString(6));
                        booking.setDestination(rs.getString(7));
                        booking.setPrice(rs.getDouble(8));
                        booking.setDate(rs.getDate(9));
                        booking.setPassengers(rs.getInt(10));
                        booking.setReference(rs.getString(11));

                        DecimalFormat df = new DecimalFormat("#.00");
                        double price = Double.parseDouble(df.format(rs.getDouble(5)));
                        booking.setPrice(price);
                    } else {
                        booking = null;
                    }
                    return booking;

                case TBL_PAYMENTS:
                    //Return payment
                    PaymentBean payment = new PaymentBean();

                    if (rs.next()) {
                        payment.setID(rs.getInt(1));
                        payment.setMemberID(rs.getString(2));
                        payment.setType(rs.getString(3));

                        DecimalFormat df = new DecimalFormat("#.00");
                        double amount = Double.parseDouble(df.format(rs.getDouble(4)));
                        payment.setAmount(amount);
                        payment.setDate(rs.getDate(5));
                        payment.setTimestamp(rs.getTime(6));
                    } else {
                        payment = null;
                    }
                    return payment;

                case TBL_MEMBERS:
                    //Return user
                    User member = new User();

                    if (rs.next()) {
                        member.setUsername(rs.getString(1));
                        String names[] = rs.getString(2).split(" ");
                        member.setFirstName(names[0]);

                        //Check for spaces for last name
                        String lastName = "";
                        if (names.length > 2) {
                            for (int i = 1; i < names.length; i++) {
                                lastName += names[i];
                                if (i < names.length - 1) {
                                    lastName += " ";
                                }
                            }
                        } else {
                            lastName = names[1];
                        }

                        member.setLastName(lastName);
                        member.setAddress(rs.getString(3));
                        member.setEmail(rs.getString(4));
                        member.setDob(rs.getDate(5));
                        member.setDor(rs.getDate(6));
                        member.setStatus(rs.getString(7));
                        member.setBalance(rs.getDouble(8));
                    } else {
                        member = null;
                    }
                    return member;

                case TBL_USERS:
                    //Return user
                    User user = new User();

                    if (rs.next()) {
                        user.setUsername(rs.getString(1));
                        user.setPassword(rs.getString(2));
                        user.setStatus(rs.getString(3));
                        user.setReset(rs.getBoolean(4));
                    } else {
                        user = null;
                    }
                    return user;
                default:
                    return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //Close result set
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //Close prepared statement
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //Update data in table, use boolean to check success or failure
    protected boolean updateData(String tableName, Object id, String columnName, Object newValue) {
        //The update query
        String query = "update " + tableName + " set " + columnName + " =? where id =?";

        //Create database connection
        try (Connection con = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = con.prepareStatement(query);) {

            //What to update
            ps.setObject(1, newValue);
            ps.setObject(2, id);

            //Check success
            if (ps.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Not added");
        return false;
    }

    //Update data in table, use boolean to check success or failure   
    protected boolean insertData(String tableName, Object type) {
        //The update query
        String query = "insert into " + tableName + " ";
        PreparedStatement ps = null;

        //Create database connection
        try (Connection con = DriverManager.getConnection(url, user, pass);) {

            //Switch based on tableName
            switch (tableName) {
                case TBL_BOOKINGS:
                    //Insert data
                    query += "(" + TBL_BOOKINGS_MEMBER_ID + ","
                            + TBL_BOOKINGS_DATE + ","
                            + TBL_BOOKINGS_STATUS + ","
                            + TBL_BOOKINGS_PRICE + ","
                            + TBL_BOOKINGS_ORIGIN + ","
                            + TBL_BOOKINGS_DESTINATION + ","
                            + TBL_BOOKINGS_DISTANCE + ","
                            + TBL_BOOKINGS_DATEOFTRAVEL + ","
                            + TBL_BOOKINGS_PASSENGERS + ","
                            + TBL_BOOKINGS_REFERENCE + ") ";

                    query += "values(?,?,?,?,?,?,?,?,?,?)";
                    ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                    BookingsBean booking = (BookingsBean) type;
                    ps.setString(1, booking.getMemberID());
                    ps.setDate(2, booking.getDate());
                    ps.setString(3, booking.getStatus());
                    ps.setDouble(4, booking.getPrice());
                    ps.setString(5, booking.getOrigin());
                    ps.setString(6, booking.getDestination());
                    ps.setInt(7, booking.getDistance());
                    ps.setDate(8, booking.getDateOfTravel());
                    ps.setInt(9, booking.getPassengers());
                    ps.setString(10, booking.getReference());

                    //Check if success or false
                    if (ps.executeUpdate() == 1) {
                        return true;
                    } else {
                        return false;
                    }

                case TBL_MEMBERS:
                    //Insert data
                    query += "values(?,?,?,?,?,?,?)";
                    ps = con.prepareStatement(query);
                    User member = (User) type;
                    String name = member.getFirstName() + " " + member.getLastName();
                    ps.setString(1, member.getUsername());
                    ps.setString(2, name);
                    ps.setString(3, member.getAddress());
                    ps.setDate(4, member.getDob());
                    ps.setDate(5, member.getDor());
                    ps.setString(6, member.getStatus());
                    ps.setDouble(7, member.getBalance());

                    //Check if success or false
                    if (ps.executeUpdate() == 1) {
                        return true;
                    } else {
                        return false;
                    }

                case TBL_PAYMENTS:
                    //Insert data
                    query += "(" + TBL_PAYMENTS_MEMBER_ID + ","
                            + TBL_PAYMENTS_TYPE_OF_PAYMENT + ","
                            + TBL_PAYMENTS_AMOUNT + ","
                            + TBL_PAYMENTS_DATE + ","
                            + TBL_PAYMENTS_TIME + ") ";
                    query += " values(?,?,?,?,?)";

                    ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                    PaymentBean payments = (PaymentBean) type;
                    ps.setString(1, payments.getMemberID());
                    ps.setString(2, payments.getType());
                    ps.setDouble(3, payments.getAmount());
                    ps.setDate(4, payments.getDate());
                    ps.setTime(5, payments.getTimestamp());

                    //Check if success or failure
                    if (ps.executeUpdate() == 1) {
                        return true;
                    } else {
                        return false;
                    }

                case TBL_USERS:
                    //Insert data
                    query += "values(?,?,?,?)";
                    ps = con.prepareStatement(query);
                    User users = (User) type;
                    ps.setString(1, users.getUsername());
                    ps.setString(2, users.getPassword());
                    ps.setString(3, users.getStatus());
                    ps.setBoolean(4, users.getReset());

                    //Check if success or failure
                    if (ps.executeUpdate() == 1) {
                        return true;
                    } else {
                        return false;
                    }
                default:
                    return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            //Close the prepared statement
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Not added");
        return false;
    }
}

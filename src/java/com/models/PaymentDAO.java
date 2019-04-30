package com.models;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author dawid
 */

public class PaymentDAO implements GenericDAO<PaymentBean, Integer> {

    private Database db;

    public PaymentDAO() {
        db = new Database();
    }

    //Add payment entry to database
    @Override
    public boolean create(PaymentBean newPayment) {
        boolean inserted = db.insertData(Database.TBL_PAYMENTS, newPayment);
        return inserted;
    }

    //Generate a list of all payments
    @Override
    public List<PaymentBean> getAll() {
        ArrayList<PaymentBean> payments = new ArrayList<>();

        for (Object object : db.getAllData(Database.TBL_PAYMENTS)) {
            PaymentBean payment = (PaymentBean) object;
            payments.add(payment);
        }

        return payments;
    }

    //Get payments by member ID
    public List<PaymentBean> getAllByMemberID(String memberId) {
        ArrayList<PaymentBean> payments = new ArrayList<>();

        for (Object object : db.getAllData(Database.TBL_PAYMENTS, Database.TBL_PAYMENTS_MEMBER_ID, memberId)) {
            PaymentBean payment = (PaymentBean) object;
            payments.add(payment);
        }

        return payments;
    }

//    //get all payments that have same type of payment
//    public List<PaymentBean> getAllByTypeOfPayment(String typeOfPayment) {
//        ArrayList<PaymentBean> payments = new ArrayList<>();
//
//        //for each payment in payments table
//        for (Object object : db.getAllData(Database.PAYMENTS_TABLE, Database.PAYMENTS_TABLE_TYPE_OF_PAYMENT, typeOfPayment)) {
//            PaymentBean payment = (PaymentBean) object;
//            payments.add(payment);
//        }
//
//        return payments;
//    }


    //Get payment by ID
    @Override
    public PaymentBean getByID(Integer id) {
        PaymentBean payment = (PaymentBean) db.searchData(Database.TBL_PAYMENTS, Database.TBL_PAYMENTS_ID, id);
        if (payment == null) {
            System.out.println("PaymentDAO error, not found");
        }
        return payment;
    }
}

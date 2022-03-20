package ru.netology.Utils;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.data.PaymentStatusDb;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    static String dataSourceUrl = "jdbc:mysql://localhost:3306/app";
    static String dataSourceUsername = "app";
    static String dataSourcePassword = "pass";

    public static PaymentStatusDb getPaymentStatus(String dataSourceUrl1) {
        val runner = new QueryRunner();
        val dataSQL = "SELECT\n" +
                "\tcre.status as creditStatus,\n" +
                "\tpe.status as paymentStatus\n" +
                "FROM\n" +
                "\torder_entity oe\n" +
                "LEFT JOIN credit_request_entity cre ON\n" +
                "\toe.payment_id = cre.bank_id\n" +
                "LEFT JOIN payment_entity pe ON\n" +
                "\toe.payment_id = pe.transaction_id\n" +
                "WHERE\n" +
                "\toe.created =(\n" +
                "\tSELECT\n" +
                "\t\tMAX(created)\n" +
                "\tFROM\n" +
                "\t\torder_entity)";
        PaymentStatusDb statusInDB = null;
        try {
            val conn = DriverManager.getConnection(dataSourceUrl, dataSourceUsername, dataSourcePassword);
            statusInDB = runner.query(conn, dataSQL, new BeanHandler<PaymentStatusDb>(PaymentStatusDb.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statusInDB;
    }
}

package ru.netology.data;

import lombok.Data;

@Data
public class PaymentStatusDb {
    private String creditStatus;
    private String paymentStatus;
}

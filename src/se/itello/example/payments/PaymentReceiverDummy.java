package src.se.itello.example.payments;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentReceiverDummy implements PaymentReceiver {
    final String name = this.getClass().getSimpleName();

    @Override
    public void startPaymentBundle(String accountNumber, Date paymentDate, String currency) {
        System.out.println(name + ".startPaymentBundle() accountNumber: " + accountNumber + " paymentDate: "
        + paymentDate + " currency: " + currency);
    }

    @Override
    public void payment(BigDecimal amount, String reference) {
        System.out.println(name + ".payment() amount: " + amount + " reference " + reference);

    }

    @Override
    public void endPaymentBundle() {
        System.out.println(name + ".endPaymentBundle()");

    }
}


















package src.se.itello.example.registration.internal;


import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public final class PaymentFileHandlerBetalningsService extends PaymentFileHandler {
    private final static String DATE_FORMAT = "yyyyMMdd";
    private final static String OPENING_POST = "O";
    private final static String PAYMENT_POST = "B";

    private final DataRowSection postTypeSection;
    private final DataRowSection accountNumberSection;
    private final DataRowSection paymentDataSection;
    private final DataRowSection currencySection;
    private final DataRowSection amountSection;
    private final DataRowSection referenceSection;


    public PaymentFileHandlerBetalningsService() {
        postTypeSection = new DataRowSection(1, 1);
        accountNumberSection = new DataRowSection(2, 16);
        paymentDataSection = new DataRowSection(41, 48);
        currencySection = new DataRowSection(40, 51);
        amountSection = new DataRowSection(2, 15);
        referenceSection = new DataRowSection(16, 50);
    }

    @Override
    protected void parse(List<String> dataRows) {
        for (String dataRow : dataRows) {
            String postType = postTypeSection.getSectionFrom(dataRow);

            switch (postType) {
                case PAYMENT_POST:
                    String amountStr = amountSection.getSectionFrom(dataRow);
                    BigDecimal amount = getBigDecimalFrom(amountStr);
                    String reference = referenceSection.getSectionFrom(dataRow);
                    addPayment(amount, reference);
                    break;


                case OPENING_POST:
                    String accountNumber = accountNumberSection.getSectionFrom(dataRow);
                    String dataStr = paymentDataSection.getSectionFrom(dataRow);
                    Date paymentDate = getDateFromString(dataStr);
                    String currency = currencySection.getSectionFrom(dataRow);
                    setPaymentInfo(accountNumber, paymentDate, currency);
                    break;
            }
        }
    }

    private BigDecimal getBigDecimalFrom(String s) {
        String formatted = s.replace(" ", "").replace(",", ".");
        return new BigDecimal(formatted);
    }

    private Date getDateFromString(String s) {
        Date result = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            result = dateFormat.parse(s);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}


























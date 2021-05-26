package src.se.itello.example.registration.internal;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public final class PaymentFileHandlerInbetalningstjansten extends PaymentFileHandler {
    private final static String OPENINIG_POST = "00";
    private final static String PAYMENT_POST = "30";
    //private final static String ENDMENT_POST = "99";

    private final static Date paymentDate = null; //informationen denna fil saknas
    private final static String currency= "SEK"; //beskrivningen enligt filformatet.

    private final DataRowSection postTypeSection;
    private final DataRowSection accountNumberSection;
    private final DataRowSection amountSection;
    private final DataRowSection referenceSection;


    public PaymentFileHandlerInbetalningstjansten() {
        postTypeSection = new DataRowSection(1, 2);
        accountNumberSection = new DataRowSection(15, 24);
        amountSection = new DataRowSection(3, 22);
        referenceSection = new DataRowSection(41, 65);
    }

    @Override
    protected void parse(List<String> dataRows){
        for (String dataRow : dataRows) {
            String postType = postTypeSection.getSectionFrom(dataRow);

            switch (postType){
                case PAYMENT_POST:
                    String amountStr = amountSection.getSectionFrom(dataRow);
                    BigDecimal amount = getBigDecimalFrom(amountStr);
                    String reference = referenceSection.getSectionFrom(dataRow);
                    addPayment(amount, reference);

                case OPENINIG_POST:
                    String accountNumber = accountNumberSection.getSectionFrom(dataRow);
                    setPaymentInfo(accountNumber, paymentDate, currency);
                    break;

              //case ENDING_POST:
              //har inga data som efterfrågas av paymnetReciver.
              //Eventuellt kan man lägga till kontroll av totalt belopet här

            }
        }

    }

    private BigDecimal getBigDecimalFrom(String s) {
        int decimalIndex = s.length()-2;
        String formatted = s.substring(0, decimalIndex) + "." + s.substring(decimalIndex);
        return new BigDecimal(formatted);
    }
}





















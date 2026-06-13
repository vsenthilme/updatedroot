public abstract class Payment {
    String customerName;
    Double amount;
    String currency;

//    Payment(String customerName, Double amount, String currency) {
//        this.customerName = customerName;
//        this.amount = amount;
//        this.currency = currency;
//    }


    abstract void creditCard();
}
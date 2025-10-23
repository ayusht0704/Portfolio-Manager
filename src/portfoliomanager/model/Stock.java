package portfoliomanager.model;

public class Stock {
    private String symbol;
    private String companyName;
    private int quantity;
    private double price;

    public Stock(String symbol, String companyName, int quantity, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Derived Value
    public double getValue() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return symbol + " (" + companyName + ") - Qty: " + quantity + " @ " + price;
    }
}

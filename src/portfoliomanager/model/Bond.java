package portfoliomanager.model;
public class Bond {
    private String id;
    private String name;
    private double faceValue;
    private double couponRate;
    private int quantity;
    private double marketPrice;

    public Bond(String id, String name, double faceValue, double couponRate, int quantity, double marketPrice) {
        this.id = id;
        this.name = name;
        this.faceValue = faceValue;
        this.couponRate = couponRate;
        this.quantity = quantity;
        this.marketPrice = marketPrice;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getFaceValue() { return faceValue; }
    public double getCouponRate() { return couponRate; }
    public int getQuantity() { return quantity; }
    public double getMarketPrice() { return marketPrice; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setFaceValue(double faceValue) { this.faceValue = faceValue; }
    public void setCouponRate(double couponRate) { this.couponRate = couponRate; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setMarketPrice(double marketPrice) { this.marketPrice = marketPrice; }

    // Market value of this bond holding (quantity * marketPrice)
    public double getValue() {
        return this.quantity * this.marketPrice;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Qty:%d Price:%.2f", name, id, quantity, marketPrice);
    }
}

package portfoliomanager.model;
public class Investment {
    private String id;
    private String name;
    private double units;
    private double nav;

    public Investment(String id, String name, double units, double nav) {
        this.id = id;
        this.name = name;
        this.units = units;
        this.nav = nav;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getUnits() { return units; }
    public double getNav() { return nav; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setUnits(double units) { this.units = units; }
    public void setNav(double nav) { this.nav = nav; }

    // Current market value
    public double getValue() {
        return units * nav;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Units:%.2f NAV:%.2f", name, id, units, nav);
    }
}

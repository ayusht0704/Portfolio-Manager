package portfoliomanager.model;

import java.io.*;
import java.util.*;

/**
 * Portfolio now manages Stocks, Bonds, and Investments.
 * Saves/loads each asset type to separate CSV files inside a folder.
 */
public class Portfolio {
    private List<Stock> stocks;
    private List<Bond> bonds;
    private List<Investment> investments;

    public Portfolio() {
        stocks = new ArrayList<>();
        bonds = new ArrayList<>();
        investments = new ArrayList<>();
    }

    // ----- Stock operations -----
    public void addStock(Stock stock) {
        for (Stock s : stocks) {
            if (s.getSymbol().equalsIgnoreCase(stock.getSymbol())) {
                s.setQuantity(s.getQuantity() + stock.getQuantity());
                return;
            }
        }
        stocks.add(stock);
    }
    public void removeStock(String symbol) {
        stocks.removeIf(s -> s.getSymbol().equalsIgnoreCase(symbol));
    }
    public void updateStockPrice(String symbol, double price) {
        for (Stock s : stocks) {
            if (s.getSymbol().equalsIgnoreCase(symbol)) {
                s.setPrice(price);
                return;
            }
        }
    }
    public List<Stock> getStocks() { return stocks; }

    // ----- Bond operations -----
    public void addBond(Bond bond) {
        // merge by id
        for (Bond b : bonds) {
            if (b.getId().equalsIgnoreCase(bond.getId())) {
                b.setQuantity(b.getQuantity() + bond.getQuantity());
                b.setMarketPrice(bond.getMarketPrice());
                return;
            }
        }
        bonds.add(bond);
    }
    public void removeBond(String id) {
        bonds.removeIf(b -> b.getId().equalsIgnoreCase(id));
    }
    public void updateBondMarketPrice(String id, double price) {
        for (Bond b : bonds) {
            if (b.getId().equalsIgnoreCase(id)) {
                b.setMarketPrice(price);
                return;
            }
        }
    }
    public List<Bond> getBonds() { return bonds; }

    // ----- Investment operations -----
    public void addInvestment(Investment inv) {
        for (Investment i : investments) {
            if (i.getId().equalsIgnoreCase(inv.getId())) {
                i.setUnits(i.getUnits() + inv.getUnits());
                i.setNav(inv.getNav());
                return;
            }
        }
        investments.add(inv);
    }
    public void removeInvestment(String id) {
        investments.removeIf(i -> i.getId().equalsIgnoreCase(id));
    }
    public void updateInvestmentNav(String id, double nav) {
        for (Investment i : investments) {
            if (i.getId().equalsIgnoreCase(id)) {
                i.setNav(nav);
                return;
            }
        }
    }
    public List<Investment> getInvestments() { return investments; }

    // ----- Totals -----
    public double getTotalValue() {
        double total = 0;
        for (Stock s : stocks) total += s.getValue();
        for (Bond b : bonds) total += b.getValue();
        for (Investment i : investments) total += i.getValue();
        return total;
    }
    public void saveAll(String folderPath) {
        File dir = new File(folderPath);
        if (!dir.exists()) dir.mkdirs();
        saveStocksCsv(new File(dir, "stocks.csv").getAbsolutePath());
        saveBondsCsv(new File(dir, "bonds.csv").getAbsolutePath());
        saveInvestmentsCsv(new File(dir, "investments.csv").getAbsolutePath());
    }

    public void loadAll(String folderPath) {
        File dir = new File(folderPath);
        if (!dir.exists()) return; // nothing to load
        loadStocksCsv(new File(dir, "stocks.csv").getAbsolutePath());
        loadBondsCsv(new File(dir, "bonds.csv").getAbsolutePath());
        loadInvestmentsCsv(new File(dir, "investments.csv").getAbsolutePath());
    }

    // Stocks (symbol,name,quantity,price)
    public void saveStocksCsv(String path) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (Stock s : stocks) {
                pw.println(String.join(",", escape(s.getSymbol()), escape(s.getCompanyName()),
                        String.valueOf(s.getQuantity()), String.valueOf(s.getPrice())));
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void loadStocksCsv(String path) {
        stocks.clear();
        File f = new File(path);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = splitCsv(line,4);
                if (parts.length < 4) continue;
                String symbol = unescape(parts[0]);
                String name = unescape(parts[1]);
                int qty = Integer.parseInt(parts[2]);
                double price = Double.parseDouble(parts[3]);
                stocks.add(new Stock(symbol,name,qty,price));
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Bonds (id,name,faceValue,couponRate,quantity,marketPrice)
    public void saveBondsCsv(String path) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (Bond b : bonds) {
                pw.println(String.join(",", escape(b.getId()), escape(b.getName()),
                        String.valueOf(b.getFaceValue()), String.valueOf(b.getCouponRate()),
                        String.valueOf(b.getQuantity()), String.valueOf(b.getMarketPrice())));
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void loadBondsCsv(String path) {
        bonds.clear();
        File f = new File(path);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = splitCsv(line,6);
                if (parts.length < 6) continue;
                String id = unescape(parts[0]);
                String name = unescape(parts[1]);
                double face = Double.parseDouble(parts[2]);
                double coupon = Double.parseDouble(parts[3]);
                int qty = Integer.parseInt(parts[4]);
                double market = Double.parseDouble(parts[5]);
                bonds.add(new Bond(id, name, face, coupon, qty, market));
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Investments (id,name,units,nav)
    public void saveInvestmentsCsv(String path) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (Investment i : investments) {
                pw.println(String.join(",", escape(i.getId()), escape(i.getName()),
                        String.valueOf(i.getUnits()), String.valueOf(i.getNav())));
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void loadInvestmentsCsv(String path) {
        investments.clear();
        File f = new File(path);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = splitCsv(line,4);
                if (parts.length < 4) continue;
                String id = unescape(parts[0]);
                String name = unescape(parts[1]);
                double units = Double.parseDouble(parts[2]);
                double nav = Double.parseDouble(parts[3]);
                investments.add(new Investment(id, name, units, nav));
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

   
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\"\""); 
    }
    private String unescape(String s) { return s == null ? "" : s.replace("\"\"", "\""); }

    
    private String[] splitCsv(String line, int expectedParts) {
        String[] parts = line.split(",", expectedParts);
        for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
        return parts;
    }
}

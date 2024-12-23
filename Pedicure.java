import java.util.*;

public class Pedicure extends Salon implements Service {
    public Map<Integer, String> services;
    public Map<Integer, Double> prices;

    public Pedicure(String salonName, String address) {
        super(salonName, address);
        services = new HashMap<>();
        prices = new HashMap<>();
        loadServices();
    }

    private void loadServices() {
        services.put(1, "Basic Pedicure");
        prices.put(1, 50.0);
        services.put(2, "Deluxe Pedicure");
        prices.put(2, 100.0);
        services.put(3, "Spa Pedicure");
        prices.put(3, 150.0);
    }

    @Override
    public void displayServices() {
        System.out.println("Layanan pedicure yang tersedia:");
        for (int id : services.keySet()) {
            System.out.println(id + ". " + services.get(id) + " - $" + prices.get(id));
        }
    }

    @Override
    public double calculatePrice(int serviceId, int quantity) {
        return prices.get(serviceId) * quantity;
    }
}
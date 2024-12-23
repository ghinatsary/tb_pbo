
public class Salon {
    protected String salonName;
    protected String address;

    public Salon(String salonName, String address) {
        this.salonName = salonName;
        this.address = address;
    }

    public void displayInfo() {
        System.out.println("SELAMAT DATANG DI " + salonName);
        System.out.println("Address: " + address);
    }
}
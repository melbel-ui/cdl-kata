import checkout.CheckoutService;
import offers.OffersService;
import products.ProductsService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean running = true;
        Scanner sc = new Scanner(System.in);
        ProductsService productsService = new ProductsService();
        OffersService offersService = new OffersService();
        CheckoutService checkoutService = new CheckoutService();

        productsService.setupAvailableProducts();
        offersService.setupAvailableOffers();

        while (running) {
            System.out.println("""
                    Welcome: Please select the action you wish to undertake:
                    1 - View available products and their prices
                    2 - View available offers
                    3 - Add or delete offers
                    4 - Scan products
                    5 - Exit
                    """);

            int option = sc.nextInt();

            switch (option) {
                case 1:
                    productsService.viewProducts();
                    break;
                case 2:
                    offersService.viewAvailableOffers();
                    break;
                case 3:
                    offersService.updateAvailableOffers(sc);
                    offersService.viewAvailableOffers();
                    break;
                case 4:
                    checkoutService.checkout(sc);
                    offersService.viewAvailableOffers();
                    break;
                case 5:
                    System.out.println("Thank you for your business.");
                    running = false;
                    break;
                default:
                    System.out.println("Unknown action. Please input one of the available options");
            }
        }
    }
}

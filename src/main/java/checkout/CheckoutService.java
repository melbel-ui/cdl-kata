package checkout;

import offers.Offer;
import offers.OffersService;
import products.Product;
import products.ProductsService;

import java.util.*;

public class CheckoutService {
    private ProductsService productsService = new ProductsService();
    private OffersService offersService = new OffersService();

    public void checkout(Scanner sc) {
        long total = 0;
        List<Product> scannedProducts = new ArrayList<>();
        HashMap<String, Integer> offersAppliedPerProduct = initialiseOffersForTransaction();
        System.out.print("Enter the code for your product. To finish please enter the word 'done': ");
        String code = sc.next();

        while(!"done".equalsIgnoreCase(code)) {
            Optional<Product> product = productsService.getProduct(code);
            if(product.isEmpty()) {
                System.out.println("Could not find this product, it will not be added to your total.");
            } else {
                scannedProducts.add(product.get());
                total = calculateTotal(product.get(), total, scannedProducts, offersAppliedPerProduct);
            }
            System.out.print("Your total: " + total + "\nEnter the code for your next product. To finish please enter the word 'done':");
            code = sc.next();
        }

        System.out.println("Your final total is: " + total);
    }

    private HashMap<String, Integer> initialiseOffersForTransaction() {
        HashMap<String, Integer> map = new HashMap<>();
        for(Offer offer: offersService.getOffers()) {
            map.put(offer.getProductCode(), 0);
        }

        return map;
    }

    private long calculateTotal(Product product, long total, List<Product> scannedProducts, HashMap<String, Integer> offersAppliedPerProduct) {
        String finalCode = product.getCode();
        Optional<Offer> productOffer = offersService.getOffer(finalCode);
        if(productOffer.isEmpty()) {
            return total + product.getPrice();
        }

        int itemsInOffer = productOffer.get().getNumberOfItems();
        int itemCount = (int) scannedProducts.stream().filter(p -> p.getCode().equals(finalCode)).count();
        int completeBundles = itemCount / itemsInOffer;
        if (completeBundles > offersAppliedPerProduct.get(finalCode)) {
            offersAppliedPerProduct.put(finalCode, completeBundles);
            int priceForLastBundleItem = productOffer.get().getPrice() - (itemsInOffer - 1)*product.getPrice();
            return total + priceForLastBundleItem;
        }

        return total + product.getPrice();
    }
}

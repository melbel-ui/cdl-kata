package products;


import offers.Offer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsService {

    List<Product> availableProducts = setupAvailableProducts();

    public List<Product> setupAvailableProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("A", 50));
        products.add(new Product("B", 30));
        products.add(new Product("C", 20));
        products.add(new Product("D", 15));

        return products;
    }

    public void viewProducts() {
        for (Product product: availableProducts) {
            System.out.println(product);
        }
    }

    public Optional<Product> getProduct (String code) {
        return availableProducts.stream()
                .filter(p -> p.getCode().equalsIgnoreCase(code))
                .findFirst();
    }
}

package offers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class OffersService {

    List<Offer> availableOffers = setupAvailableOffers();

    public List<Offer> setupAvailableOffers() {
        List<Offer> offers = new ArrayList<>();
        offers.add(new Offer("A", 3, 130));
        offers.add(new Offer("B", 2, 45));

        return offers;
    }

    public void viewAvailableOffers() {
        for(Offer offer: availableOffers) {
            System.out.println(offer);
        }
    }

    public List<Offer> getOffers() {
        return availableOffers;
    }

    public Optional<Offer> getOffer(String code) {
        return availableOffers.stream()
                .filter(offer -> offer.getProductCode().equals(code)).findFirst();
    }

    public void updateAvailableOffers(Scanner sc) {
        System.out.print("To add or update an offer enter 1. To remove an offer enter 2: ");
        int action = sc.nextInt();
        switch (action) {
            case 1:
                System.out.print("Enter the product code, number of items in offer, and bundle price, separated by a space: ");
                String[] offerDetails = sc.next().split(" ");
                this.addOrUpdateOffer(offerDetails[0], Integer.valueOf(offerDetails[1]), Integer.valueOf(offerDetails[2]));
                break;
            case 2:
                System.out.print("Enter the product code for the offer you wish to remove: ");
                String code = sc.next();
                this.removeOffer(code);
                break;
            default:
                System.out.print("You have entered an unknown option.");
        }
    }

    private void addOrUpdateOffer(String code, Integer itemsNumber, Integer price) {
        Optional<Offer> offer = getOffer(code);
        Offer newOffer = new Offer(code, itemsNumber, price);
        if(offer.isPresent()){
            availableOffers.set(availableOffers.indexOf(offer.get()), newOffer);
        } else {
            availableOffers.add(newOffer);
        }
    }

    private void removeOffer(String code) {
        Optional<Offer> offer = this.getOffer(code);
        offer.ifPresent(value -> availableOffers.remove(value));
    }
}

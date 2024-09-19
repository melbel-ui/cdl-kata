package offers;

import lombok.Data;

@Data
public class Offer {

    private final String productCode;
    private final Integer numberOfItems;
    private final Integer price;
}

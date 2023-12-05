import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AsyncProductDetailsFetcher {

    public CompletableFuture<ProductDetails> fetchProductDetailsAsync(long productId) {
        CompletableFuture<ProductInfo> infoFuture = CompletableFuture
                .supplyAsync(() -> fetchProductInfo(productId));

        CompletableFuture<List<Review>> reviewsFuture = CompletableFuture
                .supplyAsync(() -> fetchProductReviews(productId));

        CompletableFuture<PricingInfo> pricingFuture = CompletableFuture
                .supplyAsync(() -> fetchProductPricing(productId));

        // Combine results when all tasks are completed
        return infoFuture
               .thenCombine(reviewsFuture, (info, reviews) -> new ProductDetails(info, reviews))
        .thenCombine(pricingFuture, (details, pricing) -> {
            details.setProductPricing(pricing);
            return details;
        });
    }

    private ProductInfo fetchProductInfo(long productId) {
        // Simulated method to fetch product information asynchronously
        return new ProductInfo("Product Information for ID: " + productId);
    }

    private List<Review> fetchProductReviews(long productId) {
        // Simulated method to fetch product reviews asynchronously
        return List.of(new Review("Good product!", 5), new Review("Could be better.", 3));
    }

    private PricingInfo fetchProductPricing(long productId) {
        // Simulated method to fetch product pricing asynchronously
        return new PricingInfo(49.99);
    }

    public static void main(String[] args) {
        AsyncProductDetailsFetcher asyncFetcher = new AsyncProductDetailsFetcher();

        long productId = 123; // Replace with the actual product ID
        CompletableFuture<ProductDetails> productDetailsFuture = asyncFetcher.fetchProductDetailsAsync(productId);

        // Attach a callback for when the CompletableFuture is completed
        productDetailsFuture.whenComplete((result, exception) -> {
            if (exception == null) {
                System.out.println("Product Details: " + result);
            } else {
                System.err.println("Error fetching product details: " + exception.getMessage());
            }
        });

        // Wait for the CompletableFuture to complete (for demonstration purposes)
        try {
            productDetailsFuture.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ... other class definitions ...

    static class ProductDetails {
        private ProductInfo productInfo;
        private List<Review> productReviews;
        private PricingInfo productPricing;

        public ProductDetails() {
        }

        public ProductDetails(ProductInfo productInfo, List<Review> productReviews) {
            this.productInfo = productInfo;
            this.productReviews = productReviews;
        }

        // Constructors, getters, and setters...

        public ProductInfo getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(ProductInfo productInfo) {
            this.productInfo = productInfo;
        }

        public List<Review> getProductReviews() {
            return productReviews;
        }

        public void setProductReviews(List<Review> productReviews) {
            this.productReviews = productReviews;
        }

        public PricingInfo getProductPricing() {
            return productPricing;
        }

        public void setProductPricing(PricingInfo productPricing) {
            this.productPricing = productPricing;
        }

        @Override
        public String toString() {
            return "ProductDetails{" +
                   "productInfo=" + productInfo +
                   ", productReviews=" + productReviews +
                   ", productPricing=" + productPricing +
                   '}';
        }
    }

    // ... other class definitions ...

    static class ProductInfo {
        private String info;

        // Constructors, getters, and setters...

        public ProductInfo(String info) {
            this.info = info;
        }

        @Override
        public String toString() {
            return "ProductInfo{" +
                   "info='" + info + '\'' +
                   '}';
        }
    }

    static class Review {
        private String comment;
        private int rating;

        // Constructors, getters, and setters...

        public Review(String comment, int rating) {
            this.comment = comment;
            this.rating = rating;
        }

        @Override
        public String toString() {
            return "Review{" +
                   "comment='" + comment + '\'' +
                   ", rating=" + rating +
                   '}';
        }
    }

    static class PricingInfo {
        private double price;

        // Constructors, getters, and setters...

        public PricingInfo(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "PricingInfo{" +
                   "price=" + price +
                   '}';
        }
    }
}

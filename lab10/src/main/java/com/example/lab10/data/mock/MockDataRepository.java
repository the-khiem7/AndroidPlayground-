package com.example.lab10.data.mock;

import com.example.lab10.data.model.CartItem;
import com.example.lab10.data.model.CartResponse;
import com.example.lab10.data.model.Product;
import com.example.lab10.room.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory mock data repository
 * Provides static mock data for testing and development
 */
public class MockDataRepository {

    // Mock Users
    private static final Map<String, User> MOCK_USERS = new HashMap<>();

    // Mock Products
    private static final List<Product> MOCK_PRODUCTS = new ArrayList<>();

    // Mock Cart Items (productId -> CartItem)
    private static final Map<Integer, CartItem> MOCK_CART = new HashMap<>();
    private static int nextCartItemId = 1;

    // Mock JWT Token
    public static final String MOCK_JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0dXNlciIsIm5hbWUiOiJUZXN0IFVzZXIiLCJpYXQiOjE1MTYyMzkwMjIsImV4cCI6OTk5OTk5OTk5OX0.MOCK_TOKEN";

    static {
        initializeMockUsers();
        initializeMockProducts();
    }

    private static void initializeMockUsers() {
        // Add mock users (username -> User object)
        User user1 = new User("admin", "admin1234", "admin@example.com");
        user1.setUserId(1);
        MOCK_USERS.put("admin", user1);

        User user2 = new User("testuser", "password", "test@example.com");
        user2.setUserId(2);
        MOCK_USERS.put("testuser", user2);

        User user3 = new User("demo", "demo", "demo@example.com");
        user3.setUserId(3);
        MOCK_USERS.put("demo", user3);
    }

    private static void initializeMockProducts() {
        // Gaming Mouse
        Product p1 = new Product();
        p1.productId = 1;
        p1.name = "Wireless Gaming Mouse RGB";
        p1.description = "High-precision wireless gaming mouse with RGB lighting, 6 programmable buttons, and 16000 DPI sensor. Perfect for competitive gaming.";
        p1.price = 350000;
        p1.imageUrl = "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=400";
        MOCK_PRODUCTS.add(p1);

        // Mechanical Keyboard
        Product p2 = new Product();
        p2.productId = 2;
        p2.name = "Mechanical Gaming Keyboard";
        p2.description = "RGB backlit mechanical keyboard with blue switches, anti-ghosting, and aluminum frame. Durable and responsive.";
        p2.price = 850000;
        p2.imageUrl = "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=400";
        MOCK_PRODUCTS.add(p2);

        // Gaming Headset
        Product p3 = new Product();
        p3.productId = 3;
        p3.name = "7.1 Surround Gaming Headset";
        p3.description = "Immersive 7.1 surround sound gaming headset with noise-canceling microphone and comfortable ear cushions.";
        p3.price = 450000;
        p3.imageUrl = "https://images.unsplash.com/photo-1546435770-a3e426bf472b?w=400";
        MOCK_PRODUCTS.add(p3);

        // Gaming Monitor
        Product p4 = new Product();
        p4.productId = 4;
        p4.name = "27\" 144Hz Gaming Monitor";
        p4.description = "27-inch Full HD gaming monitor with 144Hz refresh rate, 1ms response time, and AMD FreeSync technology.";
        p4.price = 3500000;
        p4.imageUrl = "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=400";
        MOCK_PRODUCTS.add(p4);

        // Gaming Chair
        Product p5 = new Product();
        p5.productId = 5;
        p5.name = "Ergonomic Gaming Chair";
        p5.description = "Professional ergonomic gaming chair with lumbar support, adjustable armrests, and 180-degree recline.";
        p5.price = 2500000;
        p5.imageUrl = "https://images.unsplash.com/photo-1598550476439-6847785fcea6?w=400";
        MOCK_PRODUCTS.add(p5);

        // Gaming Laptop
        Product p6 = new Product();
        p6.productId = 6;
        p6.name = "Gaming Laptop RTX 3060";
        p6.description = "High-performance gaming laptop with Intel i7, RTX 3060, 16GB RAM, 512GB SSD, and 15.6\" 144Hz display.";
        p6.price = 25000000;
        p6.imageUrl = "https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=400";
        MOCK_PRODUCTS.add(p6);

        // Controller
        Product p7 = new Product();
        p7.productId = 7;
        p7.name = "Wireless Game Controller";
        p7.description = "Universal wireless game controller with dual vibration, 6-axis gyro, and 8-hour battery life.";
        p7.price = 550000;
        p7.imageUrl = "https://images.unsplash.com/photo-1592840062661-eb8c665d0c1f?w=400";
        MOCK_PRODUCTS.add(p7);

        // Mouse Pad
        Product p8 = new Product();
        p8.productId = 8;
        p8.name = "Extended RGB Mouse Pad";
        p8.description = "Large extended mouse pad with RGB lighting effects, anti-slip rubber base, and waterproof surface.";
        p8.price = 180000;
        p8.imageUrl = "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=400";
        MOCK_PRODUCTS.add(p8);
    }

    /**
     * Validate login credentials
     * @return User object if credentials are valid, null otherwise
     */
    public static User validateLogin(String username, String password) {
        User user = MOCK_USERS.get(username);
        if (user != null && user.getPasswordHash().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Get all mock products
     */
    public static List<Product> getAllProducts() {
        return new ArrayList<>(MOCK_PRODUCTS);
    }

    /**
     * Get product by ID
     */
    public static Product getProductById(int productId) {
        for (Product p : MOCK_PRODUCTS) {
            if (p.productId == productId) {
                return p;
            }
        }
        return null;
    }

    /**
     * Search products by name
     */
    public static List<Product> searchProducts(String query) {
        List<Product> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Product p : MOCK_PRODUCTS) {
            if (p.name.toLowerCase().contains(lowerQuery) ||
                p.description.toLowerCase().contains(lowerQuery)) {
                results.add(p);
            }
        }
        return results;
    }

    /**
     * Get mock JWT token
     */
    public static String getMockToken() {
        return MOCK_JWT_TOKEN;
    }

    /**
     * Add product to cart
     */
    public static synchronized boolean addToCart(int productId, int quantity) {
        Product product = getProductById(productId);
        if (product == null) {
            return false;
        }

        CartItem existingItem = MOCK_CART.get(productId);
        if (existingItem != null) {
            // Update quantity if item already exists
            existingItem.quantity += quantity;
        } else {
            // Create new cart item
            CartItem newItem = new CartItem();
            newItem.cartItemId = nextCartItemId++;
            newItem.quantity = quantity;
            newItem.product = product;
            MOCK_CART.put(productId, newItem);
        }
        return true;
    }

    /**
     * Get current cart
     */
    public static synchronized CartResponse getCart() {
        CartResponse response = new CartResponse();
        response.cartId = 1; // Mock cart ID
        response.items = new ArrayList<>(MOCK_CART.values());
        return response;
    }

    /**
     * Clear cart (for testing)
     */
    public static synchronized void clearCart() {
        MOCK_CART.clear();
    }

    /**
     * Remove item from cart
     */
    public static synchronized boolean removeFromCart(int productId) {
        return MOCK_CART.remove(productId) != null;
    }

    /**
     * Update cart item quantity
     */
    public static synchronized boolean updateCartItemQuantity(int productId, int quantity) {
        CartItem item = MOCK_CART.get(productId);
        if (item != null) {
            if (quantity <= 0) {
                MOCK_CART.remove(productId);
            } else {
                item.quantity = quantity;
            }
            return true;
        }
        return false;
    }
}


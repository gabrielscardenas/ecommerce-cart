package com.example.ecommerce;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final List<CartItem> cartItems = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    // View all items in the cart
    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItems;
    }

    // Add an item to the cart
    @PostMapping
    public ResponseEntity<CartItem> addCartItem(@RequestBody CartItem cartItem) {
        cartItem.setId(counter.incrementAndGet());
        cartItems.add(cartItem);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    // Remove an item from the cart
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long id) {
        Optional<CartItem> cartItemOptional = cartItems.stream().filter(item -> item.getId().equals(id)).findFirst();
        if (cartItemOptional.isPresent()) {
            cartItems.remove(cartItemOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


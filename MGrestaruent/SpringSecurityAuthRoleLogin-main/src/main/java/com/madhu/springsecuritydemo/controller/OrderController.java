package com.madhu.springsecuritydemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    // 1. Check if user is logged in (used by JavaScript)
    @GetMapping("/api/check-auth")
    public ResponseEntity<?> checkAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() &&
            !"anonymousUser".equals(auth.getPrincipal())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(401).build();
    }

    // 2. Place order – only logged-in users can access
    @PostMapping("/order/place-order")
    public ResponseEntity<?> placeOrder(@RequestBody Map<String, List<Map<String, Object>>> requestBody) {  // if you want current user object

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Please login first"));
        }

        List<Map<String, Object>> cart = requestBody.get("cart");
        if (cart == null || cart.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Cart is empty"));
        }

        int total = cart.stream()
                        .mapToInt(item -> (Integer) item.get("price"))
                        .sum();

        String username = auth.getName();

        // TODO: Later save to database
        // orderService.saveOrder(username, cart, total);

        System.out.println("Order placed by: " + username + " | Total: ₹" + total);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "total", total,
            "message", "Order placed successfully!"
        ));
    }
}
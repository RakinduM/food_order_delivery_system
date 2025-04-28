package com.edu.paymentservice.controller;

import com.edu.paymentservice.dto.ProductRequest;
import com.edu.paymentservice.dto.StripeResponse;
import com.edu.paymentservice.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product/v1")
@CrossOrigin(origins="*")
public class ProductCheckoutController {

    private final StripeService stripeService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts (@RequestBody ProductRequest productRequest) {
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }
}

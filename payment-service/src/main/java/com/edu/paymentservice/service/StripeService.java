package com.edu.paymentservice.service;

import com.edu.paymentservice.dto.ProductRequest;
import com.edu.paymentservice.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    //Stripe API
    // -> productName, amount, quantity, currency
    //return sessionId and url

    @Value("${stripe.secretKey}")
    private String secretKey;

    public StripeResponse checkoutProducts(ProductRequest productRequest) {
        Stripe.apiKey = secretKey;

        //create a payment intent with the order amount and currency
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(productRequest.getName()).build();

        //create a new line item with the above product data and associated price
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(productRequest.getCurrency() == null ? "USD" : productRequest.getCurrency())
                .setUnitAmount(productRequest.getAmount()).setProductData(productData).build();

        //create a new line item with the above price data
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(productRequest.getQuantity())
                .setPriceData(priceData)
                .build();

        //create new session with the line items
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/")
                .setCancelUrl("https://localhost:8091/cancel")
                .addLineItem(lineItem)
                .build();

        Session session = null;

        try{
            session = Session.create(params);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }

        return StripeResponse.builder()
                .status("SUCCESS")
                .message("Payment session created")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}

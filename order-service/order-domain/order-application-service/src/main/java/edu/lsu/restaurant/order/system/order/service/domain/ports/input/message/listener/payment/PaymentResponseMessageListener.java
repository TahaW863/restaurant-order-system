package edu.lsu.restaurant.order.system.order.service.domain.ports.input.message.listener.payment;

import edu.lsu.restaurant.order.system.order.service.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCancelled(PaymentResponse paymentResponse);
}

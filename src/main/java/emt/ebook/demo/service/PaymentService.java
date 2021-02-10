package emt.ebook.demo.service;

import com.stripe.exception.*;
import com.stripe.model.Charge;
import emt.ebook.demo.dto.ChargeRequest;

public interface PaymentService {
    Charge pay(ChargeRequest chargeRequest) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException;
}

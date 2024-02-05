package org.ecomm.ecommuser.rest.services;

import org.ecomm.ecommuser.rest.model.Address;
import org.ecomm.ecommuser.rest.request.AddUserAddressRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    void createAddress(AddUserAddressRequest request);

    List<Address> listAddresses();

    Address getDefaultAddress();
}

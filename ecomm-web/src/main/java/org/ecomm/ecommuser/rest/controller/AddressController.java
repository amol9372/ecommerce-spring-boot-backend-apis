package org.ecomm.ecommuser.rest.controller;

import org.ecomm.ecommuser.rest.model.Address;
import org.ecomm.ecommuser.rest.request.AddUserAddressRequest;
import org.ecomm.ecommuser.rest.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("address")
@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping
    public ResponseEntity<Object> createUserAddress(@RequestBody AddUserAddressRequest request){
         addressService.createAddress(request);
         return ResponseEntity.created(URI.create("/api/address")).build();
    }

    @GetMapping
    public ResponseEntity<List<Address>> listAddresses(){
         var addresses = addressService.listAddresses();
         return ResponseEntity.ok(addresses);
    }

    @GetMapping("default")
    public ResponseEntity<Address> defaultAddress(){
        var address = addressService.getDefaultAddress();
        return ResponseEntity.ok(address);
    }

}

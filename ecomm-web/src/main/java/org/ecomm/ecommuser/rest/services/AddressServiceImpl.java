package org.ecomm.ecommuser.rest.services;

import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommuser.exception.ErrorCodes;
import org.ecomm.ecommuser.exception.ErrorResponse;
import org.ecomm.ecommuser.exception.ResourceNotFound;
import org.ecomm.ecommuser.exception.UserNotFound;
import org.ecomm.ecommuser.persistance.entity.user.EAddress;
import org.ecomm.ecommuser.persistance.entity.user.EUserAddress;
import org.ecomm.ecommuser.persistance.repository.AddressRepository;
import org.ecomm.ecommuser.persistance.repository.UserAddressRepository;
import org.ecomm.ecommuser.rest.model.Address;
import org.ecomm.ecommuser.rest.model.User;
import org.ecomm.ecommuser.rest.request.AddUserAddressRequest;
import org.ecomm.ecommuser.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

  @Autowired UserAddressRepository userAddressRepository;

  @Autowired AddressRepository addressRepository;

  @Override
  public void createAddress(AddUserAddressRequest request) {

    User user = Utility.getLoggedInUser();

    EAddress eAddress =
        EAddress.builder()
            .streetAddress(request.getStreetAddress())
            .postalCode(request.getPostalCode())
            .state(request.getState())
            .name(request.getName())
            .city(request.getCity())
            .country(request.getCountry())
            .build();

    EAddress savedAddress = addressRepository.save(eAddress);

    EUserAddress eUserAddress =
        EUserAddress.builder()
            .address(savedAddress)
            .defaultAddress(request.isDefaultAddress())
            .userId(user.getId())
            .build();

    userAddressRepository.save(eUserAddress);
  }

  @Override
  public List<Address> listAddresses() {

    User user = Utility.getLoggedInUser();

    List<EUserAddress> userAddress =
        userAddressRepository
            .findByUserId(user.getId())
            .orElseThrow(
                () -> {
                  log.error("No address present for user ::: {}", user.getId());
                  return new ResourceNotFound(
                      HttpStatus.UNPROCESSABLE_ENTITY,
                      ErrorResponse.builder()
                          .code(ErrorCodes.USER_DOES_NOT_EXIST)
                          .message("Address does not exists for the user")
                          .build());
                });

    return Utility.stream(userAddress)
        .map(
            item ->
                Address.builder()
                    .streetAddress(item.getAddress().getStreetAddress())
                    .name(item.getAddress().getName())
                    .country(item.getAddress().getCountry())
                    .postalCode(item.getAddress().getPostalCode())
                    .city(item.getAddress().getCity())
                    .state(item.getAddress().getState())
                    .id(item.getAddress().getId())
                    .defaultAddress(item.isDefaultAddress())
                    .build())
        .collect(Collectors.toList());
  }

  @Override
  public Address getDefaultAddress() {
    User user = Utility.getLoggedInUser();

    EAddress address =
        userAddressRepository
            .findByUserIdAndDefaultAddress(user.getId(), Boolean.TRUE)
            .orElseThrow()
            .getAddress();

    return Address.builder()
        .state(address.getState())
        .streetAddress(address.getStreetAddress())
        .id(address.getId())
        .name(address.getName())
        .city(address.getCity())
        .country(address.getCountry())
        .postalCode(address.getPostalCode())
        .build();
  }
}

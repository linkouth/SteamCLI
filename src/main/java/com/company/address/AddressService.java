package com.company.address;

import java.util.List;
import java.util.Optional;

public class AddressService {
    private final AddressDao addressDao = new AddressDao();

    public void createAddress(Address address) {
        addressDao.save(address);
    }

    public Optional<Address> findById(int id) {
        return Optional.ofNullable(addressDao.findById(id));
    }

    public Optional<List<Address>> findAll() { return Optional.ofNullable(addressDao.findAll()); }

    public void update(Address category) { addressDao.update(category); }

    public void delete(Address category) {
        addressDao.delete(category);
    }
}

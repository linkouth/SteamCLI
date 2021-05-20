package com.company.app;

import com.company.address.Address;
import com.company.address.AddressService;
import com.company.user.User;
import com.company.user.UserService;
import com.company.util.Validations;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AddressApp implements AppInterface {
    private final AddressService addressService = new AddressService();
    private final UserService userService = new UserService();

    public void parseCommand() {
        printHelp();

        try {
            int commandNumber = Integer.parseInt(in.nextLine());
            switch (commandNumber) {
                case 1 -> printAllAddresses();
                case 2 -> printAddressById();
                case 3 -> createAddress();
                case 4 -> editAddress();
                case 5 -> deleteSoftware();
                case 0 -> App.INSTANCE.goToMain();
                default -> out.println("Введите корректную команду");
            }
            out.println();
        } catch (NumberFormatException err) {
            out.println("Некорректный ввод");
        }
    }

    void printHelp() {
        out.println("1 – вывести список");
        out.println("2 – вывести по id");
        out.println("3 – добавить новый адрес");
        out.println("4 – изменить существующий адрес");
        out.println("5 – удалить существующий адрес");
        out.println("0 – вернуться на главную");
        out.println("Введите команду: ");
    }

    void printAllAddresses() {
        Optional<List<Address>> addresses = addressService.findAll();

        if (addresses.isEmpty() || addresses.get().isEmpty()) {
            out.println("Список адресов пуст.");
        } else {
            out.println("Список адресов:");
            try {
                List<Address> items = addresses.get();
                for (Address address: items) {
                    out.println(serializer.writeValueAsString(address));
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    void printAddressById() {
        try {
            int id = -1;
            while (!Validations.isValidId(id)) {
                out.println("Введите id:");
                id = Integer.parseInt(in.nextLine());
                if (!Validations.isValidId(id)) {
                    out.println("id должно быть не пустым и больше нуля");
                }
            }
            Optional<Address> address = addressService.findById(id);

            if (address.isEmpty()) {
                out.println("Адреса с id " + id + " не существует");
            } else {
                out.println("Адрес с id: " + id);
                try {
                    out.println(serializer.writeValueAsString(address.get()));
                }  catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            out.println();
        } catch (NumberFormatException err) {
            out.println("Некорректный ввод");
        }
    }

    void createAddress() {
        try {
            String city = null;
            while (!Validations.isValidName(city)) {
                out.println("Введите город:");
                city = in.nextLine();
                if (!Validations.isValidName(city)) {
                    out.println("Город должен быть не пустым");
                }
            }

            String street = null;
            while (!Validations.isValidName(street)) {
                out.println("Введите улицу:");
                street = in.nextLine();
                if (!Validations.isValidName(street)) {
                    out.println("Улица должна быть не пустой");
                }
            }

            String building = null;
            while (!Validations.isValidName(building)) {
                out.println("Введите номер дома:");
                building = in.nextLine();
                if (!Validations.isValidName(building)) {
                    out.println("Номер дома должен быть не пустым");
                }
            }

            List<User> users = new ArrayList<>();
            boolean isValidIds = false;
            while (!isValidIds) {
                out.println("Введите id пользователей через пробел:");
                List<Integer> userIds = Arrays
                        .stream(in.nextLine().split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                List<Optional<User>> items = userIds.stream()
                        .map(userService::findById)
                        .collect(Collectors.toList());
                if (!items.stream().allMatch(Optional::isPresent)) {
                    out.println("Id должны существовать");
                } else {
                    isValidIds = true;
                    users = items.stream().map(Optional::get).collect(Collectors.toList());
                }
            }

            Address address = new Address(city, street, building);
            if (!users.isEmpty()) {
                List<User> usersFromDB = new ArrayList<>(users);
                for (int i = 0; i < usersFromDB.size(); ++i) {
                    User user = usersFromDB.get(i);
                    user.setAddress(address);
                    address.addUser(user);
                }
            }
            addressService.createAddress(address);
            out.println("Добавлена новый адрес");
        } catch (NumberFormatException err) {
            out.println("Некорректный ввод");
        }
    }

    void editAddress() {
        try {
            int id = -1;
            while (!Validations.isValidId(id)) {
                out.println("Введите id:");
                id = Integer.parseInt(in.nextLine());
                if (!Validations.isValidId(id)) {
                    out.println("id должно быть не пустым и больше нуля");
                }
            }
            Optional<Address> result = addressService.findById(id);

            if (result.isEmpty()) {
                out.println("Адреса с id " + id + " не существует");
                out.println();
                return;
            }

            Address address = result.get();

            String city = null;
            while (!Validations.isValidName(city)) {
                out.println("Введите город:");
                city = in.nextLine();
                if (!Validations.isValidName(city)) {
                    out.println("Город должен быть не пустым");
                }
            }

            String street = null;
            while (!Validations.isValidName(street)) {
                out.println("Введите улицу:");
                street = in.nextLine();
                if (!Validations.isValidName(street)) {
                    out.println("Улица должна быть не пустой");
                }
            }

            String building = null;
            while (!Validations.isValidName(building)) {
                out.println("Введите номер дома:");
                building = in.nextLine();
                if (!Validations.isValidName(building)) {
                    out.println("Номер дома должен быть не пустым");
                }
            }

            List<User> users = new ArrayList<>();
            boolean isValidIds = false;
            while (!isValidIds) {
                out.println("Введите id пользователей через пробел:");
                List<Integer> userIds = Arrays
                        .stream(in.nextLine().split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                List<Optional<User>> items = userIds.stream()
                        .map(userService::findById)
                        .collect(Collectors.toList());
                if (!items.stream().allMatch(Optional::isPresent)) {
                    out.println("Id должны существовать");
                } else {
                    isValidIds = true;
                    users = items.stream().map(Optional::get).collect(Collectors.toList());
                }
            }

            address.setCity(city);
            address.setStreet(street);
            address.setBuilding(building);
            address.removeUsers();
            if (!users.isEmpty()) {
                List<User> usersFromDB = new ArrayList<>(users);
                for (int i = 0; i < usersFromDB.size(); ++i) {
                    User user = usersFromDB.get(i);
                    user.setAddress(address);
                    address.addUser(user);
                }
            }

            addressService.update(address);

            out.println("Адрес с id +" + id + " изменена");
            out.println();
        } catch (NumberFormatException err) {
            out.println("Некорректный ввод");
        }
    }

    void deleteSoftware() {
        try {
            int id = -1;
            while (!Validations.isValidId(id)) {
                out.println("Введите id:");
                id = Integer.parseInt(in.nextLine());
                if (!Validations.isValidId(id)) {
                    out.println("id должно быть не пустым и больше нуля");
                }
            }
            Optional<Address> software = addressService.findById(id);

            if (software.isEmpty()) {
                out.println("Адреса с id " + id + " не существует");
            } else {
                addressService.delete(software.get());
                out.println("Адрес с id: " + id + " удален");
                out.println(software.get());
            }
            out.println();
        } catch (NumberFormatException err) {
            out.println("Некорректный ввод");
        }
    }
}

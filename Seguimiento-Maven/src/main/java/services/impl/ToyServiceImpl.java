package services.impl;

import mapping.dto.ToyDto;
import mapping.enums.ToyType;
import mapping.mappers.ToyMapper;
import model.Toy;
import services.ToyService;
import services.exceptions.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ToyServiceImpl implements ToyService {
    private List<Toy> toy  = new ArrayList<>();

    @Override
    public ToyDto createNewToy(String name, ToyType type, double price, int quantities) {
        try {
            if (name == null || name.isEmpty()) {
                throw new ArgumentException("The name of the toy cannot be null or void");
            } else if (price < 0) {
                throw new ArgumentException("The price of the toy cannot be negative");
            } else if (quantities < 0) {
                throw new ArgumentException("The number of toys can't be negative");
            } else {
                Toy toy1 = new Toy(name, type, price, quantities);
                toy.add(toy1);
                return ToyMapper.mapToDTO(toy1);
            }
        } catch (ArgumentException e) {
            System.out.println("Error creating a new toy:" + e.getMessage());
           e.printStackTrace();
            return null;
        }
    }
    @Override
    public void addtoy(ToyDto toyDto) {
        try {
            if (toyDto.name() == null || toyDto.name().isEmpty()) {
                throw new ToyAddException("The name of the toy cannot be null or void");
            } else if (toyDto.price() < 0) {
                throw new ToyAddException("The price of the toy cannot be negative");
            } else if (toyDto.quantities() < 0) {
                throw new ToyAddException("The number of toys can't be negative");
            } else {
                Toy toy1 = new Toy(toyDto.name(), toyDto.type(), toyDto.price(), toyDto.quantities());
                toy.add(toy1);
            }
        } catch (ToyAddException e) {
            System.out.println("Error adding a new toy: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int getQuantityByType(ToyType type) {
        try {
            long count = toy.stream().filter(toy1 -> toy1.getType() == type).count();

            if (count == 0) {
                throw new ToyNotFoundException("No toys found for type: " + type);
            }
            return (int) count;
        } catch (ToyNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public int getTotalAmount() {
        return toy.stream().mapToInt(Toy::getQuantities).sum();
    }

    @Override
    public double getTotalValue(){
        return toy.stream().mapToDouble(toy1->toy1.getPrice()*toy1.getQuantities()).sum();
    }
    @Override
    public Optional<Toy> decreaseStocks(String nameToy, int quantities) {
        try {
            return toy.stream()
                    .filter(toy1 -> toy1.getName().equals(nameToy))
                    .findFirst()
                    .map(toy1 -> {
                        int updatedQuantities = toy1.getQuantities() - quantities;
                        if (updatedQuantities < 0){
                            throw new ToyDecreaseStocks("Cannot decrease stock. Not enough quantity available");
                        }
                        toy1.setQuantities(Math.max(updatedQuantities, 0));
                        return toy1;
                    });
        }catch (ToyDecreaseStocks e){
            System.out.println("Error decreasing stock: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }catch (Exception e) {
            System.out.println("Error decreasing stock: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
           }

    @Override
    public Optional<Toy> increaseStock(String nameToy, int quantities) {
        try {
            return toy.stream()
                    .filter(toy1 -> toy1.getName().equals(nameToy))
                    .findFirst()
                    .map(toy1 -> {
                        int updatedQuantities = toy1.getQuantities() + quantities;
                        if (updatedQuantities < 0) {
                            throw new ToyIncreaseStock("Cannot increase stock. Invalid quantity.");
                        }
                        toy1.setQuantities(updatedQuantities);
                        return toy1;
                    });
        } catch (ToyIncreaseStock e) {
            System.out.println("Error increasing stock: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Error increasing stock: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public ToyType getTypeWithMoreToys() {
        return toy.stream().collect(Collectors.groupingBy(Toy::getType,Collectors.summingInt(Toy::getQuantities)))
                .entrySet().stream()
                .max(Comparator.comparingInt(toy1->toy1.getValue()))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    @Override
    public ToyType getTypeWithLessToys() {
        return toy.stream().collect(Collectors.groupingBy(Toy::getType,Collectors.summingInt(Toy::getQuantities)))
                .entrySet().stream()
                .min(Comparator.comparingInt(toy1->toy1.getValue()))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    @Override
    public CompletableFuture<List<ToyDto>> getToysWithValueGreaterA(double valor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (valor < 0) {
                    throw new InvalidValueException("The value cannot be negative");
                }
                return toy.stream()
                        .filter(toy1 -> toy1.getPrice() > valor)
                        .map(ToyMapper::mapToDTO)
                        .collect(Collectors.toList());
            } catch (InvalidValueException e) {
                System.out.println("Error en getToysWithValueGreaterA: " + e.getMessage());
                e.printStackTrace();
                return Collections.emptyList();
            } catch (Exception e) {
                System.out.println("Error en getToysWithValueGreaterA: " + e.getMessage());
                e.printStackTrace();
                return Collections.emptyList();
            }
        });
    }

    @Override
    public List<ToyDto> sortToysByStock() {
        return toy.stream()
                .sorted(Comparator.comparingInt(Toy::getQuantities))
                .map(ToyMapper::mapToDTO).collect(Collectors.toList());
    }
    }


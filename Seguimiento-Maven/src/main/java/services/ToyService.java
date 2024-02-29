package services;

import mapping.dto.ToyDto;
import mapping.enums.ToyType;
import model.Toy;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ToyService {
    ToyDto createNewToy(String name, ToyType type, double price, int quantities);
    void addtoy(ToyDto toyDto);
        int getQuantityByType(ToyType type);
        int getTotalAmount();
        double getTotalValue();
        Optional<Toy> decreaseStocks(String nameToy, int quantities);
        Optional<Toy> increaseStock(String nameToy, int quantities);
        ToyType getTypeWithMoreToys();
        ToyType getTypeWithLessToys();
        CompletableFuture<List<ToyDto>> getToysWithValueGreaterA(double valor);
        List<ToyDto> sortToysByStock();
    }


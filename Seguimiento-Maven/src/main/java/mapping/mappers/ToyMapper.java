package mapping.mappers;

import mapping.dto.ToyDto;
import model.Toy;

public class ToyMapper {
    public static ToyDto mapToDTO(Toy toy) {
            return new ToyDto(toy.getName(), toy.getType(), toy.getPrice(), toy.getQuantities());
        }
    }


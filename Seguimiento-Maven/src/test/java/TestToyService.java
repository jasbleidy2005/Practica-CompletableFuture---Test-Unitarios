import mapping.dto.ToyDto;
import mapping.enums.ToyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ToyService;
import services.impl.ToyServiceImpl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestToyService {
    private ToyService toyService;
    @BeforeEach
    public void setUp(){
        toyService = new ToyServiceImpl();
    }
    @Test
    public void testAddAndCountToys(){
        toyService.addtoy(new ToyDto("Doll", ToyType.FEMALE,4.2,15));
        toyService.addtoy(new ToyDto("Baseball Ball", ToyType.MALE,15.2,13));
        toyService.addtoy(new ToyDto("Water Makeup", ToyType.FEMALE,5.5,10));
        assertEquals(38,toyService.getTotalAmount());
    }
    @Test
   public void testAmountStock(){
        toyService.addtoy(new ToyDto("Drawing Books", ToyType.UNISEX,4.5,9));
        toyService.addtoy(new ToyDto("Doll", ToyType.FEMALE,4.2,15));
        toyService.addtoy(new ToyDto("Baseball Ball", ToyType.MALE,15.2,13));
        toyService.addtoy(new ToyDto("Water Makeup", ToyType.FEMALE,5.5,10));
        toyService.addtoy(new ToyDto("Drawing Books", ToyType.UNISEX,2.5,9));
        assertEquals(ToyType.FEMALE,toyService.getTypeWithMoreToys());
    }
    @Test
    public void etToysWithValueGreaterA(){
        toyService.addtoy(new ToyDto("Drawing Books", ToyType.UNISEX,4.5,9));
        toyService.addtoy(new ToyDto("Doll", ToyType.FEMALE,4.2,15));
        toyService.addtoy(new ToyDto("Baseball Ball", ToyType.MALE,15.2,13));

        assertEquals(3,toyService.getToysWithValueGreaterA(4.1));
    }
    @Test
    public void typeTestWithMoreToys() {
        toyService.addtoy(new ToyDto("Baseball Ball", ToyType.MALE,15.2,13));
        toyService.addtoy(new ToyDto("Water Makeup", ToyType.FEMALE,5.5,10));
        toyService.addtoy(new ToyDto("Drawing Books", ToyType.UNISEX,2.5,9));
        toyService.addtoy(new ToyDto("Soccer Balls", ToyType.MALE,10.2,20));
        toyService.addtoy(new ToyDto("bubble Clock", ToyType.UNISEX,13.3,19));

        assertEquals(ToyType.MALE, toyService.getTypeWithMoreToys());
    }
    @Test
    public void testCreateGetNewToy() {
        toyService.createNewToy("Cards", ToyType.UNISEX, 9.99, 20);
        CompletableFuture<List<ToyDto>> futureToys = toyService.getToysWithValueGreaterA(0);
        futureToys.thenAccept(toys -> {
            assertFalse(toys.isEmpty(), "The list of toys should not be empty");
            ToyDto toyNew = toys.get(0);
            assertEquals("Cards", toyNew.name());
            assertEquals(ToyType.UNISEX, toyNew.type());
            assertEquals(9.99, toyNew.price());
            assertEquals(20, toyNew.quantities());
        }).join();

    }
}

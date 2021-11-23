package marketTests;

import market.MarketApiService;
import market.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.EOFException;
import java.io.IOException;

public class MarketApiTests {

    private static MarketApiService apiService;

    @BeforeAll
    static void beforeAll() {
        apiService = new MarketApiService();
    }

    @Test
    void testGetProductByIdIfProductIsExist() throws IOException {

        Product product = apiService.getProduct(1L);
        Assertions.assertEquals(1L, product.getId());
        Assertions.assertEquals("Milk", product.getTitle());
        Assertions.assertEquals("Food", product.getCategoryTitle());
        Assertions.assertEquals(95, product.getPrice());
    }

    @Test
    void testGetProductByIdIfProductIsNotExist() {

        Assertions.assertThrows(RuntimeException.class, () -> {
            Product product = apiService.getProduct(100L);
        });
    }

    @Test
    void testCreateNewProduct() {
//         !!!! Не могу понять почему у меня не работает ламбок, подключено в pomp
//        всё как у вас, попробовала в ручную всё прописать в классе Product, но всё равно не собирает мне если пробовать
 //       такую вот конструкцию : Product product = Product.builder().withPrice(150).withTitle("Strawberry")
        //       .withCategoryTitle("Food").build();

//        Product product = Product.builder()
//                .categoryTitle("Food")
//                .price(150)
//                .title("Strawberry")
//                .build();
//        id = apiService.createProduct(product);
//        Product expected = apiService.getProduct(id);
//        Assertions.assertEquals(id, expected.getId());
   }

   @Test
    void testDeleteById() {
       Assertions.assertThrows(EOFException.class, () -> {
           apiService.deleteProduct(6L);
       });

       Assertions.assertThrows(RuntimeException.class, () -> {
           apiService.getProduct(6L);
       });
   }
}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    //REFACTOR ALL THE REPEATED LINES OF CODE
    private Restaurant createRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        Restaurant restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        return restaurant;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant restaurant = createRestaurant();
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        LocalTime now = LocalTime.parse("12:30:00");
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(now);
        assertTrue(mockRestaurant.isRestaurantOpen());
    }


    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant restaurant = createRestaurant();
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        LocalTime now = LocalTime.parse("09:30:00");
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(now);
        assertFalse(mockRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        Restaurant restaurant = createRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        Restaurant restaurant = createRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        Restaurant restaurant = createRestaurant();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void get_order_value_on_select_items_from_menu_should_be_non_zero(){
        Restaurant restaurant = createRestaurant();
        String[] items = {"Sweet corn soup"};
        int orderValue = restaurant.getOrderValue(items);
        assertEquals(119, orderValue);
        assertNotEquals(0, orderValue);
    }

    @Test
    public void get_order_value_on_no_items_selected_from_menu_should_be_zero(){
        Restaurant restaurant = createRestaurant();
        String[] items = {};
        int orderValue = restaurant.getOrderValue(items);
        assertEquals(0, orderValue);
    }
}
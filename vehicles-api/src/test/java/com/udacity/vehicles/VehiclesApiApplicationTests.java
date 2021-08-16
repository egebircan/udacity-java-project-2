package com.udacity.vehicles;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PricingClient;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.service.CarNotFoundException;
import com.udacity.vehicles.service.CarService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class VehiclesApiApplicationTests {

    @Mock
    CarRepository carRepository;

    @Mock
    MapsClient mapsClient;

    @Mock
    PricingClient pricingClient;

    @InjectMocks
    CarService carService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void shouldCreateCar() {
        Car car = new Car();

        Car returnedCar = new Car();
        returnedCar.setId(123L);

        Mockito.when(carRepository.save(car)).thenReturn(returnedCar);

        Assert.assertEquals(carService.save(car).getId().longValue(), 123);
    }

    @Test
    public void shouldReturnCarById() {
        Location location = new Location();
        location.setCity("istanbul");

        Mockito.when(carRepository.findById(123L)).thenReturn(Optional.of(new Car()));
        Mockito.when(mapsClient.getAddress(any(Location.class))).thenReturn(location);
        Mockito.when(pricingClient.getPrice(123L)).thenReturn("120.000 USD");

        Car car = carService.findById(123L);

        Assert.assertEquals(car.getLocation().getCity(), "istanbul");
        Assert.assertEquals(car.getPrice(), "120.000 USD");
    }

    @Test
    public void shouldUpdateCar() {
        Car car = new Car();
        car.setId(123L);
        car.setCondition(Condition.NEW);

        Car updatedCar = new Car();
        updatedCar.setId(123L);
        updatedCar.setCondition(Condition.USED);

        Mockito.when(carRepository.findById(123L)).thenReturn(Optional.of(car));
        Mockito.when(carRepository.save(any(Car.class))).thenReturn(updatedCar);

        Car returnedCar = carService.save(updatedCar);
        Assert.assertEquals(returnedCar.getId().longValue(), 123);
        Assert.assertEquals(returnedCar.getCondition(), Condition.USED);
    }

    @Test(expected = CarNotFoundException.class)
    public void shouldDeleteCar() {
        Mockito.when(carRepository.findById(123L)).thenReturn(Optional.empty());

        carService.delete(123L);

        // mock a void method with spy?
    }
}

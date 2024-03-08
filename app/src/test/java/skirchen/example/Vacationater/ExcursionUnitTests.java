package skirchen.example.Vacationater;

import skirchen.example.Vacationater.entity.Excursion;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExcursionUnitTests {
    Excursion excursion = new Excursion(0, 0, "Test", "08/31/23");
    @Test
    public void titleGetSetTest(){
        String expectedTitle = "Expected Title";
        excursion.setTitle(expectedTitle);
        String retrievedTitle = excursion.getTitle();
        assertEquals(expectedTitle, retrievedTitle);
    }

    @Test
    public void dateGetSetTest(){
        String expectedDate = "09/01/23";
        excursion.setDate(expectedDate);
        String retrievedDate = excursion.getDate();
        assertEquals(expectedDate,retrievedDate);
    }

    @Test
    public void excursionIdGetSetTest(){
        int expectedId = 1;
        excursion.setExcursionId(expectedId);
        int recievedId = excursion.getExcursionId();
        assertEquals(expectedId,recievedId);
    }

    @Test
    public void vacationIdGetSetTest(){
        int expectedId = 1;
        excursion.setVacationId(expectedId);
        int recievedId = excursion.getVacationId();
        assertEquals(expectedId, recievedId);
    }
}

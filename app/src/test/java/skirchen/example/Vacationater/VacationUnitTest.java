package skirchen.example.Vacationater;

import org.junit.Test;
import static org.junit.Assert.*;

import skirchen.example.Vacationater.entity.Vacation;

public class VacationUnitTest {
    Vacation vacation =new Vacation(0,"test","test","test","test");
    @Test
    public void vacationIdGetSetTest(){
        int expectedId = 1;
        vacation.setVacationId(expectedId);
        int reveivedId = vacation.getVacationId();
        assertEquals(expectedId,reveivedId);
    }

    @Test
    public void vacationTitleGetSetTest(){
        String expectedTitle = "Expected Title";
        vacation.setTitle(expectedTitle);
        String recievedTitle = vacation.getTitle();
        assertEquals(expectedTitle,recievedTitle);
    }
    @Test
    public void vacationLodgingGetSetTest(){
        String expectedDtring = "Expected String";
        vacation.setLodging(expectedDtring);
        String recievedString = vacation.getLodging();
        assertEquals(expectedDtring,recievedString);
    }

    @Test
    public void vacationStartDateGetSetTest(){
        String expectedString = "08/31/23";
        vacation.setStartDate(expectedString);
        String revievedString = vacation.getStartDate();
        assertEquals(expectedString,revievedString);
    }

    @Test
    public void vacationEndDateGetSetTest(){
        String expectedString = "08/31/23";
        vacation.setEndDate(expectedString);
        String recievedString = vacation.getEndDate();
        assertEquals(expectedString,recievedString);
    }
}

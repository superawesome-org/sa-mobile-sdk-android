package tv.superawesome.lib.sasession.defines;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SAConfigurationTest  {

    @Test
    public void test_ensure_parcel_and_un_parcel_works_dev(){
        // arrange
        SAConfiguration expected = SAConfiguration.DEV;
        List<Integer> parcel = new ArrayList<>();
        parcel.add(expected.ordinal());

        // act
        SAConfiguration actual = SAConfiguration.fromOrdinal( parcel.get(0));

        // assert
        assertEquals(expected,actual);
    }

    @Test
    public void test_ensure_parcel_and_un_parcel_works_Staging(){
        // arrange
        SAConfiguration expected = SAConfiguration.STAGING;
        List<Integer> parcel = new ArrayList<>();
        parcel.add(expected.ordinal());

        // act
        SAConfiguration actual = SAConfiguration.fromOrdinal( parcel.get(0));

        // assert
        assertEquals(expected,actual);
    }

    @Test
    public void test_ensure_parcel_and_un_parcel_works_Production(){
        // arrange
        SAConfiguration expected = SAConfiguration.PRODUCTION;
        List<Integer> parcel = new ArrayList<>();
        parcel.add(expected.ordinal());

        // act
        SAConfiguration actual = SAConfiguration.fromOrdinal( parcel.get(0));

        // assert
        assertEquals(expected,actual);
    }

}
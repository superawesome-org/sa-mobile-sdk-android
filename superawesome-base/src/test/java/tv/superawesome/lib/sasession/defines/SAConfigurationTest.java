package tv.superawesome.lib.sasession.defines;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SAConfigurationTest  {

    private static final int DEV = -1;
    private static final int PRODUCTION = 0;
    private static final int STAGING = 1;

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

    @Test
    public void test_ensure_fromValue_returns_expected_config_Dev(){
        // arrange
        SAConfiguration expected = SAConfiguration.DEV;

        // act
        SAConfiguration actual = SAConfiguration.fromValue(DEV);

        // assert
        assertEquals(expected,actual);
    }

    @Test
    public void test_ensure_fromValue_returns_expected_config_Staging(){
        // arrange
        SAConfiguration expected = SAConfiguration.STAGING;

        // act
        SAConfiguration actual = SAConfiguration.fromValue(STAGING);

        // assert
        assertEquals(expected,actual);
    }

    @Test
    public void test_ensure_fromValue_returns_expected_config_Production(){
        // arrange
        SAConfiguration expected = SAConfiguration.PRODUCTION;

        // act
        SAConfiguration actual = SAConfiguration.fromValue(PRODUCTION);

        // assert
        assertEquals(expected,actual);
    }

}
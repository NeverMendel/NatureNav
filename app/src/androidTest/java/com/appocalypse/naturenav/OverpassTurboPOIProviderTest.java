package com.appocalypse.naturenav;

import com.appocalypse.naturenav.api.OverpassTurboPOIProvider;
import com.appocalypse.naturenav.api.POI;
import com.appocalypse.naturenav.utility.GeocoderSingleton;
import com.appocalypse.naturenav.utility.PoiFinder;

import org.osmdroid.util.GeoPoint;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import java.util.ArrayList;

import org.junit.Test;

public class OverpassTurboPOIProviderTest {

    GeoPoint location = new GeoPoint(45.4935, 12.2405);
    Context appContext;

    public OverpassTurboPOIProviderTest() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        GeocoderSingleton.getInstance(appContext);
        PoiFinder.getInstance(appContext).setLocation(location);
    }

    @Test
    public void testGetPOICloseToReturnsMaxResult() {
        OverpassTurboPOIProvider provider = new OverpassTurboPOIProvider();
        ArrayList<POI> result = provider.getPOICloseTo(appContext, location, "drinking_water", 20, 50000);
        assertEquals(result.size(), 20);
    }

    @Test
    public void testGetPOICloseToReturnsNoResult() {
        OverpassTurboPOIProvider provider = new OverpassTurboPOIProvider();
        ArrayList<POI> result = provider.getPOICloseTo(appContext, location, "drinking_water", 20, 0);
        assertEquals(result.size(), 0);
    }
}

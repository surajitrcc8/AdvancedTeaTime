package com.example.android.teatime;

import android.content.res.Resources;

import com.example.android.teatime.util.TeaUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by surajitbiswas on 1/1/19.
 */
@RunWith(MockitoJUnitRunner.class)
public class TeaUtilTest {
    public Resources resources;

    @Before
    public void setUp() throws Exception {
        resources = mock(Resources.class);
    }

    @Test
    public void shouldGetTeaSize() throws Exception {
        String expected = "Small ($5/cup)";
        when(resources.getString(R.string.tea_size_small)).thenReturn(expected);
        String actual = TeaUtil.getTeaSize(0, resources);
        assertEquals("Got wrong tea size", expected, actual);
    }

    @Test
    public void shouldGetMilkType() throws Exception {
        String expected = "Nonfat Milk";
        when(resources.getString(R.string.milk_type_nonfat)).thenReturn(expected);
        String actual = TeaUtil.getMilkType(1, resources);
        assertEquals("Got wrong milk type", expected, actual);
    }

    @Test
    public void shouldGetSugarType() throws Exception {
        String expected = "50% - Half Sweet";
        when(resources.getString(R.string.sweet_type_50)).thenReturn(expected);
        String actual = TeaUtil.getSugarType(2, resources);
        assertEquals("Got wrong milk type", expected, actual);
    }

    @Test
    public void shouldIncrement() throws Exception {
        int value = 1;
        int expected = value + 1;
        int actual = TeaUtil.increment(value);
        assertEquals("Got wrong value", expected, actual);
    }

    @Test
    public void shouldDecrement() throws Exception {
        int value = 2;
        int expected = value - 1;
        int actual = TeaUtil.decrement(value);
        assertEquals("Got wrong value", expected, actual);
    }

    @Test
    public void shouldGetTeaPrice() throws Exception {
        int value = 1;
        int expected = value * TeaUtil.LARGE_PRICE;
        int actual = TeaUtil.getTeaPrice(TeaUtil.TEA_SIZE_LARGE, value);
        assertEquals("Got wrong value", expected, actual);
    }

}

package me.vukas.common.entity.operation;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CompareTests {
    private Compare compare;

    @Before
    public void buildNewCompareObject(){
        this.compare = new Compare.Builder().build();
    }

    @Test
    public void comparingNullsShouldReturnTrue() {
        assertThat(this.compare.compare(null, null), is(true));
    }

    @Test
    public void comparingNullToStringShouldReturnFalse(){
        assertThat(this.compare.compare(null, "null"), is(false));
    }

    @Test
    public void comparingStringToNullShouldReturnFalse(){
        assertThat(this.compare.compare("null", null), is(false));
    }

    @Test
    public void comparingEqualIntegersShouldReturnTrue(){
        assertThat(this.compare.compare(1, 1), is(true));
    }

    @Test
    public void comparingDifferentIntegersShouldReturnFalse(){
        assertThat(this.compare.compare(1, 2), is(false));
    }

    @Test
    public void comparingIntegerToLongShouldReturnFalse(){
        assertThat(this.compare.compare(1, 1L), is(false));
    }

    @Test
    public void comparingDoubleToIntegerShouldReturnFalse(){
        assertThat(this.compare.compare(1.0, 1), is(false));
    }

    @Test
    public void comparingEqualStringsShouldReturnTrue(){
        assertThat(this.compare.compare("one", "one"), is(true));
    }

    @Test
    public void comparingDifferentStringsShouldReturnTrue(){
        assertThat(this.compare.compare("one", "two"), is(false));
    }

    @Test
    public void comparingEmptyPrimitiveIntegerArraysShouldReturnTrue(){
        assertThat(this.compare.compare(new int[]{}, new int[]{}), is(true));
    }

    @Test
    public void comparingEqualPrimitiveIntegerArraysShouldReturnTrue(){
        assertThat(this.compare.compare(new int[]{1, 2, 3, 4}, new int[]{1, 2, 3, 4}), is(true));
    }

    @Test
    public void comparingEqualPrimitiveIntegerArraysWithRepeatingElementsShouldReturnTrue(){
        assertThat(this.compare.compare(new int[]{1, 2, 1, 3, 2, 4}, new int[]{1, 2, 1, 3, 2, 4}), is(true));
    }

    @Test
    public void comparingDifferentPrimitiveIntegerArraysWithSameLengthShouldReturnFalse(){
        assertThat(this.compare.compare(new int[]{1, 2, 3, 4}, new int[]{1, 2, 3, 5}), is(false));
    }

    @Test
    public void comparingDifferentPrimitiveIntegerArraysWithDifferentLengthShouldReturnFalse(){
        assertThat(this.compare.compare(new int[]{1, 2, 3, 4}, new int[]{1, 2, 3}), is(false));
    }

    @Test
    public void comparingEmptyIntegerArraysShouldReturnTrue(){
        assertThat(this.compare.compare(new Integer[]{}, new Integer[]{}), is(true));
    }

    @Test
    public void comparingEqualIntegerArraysShouldReturnTrue(){
        assertThat(this.compare.compare(new Integer[]{1, 2, 3, 4}, new Integer[]{1, 2, 3, 4}), is(true));
    }

    @Test
    public void comparingEqualIntegerArraysWithNullElementsShouldReturnTrue(){
        assertThat(this.compare.compare(new Integer[]{1, 2, 3, 4, null}, new Integer[]{1, 2, 3, 4, null}), is(true));
    }

    @Test
    public void comparingEqualIntegerArraysWithRepeatingElementsShouldReturnTrue(){
        assertThat(this.compare.compare(new Integer[]{1, 2, 1, 3, 2, 4}, new Integer[]{1, 2, 1, 3, 2, 4}), is(true));
    }

    @Test
    public void comparingDifferentIntegerArraysWithSameLengthShouldReturnFalse(){
        assertThat(this.compare.compare(new Integer[]{1, 2, 3, 4}, new Integer[]{1, 2, 3, 5}), is(false));
    }

    @Test
    public void comparingDifferentIntegerArraysWithDifferentLengthShouldReturnFalse(){
        assertThat(this.compare.compare(new Integer[]{1, 2, 3, 4}, new Integer[]{1, 2, 3}), is(false));
    }

    @Test
    public void comparingEmptyArrayListsShouldReturnTrue(){
        assertThat(this.compare.compare(Collections.emptyList(), Collections.emptyList()), is(true));
    }

    @Test
    public void comparingEqualIntegerArrayListsShouldReturnTrue(){
        assertThat(this.compare.compare(Arrays.asList(1, 2, 3, 4), Arrays.asList(1, 2, 3, 4)), is(true));
    }

    @Test
    public void comparingEqualIntegerArrayListsWithNullElementsShouldReturnTrue(){
        assertThat(this.compare.compare(Arrays.asList(1, 2, 3, 4, null), Arrays.asList(1, 2, 3, 4, null)), is(true));
    }

    @Test
    public void comparingEqualIntegerArrayListsWithRepeatingElementsShouldReturnTrue(){
        assertThat(this.compare.compare(Arrays.asList(1, 2, 1, 3, 2, 4), Arrays.asList(1, 2, 1, 3, 2, 4)), is(true));
    }

    @Test
    public void comparingDifferentIntegerArrayListsWithSameLengthShouldReturnFalse(){
        assertThat(this.compare.compare(Arrays.asList(1, 2, 3, 4), Arrays.asList(1, 2, 3, 5)), is(false));
    }

    @Test
    public void comparingDifferentIntegerArrayListsWithDifferentLengthShouldReturnFalse(){
        assertThat(this.compare.compare(Arrays.asList(1, 2, 3, 4), Arrays.asList(1, 2, 3)), is(false));
    }

//    @Test
//    public void comparingEmptySetsShouldReturnTrue(){
//        assertThat(this.compare.compare(Collections.emptySet(), Collections.emptySet()), is(true));
//    }
//
//    @Test
//    public void comparingEqualIntegerSetsShouldReturnTrue(){
//        assertThat(this.compare.compare(new HashSet<Integer>(Arrays.asList(null, 2, 3, 4)), new HashSet<Integer>(Arrays.asList(4, 3, 2, null))), is(true));
//    }
//
//    @Test
//    public void comparingDifferentIntegerSetsShouldReturnFalse(){
//        assertThat(this.compare.compare(new HashSet<Integer>(Arrays.asList(1, 2, 3, 4)), new HashSet<Integer>(Arrays.asList(1, 2, 3, 5))), is(false));
//    }
}

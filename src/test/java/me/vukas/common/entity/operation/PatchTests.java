package me.vukas.common.entity.operation;

import me.vukas.common.entity.Name;
import me.vukas.common.entity.element.Element;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PatchTests {
    private Diff diff;
    private Compare compare;
    private Patch patch;

    @Before
    public void buildNewCompareObject() {
        this.diff = new Diff.Builder().build();
        this.compare = new Compare.Builder().build();
        this.patch = new Patch.Builder().build();
    }

    @Test
    public void patchingNullWithNullShouldProduceNull() {
        Element diffElement = this.diff.diff(null, null);
        assertThat(this.compare.compare(this.patch.patch(null, diffElement), null), is(true));
    }

    @Test
    public void patchingNullWithIntegerShouldProduceInteger(){
        Element<Name, Integer> diffElement = this.diff.diff(null, 1);
        assertThat(this.compare.compare(this.patch.patch(null, diffElement), 1), is(true));
    }

    @Test
    public void patchingIntegerWithNullShouldProduceNull(){
        Element<Name, Integer> diffElement = this.diff.diff(1, null);
        assertThat(this.compare.compare(this.patch.patch(1, diffElement), null), is(true));
    }

    @Test
    public void patchingNullWithStringShouldProduceString(){
        Element<Name, String> diffElement = this.diff.diff(null, "revised");
        assertThat(this.compare.compare(this.patch.patch(null, diffElement), "revised"), is(true));
    }

    @Test
    public void patchingStringWithNullShouldProduceNull(){
        Element<Name, String> diffElement = this.diff.diff("original", null);
        assertThat(this.compare.compare(this.patch.patch("original", diffElement), null), is(true));
    }

    @Test
    public void patchingIntegerWithIntegerShouldProduceInteger(){
        Element<Name, Integer> diffElement = this.diff.diff(1, 2);
        assertThat(this.compare.compare(this.patch.patch(1, diffElement), 2), is(true));
    }

    @Test
    public void patchingStringWithStringShouldProduceString(){
        Element<Name, String> diffElement = this.diff.diff("original", "revised");
        assertThat(this.compare.compare(this.patch.patch("original", diffElement), "revised"), is(true));
    }
}

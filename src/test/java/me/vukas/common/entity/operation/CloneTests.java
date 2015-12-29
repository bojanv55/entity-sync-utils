package me.vukas.common.entity.operation;

import me.vukas.common.entity.operation.model.EntityNoDefConstructor;
import me.vukas.common.entity.operation.model.GrandChildEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CloneTests {
    private Clone clone;
    private Compare compare;

    @Before
    public void buildNewCloneObject() {
        this.clone = new Clone.Builder().build();
        this.compare = new Compare.Builder().build();
    }

    @Test
    public void cloningNullShouldProduceNull() {
        Object cloned = this.clone.clone(null);
        assertThat(this.compare.compare(cloned, null), is(true));
    }

    @Test
    public void cloningIntegerShouldProduceInteger() {
        int cloned = this.clone.clone(1);
        assertThat(this.compare.compare(cloned, 1), is(true));
    }

    @Test
    public void cloningStringShouldProduceString() {
        String cloned = this.clone.clone("cloned");
        assertThat(this.compare.compare(cloned, "cloned"), is(true));
    }

    @Test
    public void cloningEmptyPrimitiveArrayShouldProduceArray() {
        char[] cloned = this.clone.clone(new char[5]);
        assertThat(this.compare.compare(cloned, new char[5]), is(true));
    }

    @Test
    public void cloningPrimitiveArrayShouldProduceArray() {
        char[] cloned = this.clone.clone(new char[]{1,2,3,4,5});
        assertThat(this.compare.compare(cloned, new char[]{1,2,3,4,5}), is(true));
    }

    @Test
    public void cloningEmptyArrayShouldProduceArray() {
        Byte[] cloned = this.clone.clone(new Byte[5]);
        assertThat(this.compare.compare(cloned, new Byte[5]), is(true));
    }

    @Test
    public void cloningArrayShouldProduceArray() {
        Double[] cloned = this.clone.clone(new Double[]{1.0,2.1,3.2,4.3,5.4});
        assertThat(this.compare.compare(cloned, new Double[]{1.0,2.1,3.2,4.3,5.4}), is(true));
    }

    @Test
    public void cloningEmptyArrayListShouldProduceArrayList() {
        List cloned = this.clone.clone(new ArrayList());
        assertThat(this.compare.compare(cloned, new ArrayList()), is(true));
    }

    @Test
    public void cloningArrayListShouldProduceArrayList() {
        List cloned = this.clone.clone(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)));
        assertThat(this.compare.compare(cloned, new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4))), is(true));
    }

    @Test
    public void cloningEmptySetShouldProduceSet() {
        Set cloned = this.clone.clone(new HashSet());
        assertThat(this.compare.compare(cloned, new HashSet()), is(true));
    }

    @Test
    public void cloningSetShouldProduceSet() {
        Set cloned = this.clone.clone(new HashSet<Integer>(Arrays.asList(1, 2, 3, 4)));
        assertThat(this.compare.compare(cloned, new HashSet<Integer>(Arrays.asList(1, 2, 3, 4))), is(true));
    }

    @Test
    public void cloningEmptyMapShouldProduceMap() {
        Map cloned = this.clone.clone(new HashMap());
        assertThat(this.compare.compare(cloned, new HashMap()), is(true));
    }

    @Test
    public void cloningMapShouldProduceMap() {
        Map<Integer, Integer> map1 = new HashMap<Integer, Integer>();
        map1.put(1, 2);
        map1.put(2, 3);
        map1.put(3, 4);
        map1.put(4, 5);

        Map cloned = this.clone.clone(map1);
        assertThat(this.compare.compare(cloned, map1), is(true));
    }

    @Test
    public void cloningEmptyObjectGraphsShouldProduceObjectGraph() {
        GrandChildEntity gce1 = new GrandChildEntity(false);

        GrandChildEntity cloned = this.clone.clone(gce1);
        assertThat(this.compare.compare(cloned, gce1), is(true));
    }

    @Test
    public void cloningObjectWithoutDefaultConstructorShouldProduceEntity() {
        GrandChildEntity gce1 = new GrandChildEntity(1);
        GrandChildEntity gce2 = new GrandChildEntity(2);
        gce1.setParent1(gce2);
        gce2.setParent1(gce1);
        EntityNoDefConstructor e1 = new EntityNoDefConstructor(1, gce1);

        EntityNoDefConstructor cloned = this.clone.clone(e1);
        assertThat(this.compare.compare(cloned, e1), is(true));
    }
}

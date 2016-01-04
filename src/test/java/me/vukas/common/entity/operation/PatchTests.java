package me.vukas.common.entity.operation;

import me.vukas.common.entity.EntityDefinition;
import me.vukas.common.entity.IgnoredFields;
import me.vukas.common.entity.Name;
import me.vukas.common.entity.element.Element;
import me.vukas.common.entity.operation.model.BaseEntity;
import me.vukas.common.entity.operation.model.GrandChildEntity;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

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

    @Test
    public void patchingEmptyPrimitiveIntegerArrayWithEmptyPrimitiveIntegerArrayShouldProduceEmptyArray(){
        int[] array1 = new int[0];
        int[] array2 = new int[0];
        Element<Name, int[]> diffElement = this.diff.diff(array1, array2);
        assertThat(this.compare.compare(this.patch.patch(new int[0], diffElement), new int[0]), is(true));
    }

    @Test
    public void patchingEmptyPrimitiveIntegerArrayWithPrimitiveIntegerArrayShouldProduceArray(){
        int[] array1 = new int[0];
        int[] array2 = new int[]{1, 2, 3, 4, 5};
        Element<Name, int[]> diffElement = this.diff.diff(array1, array2);
        assertThat(this.compare.compare(this.patch.patch(new int[0], diffElement), new int[]{1, 2, 3, 4, 5}), is(true));
    }

    @Test
    public void patchingPrimitiveIntegerArrayWithEmptyPrimitiveIntegerArrayShouldProduceArray(){
        int[] array1 = new int[]{1, 2, 3, 4, 5};
        int[] array2 = new int[0];
        Element<Name, int[]> diffElement = this.diff.diff(array1, array2);
        assertThat(this.compare.compare(this.patch.patch(new int[]{1, 2, 3, 4, 5}, diffElement), new int[0]), is(true));
    }

    @Test
    public void patchingPrimitiveIntegerArrayWithPrimitiveIntegerArrayShouldProduceArray(){
        int[] array1 = new int[]{1, 2, 3, 4, 5};
        int[] array2 = new int[]{2, 6, 7};
        Element<Name, int[]> diffElement = this.diff.diff(array1, array2);
        assertThat(this.compare.compare(this.patch.patch(new int[]{1, 2, 3, 4, 5}, diffElement), new int[]{2, 6, 7}), is(true));
    }

    @Test
    public void patchingPrimitiveIntegerArrayWithRepeatingElementsWithPrimitiveIntegerArrayWithRepeatingElementsShouldProduceArray(){
        int[] array1 = new int[]{1, 2, 2, 3, 4, 7, 5, 1};
        int[] array2 = new int[]{2, 6, 6, 7, 2, 11, 22, 8, 11};
        Element<Name, int[]> diffElement = this.diff.diff(array1, array2);
        assertThat(this.compare.compare(this.patch.patch(new int[]{1, 2, 2, 3, 4, 7, 5, 1}, diffElement), new int[]{2, 6, 6, 7, 2, 11, 22, 8, 11}), is(true));
    }

    @Test
    public void patchingEmptyIntegerArrayWithEmptyIntegerArrayShouldProduceEmptyArray(){
        Integer[] array1 = new Integer[0];
        Integer[] array2 = new Integer[0];
        Element<Name, Integer[]> diffElement = this.diff.diff(array1, array2);
        assertThat(this.compare.compare(this.patch.patch(new Integer[0], diffElement), new Integer[0]), is(true));
    }

    @Test
    public void patchingEmptyIntegerArrayWithIntegerArrayShouldProduceEmptyArray(){
        Integer[] array1 = new Integer[0];
        Integer[] array2 = new Integer[]{1, 2, 3, 4, 5};
        Element<Name, Integer[]> diffElement = this.diff.diff(array1, array2);
        assertThat(this.compare.compare(this.patch.patch(new Integer[0], diffElement), new Integer[]{1, 2, 3, 4, 5}), is(true));
    }

    @Test
    public void patchingIntegerArrayWithEmptyIntegerArrayShouldProduceEmptyArray(){
        Integer[] array1 = new Integer[]{1, 2, 3, 4, 5};
        Integer[] array2 = new Integer[0];
        Element<Name, Integer[]> diffElement = this.diff.diff(array1, array2);
        assertThat(this.compare.compare(this.patch.patch(new Integer[]{1, 2, 3, 4, 5}, diffElement), new Integer[0]), is(true));
    }

    @Test
    public void patchingIntegerArrayWithIntegerArrayShouldProduceArray(){
        Integer[] array1 = new Integer[]{1, 2, 3, 4, 5};
        Integer[] array2 = new Integer[]{2, 6, 7};
        Element<Name, Integer[]> diffElement = this.diff.diff(array1, array2);
        assertThat(this.compare.compare(this.patch.patch(new Integer[]{1, 2, 3, 4, 5}, diffElement), new Integer[]{2, 6, 7}), is(true));
    }

    @Test
    public void patchingIntegerArrayWithRepeatingElementsWithIntegerArrayWithRepeatingElementsShouldProduceArray(){
        Integer[] array1 = new Integer[]{1, 2, 2, 3, 4, 7, 5, 1};
        Integer[] array2 = new Integer[]{2, 6, 6, 7, 2, 11, 22, 8, 11};
        Element<Name, Integer[]> diffElement = this.diff.diff(array1, array2);
        assertThat(this.compare.compare(this.patch.patch(new Integer[]{1, 2, 2, 3, 4, 7, 5, 1}, diffElement), new Integer[]{2, 6, 6, 7, 2, 11, 22, 8, 11}), is(true));
    }

    @Test
    public void patchingEmptyArrayListWithEmptyArrayListShouldProduceArrayList() {
        List<Integer> list1 = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>();
        Element<Name, List<Integer>> diffElement = this.diff.diff(list1, list2);
        assertThat(this.compare.compare(this.patch.patch(new ArrayList<Integer>(), diffElement), new ArrayList<Integer>()), is(true));
    }

    @Test
    public void patchingEmptyArrayListWithArrayListShouldProduceArrayList() {
        List<Integer> list1 = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        Element<Name, List<Integer>> diffElement = this.diff.diff(list1, list2);
        assertThat(this.compare.compare(this.patch.patch(new ArrayList<Integer>(), diffElement), new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5))), is(true));
    }

    @Test
    public void patchingArrayListWithEmptyArrayListShouldProduceArrayList() {
        List<Integer> list1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> list2 = new ArrayList<Integer>();
        Element<Name, List<Integer>> diffElement = this.diff.diff(list1, list2);
        assertThat(this.compare.compare(this.patch.patch(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5)), diffElement), new ArrayList<Integer>()), is(true));
    }

    @Test
    public void patchingArrayListWithArrayListShouldProduceArrayList() {
        List<Integer> list1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> list2 = new ArrayList<Integer>(Arrays.asList(11, 12, 13, 1));
        Element<Name, List<Integer>> diffElement = this.diff.diff(list1, list2);
        assertThat(this.compare.compare(this.patch.patch(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5)), diffElement), new ArrayList<Integer>(Arrays.asList(11, 12, 13, 1))), is(true));
    }

    @Test
    public void patchingArrayListWithRepeatingElementsWithArrayListWithRepeatingElementsShouldProduceArrayList() {
        List<Integer> list1 = new ArrayList<Integer>(Arrays.asList(1, 2, 2, 3, 4, 7, 5, 1));
        List<Integer> list2 = new ArrayList<Integer>(Arrays.asList(2, 6, 6, 7, 2, 11, 22, 8, 11));
        Element<Name, List<Integer>> diffElement = this.diff.diff(list1, list2);
        assertThat(this.compare.compare(this.patch.patch(new ArrayList<Integer>(Arrays.asList(1, 2, 2, 3, 4, 7, 5, 1)), diffElement), new ArrayList<Integer>(Arrays.asList(2, 6, 6, 7, 2, 11, 22, 8, 11))), is(true));
    }

    @Test
    public void patchingEmptyHashSetWithEmptyHashSetShouldProduceHashSet() {
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();
        Element<Name, Set<Integer>> diffElement = this.diff.diff(set1, set2);
        assertThat(this.compare.compare(this.patch.patch(new HashSet<Integer>(), diffElement), new HashSet<Integer>()), is(true));
    }

    @Test
    public void patchingEmptyHashSetWithHashSetShouldProduceHashSet() {
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        Element<Name, Set<Integer>> diffElement = this.diff.diff(set1, set2);
        assertThat(this.compare.compare(this.patch.patch(new HashSet<Integer>(), diffElement), new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5))), is(true));
    }

    @Test
    public void patchingHashSetWithEmptyHashSetShouldProduceHashSet() {
        Set<Integer> set1 = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> set2 = new HashSet<Integer>();
        Element<Name, Set<Integer>> diffElement = this.diff.diff(set1, set2);
        assertThat(this.compare.compare(this.patch.patch(new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5)), diffElement), new HashSet<Integer>()), is(true));
    }

    @Test
    public void patchingHashSetWithHashSetShouldProduceHashSet() {
        Set<Integer> set1 = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> set2 = new HashSet<Integer>(Arrays.asList(11, 12, 13, 1));
        Element<Name, Set<Integer>> diffElement = this.diff.diff(set1, set2);
        assertThat(this.compare.compare(this.patch.patch(new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5)), diffElement), new HashSet<Integer>(Arrays.asList(11, 12, 13, 1))), is(true));
    }

    @Test
    public void patchingHashSetWithRepeatingElementsWithHashSetWithRepeatingElementsShouldProduceHashSet() {
        Set<Integer> set1 = new HashSet<Integer>(Arrays.asList(1, 2, 2, 3, 4, 7, 5, 1));
        Set<Integer> set2 = new HashSet<Integer>(Arrays.asList(2, 6, 6, 7, 2, 11, 22, 8, 11));
        Element<Name, Set<Integer>> diffElement = this.diff.diff(set1, set2);
        assertThat(this.compare.compare(this.patch.patch(new HashSet<Integer>(Arrays.asList(1, 2, 2, 3, 4, 7, 5, 1)), diffElement), new HashSet<Integer>(Arrays.asList(2, 6, 6, 7, 2, 11, 22, 8, 11))), is(true));
    }

    @Test
    public void patchingUnorderedLinkedHashSetWithUnorderedLinkedHashSetShouldProduceLinkedHashSet() {
        Set<Integer> set1 = new LinkedHashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> set2 = new LinkedHashSet<Integer>(Arrays.asList(11, 12, 13, 1));
        Element<Name, Set<Integer>> diffElement = this.diff.diff(set1, set2);
        assertThat(this.compare.compare(this.patch.patch(new LinkedHashSet<Integer>(Arrays.asList(5, 4, 3, 2, 1)), diffElement), new LinkedHashSet<Integer>(Arrays.asList(1, 13, 12, 11))), is(true));
    }

    @Test
    public void patchingUnorderedLinkedHashSetWithRepeatingElementsWithUnorderedLinkedHashSetWithRepeatingElementsShouldProduceLinkedHashSet() {
        Set<Integer> set1 = new LinkedHashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 2, 2, 4, 11));
        Set<Integer> set2 = new LinkedHashSet<Integer>(Arrays.asList(11, 2, 2, 12, 13, 1));
        Element<Name, Set<Integer>> diffElement = this.diff.diff(set1, set2);
        assertThat(this.compare.compare(this.patch.patch(new LinkedHashSet<Integer>(Arrays.asList(5, 4, 3, 2, 1, 11, 4, 2, 2)), diffElement), new LinkedHashSet<Integer>(Arrays.asList(1, 13, 12, 11, 2, 2))), is(true));
    }

    @Test
    public void patchingEmptyHashMapWithEmptyHashMapShouldProduceHashMap() {
        Map<Integer, Integer> map1 = new HashMap<Integer, Integer>();
        Map<Integer, Integer> map2 = new HashMap<Integer, Integer>();
        Element<Name, Map<Integer, Integer>> diffElement = this.diff.diff(map1, map2);
        assertThat(this.compare.compare(this.patch.patch(new HashMap<Integer, Integer>(), diffElement), new HashMap<Integer, Integer>()), is(true));
    }

    @Test
    public void patchingEmptyHashMapWithHashMapShouldProduceHashMap() {
        Map<Integer, Integer> map1 = new HashMap<Integer, Integer>();
        Map<Integer, Integer> map2 = new HashMap<Integer, Integer>();
        map2.put(4, 5);
        map2.put(3, 4);
        map2.put(2, 3);
        map2.put(1, 2);
        Element<Name, Map<Integer, Integer>> diffElement = this.diff.diff(map1, map2);
        assertThat(this.compare.compare(this.patch.patch(new HashMap<Integer, Integer>(), diffElement), map2), is(true));
    }

    @Test
    public void patchingHashMapWithEmptyHashMapShouldProduceHashMap() {
        Map<Integer, Integer> map1 = new HashMap<Integer, Integer>();
        map1.put(4, 5);
        map1.put(3, 4);
        map1.put(2, 3);
        map1.put(1, 2);
        Map<Integer, Integer> map2 = new HashMap<Integer, Integer>();
        Element<Name, Map<Integer, Integer>> diffElement = this.diff.diff(map1, map2);
        assertThat(this.compare.compare(this.patch.patch(map1, diffElement), new HashMap<Integer, Integer>()), is(true));
    }

    @Test
    public void patchingHashMapWithHashMapShouldProduceHashMap() {
        Map<Integer, Integer> map1 = new HashMap<Integer, Integer>();
        map1.put(4, 5);
        map1.put(3, 4);
        map1.put(2, 3);
        map1.put(1, 2);
        Map<Integer, Integer> map2 = new HashMap<Integer, Integer>();
        map2.put(10, 22);
        map2.put(2, 14);
        map2.put(42, 8);
        map2.put(1, 2);
        Element<Name, Map<Integer, Integer>> diffElement = this.diff.diff(map1, map2);
        assertThat(this.compare.compare(this.patch.patch(map1, diffElement), map2), is(true));
    }

    @Test
    public void patchingEmptyObjectGraphWithEmptyObjectGraphShouldProduceObjectGraph(){
        GrandChildEntity gce1 = new GrandChildEntity(false);
        GrandChildEntity gce2 = new GrandChildEntity(false);
        Element<Name, GrandChildEntity> diffElement = this.diff.diff(gce1, gce2);
        assertThat(this.compare.compare(this.patch.patch(new GrandChildEntity(false), diffElement), new GrandChildEntity(false)), is(true));
    }

    @Test
    public void patchingObjectGraphWithObjectGraphShouldProduceObjectGraph(){
        GrandChildEntity gce1 = new GrandChildEntity(1);
        GrandChildEntity gce2 = new GrandChildEntity(2);
        Element<Name, GrandChildEntity> diffElement = this.diff.diff(gce1, gce2);
        assertThat(this.compare.compare(this.patch.patch(new GrandChildEntity(1), diffElement), new GrandChildEntity(2)), is(true));
    }

    @Test
    public void patchingObjectGraphWithObjectGraphWitchCircularReferencesShouldProduceObjectGraph() throws FileNotFoundException {
        GrandChildEntity gce1 = new GrandChildEntity(1);
        GrandChildEntity gce2 = new GrandChildEntity(2);
        gce1.setParent1(gce2);
        gce1.setParent2(gce1);
        gce1.addParentInList(gce1);
        gce1.addParentInList(gce2);
        gce1.addParentInList(gce1);
        gce1.addParentInList(gce2);
        gce1.addParentInSet(gce1);
        gce1.addParentInSet(gce2);
        gce1.addParentInSet(gce1);
        gce1.addParentInSet(gce2);
        gce1.addParentInArray(0, gce1);
        gce1.addParentsInMap(gce1, gce1);
        gce1.addParentsInMap(gce2, gce1);
        gce2.setParent1(gce1);
        gce2.setParent2(gce2);
        gce2.addParentInList(gce2);
        gce2.addParentInList(gce1);
        gce2.addParentInSet(gce2);
        gce2.addParentInSet(gce1);
        gce2.addParentInSet(null);
        gce2.addParentInSet(null);
        gce2.addParentInArray(0, gce2);
        gce2.addParentInArray(1, gce1);
        gce2.addParentsInMap(gce2, gce2);
        gce2.addParentsInMap(null, gce1);
        gce2.addParentsInMap(gce1, null);
        gce2.addParentsInMap(gce2, gce1);

        GrandChildEntity gce3 = new GrandChildEntity(1);
        GrandChildEntity gce4 = new GrandChildEntity(2);
        gce3.setParent1(gce4);
        gce3.setParent2(gce3);
        gce3.addParentInList(gce3);
        gce3.addParentInList(gce4);
        gce3.addParentInList(gce3);
        gce3.addParentInList(gce4);
        gce3.addParentInSet(gce3);
        gce3.addParentInSet(gce4);
        gce3.addParentInSet(gce3);
        gce3.addParentInSet(gce4);
        gce3.addParentInArray(0, gce3);
        gce3.addParentsInMap(gce3, gce3);
        gce3.addParentsInMap(gce4, gce3);
        gce4.setParent1(gce3);
        gce4.setParent2(gce4);
        gce4.addParentInList(gce4);
        gce4.addParentInList(gce3);
        gce4.addParentInSet(gce4);
        gce4.addParentInSet(gce3);
        gce4.addParentInSet(null);
        gce4.addParentInSet(null);
        gce4.addParentInArray(0, gce4);
        gce4.addParentInArray(1, gce3);
        gce4.addParentsInMap(gce4, gce4);
        gce4.addParentsInMap(null, gce3);
        gce4.addParentsInMap(gce3, null);
        gce4.addParentsInMap(gce4, gce3);

        Element<Name, GrandChildEntity> diffElement = this.diff.diff(gce1, gce2);
        GrandChildEntity patched = this.patch.patch(gce3, diffElement);

        assertThat(this.compare.compare(patched, gce2), is(true));
    }

    @Test
    public void patchingObjectGraphWithObjectGraphWitchCircularReferencesUsingPartialKeyShouldProduceObjectGraph(){
        GrandChildEntity gce1 = new GrandChildEntity(1);
        GrandChildEntity gce2 = new GrandChildEntity(2);
        gce1.setParent1(gce2);
        gce1.setParent2(gce1);
        gce1.addParentInList(gce1);
        gce1.addParentInList(gce2);
        gce1.addParentInList(gce1);
        gce1.addParentInList(gce2);
        gce1.addParentInSet(gce1);
        gce1.addParentInSet(gce2);
        gce1.addParentInSet(gce1);
        gce1.addParentInSet(gce2);
        gce1.addParentInArray(0, gce1);
        gce1.addParentsInMap(gce1, gce1);
        gce1.addParentsInMap(gce2, gce1);
        gce1.setParentInterface(gce2);
        gce1.setParentAbstract(gce1);
        gce1.addParentInInterfaceArray(0, gce1);
        gce1.addParentInAbstractArray(0, gce2);
        gce2.setParent1(gce1);
        gce2.setParent2(gce2);
        gce2.addParentInList(gce2);
        gce2.addParentInList(gce1);
        gce2.addParentInSet(gce2);
        gce2.addParentInSet(gce1);
        gce2.addParentInSet(null);
        gce2.addParentInSet(null);
        gce2.addParentInArray(0, gce2);
        gce2.addParentInArray(1, gce1);
        gce2.addParentsInMap(gce2, gce2);
        gce2.addParentsInMap(null, gce1);
        gce2.addParentsInMap(gce1, null);
        gce2.addParentsInMap(gce2, gce1);

        GrandChildEntity gce3 = new GrandChildEntity(1);
        GrandChildEntity gce4 = new GrandChildEntity(2);
        gce3.setParent1(gce4);
        gce3.setParent2(gce3);
        gce3.addParentInList(gce3);
        gce3.addParentInList(gce4);
        gce3.addParentInList(gce3);
        gce3.addParentInList(gce4);
        gce3.addParentInSet(gce3);
        gce3.addParentInSet(gce4);
        gce3.addParentInSet(gce3);
        gce3.addParentInSet(gce4);
        gce3.addParentInArray(0, gce3);
        gce3.addParentsInMap(gce3, gce3);
        gce3.addParentsInMap(gce4, gce3);
        gce3.setParentInterface(gce4);
        gce3.setParentAbstract(gce3);
        gce3.addParentInInterfaceArray(0, gce3);
        gce3.addParentInAbstractArray(0, gce4);
        gce4.setParent1(gce3);
        gce4.setParent2(gce4);
        gce4.addParentInList(gce4);
        gce4.addParentInList(gce3);
        gce4.addParentInSet(gce4);
        gce4.addParentInSet(gce3);
        gce4.addParentInSet(null);
        gce4.addParentInSet(null);
        gce4.addParentInArray(0, gce4);
        gce4.addParentInArray(1, gce3);
        gce4.addParentsInMap(gce4, gce4);
        gce4.addParentsInMap(null, gce3);
        gce4.addParentsInMap(gce3, null);
        gce4.addParentsInMap(gce4, gce3);

        EntityDefinition entityDefinition = new EntityDefinition(GrandChildEntity.class, "commonInt", "commonString");
        Diff diff = new Diff.Builder().registerEntity(entityDefinition).build();
        Patch patch = new Patch.Builder().build();
        Compare compare = new Compare.Builder().build();

        Element<Name, GrandChildEntity> diffElement = diff.diff(gce1, gce2);
        GrandChildEntity patched = patch.patch(gce3, diffElement);
        assertThat(compare.compare(patched, gce2), is(true));
    }

    @Test
    public void patchingObjectGraphWithObjectGraphUsingPartialKeyShouldProduceObjectGraph(){
        GrandChildEntity gce1 = new GrandChildEntity(1);
        GrandChildEntity gce2 = new GrandChildEntity(2);

        EntityDefinition entityDefinition = new EntityDefinition(GrandChildEntity.class, "commonInt", "commonString").registerSuperclass(BaseEntity.class, "commonInt");
        Diff diff = new Diff.Builder().registerEntity(entityDefinition).build();
        Patch patch = new Patch.Builder().build();
        Compare compare = new Compare.Builder().build();

        Element<Name, GrandChildEntity> diffElement = diff.diff(gce1, gce2);
        assertThat(compare.compare(patch.patch(new GrandChildEntity(1), diffElement), new GrandChildEntity(2)), is(true));
    }

    @Test
    public void patchingObjectGraphWithObjectGraphUsingPartialKeyAndIgnoredFieldsShouldPatchPartialObjectGraph(){
        GrandChildEntity gce1 = new GrandChildEntity(1);
        GrandChildEntity gce2 = new GrandChildEntity(2);
        gce2.setParent1(gce2);

        EntityDefinition entityDefinition = new EntityDefinition(GrandChildEntity.class, "commonInt", "commonString").registerSuperclass(BaseEntity.class, "commonInt");
        IgnoredFields ignoredFields = new IgnoredFields(GrandChildEntity.class, "parent1", "parent2");
        Diff diff = new Diff.Builder().registerEntity(entityDefinition).ignoreFields(ignoredFields).build();
        Patch patch = new Patch.Builder().build();
        Compare compare = new Compare.Builder().build();

        Element<Name, GrandChildEntity> diffElement = diff.diff(gce1, gce2);
        GrandChildEntity patched = patch.patch(new GrandChildEntity(1), diffElement);
        assertThat(compare.compare(patched, gce2), is(false));  //parent1 in patched object will be null, since this field is ignored
    }
}

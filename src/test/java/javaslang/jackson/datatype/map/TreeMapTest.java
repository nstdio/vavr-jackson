package javaslang.jackson.datatype.map;

import com.fasterxml.jackson.core.type.TypeReference;

import javaslang.collection.Map;
import javaslang.collection.SortedMap;
import javaslang.collection.TreeMap;
import javaslang.control.Option;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class TreeMapTest extends MapTest {
    @Override
    Class<?> clz() {
        return TreeMap.class;
    }

    @Override
    protected TypeReference<TreeMap<String, Option<Integer>>> typeReferenceWithOption() {
        return new TypeReference<TreeMap<String, Option<Integer>>>() {};
    }

    @Override
    <K, V> Map<K, V> emptyMap() {
        return TreeMap.empty((o1, o2) -> o1.toString().compareTo(o2.toString()));
    }

    static class Clazz{
        private SortedMap<Integer, Integer> set;
        public SortedMap<Integer, Integer> getSet() {return set;}
        public void setSet(SortedMap<Integer, Integer> set) {this.set = set;}
        public boolean equals(Object o){
            return Objects.equals(set, ((Clazz)o).set);
        }
        public int hashCode(){
            return set.hashCode();
        }
    }

    @Test
    public void testDeserializeToSortedMap() throws IOException {
        Clazz c = new Clazz();
        c.setSet(TreeMap.of(1, 3, 5, 7));
        Clazz dc = mapper().readValue(mapper().writeValueAsString(c), Clazz.class);
        assertEquals(c, dc);
    }
}

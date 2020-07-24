package ru.whitebite.redis.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author WhiteBite
 */
public class RedisMapTest {

    Map<String,String> map1;
    Map<String,String> map2;
    @Before
    public void setup(){
        map1 = new RedisMap();
        map2 = new RedisMap();
    }

    @Test
    public void testClear1() {

        map1.put("clear1_value", "clear1_value");
        map1.put("clear2_value", "clear2_value");
        Assert.assertEquals(2, map1.size());
        map1.clear();
        Assert.assertEquals(0, map1.size());
    }

    @Test
    public void testClear2() {

       // Map<String, String> map1 = new RedisMap();
        map1.put("clear1_value", "clear1_value");
        map1.put("clear2_value", "clear2_value");
        Assert.assertEquals(2, map1.size());
        map1.clear();
        Assert.assertEquals(0, map1.size());
    }

    @Test
    public void testClear3() {
        map1.clear();
        map2.clear();
        map1.put("one", "1");
        map1.put("one2", "2");
        map1.put("one3", "3");
        map2.put("two", "4");
        map2.put("two2", "5");
        map2.put("two3", "6");

        Assert.assertEquals(3, map1.size());
        Assert.assertEquals(3, map2.size());
        map1.clear();
        Assert.assertEquals(0, map1.size());
        Assert.assertEquals(3, map2.size());

        map2.clear();
        Assert.assertEquals(0, map2.size());

    }

    @Test
    public void baseTests() {
        Map<String, String> map1 = new RedisMap("map1");
        Map<String, String> map2 = new RedisMap("map2");

        map1.put("one", "1");

        map2.put("one", "ONE");
        map2.put("two", "TWO");

        Assert.assertEquals("1", map1.get("one"));
        Assert.assertEquals(1, map1.size());
        Assert.assertEquals(2, map2.size());

        map1.put("one", "first");

        Assert.assertEquals("first", map1.get("one"));
        Assert.assertEquals(1, map1.size());

        Assert.assertTrue(map1.containsKey("one"));
        Assert.assertFalse(map1.containsKey("two"));

        Set<String> keys2 = map2.keySet();
        Assert.assertEquals(2, keys2.size());
        Assert.assertTrue(keys2.contains("one"));
        Assert.assertTrue(keys2.contains("two"));

        Collection<String> values1 = map1.values();
        Assert.assertEquals(1, values1.size());
        Assert.assertTrue(values1.contains("first"));

    }

    //++
    @Test
    public void testPut1() {
        int size = map1.size();
        map1.put("testPut1_key", "testPut1_value");
        Assert.assertEquals(++size, map1.size());
        map1.remove("testPut1_key");
    }

    //++
    @Test(expected = NullPointerException.class)
    public void testPut3_tryPutNullKey() {
        int size = map1.size();
        map1.put(null, "testPut3_value");
    }

    //++
    @Test(expected = NullPointerException.class)
    public void testPut4_tryPutNullValue() {
        int size = map1.size();
        map1.put("testPut4_null", null);
    }

    //++
    @Test
    public void testPut2_withEmptyKey() {
        int size = map1.size();

        String key = "";
        String value = "testPut2_withEmptyKey_value";
        //clear empty key if exists
        map1.remove(key);

        map1.put(key, value);
        Assert.assertEquals(++size, map1.size());
        map1.put(key, value);
        Assert.assertEquals(size, map1.size());
        map1.remove(key);
        Assert.assertEquals(--size, map1.size());
        map1.remove(key);
        Assert.assertEquals(size, map1.size());
    }

    //++
    @Test
    public void testGet1() {

        String key = "testGet1_key";
        String value = "testGet1_value";
        map1.put(key, value);
        Assert.assertEquals(value, map1.get(key));

        map1.remove("testGet1_key");
    }

    //++
    @Test
    public void testGet2_withEmptyKey() {
        String key = "";
        String value = "testGet2_value";
        map1.remove(key);
        Assert.assertNull(map1.get(key));
        map1.put(key, value);
        Assert.assertEquals(value, map1.get(key));
        map1.remove("testGet2_key");
    }

    //++
    @Test
    public void testSize() {
        int curSize = map1.size();
        map1.put("testSizeKey1", "testSizeValue1");
        map1.put("testSizeKey2", "testSizeValue2");
        Assert.assertTrue(map1.containsKey("testSizeKey1"));
        Assert.assertTrue(map1.containsKey("testSizeKey2"));

        Assert.assertEquals(curSize + 2, map1.size());
        map1.remove("testSizeKey1");
        map1.remove("testSizeKey2");
        Assert.assertEquals(curSize, map1.size());
    }

    @Test
    public void testEmptyNewMap() {

        Assert.assertTrue(map1.isEmpty());
        Assert.assertEquals(0, map1.size());
    }

    @Test
    public void test_containsKey1() {
        Assert.assertFalse(map1.containsKey("key1"));
        map1.put("key1", "value1");
        Assert.assertTrue(map1.containsKey("key1"));
    }

    @Test
    public void test_containsKey2() {
        Assert.assertFalse(map1.containsKey("key1"));
        map1.put("key1", "value1");
        Assert.assertTrue(map1.containsKey("key1"));
        map1.remove("key1");
        Assert.assertFalse(map1.containsKey("key1"));
        map1.put("key1", "value1");
        Assert.assertTrue(map1.containsKey("key1"));
        map1.clear();
        Assert.assertFalse(map1.containsKey("key1"));

    }

    @Test(expected = NullPointerException.class)
    public void test_containsKey3_WithNull() {
        Assert.assertFalse(map1.containsKey(null));
    }

    @Test
    public void test_putAll() {
        Map<String, String> q = new HashMap<>();
        q.put("1_key", "1_val");
        q.put("2_key", "2_val");
        q.put("3_key", "3_val");
        q.put("4_key", "4_val");
        Assert.assertEquals(map1.size(), 0);
        map1.putAll(q);
        Assert.assertEquals(q.size(), map1.size());
    }

    @Test
    public void test_keySet1() {
        map1.put("1_key", "1_val");
        map1.put("2_key", "2_val");
        map1.put("3_key", "3_val");
        map1.put("4_key", "4_val");
        Set<Map.Entry<String, String>> q = map1.entrySet();
        Assert.assertEquals(q.size(), map1.size());
    }

    @Test
    public void test_keySet2() {
        map1.put("1_key", "1_val");
        map1.put("2_key", "2_val");
        map1.put("3_key", "3_val");
        map1.put("4_key", "4_val");
        Set<Map.Entry<String, String>> q = map1.entrySet();
        q.forEach(o -> Assert.assertTrue(map1.containsKey(o.getKey())));
    }

    @Test
    public void test_entrySet1() {
        map1.put("1_key", "1_val");
        map1.put("2_key", "2_val");
        map1.put("3_key", "3_val");
        map1.put("4_key", "4_val");
        var q = map1.entrySet();
        Assert.assertEquals(map1.size(), q.size());
        for (var val : q) {
            if (!map1.containsKey(val.getKey()))
                Assert.fail();
        }
        map1.remove("2_key");
        Assert.assertNotEquals(0, map1.size() - q.size());

    }

    @Test
    public void test_keySet3() {
        Map<String, String> map1 = new RedisMap();
        map1.put("1_key", "1_val");
        map1.put("2_key", "2_val");
        map1.put("3_key", "3_val");
        map1.put("4_key", "4_val");
        Set<Map.Entry<String, String>> q = map1.entrySet();
        Assert.assertTrue(q.containsAll(map1.entrySet()));
    }

    @Test
    public void test_values1() {
        map1.put("1_key", "1_val");
        map1.put("2_key", "2_val");
        map1.put("3_key", "3_val");
        map1.put("4_key", "4_val");
        Assert.assertTrue(map1.containsValue("4_val"));
        Assert.assertFalse(map1.containsValue("5_val"));
        Assert.assertFalse(map1.containsValue("3_key"));

    }

    @Test
    public void test_ConstainsValue1() {
        map1.put("1_key", "1_val");
        map1.put("2_key", "2_val");
        map1.put("3_key", "3_val");
        map1.put("4_key", "4_val");
        Assert.assertTrue(map1.containsValue("1_val"));
        Assert.assertFalse(map1.containsValue("1_key"));
    }

}

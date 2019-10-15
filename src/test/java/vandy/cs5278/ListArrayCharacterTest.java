/** @author Vanderbilt University, copyright 2019 - All rights reserved 
 * @author Max Coursey, coursem, maxime.coursey@vanderbilt.edu
 */
package vandy.cs5278;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Test the ListArray<Character> class.
 */
public class ListArrayCharacterTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void test_Constructor() {
        ListArray<Character> tmp = new ListArray<>(10);
        assertEquals(10, tmp.size());

        tmp = new ListArray<>(15);
        assertEquals(15, tmp.size());

        tmp = new ListArray<>(0);
        assertEquals(0, tmp.size());

        exception.expect(NegativeArraySizeException.class);
        tmp = new ListArray<>(-1);
        assertEquals(-1, tmp.size());
    }

    @Test
    public void test_DefaultValueConstructor() {
        ListArray<Character> tmp = new ListArray<>(10, 'b');

        for (int i = 0; i < 10; ++i) {
            assertEquals('b', (char) tmp.get(i));
        }
    }

    @Test
    public void test_CopyConstructor() {
        ListArray<Character> tmp = new ListArray<>(10, 'b');
        ListArray<Character> tmp_c = new ListArray<>(tmp);

        assertEquals(tmp.size(), tmp_c.size());
        assertEquals(0, tmp.compareTo(tmp_c));
        assertEquals(0, tmp_c.compareTo(tmp));

        tmp.resize(5);

        tmp_c = new ListArray<>(tmp);

        assertEquals(tmp.size(), tmp_c.size());
        assertEquals(0, tmp.compareTo(tmp_c));
        assertEquals(0, tmp_c.compareTo(tmp));

        tmp_c.resize(11);

        assertEquals('b', (char) tmp_c.get(10));

        tmp_c.resize(2);

        tmp = new ListArray<>(tmp_c);
        assertEquals(tmp.size(), tmp_c.size());
        assertEquals(0, tmp.compareTo(tmp_c));
        assertEquals(0, tmp_c.compareTo(tmp));

        ListArray<Character> tmp2 = new ListArray<>(0, 'b');
        ListArray<Character> tmp3 = new ListArray<>(tmp2);
        assertEquals(0, tmp3.size());
    }

    @Test
    public void test_SetGet() {
        ListArray<Character> tmp = new ListArray<>(1);
        assertNull(tmp.get(0));

        tmp.set(0, 'a');
        assertEquals('a', (char) tmp.get(0));

        tmp.set(0, 'b');
        assertEquals('b', (char) tmp.get(0));
    }

    @Test
    public void test_SetExceptionPositive() {
        ListArray<Character> tmp = new ListArray<>(1);
        exception.expect(ArrayIndexOutOfBoundsException.class);
        tmp.set(1, 'a');
    }

    @Test
    public void test_GetExceptionPositive() {
        ListArray<Character> tmp = new ListArray<>(1);
        exception.expect(ArrayIndexOutOfBoundsException.class);
        tmp.get(1);
    }

    @Test
    public void test_GetExceptionNegative() {
        ListArray<Character> tmp = new ListArray<>(1);
        exception.expect(ArrayIndexOutOfBoundsException.class);
        tmp.get(-1);
    }

    @Test
    public void test_SetExceptionNegative() {
        ListArray<Character> tmp = new ListArray<>(1);
        exception.expect(ArrayIndexOutOfBoundsException.class);
        tmp.set(-1, 'a');
    }

    @Test
    public void test_SetExceptionResized() {
        ListArray<Character> tmp = new ListArray<>(10);
        tmp.resize(1);
        exception.expect(ArrayIndexOutOfBoundsException.class);
        tmp.set(1, 'a');
    }

    @Test
    public void test_GetExceptionResized() {
        ListArray<Character> tmp = new ListArray<>(10);
        tmp.resize(1);
        exception.expect(ArrayIndexOutOfBoundsException.class);
        tmp.get(1);
    }

    @Test
    public void test_Resize() {
        ListArray<Character> tmp = new ListArray<>(0);
        assertEquals(0, tmp.size());

        tmp.resize(0);
        assertEquals(0, tmp.size());

        tmp.resize(1);
        assertEquals(1, tmp.size());
        assertNull(tmp.get(0));
        tmp.set(0, 'a');

        tmp.resize(1);
        assertEquals(1, tmp.size());
        assertEquals('a', (char) tmp.get(0));

        tmp.resize(2);
        assertEquals(2, tmp.size());
        assertEquals('a', (char) tmp.get(0));
        assertNull(tmp.get(1));

        tmp = new ListArray<>(3, 'a');
        assertEquals(3, tmp.size());
        tmp.set(1, 'b');
        tmp.set(2, 'c');
        tmp.resize(0);
        assertEquals(0, tmp.size());
        tmp.resize(1);
        assertEquals(1, tmp.size());
        assertEquals('a', (char) tmp.get(0));
        tmp.resize(2);
        assertEquals(2, tmp.size());
        assertEquals('a', (char) tmp.get(0));
        assertEquals('a', (char) tmp.get(1));
        tmp.resize(3);
        assertEquals(3, tmp.size());
        assertEquals('a', (char) tmp.get(0));
        assertEquals('a', (char) tmp.get(1));
        assertEquals('a', (char) tmp.get(2));
    }

    @Test
    public void test_CompareTo() {
        ListArray<Character> a = new ListArray<>(0, 'a');
        ListArray<Character> b = new ListArray<>(0, 'b');

        assertEquals(0, a.compareTo(b));
        assertEquals(0, a.compareTo(a));
        assertEquals(0, b.compareTo(b));

        a.resize(1);

        assertTrue(a.compareTo(b) != 0);
        assertTrue(b.compareTo(a) != 0);

        b.resize(1);

        assertTrue(a.compareTo(b) != 0);
        assertTrue(b.compareTo(a) != 0);
        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);

        b.resize(2);

        assertTrue(a.compareTo(b) != 0);
        assertTrue(b.compareTo(a) != 0);
        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);

        ListArray<Character> bba = new ListArray<>(b);
        assertEquals(0, b.compareTo(bba));

        bba.resize(3);
        bba.set(2, 'a');

        assertTrue(b.compareTo(bba) < 0);
        assertTrue(bba.compareTo(b) > 0);

        b.resize(10);
        b.resize(3);
        b.set(2, 'a');

        assertEquals(0, b.compareTo(bba));
        assertEquals(0, bba.compareTo(b));

        a = new ListArray<>(1, 'a');
        b = new ListArray<>(2, 'a');

        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);

        b = new ListArray<>(2, 'a');
        b.set(1, 'b');

        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);

        a.resize(3);
        a.set(1, 'b');
        a.set(2, 'c');


        b = new ListArray<>(a);

        b.resize(4);
        b.set(3, 'd');

        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);

        a.set(1, 'z');

        assertTrue(a.compareTo(b) > 0);
        assertTrue(b.compareTo(a) < 0);
    }

    @Test
    public void test_Iterator() {
        ListArray<Character> a = new ListArray<>(0, 'a');

        Iterator<Character> it = a.iterator();

        assertFalse(it.hasNext());

        a.resize(1);

        it = a.iterator();
        assertTrue(it.hasNext());
        assertEquals('a', (char) it.next());
        assertFalse(it.hasNext());

        a.resize(2);

        it = a.iterator();
        assertTrue(it.hasNext());
        assertEquals('a', (char) it.next());
        assertTrue(it.hasNext());
        assertEquals('a', (char) it.next());
        assertFalse(it.hasNext());

        a.set(1, 'b');

        it = a.iterator();
        assertTrue(it.hasNext());
        assertEquals('a', (char) it.next());
        assertTrue(it.hasNext());
        assertEquals('b', (char) it.next());
        assertFalse(it.hasNext());

        exception.expect(NoSuchElementException.class);
        it.next();
    }

    @Test
    public void test_IteratorRemove() {
        ListArray<Character> a = new ListArray<>(2, 'a');
        a.set(1, 'b');

        Iterator<Character> it = a.iterator();

        char c = it.next();
        assertEquals('a', c);

        it.remove();

        assertEquals(1, a.size());
        assertEquals('b', (char) a.get(0));

        c = it.next();

        assertEquals('a', c);

        it.remove();

        assertEquals(0, a.size());

        a.resize(2);

        it = a.iterator();
        c = it.next();
        it.remove();

        ListArray<Character> d = new ListArray<>(5, 'a');
        d.set(1, 'b');
        d.set(2, 'c');
        d.set(3, 'd');
        d.set(4, 'e');
        it = d.iterator();
        it.next();
        it.next();
        it.next();
        it.remove();
        assertEquals(4, d.size());
        assertEquals('e', (char) d.get(3));

        exception.expect(IllegalStateException.class);
        it.remove();
    }

    @Test
    public void test_IteratorRemoveEmpty() {
        ListArray<Character> a = new ListArray<>(2, 'a');
        Iterator<Character> it = a.iterator();
        exception.expect(IllegalStateException.class);
        it.remove();
    }
    
    @Test
    public void test_remove1() {
    	ListArray<Character> x = new ListArray<>(2, 'x');
    	Iterator<Character> itX = x.iterator();
    	itX.next();
    	itX.remove();
    	assertEquals(1, x.size());
    }
    @Test
    public void test_remove2() {
    	ListArray<Character> y = new ListArray<>(1, 'y');
    	Iterator<Character> itY = y.iterator();
    	itY.next();
    	itY.remove();
    	assertEquals(0, y.size());
    }
    @Test
    public void test_remove3() {
    	ListArray<Character> z = new ListArray<>(1, 'z');
    	Iterator<Character> itZ = z.iterator();
    	exception.expect(IllegalStateException.class);
    	itZ.remove();
    }
    @Test
    public void test_remove4() {
    	ListArray<Character> l = new ListArray<>(2);
    	exception.expect(ArrayIndexOutOfBoundsException.class);
    	l.remove(2);
    }
    
    @Test
    public void test_remove5() {
    	ListArray<Character> m = new ListArray<>(2);
    	m.remove(1);
    	assertEquals(1, m.size());
    }

}

package com.dai.message;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    
    
    @Test
    public  void  test(){
        List<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("");
        list.add("test5");
        list.add("test6");
       
        for (String string : list){
            System.out.println("string = " + string);
        }

        System.out.println("list.contains(null) = " + list.contains(null));
    }
}
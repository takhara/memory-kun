package com.example.memorykun;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MathmaticsTest {
/*
 * 動くか否か
 * ０で０が変えるか
 */
    @Test
    public void ok(){
        //System.out.println(Mathmatics.sqrt(0));
        assertThat(Mathmatics.sqrt(9), is(3d));
        
    }
    @Test
    public void zero(){
        assertThat(Mathmatics.sqrt(0), is(0d));
    }

}

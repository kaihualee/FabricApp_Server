/**
 * @(#)MD5UtilTest.java, 2013-7-10. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.utils;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

/**
 * @author nisonghai
 */
public class MD5UtilTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {}

    @Test
    public void testMd5String() {
        String source = "123456";
        String des = MD5Util.md5(source);
        System.out.println(des + " size=" + des.length());
        String tager = "e10adc3949ba59abbe56e057f20f883e";
        System.out.println(tager + " size=" + tager.length());
        File f = new File("E:\\md5.txt");
        des = MD5Util.md5(f);
        System.out.println(des + " size=" + des.length());
        
    }

}

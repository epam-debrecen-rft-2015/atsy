package com.epam.rft.atsy.web.encryption;

import com.epam.rft.atsy.web.encryption.impl.Md5EncryptionUtilImpl;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created by tothd on 2015. 10. 26..
 */
public class EncryptionUtilTest {


    @Test
    public void getStringShouldReturnEncrypted() {

        //given
        String password = "pass1";
        EncryptionUtil encryptionUtil = new Md5EncryptionUtilImpl();

        //when
        String myPassword = encryptionUtil.hash(password);
        String result = encryptionUtil.hash(password);

        //then
        assertEquals(myPassword, result);


    }

    @Test
    public void getStringIsNullShouldReturnNull() {

        //given
        String password = null;

        EncryptionUtil encryptionUtil = new Md5EncryptionUtilImpl();

        //when
        String result = encryptionUtil.hash(password);

        //then
        assertNull(result);


    }

}

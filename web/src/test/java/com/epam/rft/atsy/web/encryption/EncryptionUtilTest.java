package com.epam.rft.atsy.web.encryption;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

import com.epam.rft.atsy.web.encryption.impl.EncryptionUtilMd5HashImpl;

/**
 * Created by tothd on 2015. 10. 26..
 */
public class EncryptionUtilTest {


    @Test
    public void getStringShouldReturnEncrypted() {

        //given
        String password = "pass1";
        EncryptionUtil encryptionUtil = new EncryptionUtilMd5HashImpl();

        //when
        String myPassword = encryptionUtil.passwordHash(password);
        String result = encryptionUtil.passwordHash(password);

        //then
        assertEquals(myPassword, result);


    }

    @Test
    public void getStringIsNullShouldReturnNull() {

        //given
        String password = null;

        EncryptionUtil encryptionUtil = new EncryptionUtilMd5HashImpl();

        //when
        String result = encryptionUtil.passwordHash(password);

        //then
        assertNull(result);


    }

}

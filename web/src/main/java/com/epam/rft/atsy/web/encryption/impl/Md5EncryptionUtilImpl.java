package com.epam.rft.atsy.web.encryption.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.epam.rft.atsy.web.encryption.EncryptionUtil;

/**
 * Created by tothd on 2015. 10. 26..
 */
@Component
public class Md5EncryptionUtilImpl implements EncryptionUtil{

    private final static String SALT ="DGE$5SGr@3VsHYUMas2323E4d57vfBfFSTRU@!DSH(*%FDSdfg13sgfsg";
    private final static Logger LOGGER= LoggerFactory.getLogger(Md5EncryptionUtilImpl.class);


    @Override
    public String hash(String input) {
        String md5 = "";
        if(null == input)
            return null;

        input = input + SALT;//adding a SALT to the string before it gets hashed.
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");//Create MessageDigest object for MD5
            digest.update(input.getBytes(), 0, input.length());//Update input string in message digest
            md5 = new BigInteger(1, digest.digest()).toString(16);//Converts message digest value in base 16 (hex)

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Unable to use password",e);
        }
        return md5;
    }
}

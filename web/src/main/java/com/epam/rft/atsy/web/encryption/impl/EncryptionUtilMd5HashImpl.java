package com.epam.rft.atsy.web.encryption.impl;

import com.epam.rft.atsy.web.encryption.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tothd on 2015. 10. 26..
 */
public class EncryptionUtilMd5HashImpl implements EncryptionUtil{

    private final static String SALT ="DGE$5SGr@3VsHYUMas2323E4d57vfBfFSTRU@!DSH(*%FDSdfg13sgfsg";
    private final static Logger LOGGER= LoggerFactory.getLogger(EncryptionUtilMd5HashImpl.class);


    @Override
    public String passwordHash(String password) {
        String md5 = "";
        if(null == password)
            return null;

        password = password+ SALT;//adding a SALT to the string before it gets hashed.
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");//Create MessageDigest object for MD5
            digest.update(password.getBytes(), 0, password.length());//Update input string in message digest
            md5 = new BigInteger(1, digest.digest()).toString(16);//Converts message digest value in base 16 (hex)

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Unable to use password",e);
        }
        return md5;
    }
}

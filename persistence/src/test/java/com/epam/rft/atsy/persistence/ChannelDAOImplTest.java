package com.epam.rft.atsy.persistence;

import com.epam.rft.atsy.persistence.dao.ChannelDAO;
import com.epam.rft.atsy.persistence.dao.impl.ChannelDAOImpl;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by mates on 2015. 11. 26..
 */
public class ChannelDAOImplTest {
    @InjectMocks
    ChannelDAOImpl channelDAO;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void emptyTest(){

    }
}
package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.hibernate.exception.ConstraintViolationException;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link ChannelServiceImpl}.
 */
/*public class ChannelServiceImplTest {
    @InjectMocks
    private ChannelServiceImpl underTest;
    @Mock
    private ChannelDAO dao;
    @Mock
    private ModelMapper modelMapper;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnChannels() {
        //given
        given(dao.loadAll()).willReturn(Arrays.asList(new ChannelEntity(12l, "test")));
        given(modelMapper.map(Matchers.any(Collection.class), Matchers.any(Type.class)))
                .willReturn(Arrays.asList(new ChannelDTO(12l, "test")));
        //when
        Collection<ChannelDTO> result = underTest.getAllChannels();
        //then
        assertThat(result, containsInAnyOrder(new ChannelDTO(12l, "test")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNullGiven() {
        //when
        underTest.saveOrUpdate(null);
    }

    @Test(expectedExceptions = DuplicateRecordException.class)
    public void shouldThrowExceptionDuplicateNameGiven() {
        //given
        given(modelMapper.map(Matchers.any(ChannelDTO.class), Matchers.any(Class.class)))
                .willReturn(new ChannelEntity());
        given(dao.create(Matchers.any(ChannelEntity.class)))
                .willThrow(ConstraintViolationException.class);
        //when
        underTest.saveOrUpdate(new ChannelDTO());
    }

    @Test
    public void shouldCallPersistWithNoIdGiven() {
        //given
        given(modelMapper.map(Matchers.any(ChannelDTO.class), Matchers.any(Class.class)))
                .willReturn(new ChannelEntity());
        //when
        underTest.saveOrUpdate(new ChannelDTO());
        //then
        verify(dao, atLeastOnce()).create(Matchers.any(ChannelEntity.class));
    }

    @Test
    public void shouldCallUpdateWithIdGiven() {
        //given
        given(modelMapper.map(Matchers.any(ChannelDTO.class), Matchers.any(Class.class)))
                .willReturn(new ChannelEntity(1l, "test"));
        //when
        underTest.saveOrUpdate(new ChannelDTO(1l, "test"));
        //then
        verify(dao, atLeastOnce()).update(Matchers.any(ChannelEntity.class));
    }
}*/

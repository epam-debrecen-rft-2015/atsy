package com.epam.rft.atsy.service.impl;

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

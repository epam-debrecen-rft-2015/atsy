package com.epam.rft.atsy.service.impl;

/**
 * Unit tests for {@link PositionServiceImpl}.
 */
/*public class PositionServiceImplTest {
    @InjectMocks
    private PositionServiceImpl underTest;
    @Mock
    private PositionDAO dao;
    @Mock
    private ModelMapper modelMapper;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnPositions() {
        //given
        given(dao.loadAll()).willReturn(Arrays.asList(new PositionEntity(12l, "test")));
        given(modelMapper.map(Matchers.any(Collection.class), Matchers.any(Type.class)))
                .willReturn(Arrays.asList(new PositionDTO(12l, "test")));
        //when
        Collection<PositionDTO> result = underTest.getAllPositions();
        //then
        assertThat(result, containsInAnyOrder(new PositionDTO(12l, "test")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNullGiven() {
        //when
        underTest.saveOrUpdate(null);
    }

    @Test(expectedExceptions = DuplicateRecordException.class)
    public void shouldThrowExceptionDuplicateNameGiven() {
        //given
        given(modelMapper.map(Matchers.any(PositionDTO.class), Matchers.any(Class.class)))
                .willReturn(new PositionEntity());
        given(dao.create(Matchers.any(PositionEntity.class)))
                .willThrow(ConstraintViolationException.class);
        //when
        underTest.saveOrUpdate(new PositionDTO());
    }

    @Test
    public void shouldCallPersistWithNoIdGiven() {
        //given
        given(modelMapper.map(Matchers.any(PositionDTO.class), Matchers.any(Class.class)))
                .willReturn(new PositionEntity());
        //when
        underTest.saveOrUpdate(new PositionDTO());
        //then
        verify(dao, atLeastOnce()).create(Matchers.any(PositionEntity.class));
    }

    @Test
    public void shouldCallUpdateWithIdGiven() {
        //given
        given(modelMapper.map(Matchers.any(PositionDTO.class), Matchers.any(Class.class)))
                .willReturn(new PositionEntity(1l, "test"));
        //when
        underTest.saveOrUpdate(new PositionDTO(1l, "test"));
        //then
        verify(dao, atLeastOnce()).update(Matchers.any(PositionEntity.class));
    }
}*/

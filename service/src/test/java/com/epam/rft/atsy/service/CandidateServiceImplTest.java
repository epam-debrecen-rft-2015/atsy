package com.epam.rft.atsy.service;

/*public class CandidateServiceImplTest {

    @InjectMocks
    CandidateServiceImpl candidateService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    CandidateDAO candidateDAO;

    @Mock
    CandidateDTO candidateDTO;

    @Mock
    CandidateEntity candidateEntity;

    @Mock
    CandidateEntity candidateEntity2;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnCandidates() {

        //given
        FilterRequest sortingRequest = new FilterRequest();
        given(candidateDAO.loadAll(sortingRequest)).willReturn(Arrays.asList(new CandidateEntity("name", "email", "phome", "description", "referer", new Short("1"))));
        given(modelMapper.map(Matchers.any(Collection.class), Matchers.any(Type.class)))
                .willReturn(Arrays.asList(new CandidateDTO("name", "email", "phome", "description", "referer", new Short("1"))));

        //when
        Collection<CandidateDTO> result = candidateService.getAllCandidate(sortingRequest);

        //then
        assertThat(result, containsInAnyOrder(new CandidateDTO("name", "email", "phome", "description", "referer", new Short("1"))));

    }

    @Test
    public void updateTest(){
        Long id=new Long(1);
        given(candidateDTO.getId()).willReturn(id);
        given(candidateDTO.getName()).willReturn("Candidate A");
        given(candidateDTO.getEmail()).willReturn("candidate.a@atsy.com");
        given(candidateDTO.getPhone()).willReturn("+36105555555");
        given(candidateDTO.getReferer()).willReturn("google");
        given(candidateDTO.getDescription()).willReturn("Elegáns, kicsit furi");
        given(candidateDTO.getLanguageSkill()).willReturn((short) 5);
        given(modelMapper.map(candidateDTO,CandidateEntity.class)).willReturn(candidateEntity);
        given(candidateEntity.getId()).willReturn(id);
        given(candidateDAO.update(candidateEntity)).willReturn(candidateEntity);
        given(candidateDAO.update(candidateEntity).getId()).willReturn(id);

        Long candidateId=candidateService.saveOrUpdate(candidateDTO);

        assertThat(candidateId, is(id));

    }

    @Test
    public void saveTest(){
        Long id=new Long(1);
        given(candidateDTO.getId()).willReturn(null);
        given(candidateDTO.getName()).willReturn("test");
        given(candidateDTO.getEmail()).willReturn("test@test.com");
        given(candidateDTO.getPhone()).willReturn("+36105555557");
        given(candidateDTO.getReferer()).willReturn("fb");
        given(candidateDTO.getDescription()).willReturn("test");
        given(candidateDTO.getLanguageSkill()).willReturn((short) 5);
        given(candidateEntity2.getId()).willReturn(id);
        given(candidateEntity2.getName()).willReturn("test");
        given(candidateEntity2.getEmail()).willReturn("test@test.com");
        given(candidateEntity2.getPhone()).willReturn("+36105555557");
        given(candidateEntity2.getReferer()).willReturn("fb");
        given(candidateEntity2.getDescription()).willReturn("test");
        given(candidateEntity2.getLanguageSkill()).willReturn((short) 5);
        given(modelMapper.map(candidateDTO,CandidateEntity.class)).willReturn(candidateEntity);
        given(candidateDAO.create(candidateEntity)).willReturn(candidateEntity2);
        given(candidateEntity.getId()).willReturn(null);

        given(candidateDAO.create(candidateEntity).getId()).willReturn(id);

        Long candidateId=candidateService.saveOrUpdate(candidateDTO);

        assertThat(candidateId, is(id));

    }

    @Test
    public void getCandidateTest(){
        //given
        Long id=new Long(1);
        given(candidateDTO.getId()).willReturn(id);
        given(candidateDTO.getName()).willReturn("Candidate A");
        given(candidateDTO.getEmail()).willReturn("candidate.a@atsy.com");
        given(candidateDTO.getPhone()).willReturn("+36105555555");
        given(candidateDTO.getReferer()).willReturn("google");
        given(candidateDTO.getDescription()).willReturn("Elegáns, kicsit furi");
        given(candidateDTO.getLanguageSkill()).willReturn((short) 5);
        given(candidateDAO.read(id)).willReturn(candidateEntity);
        given(modelMapper.map(candidateEntity,CandidateDTO.class)).willReturn(candidateDTO);

        CandidateDTO dto=candidateService.getCandidate(id);

        assertThat(dto.getId(),is(id));
    }

    @Test(expectedExceptions = DuplicateRecordException.class)
    public void updateTestExceptions() throws  DataIntegrityViolationException{
        Long id=new Long(1);
        given(candidateDTO.getId()).willReturn(id);
        given(candidateDTO.getName()).willReturn("Candidate A");
        given(candidateDTO.getEmail()).willReturn("candidate.a@atsy.com");
        given(candidateDTO.getPhone()).willReturn("+36105555555");
        given(candidateDTO.getReferer()).willReturn("google");
        given(candidateDTO.getDescription()).willReturn("Elegáns, kicsit furi");
        given(candidateDTO.getLanguageSkill()).willReturn((short) 5);
        given(modelMapper.map(candidateDTO,CandidateEntity.class)).willReturn(candidateEntity);
        given(candidateEntity.getId()).willReturn(id);
        given(candidateEntity.getName()).willReturn("Candidate A");
        given(candidateDTO.getName()).willReturn("Candidate A");
        given(candidateDAO.update(candidateEntity)).willThrow(new DataIntegrityViolationException("Candidate A") );
        //given(candidateDAO.update(candidateEntity).getId()).willReturn(id);

        given(candidateService.saveOrUpdate(candidateDTO)).willThrow(new DataIntegrityViolationException("Candidate A"));

        //assertThat(candidateId, is(01));

    }

}*/

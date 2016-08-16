package com.epam.rft.atsy.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;

import com.epam.rft.atsy.service.converter.ConverterAdapter;
import com.epam.rft.atsy.service.converter.CustomConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.Mapping;
import org.modelmapper.spi.PropertyInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ModelMapperConverterServiceImplTest {

  private static final Long TEST_CLASS_FIRST_ID = 1L;
  private static final Long TEST_CLASS_SECOND_ID = 2L;

  @Mock
  private ModelMapper modelMapper;

  private ModelMapperConverterServiceImpl modelMapperConverterService;

  @Before
  public void setUp() {
    given(modelMapper.createTypeMap(any(), any())).willReturn(newTypeMap());

    this.modelMapperConverterService = new ModelMapperConverterServiceImpl(modelMapper);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setupCustomConvertersShouldThrowIllegalArgumentExceptionWhenConverterAdapterListIsNull() {
    //Given

    //When
    modelMapperConverterService.setupCustomConverters(null);

    //Then
  }

  @Test
  public void setupCustomConvertersShouldAddCustomConvertersToModelMapperWhenGivenConverterAdapterListHasElements() {
    //Given
    List<ConverterAdapter> twoElementConverterAdapterList = Arrays.asList(
        new ConverterAdapter(TestClass1.class, TestClass2.class,
            new CustomConverter<TestClass1, TestClass2>() {
              @Override
              public TestClass2 convert(TestClass1 source) {
                return new TestClass2(source.getId());
              }
            }),
        new ConverterAdapter(TestClass2.class, TestClass1.class,
            new CustomConverter<TestClass2, TestClass1>() {
              @Override
              public TestClass1 convert(TestClass2 source) {
                return new TestClass1(source.getId());
              }
            })
    );

    //When
    modelMapperConverterService.setupCustomConverters(twoElementConverterAdapterList);

    //Then
    then(modelMapper).should().createTypeMap(TestClass1.class, TestClass2.class);
    then(modelMapper).should().createTypeMap(TestClass2.class, TestClass1.class);
  }

  @Test
  public void setupCustomConvertersShouldNotAddCustomConvertersToModelMapperWhenGivenConverterAdapterListIsEmpty() {
    //Given
    List<ConverterAdapter> emptyConverterAdapterList = Collections.emptyList();

    //When
    modelMapperConverterService.setupCustomConverters(emptyConverterAdapterList);

    //Then
    then(modelMapper).should(never()).createTypeMap(any(), any());
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertSingleElementShouldThrowIllegalArgumentExceptionWhenSourceParameterIsNull() {
    //Given

    //When
    modelMapperConverterService.convert(null, TestClass2.class);

    //Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertSingleElementShouldThrowIllegalArgumentExceptionWhenTargetTypeParameterIsNull() {
    //Given
    TestClass1 source = new TestClass1(TEST_CLASS_FIRST_ID);

    //When
    modelMapperConverterService.convert(source, null);

    //Then
  }

  @Test
  public void convertSingleElementShouldReturnConvertedObjectWhenProperParametersWereGiven() {
    //Given
    TestClass1 source = new TestClass1(TEST_CLASS_FIRST_ID);
    TestClass2 expected = new TestClass2(TEST_CLASS_FIRST_ID);

    given(modelMapper.map(source, TestClass2.class)).willReturn(expected);

    //When
    TestClass2 result = modelMapperConverterService.convert(source, TestClass2.class);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, is(expected));
    then(modelMapper).should().map(source, TestClass2.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertListShouldThrowIllegalArgumentExceptionWhenSourceListParameterIsNull() {
    //Given

    //When
    modelMapperConverterService.convert(null, TestClass2.class);

    //Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertListShouldThrowIllegalArgumentExceptionWhenTargetTypeParameterIsNull() {
    //Given
    List<TestClass1> sourceList = Collections.singletonList(new TestClass1(TEST_CLASS_FIRST_ID));

    //When
    modelMapperConverterService.convert(sourceList, null);

    //Then
  }

  @Test
  public void convertListShouldReturnEmptyListWhenEmptyListIsGivenAsSourceList() {
    //Given
    List<TestClass1> emptySourceList = Collections.emptyList();

    //When
    List<TestClass2>
        result =
        modelMapperConverterService.convert(emptySourceList, TestClass2.class);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, empty());
  }

  @Test
  public void convertListShouldReturnConvertedListWhenProperParametersWereGiven() {
    //Given
    List<TestClass1> sourceList = Arrays.asList(
        new TestClass1(TEST_CLASS_FIRST_ID),
        new TestClass1(TEST_CLASS_SECOND_ID)
    );
    List<TestClass2> expected = Arrays.asList(
        new TestClass2(TEST_CLASS_FIRST_ID),
        new TestClass2(TEST_CLASS_SECOND_ID)
    );

    for (int i = 0; i < 2; i++) {
      given(modelMapper.map(sourceList.get(i), TestClass2.class))
          .willReturn(expected.get(i));
    }

    //When
    List<TestClass2>
        result =
        modelMapperConverterService.convert(sourceList, TestClass2.class);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, not(empty()));
    assertThat(result, is(expected));

    for (int i = 0; i < 2; i++) {
      then(modelMapper).should().map(sourceList.get(i), TestClass2.class);
    }

  }

  private class TestClass1 {

    private TestClass1(Long id) {
      this.id = id;
    }

    private Long id;

    private Long getId() {
      return id;
    }

  }

  private class TestClass2 {

    private TestClass2(Long id) {
      this.id = id;
    }

    private Long id;

    private Long getId() {
      return id;
    }

  }

  public TypeMap newTypeMap() {
    return new TypeMap() {
      @Override
      public void addMappings(PropertyMap propertyMap) {

      }

      @Override
      public Condition<?, ?> getCondition() {
        return null;
      }

      @Override
      public Converter getConverter() {
        return null;
      }

      @Override
      public Class getDestinationType() {
        return null;
      }

      @Override
      public List<Mapping> getMappings() {
        return null;
      }

      @Override
      public String getName() {
        return null;
      }

      @Override
      public Converter getPostConverter() {
        return null;
      }

      @Override
      public Converter getPreConverter() {
        return null;
      }

      @Override
      public Condition<?, ?> getPropertyCondition() {
        return null;
      }

      @Override
      public Converter<?, ?> getPropertyConverter() {
        return null;
      }

      @Override
      public Provider<?> getPropertyProvider() {
        return null;
      }

      @Override
      public Provider getProvider() {
        return null;
      }

      @Override
      public Class getSourceType() {
        return null;
      }

      @Override
      public List<PropertyInfo> getUnmappedProperties() {
        return null;
      }

      @Override
      public Object map(Object source) {
        return null;
      }

      @Override
      public void map(Object source, Object destination) {

      }

      @Override
      public TypeMap setCondition(Condition condition) {
        return null;
      }

      @Override
      public TypeMap setConverter(Converter converter) {
        return null;
      }

      @Override
      public TypeMap setPostConverter(Converter converter) {
        return null;
      }

      @Override
      public TypeMap setPreConverter(Converter converter) {
        return null;
      }

      @Override
      public TypeMap setPropertyCondition(Condition condition) {
        return null;
      }

      @Override
      public TypeMap setPropertyConverter(Converter converter) {
        return null;
      }

      @Override
      public TypeMap setPropertyProvider(Provider provider) {
        return null;
      }

      @Override
      public TypeMap setProvider(Provider provider) {
        return null;
      }

      @Override
      public void validate() {

      }
    };
  }

}

package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {PetTypeFormatter.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PetTypeFormatterDiffblueTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @MockBean
  private PetRepository petRepository;

  @Autowired
  private PetTypeFormatter petTypeFormatter;

  /**
   * Test {@link PetTypeFormatter#parse(String, Locale)}.
   * <ul>
   *   <li>Given {@link PetType} (default constructor) Id is one.</li>
   *   <li>Then return {@link PetType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link PetTypeFormatter#parse(String, Locale)}
   */
  @Test
  public void testParse_givenPetTypeIdIsOne_thenReturnPetType() throws ParseException {
    // Arrange
    PetType petType = new PetType();
    petType.setId(1);
    petType.setName("Dog");

    ArrayList<PetType> petTypeList = new ArrayList<>();
    petTypeList.add(petType);
    when(petRepository.findPetTypes()).thenReturn(petTypeList);

    // Act
    PetType actualParseResult = petTypeFormatter.parse("Dog", Locale.getDefault());

    // Assert
    verify(petRepository).findPetTypes();
    assertSame(petType, actualParseResult);
  }

  /**
   * Test {@link PetTypeFormatter#parse(String, Locale)}.
   * <ul>
   *   <li>Given {@link PetType} (default constructor) Id is two.</li>
   *   <li>Then return {@link PetType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link PetTypeFormatter#parse(String, Locale)}
   */
  @Test
  public void testParse_givenPetTypeIdIsTwo_thenReturnPetType() throws ParseException {
    // Arrange
    PetType petType = new PetType();
    petType.setId(1);
    petType.setName("Dog");

    PetType petType2 = new PetType();
    petType2.setId(2);
    petType2.setName("Bella");

    ArrayList<PetType> petTypeList = new ArrayList<>();
    petTypeList.add(petType2);
    petTypeList.add(petType);
    when(petRepository.findPetTypes()).thenReturn(petTypeList);

    // Act
    PetType actualParseResult = petTypeFormatter.parse("Dog", Locale.getDefault());

    // Assert
    verify(petRepository).findPetTypes();
    assertSame(petType, actualParseResult);
  }

  /**
   * Test {@link PetTypeFormatter#parse(String, Locale)}.
   * <ul>
   *   <li>Then throw {@link ParseException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link PetTypeFormatter#parse(String, Locale)}
   */
  @Test
  public void testParse_thenThrowParseException() throws ParseException {
    // Arrange
    when(petRepository.findPetTypes()).thenReturn(new ArrayList<>());

    // Act and Assert
    thrown.expect(ParseException.class);
    petTypeFormatter.parse("Dog", Locale.getDefault());
    verify(petRepository).findPetTypes();
  }
}

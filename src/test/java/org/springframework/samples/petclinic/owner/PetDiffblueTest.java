package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.springframework.samples.petclinic.visit.Visit;

public class PetDiffblueTest {
  /**
   * Test {@link Pet#getVisitsInternal()}.
   * <ul>
   *   <li>Given {@link Owner} (default constructor) Address is
   * {@code 42 Main St}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pet#getVisitsInternal()}
   */
  @Test
  public void testGetVisitsInternal_givenOwnerAddressIs42MainSt() {
    // Arrange
    Owner owner = new Owner();
    owner.setAddress("42 Main St");
    owner.setCity("Oxford");
    owner.setFirstName("Jane");
    owner.setId(1);
    owner.setLastName("Doe");
    owner.setPetsInternal(new HashSet<>());
    owner.setTelephone("6625550144");

    PetType type = new PetType();
    type.setId(1);
    type.setName("Dog");

    Pet pet = new Pet();
    pet.setBirthDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    pet.setId(1);
    pet.setName("Bella");
    pet.setOwner(owner);
    pet.setType(type);
    pet.setVisitsInternal(null);

    // Act and Assert
    assertTrue(pet.getVisitsInternal().isEmpty());
  }

  /**
   * Test {@link Pet#getVisitsInternal()}.
   * <ul>
   *   <li>Given {@link Pet} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Pet#getVisitsInternal()}
   */
  @Test
  public void testGetVisitsInternal_givenPet() {
    // Arrange, Act and Assert
    assertTrue((new Pet()).getVisitsInternal().isEmpty());
  }

  /**
   * Test {@link Pet#getVisits()}.
   * <ul>
   *   <li>Given {@link Owner} (default constructor) Address is
   * {@code 42 Main St}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pet#getVisits()}
   */
  @Test
  public void testGetVisits_givenOwnerAddressIs42MainSt() {
    // Arrange
    Owner owner = new Owner();
    owner.setAddress("42 Main St");
    owner.setCity("Oxford");
    owner.setFirstName("Jane");
    owner.setId(1);
    owner.setLastName("Doe");
    owner.setPetsInternal(new HashSet<>());
    owner.setTelephone("6625550144");

    PetType type = new PetType();
    type.setId(1);
    type.setName("Dog");

    Pet pet = new Pet();
    pet.setBirthDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    pet.setId(1);
    pet.setName("Bella");
    pet.setOwner(owner);
    pet.setType(type);
    pet.setVisitsInternal(null);

    // Act and Assert
    assertTrue(pet.getVisits().isEmpty());
  }

  /**
   * Test {@link Pet#getVisits()}.
   * <ul>
   *   <li>Given {@link Pet} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Pet#getVisits()}
   */
  @Test
  public void testGetVisits_givenPet() {
    // Arrange, Act and Assert
    assertTrue((new Pet()).getVisits().isEmpty());
  }

  /**
   * Test {@link Pet#addVisit(Visit)}.
   * <ul>
   *   <li>Given {@link Owner} (default constructor) Address is
   * {@code 42 Main St}.</li>
   *   <li>Then {@link Visit} (default constructor) PetId intValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pet#addVisit(Visit)}
   */
  @Test
  public void testAddVisit_givenOwnerAddressIs42MainSt_thenVisitPetIdIntValueIsOne() {
    // Arrange
    Owner owner = new Owner();
    owner.setAddress("42 Main St");
    owner.setCity("Oxford");
    owner.setFirstName("Jane");
    owner.setId(1);
    owner.setLastName("Doe");
    owner.setPetsInternal(new HashSet<>());
    owner.setTelephone("6625550144");

    PetType type = new PetType();
    type.setId(1);
    type.setName("Dog");

    Pet pet = new Pet();
    pet.setBirthDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    pet.setId(1);
    pet.setName("Bella");
    pet.setOwner(owner);
    pet.setType(type);
    pet.setVisitsInternal(null);

    Visit visit = new Visit();
    visit.setDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    visit.setDescription("The characteristics of someone or something");
    visit.setId(1);
    visit.setPetId(1);

    // Act
    pet.addVisit(visit);

    // Assert
    assertEquals(1, visit.getPetId().intValue());
    List<Visit> visits = pet.getVisits();
    assertEquals(1, visits.size());
    assertEquals(1, pet.getVisitsInternal().size());
    assertSame(visit, visits.get(0));
  }

  /**
   * Test {@link Pet#addVisit(Visit)}.
   * <ul>
   *   <li>Given {@link Pet} (default constructor).</li>
   *   <li>Then {@link Visit} (default constructor) PetId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pet#addVisit(Visit)}
   */
  @Test
  public void testAddVisit_givenPet_thenVisitPetIdIsNull() {
    // Arrange
    Pet pet = new Pet();

    Visit visit = new Visit();
    visit.setDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    visit.setDescription("The characteristics of someone or something");
    visit.setId(1);
    visit.setPetId(1);

    // Act
    pet.addVisit(visit);

    // Assert
    assertNull(visit.getPetId());
    List<Visit> visits = pet.getVisits();
    assertEquals(1, visits.size());
    assertEquals(1, pet.getVisitsInternal().size());
    assertSame(visit, visits.get(0));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Pet}
   *   <li>{@link Pet#setBirthDate(Date)}
   *   <li>{@link Pet#setOwner(Owner)}
   *   <li>{@link Pet#setType(PetType)}
   *   <li>{@link Pet#setVisitsInternal(Set)}
   *   <li>{@link Pet#getBirthDate()}
   *   <li>{@link Pet#getOwner()}
   *   <li>{@link Pet#getType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Pet actualPet = new Pet();
    Date birthDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualPet.setBirthDate(birthDate);
    Owner owner = new Owner();
    owner.setAddress("42 Main St");
    owner.setCity("Oxford");
    owner.setFirstName("Jane");
    owner.setId(1);
    owner.setLastName("Doe");
    owner.setPetsInternal(new HashSet<>());
    owner.setTelephone("6625550144");
    actualPet.setOwner(owner);
    PetType type = new PetType();
    type.setId(1);
    type.setName("Dog");
    actualPet.setType(type);
    HashSet<Visit> visits = new HashSet<>();
    actualPet.setVisitsInternal(visits);
    Date actualBirthDate = actualPet.getBirthDate();
    Owner actualOwner = actualPet.getOwner();
    PetType actualType = actualPet.getType();

    // Assert
    assertNull(actualPet.getId());
    assertNull(actualPet.getName());
    Set<Visit> visitsInternal = actualPet.getVisitsInternal();
    assertTrue(visitsInternal.isEmpty());
    assertSame(visits, visitsInternal);
    assertSame(owner, actualOwner);
    assertSame(type, actualType);
    assertSame(birthDate, actualBirthDate);
  }
}

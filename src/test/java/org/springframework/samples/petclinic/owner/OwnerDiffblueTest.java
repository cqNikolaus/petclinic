package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class OwnerDiffblueTest {
  /**
   * Test {@link Owner#getPetsInternal()}.
   * <ul>
   *   <li>Given {@link Owner} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#getPetsInternal()}
   */
  @Test
  public void testGetPetsInternal_givenOwner() {
    // Arrange, Act and Assert
    assertTrue((new Owner()).getPetsInternal().isEmpty());
  }

  /**
   * Test {@link Owner#getPetsInternal()}.
   * <ul>
   *   <li>Given {@link Owner} (default constructor) Address is
   * {@code 42 Main St}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#getPetsInternal()}
   */
  @Test
  public void testGetPetsInternal_givenOwnerAddressIs42MainSt() {
    // Arrange
    Owner owner = new Owner();
    owner.setAddress("42 Main St");
    owner.setCity("Oxford");
    owner.setFirstName("Jane");
    owner.setId(1);
    owner.setLastName("Doe");
    owner.setTelephone("6625550144");
    owner.setPetsInternal(new HashSet<>());

    // Act and Assert
    assertTrue(owner.getPetsInternal().isEmpty());
  }

  /**
   * Test {@link Owner#getPets()}.
   * <ul>
   *   <li>Given {@link Owner} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#getPets()}
   */
  @Test
  public void testGetPets_givenOwner() {
    // Arrange, Act and Assert
    assertTrue((new Owner()).getPets().isEmpty());
  }

  /**
   * Test {@link Owner#getPets()}.
   * <ul>
   *   <li>Given {@link Owner} (default constructor) Address is
   * {@code 42 Main St}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#getPets()}
   */
  @Test
  public void testGetPets_givenOwnerAddressIs42MainSt() {
    // Arrange
    Owner owner = new Owner();
    owner.setAddress("42 Main St");
    owner.setCity("Oxford");
    owner.setFirstName("Jane");
    owner.setId(1);
    owner.setLastName("Doe");
    owner.setTelephone("6625550144");
    owner.setPetsInternal(new HashSet<>());

    // Act and Assert
    assertTrue(owner.getPets().isEmpty());
  }

  /**
   * Test {@link Owner#addPet(Pet)}.
   * <p>
   * Method under test: {@link Owner#addPet(Pet)}
   */
  @Test
  public void testAddPet() {
    // Arrange
    Owner owner = new Owner();

    Owner owner2 = new Owner();
    owner2.setAddress("42 Main St");
    owner2.setCity("Oxford");
    owner2.setFirstName("Jane");
    owner2.setId(1);
    owner2.setLastName("Doe");
    owner2.setPetsInternal(new HashSet<>());
    owner2.setTelephone("6625550144");

    PetType type = new PetType();
    type.setId(1);
    type.setName("Dog");

    Pet pet = new Pet();
    pet.setBirthDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    pet.setId(1);
    pet.setName("Bella");
    pet.setOwner(owner2);
    pet.setType(type);
    pet.setVisitsInternal(new HashSet<>());

    // Act
    owner.addPet(pet);

    // Assert
    Owner owner3 = pet.getOwner();
    assertNull(owner3.getId());
    assertNull(owner3.getFirstName());
    assertNull(owner3.getLastName());
    assertNull(owner3.getAddress());
    assertNull(owner3.getCity());
    assertNull(owner3.getTelephone());
    assertTrue(owner3.isNew());
  }

  /**
   * Test {@link Owner#getPet(String, boolean)} with {@code name},
   * {@code ignoreNew}.
   * <ul>
   *   <li>Given {@link Owner} (default constructor) PetsInternal is
   * {@link HashSet#HashSet()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#getPet(String, boolean)}
   */
  @Test
  public void testGetPetWithNameIgnoreNew_givenOwnerPetsInternalIsHashSet_thenReturnNull() {
    // Arrange
    Owner owner = new Owner();
    owner.setPetsInternal(new HashSet<>());

    // Act and Assert
    assertNull(owner.getPet("Bella", true));
  }

  /**
   * Test {@link Owner#getPet(String, boolean)} with {@code name},
   * {@code ignoreNew}.
   * <ul>
   *   <li>Given {@link Owner} (default constructor).</li>
   *   <li>When {@code Bella}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#getPet(String, boolean)}
   */
  @Test
  public void testGetPetWithNameIgnoreNew_givenOwner_whenBella_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new Owner()).getPet("Bella", true));
  }

  /**
   * Test {@link Owner#getPet(String, boolean)} with {@code name},
   * {@code ignoreNew}.
   * <ul>
   *   <li>Given {@link Pet} (default constructor) Id is {@code null}.</li>
   *   <li>When {@code Bella}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#getPet(String, boolean)}
   */
  @Test
  public void testGetPetWithNameIgnoreNew_givenPetIdIsNull_whenBella_thenReturnNull() {
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
    pet.setId(null);
    pet.setName("Bella");
    pet.setOwner(owner);
    pet.setType(type);
    pet.setVisitsInternal(new HashSet<>());

    HashSet<Pet> pets = new HashSet<>();
    pets.add(pet);

    Owner owner2 = new Owner();
    owner2.setPetsInternal(pets);

    // Act and Assert
    assertNull(owner2.getPet("Bella", true));
  }

  /**
   * Test {@link Owner#getPet(String, boolean)} with {@code name},
   * {@code ignoreNew}.
   * <ul>
   *   <li>Given {@link Pet} (default constructor) Name is {@code Bella}.</li>
   *   <li>When {@code Bella}.</li>
   *   <li>Then return {@link Pet} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#getPet(String, boolean)}
   */
  @Test
  public void testGetPetWithNameIgnoreNew_givenPetNameIsBella_whenBella_thenReturnPet() {
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
    pet.setVisitsInternal(new HashSet<>());

    HashSet<Pet> pets = new HashSet<>();
    pets.add(pet);

    Owner owner2 = new Owner();
    owner2.setPetsInternal(pets);

    // Act and Assert
    assertSame(pet, owner2.getPet("Bella", true));
  }

  /**
   * Test {@link Owner#getPet(String, boolean)} with {@code name},
   * {@code ignoreNew}.
   * <ul>
   *   <li>Given {@link Pet} (default constructor) Name is {@code Name}.</li>
   *   <li>When {@code Bella}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#getPet(String, boolean)}
   */
  @Test
  public void testGetPetWithNameIgnoreNew_givenPetNameIsName_whenBella_thenReturnNull() {
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
    pet.setName("Name");
    pet.setOwner(owner);
    pet.setType(type);
    pet.setVisitsInternal(new HashSet<>());

    HashSet<Pet> pets = new HashSet<>();
    pets.add(pet);

    Owner owner2 = new Owner();
    owner2.setPetsInternal(pets);

    // Act and Assert
    assertNull(owner2.getPet("Bella", true));
  }

  /**
   * Test {@link Owner#getPet(String)} with {@code name}.
   * <ul>
   *   <li>Given {@link Owner} (default constructor) PetsInternal is
   * {@link HashSet#HashSet()}.</li>
   *   <li>When {@code Bella}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#getPet(String)}
   */
  @Test
  public void testGetPetWithName_givenOwnerPetsInternalIsHashSet_whenBella_thenReturnNull() {
    // Arrange
    Owner owner = new Owner();
    owner.setPetsInternal(new HashSet<>());

    // Act and Assert
    assertNull(owner.getPet("Bella"));
  }

  /**
   * Test {@link Owner#getPet(String)} with {@code name}.
   * <ul>
   *   <li>Given {@link Owner} (default constructor).</li>
   *   <li>When {@code Bella}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#getPet(String)}
   */
  @Test
  public void testGetPetWithName_givenOwner_whenBella_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new Owner()).getPet("Bella"));
  }

  /**
   * Test {@link Owner#getPet(String)} with {@code name}.
   * <ul>
   *   <li>Given {@link Pet} (default constructor) Name is {@code Bella}.</li>
   *   <li>When {@code Bella}.</li>
   *   <li>Then return {@link Pet} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#getPet(String)}
   */
  @Test
  public void testGetPetWithName_givenPetNameIsBella_whenBella_thenReturnPet() {
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
    pet.setVisitsInternal(new HashSet<>());

    HashSet<Pet> pets = new HashSet<>();
    pets.add(pet);

    Owner owner2 = new Owner();
    owner2.setPetsInternal(pets);

    // Act and Assert
    assertSame(pet, owner2.getPet("Bella"));
  }

  /**
   * Test {@link Owner#getPet(String)} with {@code name}.
   * <ul>
   *   <li>Given {@link Pet} (default constructor) Name is {@code Name}.</li>
   *   <li>When {@code Bella}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Owner#getPet(String)}
   */
  @Test
  public void testGetPetWithName_givenPetNameIsName_whenBella_thenReturnNull() {
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
    pet.setName("Name");
    pet.setOwner(owner);
    pet.setType(type);
    pet.setVisitsInternal(new HashSet<>());

    HashSet<Pet> pets = new HashSet<>();
    pets.add(pet);

    Owner owner2 = new Owner();
    owner2.setPetsInternal(pets);

    // Act and Assert
    assertNull(owner2.getPet("Bella"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Owner}
   *   <li>{@link Owner#setAddress(String)}
   *   <li>{@link Owner#setCity(String)}
   *   <li>{@link Owner#setPetsInternal(Set)}
   *   <li>{@link Owner#setTelephone(String)}
   *   <li>{@link Owner#toString()}
   *   <li>{@link Owner#getAddress()}
   *   <li>{@link Owner#getCity()}
   *   <li>{@link Owner#getTelephone()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Owner actualOwner = new Owner();
    actualOwner.setAddress("42 Main St");
    actualOwner.setCity("Oxford");
    HashSet<Pet> pets = new HashSet<>();
    actualOwner.setPetsInternal(pets);
    actualOwner.setTelephone("6625550144");
    actualOwner.toString();
    String actualAddress = actualOwner.getAddress();
    String actualCity = actualOwner.getCity();

    // Assert
    assertEquals("42 Main St", actualAddress);
    assertEquals("6625550144", actualOwner.getTelephone());
    assertEquals("Oxford", actualCity);
    assertNull(actualOwner.getId());
    assertNull(actualOwner.getFirstName());
    assertNull(actualOwner.getLastName());
    Set<Pet> petsInternal = actualOwner.getPetsInternal();
    assertTrue(petsInternal.isEmpty());
    assertSame(pets, petsInternal);
  }
}

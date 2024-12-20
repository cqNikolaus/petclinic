package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = {PetController.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PetControllerDiffblueTest {
  @MockBean
  private OwnerRepository ownerRepository;

  @Autowired
  private PetController petController;

  @MockBean
  private PetRepository petRepository;

  /**
   * Test {@link PetController#populatePetTypes()}.
   * <p>
   * Method under test: {@link PetController#populatePetTypes()}
   */
  @Test
  public void testPopulatePetTypes() {
    // Arrange
    ArrayList<PetType> petTypeList = new ArrayList<>();
    when(petRepository.findPetTypes()).thenReturn(petTypeList);

    // Act
    Collection<PetType> actualPopulatePetTypesResult = petController.populatePetTypes();

    // Assert
    verify(petRepository).findPetTypes();
    assertTrue(actualPopulatePetTypesResult instanceof List);
    assertTrue(actualPopulatePetTypesResult.isEmpty());
    assertSame(petTypeList, actualPopulatePetTypesResult);
  }

  /**
   * Test {@link PetController#findOwner(int)}.
   * <p>
   * Method under test: {@link PetController#findOwner(int)}
   */
  @Test
  public void testFindOwner() {
    // Arrange
    Owner owner = new Owner();
    owner.setAddress("42 Main St");
    owner.setCity("Oxford");
    owner.setFirstName("Jane");
    owner.setId(1);
    owner.setLastName("Doe");
    owner.setPetsInternal(new HashSet<>());
    owner.setTelephone("6625550144");
    when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);

    // Act
    Owner actualFindOwnerResult = petController.findOwner(1);

    // Assert
    verify(ownerRepository).findById(anyInt());
    assertSame(owner, actualFindOwnerResult);
  }

  /**
   * Test {@link PetController#initCreationForm(Owner, ModelMap)}.
   * <p>
   * Method under test: {@link PetController#initCreationForm(Owner, ModelMap)}
   */
  @Test
  public void testInitCreationForm() throws Exception {
    // Arrange
    when(petRepository.findPetTypes()).thenReturn(new ArrayList<>());

    Owner owner = new Owner();
    owner.setAddress("42 Main St");
    owner.setCity("Oxford");
    owner.setFirstName("Jane");
    owner.setId(1);
    owner.setLastName("Doe");
    owner.setPetsInternal(new HashSet<>());
    owner.setTelephone("6625550144");
    when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/owners/{ownerId}/pets/new", 1);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(petController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(3))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner", "pet", "types"))
        .andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("pets/createOrUpdatePetForm"));
  }

  /**
   * Test
   * {@link PetController#processCreationForm(Owner, Pet, BindingResult, ModelMap)}.
   * <ul>
   *   <li>When {@code Bella}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link PetController#processCreationForm(Owner, Pet, BindingResult, ModelMap)}
   */
  @Test
  public void testProcessCreationForm_whenBella() throws Exception {
    // Arrange
    when(petRepository.findPetTypes()).thenReturn(new ArrayList<>());

    Owner owner = new Owner();
    owner.setAddress("42 Main St");
    owner.setCity("Oxford");
    owner.setFirstName("Jane");
    owner.setId(1);
    owner.setLastName("Doe");
    owner.setPetsInternal(new HashSet<>());
    owner.setTelephone("6625550144");
    when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/owners/{ownerId}/pets/new", 1)
        .param("name", "Bella");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(petController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(3))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner", "pet", "types"))
        .andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("pets/createOrUpdatePetForm"));
  }

  /**
   * Test
   * {@link PetController#processCreationForm(Owner, Pet, BindingResult, ModelMap)}.
   * <ul>
   *   <li>When {@link MockMvcRequestBuilders#post(String, Object[])}
   * {@code /owners/{ownerId}/pets/new} one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link PetController#processCreationForm(Owner, Pet, BindingResult, ModelMap)}
   */
  @Test
  public void testProcessCreationForm_whenPostOwnersOwnerIdPetsNewOne() throws Exception {
    // Arrange
    when(petRepository.findPetTypes()).thenReturn(new ArrayList<>());

    Owner owner = new Owner();
    owner.setAddress("42 Main St");
    owner.setCity("Oxford");
    owner.setFirstName("Jane");
    owner.setId(1);
    owner.setLastName("Doe");
    owner.setPetsInternal(new HashSet<>());
    owner.setTelephone("6625550144");
    when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/owners/{ownerId}/pets/new", 1);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(petController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(3))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner", "pet", "types"))
        .andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("pets/createOrUpdatePetForm"));
  }

  /**
   * Test {@link PetController#initUpdateForm(int, ModelMap)}.
   * <p>
   * Method under test: {@link PetController#initUpdateForm(int, ModelMap)}
   */
  @Test
  public void testInitUpdateForm() throws Exception {
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
    when(petRepository.findPetTypes()).thenReturn(new ArrayList<>());
    when(petRepository.findById(Mockito.<Integer>any())).thenReturn(pet);

    Owner owner2 = new Owner();
    owner2.setAddress("42 Main St");
    owner2.setCity("Oxford");
    owner2.setFirstName("Jane");
    owner2.setId(1);
    owner2.setLastName("Doe");
    owner2.setPetsInternal(new HashSet<>());
    owner2.setTelephone("6625550144");
    when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner2);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/owners/{ownerId}/pets/{petId}/edit", 1,
        1);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(petController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(3))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner", "pet", "types"))
        .andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("pets/createOrUpdatePetForm"));
  }

  /**
   * Test
   * {@link PetController#processUpdateForm(Pet, BindingResult, Owner, ModelMap)}.
   * <p>
   * Method under test:
   * {@link PetController#processUpdateForm(Pet, BindingResult, Owner, ModelMap)}
   */
  @Test
  public void testProcessUpdateForm() throws Exception {
    // Arrange
    when(petRepository.findPetTypes()).thenReturn(new ArrayList<>());

    Owner owner = new Owner();
    owner.setAddress("42 Main St");
    owner.setCity("Oxford");
    owner.setFirstName("Jane");
    owner.setId(1);
    owner.setLastName("Doe");
    owner.setPetsInternal(new HashSet<>());
    owner.setTelephone("6625550144");
    when(ownerRepository.findById(Mockito.<Integer>any())).thenReturn(owner);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/owners/{ownerId}/pets/{petId}/edit", 1,
        1);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(petController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(3))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner", "pet", "types"))
        .andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdatePetForm"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("pets/createOrUpdatePetForm"));
  }
}

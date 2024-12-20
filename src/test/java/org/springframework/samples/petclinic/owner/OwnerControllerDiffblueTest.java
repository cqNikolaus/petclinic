package org.springframework.samples.petclinic.owner;

import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
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
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = {OwnerController.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class OwnerControllerDiffblueTest {
  @Autowired
  private OwnerController ownerController;

  @MockBean
  private OwnerRepository ownerRepository;

  /**
   * Test {@link OwnerController#initCreationForm(Map)}.
   * <p>
   * Method under test: {@link OwnerController#initCreationForm(Map)}
   */
  @Test
  public void testInitCreationForm() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/owners/new");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(ownerController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
        .andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("owners/createOrUpdateOwnerForm"));
  }

  /**
   * Test {@link OwnerController#processCreationForm(Owner, BindingResult)}.
   * <p>
   * Method under test:
   * {@link OwnerController#processCreationForm(Owner, BindingResult)}
   */
  @Test
  public void testProcessCreationForm() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/owners/new");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(ownerController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
        .andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("owners/createOrUpdateOwnerForm"));
  }

  /**
   * Test {@link OwnerController#initFindForm(Map)}.
   * <p>
   * Method under test: {@link OwnerController#initFindForm(Map)}
   */
  @Test
  public void testInitFindForm() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/owners/find");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(ownerController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
        .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("owners/findOwners"));
  }

  /**
   * Test {@link OwnerController#processFindForm(Owner, BindingResult, Map)}.
   * <ul>
   *   <li>Given {@link Owner} (default constructor) Address is
   * {@code 17 High St}.</li>
   *   <li>When {@link MockMvcRequestBuilders#get(String, Object[])}
   * {@code /owners}.</li>
   *   <li>Then model size two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link OwnerController#processFindForm(Owner, BindingResult, Map)}
   */
  @Test
  public void testProcessFindForm_givenOwnerAddressIs17HighSt_whenGetOwners_thenModelSizeTwo() throws Exception {
    // Arrange
    Owner owner = new Owner();
    owner.setAddress("42 Main St");
    owner.setCity("Oxford");
    owner.setFirstName("Jane");
    owner.setId(1);
    owner.setLastName("Doe");
    owner.setPetsInternal(new HashSet<>());
    owner.setTelephone("6625550144");

    Owner owner2 = new Owner();
    owner2.setAddress("17 High St");
    owner2.setCity("London");
    owner2.setFirstName("John");
    owner2.setId(2);
    owner2.setLastName("Smith");
    owner2.setPetsInternal(new HashSet<>());
    owner2.setTelephone("8605550118");

    ArrayList<Owner> ownerList = new ArrayList<>();
    ownerList.add(owner2);
    ownerList.add(owner);
    when(ownerRepository.findByLastName(Mockito.<String>any())).thenReturn(ownerList);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/owners");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(ownerController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(2))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner", "selections"))
        .andExpect(MockMvcResultMatchers.view().name("owners/ownersList"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("owners/ownersList"));
  }

  /**
   * Test {@link OwnerController#processFindForm(Owner, BindingResult, Map)}.
   * <ul>
   *   <li>Given {@link Owner} (default constructor) Address is
   * {@code 42 Main St}.</li>
   *   <li>When {@link MockMvcRequestBuilders#get(String, Object[])}
   * {@code /owners}.</li>
   *   <li>Then status {@link StatusResultMatchers#isFound()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link OwnerController#processFindForm(Owner, BindingResult, Map)}
   */
  @Test
  public void testProcessFindForm_givenOwnerAddressIs42MainSt_whenGetOwners_thenStatusIsFound() throws Exception {
    // Arrange
    Owner owner = new Owner();
    owner.setAddress("42 Main St");
    owner.setCity("Oxford");
    owner.setFirstName("Jane");
    owner.setId(1);
    owner.setLastName("Doe");
    owner.setPetsInternal(new HashSet<>());
    owner.setTelephone("6625550144");

    ArrayList<Owner> ownerList = new ArrayList<>();
    ownerList.add(owner);
    when(ownerRepository.findByLastName(Mockito.<String>any())).thenReturn(ownerList);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/owners");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(ownerController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
        .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"))
        .andExpect(MockMvcResultMatchers.redirectedUrl("/owners/1"));
  }

  /**
   * Test {@link OwnerController#processFindForm(Owner, BindingResult, Map)}.
   * <ul>
   *   <li>When {@code Doe}.</li>
   *   <li>Then view name {@code owners/findOwners}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link OwnerController#processFindForm(Owner, BindingResult, Map)}
   */
  @Test
  public void testProcessFindForm_whenDoe_thenViewNameOwnersFindOwners() throws Exception {
    // Arrange
    when(ownerRepository.findByLastName(Mockito.<String>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/owners").param("lastName", "Doe");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(ownerController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
        .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("owners/findOwners"));
  }

  /**
   * Test {@link OwnerController#processFindForm(Owner, BindingResult, Map)}.
   * <ul>
   *   <li>When {@link MockMvcRequestBuilders#get(String, Object[])}
   * {@code /owners}.</li>
   *   <li>Then view name {@code owners/findOwners}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link OwnerController#processFindForm(Owner, BindingResult, Map)}
   */
  @Test
  public void testProcessFindForm_whenGetOwners_thenViewNameOwnersFindOwners() throws Exception {
    // Arrange
    when(ownerRepository.findByLastName(Mockito.<String>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/owners");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(ownerController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
        .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("owners/findOwners"));
  }

  /**
   * Test {@link OwnerController#initUpdateOwnerForm(int, Model)}.
   * <p>
   * Method under test: {@link OwnerController#initUpdateOwnerForm(int, Model)}
   */
  @Test
  public void testInitUpdateOwnerForm() throws Exception {
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
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/owners/{ownerId}/edit", 1);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(ownerController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
        .andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("owners/createOrUpdateOwnerForm"));
  }

  /**
   * Test
   * {@link OwnerController#processUpdateOwnerForm(Owner, BindingResult, int)}.
   * <p>
   * Method under test:
   * {@link OwnerController#processUpdateOwnerForm(Owner, BindingResult, int)}
   */
  @Test
  public void testProcessUpdateOwnerForm() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/owners/{ownerId}/edit", 1);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(ownerController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
        .andExpect(MockMvcResultMatchers.view().name("owners/createOrUpdateOwnerForm"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("owners/createOrUpdateOwnerForm"));
  }

  /**
   * Test {@link OwnerController#showOwner(int)}.
   * <p>
   * Method under test: {@link OwnerController#showOwner(int)}
   */
  @Test
  public void testShowOwner() throws Exception {
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
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/owners/{ownerId}", 1);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(ownerController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
        .andExpect(MockMvcResultMatchers.view().name("owners/ownerDetails"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("owners/ownerDetails"));
  }
}

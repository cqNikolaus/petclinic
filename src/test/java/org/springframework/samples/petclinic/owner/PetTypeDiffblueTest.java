package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.assertNull;
import org.junit.Test;

public class PetTypeDiffblueTest {
  /**
   * Test new {@link PetType} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link PetType}
   */
  @Test
  public void testNewPetType() {
    // Arrange and Act
    PetType actualPetType = new PetType();

    // Assert
    assertNull(actualPetType.getId());
    assertNull(actualPetType.getName());
  }
}

package org.springframework.samples.petclinic.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class NamedEntityDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link NamedEntity}
   *   <li>{@link NamedEntity#setName(String)}
   *   <li>{@link NamedEntity#toString()}
   *   <li>{@link NamedEntity#getName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    NamedEntity actualNamedEntity = new NamedEntity();
    actualNamedEntity.setName("Bella");
    String actualToStringResult = actualNamedEntity.toString();

    // Assert
    assertEquals("Bella", actualNamedEntity.getName());
    assertEquals("Bella", actualToStringResult);
    assertNull(actualNamedEntity.getId());
  }
}

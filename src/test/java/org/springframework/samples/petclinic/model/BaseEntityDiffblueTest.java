package org.springframework.samples.petclinic.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BaseEntityDiffblueTest {
  /**
   * Test {@link BaseEntity#isNew()}.
   * <ul>
   *   <li>Given {@link BaseEntity} (default constructor) Id is one.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseEntity#isNew()}
   */
  @Test
  public void testIsNew_givenBaseEntityIdIsOne_thenReturnFalse() {
    // Arrange
    BaseEntity baseEntity = new BaseEntity();
    baseEntity.setId(1);

    // Act and Assert
    assertFalse(baseEntity.isNew());
  }

  /**
   * Test {@link BaseEntity#isNew()}.
   * <ul>
   *   <li>Given {@link BaseEntity} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseEntity#isNew()}
   */
  @Test
  public void testIsNew_givenBaseEntity_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new BaseEntity()).isNew());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BaseEntity}
   *   <li>{@link BaseEntity#setId(Integer)}
   *   <li>{@link BaseEntity#getId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    BaseEntity actualBaseEntity = new BaseEntity();
    actualBaseEntity.setId(1);

    // Assert
    assertEquals(1, actualBaseEntity.getId().intValue());
  }
}

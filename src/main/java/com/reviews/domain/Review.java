/**
 * 
 */
package com.reviews.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Objeto que representa uma revisão.
 * 
 * @author Vinicius N. R. Garcia
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Review {

    private Long nuOperation;
    private String user;
    private BigDecimal netValue;
    private int totalMessages;
    private long sequenceMessage;
    private long countMessages;

}
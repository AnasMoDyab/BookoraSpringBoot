package com.tietoevry.bookorabackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Entity that represents a activation code for resetting password.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RestPasswordCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String confirmationCode;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ManyToOne
    private Employee employee;

    private Timestamp expiryDate;

    public RestPasswordCode(Employee employee) {
        this.employee = employee;
        createdDate = new Date();
        confirmationCode = getAlphaNumericString(8);
    }


    /**
     * Generates a random string.
     *
     * @param n A integer that represents the total length of the random string
     * @return A random string
     */
    static String getAlphaNumericString(int n) {

        // Possible choices of character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}



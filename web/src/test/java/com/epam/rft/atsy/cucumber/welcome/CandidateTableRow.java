package com.epam.rft.atsy.cucumber.welcome;

/**
 * @author Antal_Kiss on 11/18/2015.
 */
public class CandidateTableRow {
     String name;
     String email;
     String phone;
     String positions;

    public CandidateTableRow(String name, String email, String phone, String positions) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.positions = positions;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPositions() {
        return positions;
    }
}

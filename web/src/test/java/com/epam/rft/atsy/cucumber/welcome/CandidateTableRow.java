package com.epam.rft.atsy.cucumber.welcome;

import java.lang.reflect.Field;

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

    public static class Comparator implements java.util.Comparator<CandidateTableRow> {
        private String orderField;
        private String order;

        public Comparator(String orderField, String order) {
            this.orderField = orderField;
            this.order = order;
        }

        @Override
        public int compare(CandidateTableRow o1, CandidateTableRow o2) {
            int compareValue = getFieldValue(o1).compareTo(getFieldValue(o2));
            if ("desc".equals(order)) {
                compareValue = compareValue * -1;
            }
            return compareValue;
        }

        private String getFieldValue(CandidateTableRow row) {
            String value = "";
            try {
                Field field = CandidateTableRow.class.getDeclaredField(orderField);
                field.setAccessible(true);
                value = (String) field.get(row);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
            return value;
        }
    }
}


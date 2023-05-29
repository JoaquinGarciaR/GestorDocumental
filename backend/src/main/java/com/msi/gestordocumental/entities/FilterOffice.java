package com.msi.gestordocumental.entities;

public class FilterOffice {
    private String start_range_date;
    private String end_range_date;
    private Boolean order_ascendant;
    public FilterOffice() {
        this.start_range_date = "";
        this.end_range_date = "";
        this.order_ascendant = false;
    }
    public FilterOffice(String start_range_date, String end_range_date, Boolean order_ascendant) {
        this.start_range_date = start_range_date;
        this.end_range_date = end_range_date;
        this.order_ascendant = order_ascendant;
    }
    public String getStart_range_date() {
        return start_range_date;
    }
    public void setStart_range_date(String start_range_date) {
        this.start_range_date = start_range_date;
    }
    public String getEnd_range_date() {
        return end_range_date;
    }
    public void setEnd_range_date(String end_range_date) {
        this.end_range_date = end_range_date;
    }
    public Boolean getOrder_ascendant() {
        return order_ascendant;
    }
    public void setOrder_ascendant(Boolean order_ascendant) {
        this.order_ascendant = order_ascendant;
    }

    public String parseStartDateToDB(){
        if(start_range_date == null || start_range_date.isEmpty()) return null;
        return start_range_date + " 0:0:0.0";
    }

    public String parseEndDateToDB(){
        if(end_range_date == null || end_range_date.isEmpty()) return null;
        return end_range_date + " 23:59:59.999";
    }
}

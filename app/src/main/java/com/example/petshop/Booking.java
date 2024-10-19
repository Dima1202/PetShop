package com.example.petshop;

    public class Booking {
        private String caregiverId;
        private String customerId;
        private String bookingDate;
        private String startTime;
        private String endTime;

        public Booking() {
            // Default constructor required for Firestore
        }

        public Booking(String caregiverId, String customerId, String bookingDate, String startTime, String endTime) {
            this.caregiverId = caregiverId;
            this.customerId = customerId;
            this.bookingDate = bookingDate;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public String getCaregiverId() {
            return caregiverId;
        }

        public void setCaregiverId(String caregiverId) {
            this.caregiverId = caregiverId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getBookingDate() {
            return bookingDate;
        }

        public void setBookingDate(String bookingDate) {
            this.bookingDate = bookingDate;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }


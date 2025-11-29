package com.example.strayanimals.data.model;

import com.google.gson.annotations.SerializedName;

public class AddressInfo {
    private String status;
    private Result result;

    public class Result {
        private Location location;
        @SerializedName("formatted_address")
        private String formattedAddress;
        private String business;
        private AddressComponent addressComponent;
        private int cityCode;

        public class Location {
            private float lng;
            private float lat;

            public float getLng() {
                return lng;
            }

            public void setLng(long lng) {
                this.lng = lng;
            }

            public float getLat() {
                return lat;
            }

            public void setLat(long lat) {
                this.lat = lat;
            }
        }

        public class AddressComponent {
            private String city;
            private String direction;
            private String distance;
            private String district;
            private String province;
            private String street;
            @SerializedName("street_number")
            private String streetNumber;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getStreetNumber() {
                return streetNumber;
            }

            public void setStreetNumber(String streetNumber) {
                this.streetNumber = streetNumber;
            }
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public AddressComponent getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponent addressComponent) {
            this.addressComponent = addressComponent;
        }

        public int getCityCode() {
            return cityCode;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}

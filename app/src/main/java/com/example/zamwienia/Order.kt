package com.example.zamwienia

class Order {

    var street: String = ""
    var houseNo: String = ""
    var phoneNo: String = ""
    var deliveryTime: String = ""
    var deliveryTimeMax: String = ""
    var deliveryTimes: String =""

    constructor(street: String, houseNo: String, phoneNo: String, deliveryTime: String, deliveryTimeMax: String, deliveryTimes: String) {
        this.phoneNo = phoneNo
        this.street = street
        this.houseNo = houseNo
        this.deliveryTime = deliveryTime
        this.deliveryTimeMax = deliveryTimeMax
        this.deliveryTimes = deliveryTimes

    }




}
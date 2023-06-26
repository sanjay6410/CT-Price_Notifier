import React, { useState, useEffect } from "react";
import { useParams } from 'react-router-dom';
import axios from "axios";
import "./css/CustomerUpdateInfo.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import NavBar from './NavBar';

function EditProfile() {
  const [custId, setCustId] = useState(localStorage.getItem("customerId"));
  const [formData, setFormData] = useState({
    country: "",
    streetName: "",
    streetNumber: "",
    building: "",
    city: "",
    region: "",
    state: "",
    company: "",
    department: "",
    apartment: "",
    pOBox: ""
  });
  const [selectedDate, setSelectedDate] = useState(null);
  const [customerData, setCustomerData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    mobileNumber: "",
    
  });
  const [showAddressForm, setShowAddressForm] = useState(false);


  const customerDataInfo = async () => {
    axios.get("http://localhost:8080/getCustomerById?custId=" + custId)
      .then((response) => {
        setCustomerData({
          firstName: response.data.firstName,
          lastName: response.data.lastName,
          email: response.data.email,
          password: response.data.password,
          mobileNumber: response.data.custom.fields["Customer-mobile-number"],
        });
        //setSelectedDate
      });
  };

  useEffect(() => {
    customerDataInfo();
  }, []);

  const handleReset = (e) => {
    e.preventDefault();
    setFormData({
      country: "",
      streetName: "",
      streetNumber: "",
      building: "",
      city: "",
      region: "",
      state: "",
      company: "",
      department: "",
      apartment: "",
      pOBox: ""
    });
  };



  const handleAddAddress = (event) => {
    event.preventDefault();
    // alert(formatDate(selectedDate));
    setShowAddressForm(true);
  };

  const handleAdrressFormClose = (event) => {
    event.preventDefault();
    setShowAddressForm(false);
  };

  const handleAddressChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    axios.post("http://localhost:8080/customerProfileUpdate?email=" + customerData.email , formData)
      .then((response) => {
        console.log(response.data);
      })
  };
  const handleChangePassword=(e)=>{
    e.preventDefault();
    window.location.assign("/changePassword");
  }


 

  return (
    <div>
        <NavBar />
   
    <div className="CUIDivMain">
    
      <h1 className="CUIHeadingH1">Profile Update</h1>
      <form className="customerFormData">
        <label>
          First Name:
          &nbsp;<input type='text' name="firstName" value={customerData.firstName} readOnly/>
        </label>
        <label>
          Last Name:
          &nbsp;<input type='text' name="lastName" value={customerData.lastName} readOnly/>
        </label>
        <label>
          Email:
          &nbsp;<input type='email' name="email" value={customerData.email} readOnly/>
        </label>
        <label>
          Mobile Number:
          &nbsp;<input type='text' name="mobileNumber" value={customerData.mobileNumber} readOnly/>
        </label>
        <br />
        <button onClick={handleAddAddress} className="btn btn-warning">Add Address</button><br />
        <button onClick={handleChangePassword} className="btn btn-warning">Change Password</button><br />
      </form>
      {showAddressForm && (
        <form onSubmit={handleSubmit} className="customerFormData">
          <label>
          Country Code:
          <input type="text" name="country" value={formData.country} onChange={handleAddressChange} />
        </label>
        <label>
          Street Name:
          <input type="text" name="streetName" value={formData.streetName} onChange={handleAddressChange} />
        </label>
        <label>
          Street Number:
          <input type="text" name="streetNumber" value={formData.streetNumber} onChange={handleAddressChange} />
        </label>
        <label>
          Building:
          <input type="text" name="building" value={formData.building} onChange={handleAddressChange} />
        </label>
        <label>
          City:
          <input type="text" name="city" value={formData.city} onChange={handleAddressChange} />
        </label>
        <label>
          Region:
          <input type="text" name="region" value={formData.region} onChange={handleAddressChange} />
        </label>
        <label>
          State:
          <input type="text" name="state" value={formData.state} onChange={handleAddressChange} />
        </label>
        <label>
          Company:
          <input type="text" name="company" value={formData.company} onChange={handleAddressChange} />
        </label>
        <label>
          Department:
          <input type="text" name="department" value={formData.department} onChange={handleAddressChange} />
        </label>
        <label>
          Apartment:
          <input type="text" name="apartment" value={formData.apartment} onChange={handleAddressChange} />
        </label>
        <label>
          PO Box:
          <input type="text" name="pOBox" value={formData.pOBox} onChange={handleAddressChange} />
        </label>
        <div className="CUIbtns">
          <button onClick={handleAdrressFormClose} className="btn btn-success">Close</button>&nbsp;&nbsp;
          <button onClick={handleReset} className="btn btn-success">Reset</button>&nbsp;&nbsp;
          <button type="submit" className="btn btn-success">Submit</button>&nbsp;&nbsp;
          </div>
        </form>
      )}
    </div>
    </div>
  );
}

export default EditProfile;
import axios from "axios";
import React, { useState } from "react";
import NavBar from "./NavBar";
import './css/CustomerChangePassword.css';

function CustomerChangePassword() {
  const [formData, setFormData] = useState({
    email: "",
    newPassword: "",
    oldPassword:""
  });

  const [passwordError, setPasswordError] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target; 
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
    if (name === "newPassword") {
        setPasswordError("");
      }
  };

  const handleReset = () => {
    setFormData({
      email: "",
      newPassword: "",
      oldPassword:""
    });
    setPasswordError("");
  };

  const validatePassword = (password) => {
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{6,}$/;
    return passwordRegex.test(password);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if(!validatePassword(formData.newPassword)){
        setPasswordError(
            "Password must be at least 6 characters long, contain at least one special character, and at least one number."
          );
          return;
    }
    axios
      .post("http://localhost:8080/customerChangePassword", formData)
      .then((response) => {
        console.log(response.data);
        
        alert("Password Changed Successfully");
        window.location.reload();
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <div>
      <NavBar />
   
    <div className="CCPDivMain">
      
      <h1>Customer Change Password</h1><br />
      <form onSubmit={handleSubmit} onReset={handleReset}>
        <label className="emailLabel">
          Email:{" "}
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
        </label><br /><br />
        <label>
          Old Password:{" "}
          <input
            type="password"
            name="oldPassword"
            value={formData.oldPassword}
            onChange={handleChange}
          />
        </label><br /><br />
        <label>
          New Password:{" "}
          <input
            type="password"
            name="newPassword"
            value={formData.newPassword}
            onChange={handleChange}
          />
        </label><br />
        {passwordError && <div>{passwordError}</div>}
        <div className="CCPbtns">
        <button type="submit" className="btn btn-success">Change Password</button><br />
        <button type="reset" className="btn btn-success CCPbtns2">Reset</button><br />
        </div>
      </form>
    </div>
    </div>
  );
}

export default CustomerChangePassword;

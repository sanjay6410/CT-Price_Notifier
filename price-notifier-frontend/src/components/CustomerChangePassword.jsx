import axios from "axios";
import React, { useState } from "react";

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
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <div>
      <h1>Customer Reset Password</h1>
      <form onSubmit={handleSubmit} onReset={handleReset}>
        <label>
          Email:{" "}
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
        </label>
        <label>
          Old Password:{" "}
          <input
            type="password"
            name="oldPassword"
            value={formData.oldPassword}
            onChange={handleChange}
          />
        </label>
        <label>
          New Password:{" "}
          <input
            type="password"
            name="newPassword"
            value={formData.newPassword}
            onChange={handleChange}
          />
        </label>
        {passwordError && <div>{passwordError}</div>}
        <button type="submit">Change Password</button>
        <button type="reset">Reset</button>
      </form>
    </div>
  );
}

export default CustomerChangePassword;

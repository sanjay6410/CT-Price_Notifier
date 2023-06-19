import React, { useState } from "react";
import axios from "axios";
import "./css/customerSignUpCss.css";

function Registration() {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
    mobileNumber: "",
  });

  const [passwordError, setPasswordError] = useState("");
  const [mobileNumberError, setMobileNumberError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [redirect, setRedirect] = useState(false);

  const validatePassword = (password) => {
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{6,}$/;
    return passwordRegex.test(password);
  };

  const validateMobileNumber = (mobileNumber) => {
    const mobileNumberRegex = /^\d{10}$/;
    return mobileNumberRegex.test(mobileNumber);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));

    if (name === "password") {
      setPasswordError("");
    }

    if (name === "mobileNumber") {
      setMobileNumberError("");
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      alert("Password and Confirm Password do not match");
      return;
    }

    if (!validatePassword(formData.password)) {
      setPasswordError(
        "Password must be at least 6 characters long, contain at least one special character, and at least one number."
      );
      return;
    }

    if (!validateMobileNumber(formData.mobileNumber)) {
      setMobileNumberError("Mobile Number should be 10 digits.");
      return;
    }

    axios
      .post("http://localhost:8080/customerSignUp", formData)
      .then((response) => {
        if (response.status === 200) {
          setSuccessMessage("Signup Successful.");
          setRedirect(true);

          setTimeout(() => {
            
            window.location.assign("/login"); 
          }, 4000);
        } else {
          console.log(response.data);
        }
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleReset = () => {
    setFormData({
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      confirmPassword: "",
      mobileNumber: "",
    });
    setPasswordError("");
    setMobileNumberError("");
  };

  if (redirect) {
    return (
      <div>
        {successMessage && <div>{successMessage}</div>}
        <p>Redirecting to login page...</p>
      </div>
    );
  }

  return (
    <div className="registration-container">
    <form onSubmit={handleSubmit} onReset={handleReset}>
      <h1 >Customer SignUp</h1>
      <label>
        First Name:{" "}
        <input
          type="text"
          name="firstName"
          value={formData.firstName}
          onChange={handleChange}
        />
      </label>
      <br />
      <label>
        Last Name:{" "}
        <input
          type="text"
          name="lastName"
          value={formData.lastName}
          onChange={handleChange}
        />
      </label>
      <br />
      <label>
        Email:{" "}
        <input
          type="email"
          name="email"
          value={formData.email}
          onChange={handleChange}
        />
      </label>
      <br />
      <label>
        Password:{" "}
        <input
          type="password"
          name="password"
          value={formData.password}
          onChange={handleChange}
        />
      </label>
      <br />
      {passwordError && <div>{passwordError}</div>}
      <br />
      <label>
        Confirm Password:{" "}
        <input
          type="password"
          name="confirmPassword"
          value={formData.confirmPassword}
          onChange={handleChange}
        />
      </label>
      <br />
      <label>
        Mobile Number:{" "}
        <input
          type="text"
          name="mobileNumber"
          value={formData.mobileNumber}
          onChange={handleChange}
        />
      </label>
      <br />
      {mobileNumberError && <div>{mobileNumberError}</div>}
      <br />
      <div className="button-container">
      <button type="submit">Submit</button>
      <button type="reset">Reset</button>
      </div>
    </form>
    </div>
  );
}

export default Registration;
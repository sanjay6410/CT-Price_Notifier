import axios from "axios";
import React, { useState } from "react";
import './css/CustomerResetPassword.css';

function CustomerResetPassword() {
  const [formData, setFormData] = useState({
    email: "",
    newPassword: "",
  });
  const [passwordError,setPasswordError]=useState("");

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
      setPasswordError("Password must be at least 6 characters long, contain at least one special character, and at least one number.");
      return;
    }
    
    axios
      .post("http://localhost:8080/customerResetPassword", formData)
      .then((response) => {
        console.log(response.data);
        if(response.status === 200){
          alert("Password Reset Successfully");
          window.location.assign("/login");
        }
      })
      .catch((error) => {
        console.error(error);
      });
  };
  const handleHomeButton=(e)=>{
    e.preventDefault();
    window.location.assign("/");
  }

  return (
    <div className="resetPasswordDivMain">
      <h1 className="resetPasswordHeadingH1">Customer Reset Password</h1><br />
      <form onSubmit={handleSubmit} onReset={handleReset} className="resetPasswordForm">
  <div className="form-group-resetPassword">
    <label className="form-label-resetPassword">
      Email:
      <input
        type="email"
        name="email"
        value={formData.email}
        onChange={handleChange}
        className="form-input-resetPassword"
      />
    </label>
  </div>
  <div className="form-group-resetPassword">
    <label className="form-label-resetPassword">
      New Password:
      <input
        type="password"
        name="newPassword"
        value={formData.newPassword}
        onChange={handleChange}
        className="form-input-resetPassword"
      />
    </label>
  </div>
  {passwordError && <div>{passwordError}</div>}
  <br />
  <button type="submit" className="btn btn-success btn-resetPassword" >Reset Password</button>
  <br />
  <button type="reset" className="btn btn-success btn-resetPassword">Reset</button>
  <br />
  <button type="button" onClick={handleHomeButton} className="btn btn-success btn-resetPassword">Home Page</button>
</form>
    </div>
  );
}

export default CustomerResetPassword;

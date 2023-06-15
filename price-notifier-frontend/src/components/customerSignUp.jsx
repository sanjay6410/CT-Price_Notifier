import React from "react";
import { useState } from "react";
import axios from "axios";

function Registration(){

    const [formData, setFormData] =useState({
        firstName:'',
        lastName:'',
        email:'',
        password:'',
        confirmPassword:'',
        mobileNumber:''
    });

    const [passwordError, setPasswordError] = useState('');

    const validatePassword = (password) => {
      const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{10,}$/;
      return passwordRegex.test(password);
    };

    const handleChange= (e) =>{
      const {name,value}=e.target;
      setFormData((prevData)=>({
        ...prevData,
        [name]:value,
      }));
    };

    const handleSubmit= (e) => {
        e.preventDefault();
        if (formData.password !== formData.confirmPassword) {
          alert('Password and Confirm Password do not match');
          return;
        }
        if (!validatePassword(formData.password)) {
          setPasswordError('Password must be at least 10 characters long, contain at least one special character, and at least one number.');
          return;
        }
        axios.post('http://localhost:8080/customerSignUp', formData)
        .then((response) => {
          // Handle the response from the server
          console.log(response.data);
        })
        .catch((error) => {
          // Handle any errors
          console.error(error);
        });
    };

    const handleReset = () => {
      setFormData({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: '',
        mobileNumber: '',
      });
    };

return (
  <form onSubmit={handleSubmit}>
    <label>
        First Name: <input type="text" name="firstName" value={formData.firstName} onChange={handleChange}/>
    </label>
    <br />
    <label>
        Last Name: <input type="text" name="lastName" value={formData.lastName} onChange={handleChange}/>
    </label>
    <br />
    <label>
        Email: <input type="email" name="email" value={formData.email} onChange={handleChange}/>
    </label>
    <br />
    <label>
        Password: <input type="password" name="password" value={formData.password} onChange={handleChange}/>
    </label>
    <br />
    {passwordError && <div>{passwordError}</div>}
    <br />
    <label>
        Confirm Password: <input type="password" name="confirmPassword" value={formData.confirmPassword} onChange={handleChange}/>
    </label>
    <br />
    <label>
        Mobile Number: <input type="text" name="mobileNumber" value={formData.mobileNumber} onChange={handleChange}/>
    </label>
   <br />
    <button type="submit">Submit</button>
      <button type="button" onClick={handleReset}>Reset</button>
  </form>
)
}

export default Registration;
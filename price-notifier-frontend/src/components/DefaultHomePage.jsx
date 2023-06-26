import React from "react";
import './css/DefaultPage.css';

function DefaultPage() {
  const handleLoginClick = () => {
    window.location.assign("/login");
  };

  const handleRegistrationClick = () => {
    window.location.assign("/registerCustomer");
  };

  return (
    <div className="homebtns">
      <button onClick={handleLoginClick} className="btn btn-primary">Login Page</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <button onClick={handleRegistrationClick} className="btn btn-primary">Registration Page</button>
    </div>
  );
}

export default DefaultPage;

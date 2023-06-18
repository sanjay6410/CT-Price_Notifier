import React from "react";
import { Button } from "react-bootstrap";
import './css/DefaultPage.css'

function DefaultPage() {
  const handleLoginClick = () => {
    window.location.assign("/login");
  };

  const handleRegistrationClick = () => {
    window.location.assign("/registerCustomer");
  };

  return (
    <div className="redirect-btns">
      <Button onClick={handleLoginClick}>Login Page</Button>
      <Button onClick={handleRegistrationClick}>Registration Page</Button>
    </div>
  );
}

export default DefaultPage;

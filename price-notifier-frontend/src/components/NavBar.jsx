import { Button } from "bootstrap";
import React from "react";
import "./css/NavBar.css";

function NavBar(){

    const handleShoppingList=(e)=>{
        e.preventDefault();
        window.location.assign("/showShoppingList");
    }
    const handleListOfProducts=(e)=>{
        e.preventDefault();
        window.location.assign("/listProducts");
    }
    const handleLogout=(e)=>{
        e.preventDefault();
        window.localStorage.removeItem("customerId");
        window.location.assign("/");
    }
    const handleUpdateProfile=(e)=>{
      e.preventDefault();
      window.location.assign("/updateCustomerInfo");
    }

    const shouldRenderListOfProductsButton=window.location.pathname !== "/listProducts";
    const shouldRenderShoppingButton=window.location.pathname !== "/showShoppingList";
    const shouldRenderUpdateProfileButton=window.location.pathname !== "/updateCustomerInfo";

    return (
        <div className="navBar">
      <div className="navBarbtns">
        {shouldRenderShoppingButton && (
            <button onClick={handleShoppingList} className="shoppingListbtn btn btn-primary">
            Shopping List
          </button>
        )}

        
        &nbsp; &nbsp; &nbsp;
        {shouldRenderListOfProductsButton && (
          <button onClick={handleListOfProducts} className="btn btn-primary">List Of Products</button>
        )} &nbsp; &nbsp; &nbsp;
        {shouldRenderUpdateProfileButton && (
           <button onClick={handleUpdateProfile} className="btn btn-primary">Update Profile</button>
        )}
       
      </div>
      <div className="logoutButton">
        <button onClick={handleLogout} className="btn btn-danger">Logout</button>
      </div>
    </div>
    );
}
export default NavBar;
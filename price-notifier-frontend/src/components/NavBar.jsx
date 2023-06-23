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

    const shouldRenderListOfProductsButton=window.location.pathname !== "/listProducts";
    const shouldRenderShoppingButton=window.location.pathname !== "/showShoppingList";

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
        )}
      </div>
      <div className="logoutButton">
        <button onClick={handleLogout} className="btn btn-danger">Logout</button>
      </div>
    </div>
    );
}
export default NavBar;
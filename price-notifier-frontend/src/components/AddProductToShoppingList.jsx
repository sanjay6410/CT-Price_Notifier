import axios from "axios";
import React, { useState } from "react";
import { useParams } from 'react-router-dom';
import './css/AddProductToShoppingLists.css';
import NavBar from './NavBar';

function AddProductToShoppingLists() {
  const [customerId, setCustomerId] = useState(localStorage.getItem("customerId"));
  const [quantity, setQuantity] = useState(null);
  const [percentageNumber, setPercentageNumber] = useState(null);
  const { sku } = useParams();
  const {productName }=useParams();
  const [successMessage, setSuccessMessage] = useState("");
  const [redirect, setRedirect] = useState(false);
  const [percentageNumberError, setPercentageNumberError]=useState("");
  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "quantity") {
      setQuantity(value);
    } else if (name === "percentageNumber") {
      setPercentageNumber(value);
    }
  };

  const handleReset = (e) => {
    e.preventDefault();
    setQuantity(null);
    setPercentageNumber(null);
    setPercentageNumberError(null);
  };
  

  const validatePercentageNumber=(percentageNumber)=>{
    const regex = /^(?:[1-9]|[1-9][0-9]|100)?$/;
    return regex.test(percentageNumber);
  }

  const handleSubmit = (e) => {
    e.preventDefault();

    if(!validatePercentageNumber(percentageNumber)){
      setPercentageNumberError("Preffered Percentage Number Should be in Range bettween 1-100");
      return;
    }

    axios
      .post(
        `http://localhost:8080/updateShoppingListAddLineItem?customerId=${customerId}&sku=${sku}&quantity=${quantity}&percentageNumber=${percentageNumber}`
      )
      .then((response) => {
        console.log(response.data);
        if(response.status === 200){
          setSuccessMessage(response.data);
          setRedirect(true);
          setTimeout(()=>{
            window.location.assign("/showShoppingList");
          },2000)
        }else{
          console.log(response.data);
        }
      }).catch((error)=>{
        console.error(error);
      });
  };
  if (redirect) {
    return (
      <div>
        {successMessage && <div>{successMessage}</div>}
        <p>Redirecting to Shopping List page...</p>
      </div>
    );
  }

  return (
    <div>
      <NavBar />
    
    <div className="addProductDiv">
      
      <h1>Add Product To Shopping List</h1>
      <p className="productNameASH">Product Name : {productName}</p>
      <form onSubmit={handleSubmit} onReset={handleReset} className="addProductToSLForm">
        <div className="addProductToSLFormField">
          
        <label className="addProductToSLFormLabel">
          Quantity:
          <input name="quantity" type="number" value={quantity} onChange={handleChange} className="addProductToSLFormInput"/>
        </label>
        </div>
        <br />
        <div className="addProductToSLFormField">
        <label className="addProductToSLFormLabel"> 
          Preferred Discount Percentage:
          <input name="percentageNumber" type="number" value={percentageNumber} onChange={handleChange} className="addProductToSLFormInput"/>
        </label>
        {percentageNumberError && <div>{percentageNumberError}</div>}
        </div>
        <br />
        <button type="submit" className="btn btn-success">Add Product</button>
        <br />
        <button type="reset" className="btn btn-success">Reset</button>
      </form>
    </div>
    </div>
  );
}

export default AddProductToShoppingLists;

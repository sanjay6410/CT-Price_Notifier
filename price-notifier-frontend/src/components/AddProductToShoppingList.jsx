import axios from "axios";
import React, { useState } from "react";

function AddProductToShoppingLists() {
  const [customerId, setCustomerId] = useState(localStorage.getItem("customerId"));
  const [quantity, setQuantity] = useState(null);
  const [percentageNumber, setPercentageNumber] = useState(null);

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
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .post(
        `http://localhost:8080/updateShoppingListAddLineItem?customerId=${customerId}&sku=M0E20000000DQQ8&quantity=${quantity}&percentageNumber=${percentageNumber}`
      )
      .then((response) => {
        console.log(response.data);
      });
  };

  return (
    <div>
      <h1>Add Product To Shopping List</h1>
      <form onSubmit={handleSubmit} onReset={handleReset}>
        <label>
          Quantity:
          <input name="quantity" type="number" value={quantity} onChange={handleChange} />
        </label>
        <label>
          Preferred Discount Percentage:
          <input name="percentageNumber" type="number" value={percentageNumber} onChange={handleChange} />
        </label>
        <button type="submit">Add Product</button>
        <button type="reset">Reset</button>
      </form>
    </div>
  );
}

export default AddProductToShoppingLists;

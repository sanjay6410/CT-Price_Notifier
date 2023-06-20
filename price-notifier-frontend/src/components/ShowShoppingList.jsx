import React, { useState, useEffect } from "react";
import axios from "axios";
import { Table } from "react-bootstrap";


function ShowShoppingList() {
  const [customerId, setCustomerId] = useState(localStorage.getItem("customerId"));
  const [ShoppingListData, setShoppingListData] = useState({
    name: "",
    description: "",
    lineItems: [],
  });
  const [ShoppingList, setShoppingList] = useState(null);
  const [LineItems, setLineItems] = useState(null);
  const [LineItemId, setLineItemId] = useState(null);

  const shoppingListInfo = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/getShoppingListByCustomerId?customerId=${customerId}`);
      console.log(response.data);
      setShoppingList(response.data);
      setLineItems(response.data.lineItems);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    shoppingListInfo();
  }, []);

  const handleRemoveProduct = (e, itemId) => {
    e.preventDefault();
    console.log(itemId);
    axios.post("http://localhost:8080/updateShoppingListRemoveLineItem?customerId="+customerId+"&lineItemId="+itemId)
        .then((response)=>{
            console.log(response.data);
        })
  };

  return (
    <div>
      <h1>Shopping List</h1>
      <h3>Name: {ShoppingList?.name?.en}</h3>
      <p>Description: {ShoppingList?.description?.en}</p>
      {LineItems && LineItems.map((item, index) => (
        <div key={index}>
            {/* {item.productId}
            {item.custom.fields["Percentage-Number"]} */}
            <table class="table table">
  <tbody>
    <tr>
      <th>Product Name</th>
      <td>{item.name.en}</td>
    </tr>
    <tr>
      <th>Quantity</th>
      <td>{item.quantity}</td>
    </tr>
    <tr>
      <th>Percentage Number</th>
      <td>{item.custom.fields["Percentage-Number"]}</td>
    </tr>
  </tbody>
  <button onClick={(e) => handleRemoveProduct(e, item.id)}>Remove Product</button>
  <button>Update Preferred Discount Percentage</button>
</table>



            </div>
      ))}
    </div>
  );
}

export default ShowShoppingList;

import React, { useState, useEffect } from "react";
import axios from "axios";
import { Table } from "react-bootstrap";
import NavBar from "./NavBar";
import './css/ShowShoppingList.css'


function ShowShoppingList() {
  const [customerId, setCustomerId] = useState(localStorage.getItem("customerId"));
  const [ShoppingListData, setShoppingListData] = useState({
    name: "",
    description: "",
    lineItems: [],
  });
  const [ShoppingList, setShoppingList] = useState(null);
  const [LineItems, setLineItems] = useState(null);
  const [ChangePercentageNumber, setChangePercentageNumber] = useState(null);

  const shoppingListInfo = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/getShoppingListByCustomerId?customerId=${customerId}`);
     // console.log(response.data);
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
            console.log(response.status);
            if(response.status === 200){
              alert("Product Removed Successfully.");
              window.location.reload();
            }else{
              alert("Product Not Removed.");
            }
        })
        .catch((error)=>{
         console.error(error);
        })
       
        //window.location.reload();
  };
  const handleUpdatePercentage=(e,itemId)=>{
    e.preventDefault();
    const input=prompt('Enter Preferred Discount Percentage');
    console.log(input);
    axios.post("http://localhost:8080/updateShoppingListChangePercentageNumber?customerId="
                  +customerId+"&percentageNumber="+input+"&lineItemId="+itemId).then((response)=>{
                   // console.log(response.data);
                    if(response.status === 200){
                      alert("Product Updated with Preferred Discount Percentage");
                      window.location.reload();
                    }else{
                      alert("Product Not Updated with Preferred Discount Percentage");
                    }
                  })
                  .catch((error)=>{
                    console.log(error);
                  })
    
  }

  return (
    <div>
      <NavBar />
    <div className="ShowSLMainDiv">
      <h1 className="ShowSLHeadingH1">Shopping List</h1>
      <h4>Name: {ShoppingList?.name?.en}</h4>
      <h4>Description: {ShoppingList?.description?.en}</h4>
      <div className="ShowSLProducts">
      {LineItems && LineItems.map((item, index) => (
        <div key={index} className="ShowSLTableDiv">
            <Table class="table-secondary ShowSLTable">
  <tbody>
    <tr>
      <th className="ShowShoppingListTh">Product Name</th>
      <td className="ShowShoppingListTd">{item.name.en}</td>
    </tr>
    <tr>
      <th className="ShowShoppingListTh">Quantity</th>
      <td className="ShowShoppingListTd">{item.quantity}</td>
    </tr>
    <tr>
      <th className="ShowShoppingListTh">Percentage Number</th>
      <td className="ShowShoppingListTd">{item.custom.fields["Percentage-Number"]}</td>
    </tr>
  </tbody>
  <br />
  <button onClick={(e) => handleRemoveProduct(e, item.id)} className="btn btn-warning">Remove Product</button>&nbsp;&nbsp;&nbsp;
  <br />
  <button onClick={(e)=> handleUpdatePercentage(e, item.id)} className="btn btn-warning">Update Preferred Discount Percentage</button>
  <br />
  <hr />
</Table>



            </div>
      ))}
      </div>
    </div>
    </div>
  );
}

export default ShowShoppingList;

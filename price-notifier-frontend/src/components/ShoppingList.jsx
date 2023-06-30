import axios from "axios";
import React from "react";
import { useState } from "react";
import { useParams } from 'react-router-dom';
import NavBar from "./NavBar";
import './css/ShoppingList.css'
function CreateShoppingList(){

    const[customerId,setCustomerId]=useState(localStorage.getItem("customerId"));
    //alert(customerId);
    const { sku } = useParams();
    const {productName}=useParams();
    const [successMessage, setSuccessMessage] = useState("");
    const [redirect, setRedirect] = useState(false);
    const [shoppingListFormdata, setShoppingListFormData] = useState({
        name: "",
        description: "",
        quantity: "",
        percentageNumber: ""
      });

    const handleReset=()=>{
        setShoppingListFormData({
            name:"",
            description:"",
            quantity:"",
            percentageNumber:""
        });
    };

    const handleChange=(e)=>{
        const{name,value}=e.target;
        setShoppingListFormData((prevData)=>({
            ...prevData,
            [name]:value,
        }));
    };

    const validateDiscountPercentage = (discountPercentage) => {
      const regex = /^(?:100|[1-9][0-9]?)$/;
      return regex.test(discountPercentage);
    };


    const handleSubmit=(e)=>{
        e.preventDefault();

        if(!validateDiscountPercentage(shoppingListFormdata.percentageNumber)){
          alert("Preferred Discount Percentage Should be less than 100");
          return "";
        }

        axios.post("http://localhost:8080/createShoppingLists?custId="+customerId+"&sku="+sku,shoppingListFormdata)
          .then((response)=>{
            console.log(response);
            if(response.status === 200){
              setSuccessMessage("Shopping List Created.");
              setRedirect(true);
              setTimeout(()=>{
                window.location.assign("/showShoppingList");
              },2000)
            }else{
              alert("Shopping List Not Created");
            }
          }).catch((error)=>{
            console.log(error);
          })
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
         <div className="ShoppingListMainDiv">
            <h1 className="headingH1ShoppingList">Create Shopping Lists</h1>
            <p className="productNameSL">Product Name : {productName}</p>
            <form onSubmit={handleSubmit} onReset={handleReset} className="ShoppingListForm">
              <label className="ShoppingListFormLabel">
                Name Of the Shopping list :
                <input type='text' name="name" onChange={handleChange} value={shoppingListFormdata.name} className="ShoppingListFormInput" />
              </label>
              <br />
              <label className="ShoppingListFormLabel">
                Description :
                &nbsp;<input type='text' name="description" onChange={handleChange} value={shoppingListFormdata.description} className="ShoppingListFormInput"/>
              </label>
              <br />
              <label className="ShoppingListFormLabel">
                Quantity:
                &nbsp;<input type='number' name="quantity" onChange={handleChange} value={shoppingListFormdata.quantity} className="ShoppingListFormInput"/>
              </label>
              <br />
              <label className="ShoppingListFormLabel">
                Preferred Discount Percentage:
                &nbsp;<input type='number' name="percentageNumber" onChange={handleChange} value={shoppingListFormdata.percentageNumber} className="ShoppingListFormInput"/>
              </label>
              <br/>
              <button type="submit" className="ShoppingListbtns btn btn-success">Create Shopping List</button>
              <br />
              <button type="reset" className="ShoppingListResetbtn btn btn-success">Reset</button>

            </form>
         </div>
         </div>
    );
};
export default CreateShoppingList;
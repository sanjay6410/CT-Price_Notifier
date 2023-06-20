import axios from "axios";
import React from "react";
import { useState } from "react";
function CreateShoppingList(){

    const[customerId,setCustomerId]=useState(localStorage.getItem("customerId"));
    //alert(customerId);

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


    const handleSubmit=(e)=>{
        e.preventDefault();
        axios.post("http://localhost:8080/createShoppingLists?custId="+customerId+"&sku=M0E20000000E2Q7",shoppingListFormdata)
          .then((response)=>{
            console.log(response);
          })
    };
    return (
         <div>
            <h1>Create Shopping Lists</h1>
            <form onSubmit={handleSubmit} onReset={handleReset}>
              <label>
                Name Of the Shopping list :
                &nbsp;<input type='text' name="name" onChange={handleChange} value={shoppingListFormdata.name}/>
              </label>
              <label>
                Description :
                &nbsp;<input type='text' name="description" onChange={handleChange} value={shoppingListFormdata.description}/>
              </label>
              <label>
                Quantity:
                &nbsp;<input type='number' name="quantity" onChange={handleChange} value={shoppingListFormdata.quantity}/>
              </label>
              <label>
                Preferred Discount Percentage:
                &nbsp;<input type='number' name="percentageNumber" onChange={handleChange} value={shoppingListFormdata.percentageNumber}/>
              </label>
              <button type="submit">Create Shopping List</button>
              <button type="reset">Reset</button>

            </form>
         </div>
    );
};
export default CreateShoppingList;
import React from "react";

function StoreSelection(){
    const handleUsStore=(e)=>{
        window.location.assign("/listProducts"+"/US")
    }
    const handleDeStore=(e)=>{
        window.location.assign("/listProducts"+"/DE")
    }
    return (
        <div>
            <h1>Store Selection </h1>
            <button onClick={handleUsStore}>US STORE</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button onClick={handleDeStore}>DE(Germany) STORE </button>
        </div>
    );
}
export default StoreSelection;
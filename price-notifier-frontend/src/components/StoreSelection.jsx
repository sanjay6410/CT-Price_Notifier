import React from "react";
import './css/StoreSelection.css';

function StoreSelection(){
    const handleUsStore=(e)=>{
        window.location.assign("/listProducts"+"/US")
        window.localStorage.setItem("storeSelected","US")
    }
    const handleDeStore=(e)=>{
        window.location.assign("/listProducts"+"/DE")
        window.localStorage.setItem("storeSelected","DE")
    }
    return (
        <div className="storeSelectionMainDiv">
            <h1>Store Selection </h1>
            <div className="storeSelectionBtns">
            <button onClick={handleUsStore} className="btn btn-warning">US STORE</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button onClick={handleDeStore} className="btn btn-warning">DE(Germany) STORE </button>
            </div>
        </div>
    );
}
export default StoreSelection;
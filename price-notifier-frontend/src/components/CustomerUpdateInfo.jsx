import React, { useState } from "react";

function EditProfile(){

    const [formData,setFormData]=useState({
        country:"",
        streetName:"",
        streetNumber:"",
        building:"",
        city:"",
        region:"",
        state:"",
        company:"",
        department:"",
        apartment:"",
        pOBox:""
    });
    const[selectedDate,setSelectedDate]=useState(null);
    const[email,setEmail]=useState(null);

    const handleReset=()=>{
        setFormData({
            country:"",
            streetName:"",
            streetNumber:"",
            building:"",
            city:"",
            region:"",
            state:"",
            company:"",
            department:"",
            apartment:"",
            pOBox:""
        });
        setEmail("");
        setSelectedDate("");
    };

    return(
     <div>
        <h1>Profile Update</h1>
        <form>

        </form>
     </div>
    );
};
export default EditProfile;
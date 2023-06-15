import React from "react";
import {Form} from "react-bootstrap";
import axios from "axios";

class Login extends React.Component{

    constructor(props){
        super(props);
        this.state= this.initiaLSTATE;
        this.state={
            customer:[]
        };
    }
    initiaLSTATE={
        email:"",
        password:""
    };
    
    handlesubmit= (event) => {
       event.preventDefault()
        const customer={
            email:this.state.email,
            password:this.state.password
        }
        this.state=this.initiaLSTATE    
        axios.post("http://localhost:8080/login", customer)
        .then(response => {
          if (response.status === 200) {
            console.log(response.data); // Access the response data
            alert("Login successful"); // Display success alert
          }
        })
        .catch(error => {
       
        //   alert("An error occurred"); // Display generic error alert
          
        //   if (error.response.status === 400) {
            console.log(error.response.data); // Access the response data in case of error
            alert("Invalid credentials"); // Display invalid credentials alert
        //   }
        });
      
      
      

    }
 change = (event) => {
        this.setState({
          [event.target.name]: event.target.value,
        });
      };

    render(){
        

       
        return(
      
        
        
 <Form onSubmit={this.handlesubmit}>


         <div>
           <h1>Login Page</h1>
           <div>
      
             <input type="text" placeholder="Email" className="name" name="email" pattern="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$" required value={this.state.email} onChange={this.change}/>
           </div>
           <div className="second-input">
   
             <input type="password" placeholder="Password" className="name" name="password" pattern="[A-Za-z0-9#@$&]{3,10}" required  value={this.state.password} onChange={this.change}/>
           </div>
          <div className="login-button">
          <button>Login</button>
        
            
           
 
         </div>
       </div>
       


    </Form>      
          
        );
}
}

export default Login;
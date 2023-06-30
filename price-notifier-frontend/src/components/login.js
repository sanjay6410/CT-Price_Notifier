import React from "react";
import { Form } from "react-bootstrap";
import axios from "axios";
import './css/login.css'

class Login extends React.Component {
   constructor(props) {
    super(props);
    this.state = this.initialState;
    this.state = {
      customer: [],
      successMsg: "",
      errorMsg: "",
    };
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const customer = {
      email: this.state.email,
      password: this.state.password
    };

    axios.post("http://localhost:8080/login", customer)
      .then(response => {
        if (response.status === 200) {
          const customerId = response.data; // Get the customer ID from the response data
          console.log(customerId);
          localStorage.setItem("customerId",customerId);
          
          console.log(response.status);
          this.setState({
            successMsg: "Login successful",
            errorMsg: "",
          });
          window.location="/storeSelection";
        }
      })
      .catch(error => {
        console.log(error)
        this.setState({
          successMsg: "",
          errorMsg: "Invalid credentials",
        }); // Display invalid credentials alert
      });

    this.setState({
      email: "",
      password: ""
    });
  };

  handleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };
  handleHomePage=(e)=>{
    e.preventDefault();
    window.location.assign("/");
  }
  handleResetPassword=(e)=>{
    e.preventDefault();
    window.location.assign("/resetPassword");
  }

  render() {
    return (
      <div className="LoginMainDiv">
      <Form onSubmit={this.handleSubmit}>
        <center>
        <div>
          <h1 className="LoginHeadingH1">Login Page</h1>
          <div className="LoginInputBtnDiv">
            <input
              type="text"
              placeholder="Email"
              className="name"
              name="email"
              pattern="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"
              required
              value={this.state.email}
              onChange={this.handleChange}
            />
          </div>
          <div className="second-input LoginInputBtnDiv">
            <input
              type="password"
              placeholder="Password"
              className="name"
              name="password"
             
              required
              value={this.state.password}
              onChange={this.handleChange}
            />
          </div>
          <div className="login-button">
            <button className="btn btn-success loginbtn" >Login</button><br /><br />
            <button onClick={this.handleHomePage} className="btn btn-success homePageBtn">Home Page</button><br /><br />
            <button onClick={this.handleResetPassword} className="btn btn-success resetPasswordBtn">Forget Password</button><br /><br />
          </div>
       
          <div className="message">
            {this.state.successMsg && (
              <div className="success">{this.state.successMsg}</div>
            )}
            {this.state.errorMsg && (
              <div className="error">{this.state.errorMsg}</div>
            )}
          </div>
        </div>
        </center>
      </Form>
      </div>
    );
  }
}

export default Login;

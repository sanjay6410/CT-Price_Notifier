import React from "react";
import { Form } from "react-bootstrap";
import axios from "axios";

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
          window.location="/addProducts/"+customerId;
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

  render() {
    return (
      <Form onSubmit={this.handleSubmit}>
        <center>
        <div>
          <h1>Login Page</h1>
          <div>
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
          <div className="second-input">
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
            <button>Login</button>
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
    );
  }
}

export default Login;

import React, { useState } from "react";
import axios from "axios";
import { Button, Form, Table } from "react-bootstrap";
import "./css/productlist.css";
import NavBar from "./NavBar";
const TableRow = ({ product }) => {
  const handleClick = () => {
    // Redirect to another page
    const productName = product.masterData.current.name.en;
const sanitizedProductName = productName.replace(/[^\w\s]/gi, '').replace(/\s+/g, '-');
window.location = "/product/" + product.id + "/" + sanitizedProductName;

  };

  return (
    <tr onClick={handleClick}>
      <td>{product.masterData.current.name.en}</td>
      <td>{product.key}</td>
      <td>{product.masterData.published ? "published" : "modified"}</td>
      <td>{product.lastModifiedAt}</td>
      <td>{product.createdAt}</td>
    </tr>
  );
};

class ProductList extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      products: [],
      search: "",
      errorMsg: "",
    };
  }

  componentDidMount() {
    this.fetchProducts();
  }

  fetchProducts = () => {
    axios
      .get("http://localhost:8080/listProducts")
      .then((response) => {
        const data = response.data;
        this.setState({ products: data });
      })
      .catch((error) => {
        console.error("Error fetching products:", error);
        
       
      });
  };

  handleSearch = (event) => {
    event.preventDefault();
    const sku = document.getElementById("search").value;
 

    axios
      .get("http://localhost:8080/listProductsBySku?sku=" + sku)
      .then((response) => {
        const data = response.data;
        console.log(data)
        this.setState({ products: data, errorMsg: "" });
        if(data.length===0){
            this.setState({
                errorMsg: "Variant id is invalid ",
              });
        }
      })
      .catch((error) => {
        console.error("Error fetching products:", error);
      });
  };
  

  handleInputChange = (event) => {
    this.setState({ search: event.target.value });
  };

  render() {
    const { products, errorMsg} = this.state;

    return (
      <div>
        <NavBar />
      
      <Form>
        <h1 className="ProductListHeadingH1">List products</h1>
        <label htmlFor="search" className="ProductListLabel">Search By Sku</label>
        <input
          type="text"
          id="search"
          value={this.state.search}
          onChange={this.handleInputChange}
          className="ProductListInput"
        />
        <br/>
        <Button className="button ProductListButton" variant="primary" align="right" onClick={this.handleSearch}>
          Search
        </Button>
        <br/>
        {errorMsg && <div className="error">{errorMsg}</div>}
      {products.length > 0 && (
       
        <Table className="table">
          <thead>
            <tr>
              <th>Product Name</th>
              <th>Product Key</th>
              <th>Status</th>
              <th>Date Modified</th>
              <th>Date Created</th>
            </tr>
          </thead>
          <tbody>
            {products.map((product) => (
              <TableRow key={product.id} product={product} />
            ))}
          </tbody>
        
        </Table>
      )}
      </Form>
      </div>
    );
  }
}

export default ProductList;

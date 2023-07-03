import React, { Component } from "react";
import axios from "axios";
import { Button, Form, Table } from "react-bootstrap";
import "./css/productlist.css";
import NavBar from "./NavBar";
import { useParams } from "react-router-dom";

function withParams(Component) {
  return (props) => {
    const params = useParams();
    return <Component {...props} params={params} />;
  };
}

class TableRow extends Component {
  handleClick = () => {
    const { countryCode } = this.props.params;
    console.log(countryCode)
    const { product } = this.props;
    const productName = product.masterData.current.name.en;
    const sanitizedProductName = productName.replace(/[^\w\s]/gi, "").replace(/\s+/g, "-");
    window.location = `/product/${product.id}/${sanitizedProductName}/${countryCode}`;
  };

  render() {
    const { product } = this.props;

    return (
      <tr onClick={this.handleClick}>
        <td>
          {product.masterData.current.masterVariant.images.length > 0 && (
            <img
              src={product.masterData.current.masterVariant.images[0].url}
              alt="Product"
              style={{ width: "100px", height: "100px" }}
            />
          )}
        </td>
        <td>{product.masterData.staged.name.en}</td>
        <td>{product.lastModifiedAt}</td>
        <td>{product.createdAt}</td>
      </tr>
    );
  }
}

class ProductList extends Component {
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
        console.log(data);
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
        console.log(data);
        this.setState({ products: data, errorMsg: "" });
        if (data.length === 0) {
          this.setState({
            errorMsg: "Variant id is invalid",
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
    const { products, errorMsg } = this.state;

    return (
      <div>
        <NavBar />
        <Form>
          <h1 className="ProductListHeadingH1">List products</h1>
          <label htmlFor="search" className="ProductListLabel">
            Search By Sku
          </label>
          <input
            type="text"
            id="search"
            value={this.state.search}
            onChange={this.handleInputChange}
            className="ProductListInput"
          />
          <br />
          <div>
            <Button
              className="button ProductListButton"
              variant="primary"
              align="right"
              onClick={this.handleSearch}
            >
              Search
            </Button>
            <br />
          </div>
          <br />

          {errorMsg && <div className="error">{errorMsg}</div>}
          {products.length > 0 && (
            <Table className="table">
              <thead>
                <tr>
                  <th>Image</th>
                  <th>Product Name</th>
                  <th>Date Modified</th>
                  <th>Date Created</th>
                </tr>
              </thead>
              <tbody>
                {products.map((product) => (
                  <TableRow key={product.id} product={product} params={this.props.params} />
                ))}
              </tbody>
            </Table>
          )}
        </Form>
      </div>
    );
  }
}

export default withParams(ProductList);

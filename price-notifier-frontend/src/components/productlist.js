import React from "react";
import axios from "axios";
import { Form, Table } from "react-bootstrap";

class ProductList extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      products: []
    };
  }

  componentDidMount() {
    axios
      .get("http://localhost:8080/listProducts")
      .then(response => {
        const data = response.data;
        this.setState({ products: data });
      })
      .catch(error => {
        console.error("Error fetching products:", error);
      });
  }

  render() {
    return (
      <Form>
        <h1>List products</h1>
        <Table bordered>
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
            {this.state.products.map(product => (
              <tr key={product.key}>
                <td>{product.masterData.current.name.en}</td>
                <td>{product.key}</td>
                <td>
                  {product.masterData.published ? "published" : "modified"}
                </td>
                <td>{product.lastModifiedAt}</td>
                <td>{product.createdAt}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </Form>
    );
  }
}

export default ProductList;

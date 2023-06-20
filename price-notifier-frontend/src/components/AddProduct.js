import React from "react";
import axios from "axios";
import { Form, Button } from "react-bootstrap";

class AddProduct extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      productTypes: [],
      selectedOption: "",
      selectedProductType: null,
      productName: "",
      productDescription: "",
      productSlug: "",
      productKey: "",
      sku: "",
      variant: "",
      successMsg: "",
      errorMsg: "",
    };
  }

  componentDidMount() {
    axios
      .get("http://localhost:8080/selectProductTypes")
      .then((response) => response.data.body.results)
      .then((data) => {
        this.setState({ productTypes: data });
      })
      .catch((error) => {
        console.error(error);
      });
  }

  handleProductTypeChange = (event) => {
    const selectedProductId = event.target.value;
    const selectedProductType = this.state.productTypes.find(
      (productType) => productType.id === selectedProductId
    );
    this.setState({ selectedOption: selectedProductId, selectedProductType });
  };

  handleNameChange = (event) => {
    this.setState({ productName: event.target.value });
  };

  handleSlugChange = (event) => {
    this.setState({ productSlug: event.target.value });
  };

  handleDescriptionChange = (event) => {
    this.setState({ productDescription: event.target.value });
  };

  handleKeyChange = (event) => {
    this.setState({ productKey: event.target.value });
  };

  handleskuChange = (event) => {
    this.setState({ sku: event.target.value });
  };

  handlevariantChange = (event) => {
    this.setState({ variant: event.target.value });
  };

  handleAddProduct = () => {
    const {
      selectedProductType,
      productName,
      productDescription,
      productKey,
      sku,
      variant,
      productSlug
    } = this.state;

    const payload = {
      productType: {
        id: selectedProductType.id
      },
      name: productName,
      description: productDescription,
      variantKey: productKey,
      sku: sku,
      variant: variant,
      slug: productSlug
    };

    axios
      .post("http://localhost:8080/addproduct", payload)
      .then((response) => {
        console.log(response.data.id);
        this.setState({
            successMsg: "Product added successful",
            errorMsg: "",
          });
      })
      .catch((error) => {
        console.error(error);
        this.setState({
            successMsg: "",
            errorMsg: "Product is not added provide crct details ",
          });
      });
  };

  render() {
    const {
      selectedProductType,
      productName,
      productDescription,
      productKey,
      sku,
      variant,
      productSlug
    } = this.state;

    return (
      <Form>
        <h1>Add products</h1>
        <select
          name="productTypes"
          value={this.state.selectedOption}
          onChange={this.handleProductTypeChange}
          required
        >
          <option value="" disabled>
            Select productType
          </option>
          {this.state.productTypes.map((productType) => (
            <option key={productType.id} value={productType.id}>
              {productType.name}
            </option>
          ))}
        </select>

        {selectedProductType && (
          <div>
            <h2>Selected Product Type Details</h2>
            <p>ID: {selectedProductType.id}</p>
            <p>Name: {selectedProductType.name}</p>
            <p>Description: {selectedProductType.description}</p>
            <p>Number of Attributes: {selectedProductType.attributes.length}</p>

            <h2>Add Product</h2>
            <Form.Group>
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                value={productName}
                onChange={this.handleNameChange}
                required
              />
            </Form.Group>
            <Form.Group>
              <Form.Label>Slug</Form.Label>
              <Form.Control
                type="text"
                value={productSlug}
                onChange={this.handleSlugChange}
                required
              />
            </Form.Group>
            <Form.Group>
              <Form.Label>Description</Form.Label>
              <Form.Control
                type="text"
                value={productDescription}
                onChange={this.handleDescriptionChange}
              />
            </Form.Group>
            <Form.Group>
              <Form.Label>Key</Form.Label>
              <Form.Control
                type="text"
                value={productKey}
                onChange={this.handleKeyChange}
              />
            </Form.Group>
            <h2>Add Variant</h2>
            <Form.Group>
              <Form.Label>SKU</Form.Label>
              <Form.Control
                type="text"
                value={sku}
                onChange={this.handleskuChange}
              />
            </Form.Group>
            <Form.Group>
              <Form.Label>Variant key</Form.Label>
              <Form.Control
                type="text"
                value={variant}
                onChange={this.handlevariantChange}
              />
            </Form.Group>
            <Button variant="primary" onClick={this.handleAddProduct}>
              Add Product
            </Button>
            <div className="message">
            {this.state.successMsg && (
              <div className="success">{this.state.successMsg}</div>
            )}
            {this.state.errorMsg && (
              <div className="error">{this.state.errorMsg}</div>
            )}
            </div>
          </div>
          
        )}
      </Form>
    );
  }
}

export default AddProduct;

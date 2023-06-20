import React from "react";
import axios from "axios";
import { Button, Form, Table } from "react-bootstrap";
import { useParams } from "react-router";

function withParams(Component) {
  return (props) => <Component {...props} params={useParams()} />;
}

class Variants extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      variants: [],
      minPrice: null, // Added state variable for minimum price
    };
  }

  componentDidMount() {
    let { id } = this.props.params;
    axios
      .get("http://localhost:8080/getVariantsOfTheProduct?id=" + id)
      .then((response) => {
        const data = response.data;
        this.setState({ variants: data });
        this.calculateMinPrice(data); // Calculate minimum price
      })
      .catch((error) => {
        console.error("Error fetching variants:", error);
      });
  }

  calculateMinPrice(variants) {
    if (variants.length > 0) {
      const minPrice = variants.reduce((min, variant) => {
        const centAmount = variant.prices[0].value.centAmount;
        return centAmount < min ? centAmount : min;
      }, Infinity);
  
      this.setState({ minPrice: (minPrice / 100).toFixed(2) });
    }
  }


  render() {
    return (
      <Form>

        <h1>List Variants</h1>
        <Table bordered>
          <thead>
            <tr>
              <th>Variant Id</th>
              <th>SKU</th>
              <th>Key</th>
              <th>Pricing From</th>
            </tr>
          </thead>
          <tbody>
            {this.state.variants.map((variant) => (
              <tr key={variant.id}>
                <td>{variant.id}</td>
                <td>{variant.sku}</td>
                <td>{variant.key}</td>
                <td>{this.state.minPrice}</td>
                <td> <Button variant="primary" align="Right" onClick={this.handleAddShoppingList}>
             Add To Shopping List
            </Button></td>
              </tr>
            ))}
          </tbody>
          
        </Table>
       
           
      </Form>
    );
  }
}

export default withParams(Variants);

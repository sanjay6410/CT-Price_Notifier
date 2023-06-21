import React, { useState, useEffect } from "react";
import axios from "axios";
import { Button, Form, Table } from "react-bootstrap";
import { useParams } from "react-router-dom";

function withParams(Component) {
  return (props) => {
    const params = useParams();
    return <Component {...props} params={params} />;
  };
}

function Variants(props) {
  const { id } = props.params;
  const [variants, setVariants] = useState([]);
  const [minPrice, setMinPrice] = useState(null);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/getVariantsOfTheProduct?id=${id}`)
      .then((response) => {
        const data = response.data;
        setVariants(data);
        calculateMinPrice(data);
      })
      .catch((error) => {
        console.error("Error fetching variants:", error);
      });
  }, [id]);

  const calculateMinPrice = (variants) => {
    if (variants.length > 0) {
      const minPrice = variants.reduce((min, variant) => {
        const centAmount = variant.prices[0].value.centAmount;
        return centAmount < min ? centAmount : min;
      }, Infinity);

      setMinPrice((minPrice / 100).toFixed(2));
    }
  };

  const handleAddShoppingList = (sku) => {
    const customerId = window.localStorage.getItem("customerId");
    console.log(sku);
    axios
      .get(`http://localhost:8080/checkShoppingListExists?customerId=${customerId}`)
      .then((response) => {
        console.log(response.data);
        if (response.data === "Exists") {
          window.location.assign(`/addProductToShoppingLists/${sku}`);
        } else {
          window.location.assign(`/createShoppingList/${sku}`);
        }
      });
  };

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
          {variants.map((variant) => (
            <tr key={variant.id}>
              <td>{variant.id}</td>
              <td>{variant.sku}</td>
              <td>{variant.key}</td>
              <td>{minPrice}</td>
              <td>
                <Button
                  variant="primary"
                  align="right"
                  onClick={() => handleAddShoppingList(variant.sku)}
                >
                  Add To Shopping List
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </Form>
  );
}

export default withParams(Variants);

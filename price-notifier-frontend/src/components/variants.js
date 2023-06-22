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

  useEffect(() => {
    axios
      .get(`http://localhost:8080/getVariantsOfTheProduct?id=${id}`)
      .then((response) => {
        const data = response.data;
        setVariants(data);
      })
      .catch((error) => {
        console.error("Error fetching variants:", error);
      });
  }, [id]);

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
  const formatCentAmount = (centAmount, currencyCode) => {
    const symbol = currencyCode === "EUR" ? "€" : "US$";
    return symbol + (centAmount / 100).toFixed(2);
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
            <th>Cent Amount</th>
            <th>Pricing From</th>
          </tr>
        </thead>
        <tbody>
        {variants.map((variant) => {
            const price = variant.prices.length > 0 ? variant.prices[0] : null;
            const centAmount = price ? price.value.centAmount : null;
            const currencyCode = price ? price.value.currencyCode : null;
            const formattedPrice = centAmount
              ? formatCentAmount(centAmount, currencyCode)
              : null;
            return (
              <tr key={variant.id}>
                <td>{variant.id}</td>
                <td>{variant.sku}</td>
                <td>{variant.key}</td>
                <td>{formattedPrice}</td>
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
            );
          })}
        </tbody>
      </Table>
    </Form>
  );
}

export default withParams(Variants);

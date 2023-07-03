import React, { useState, useEffect } from "react";
import axios from "axios";
import { Button, Form, Table } from "react-bootstrap";
import { useParams } from "react-router-dom";
import NavBar from "./NavBar";
import "./css/Variants.css";

function withParams(Component) {
  return (props) => {
    const params = useParams();
    return <Component {...props} params={params} />;
  };
}

function Variants(props) {
  const { id, productName, countryCode } = props.params;
  const [variants, setVariants] = useState([]);
  const [priceVariants, setPriceVariants] = useState({});

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
    axios
      .get(`http://localhost:8080/checkShoppingListExists?customerId=${customerId}`)
      .then((response) => {
        if (response.data === "Exists") {
          window.location.assign(`/addProductToShoppingLists/${sku}/${productName}`);
        } else {
          window.location.assign(`/createShoppingList/${sku}/${productName}`);
        }
      })
      .catch((error) => {
        console.error("Error checking shopping list:", error);
      });
  };

  const formatCentAmount = (centAmount, currencyCode) => {
    const symbol = currencyCode === "EUR" ? "€" : "US$";
    return symbol + (centAmount / 100).toFixed(2);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        let modifiedCountryCode = countryCode;
        if (modifiedCountryCode === "US") {
          modifiedCountryCode = "USD";
        } else if (modifiedCountryCode === "DE") {
          modifiedCountryCode = "EUR";
        }

        const priceData = {};
        for (const variant of variants) {
          const response = await axios.post(
            `http://localhost:8080/getPriceForProduct?sku=${variant.sku}&currencyCode=${modifiedCountryCode}`
          );
          priceData[variant.sku] = response.data;
        }
        setPriceVariants(priceData);
      } catch (error) {
        console.error("Error fetching price variants:", error);
      }
    };

    fetchData();
  }, [countryCode, variants]);

  return (
    <div className="VariantsDivMain">
      <NavBar />
      <Form>
        <h1>List Variants</h1>
        <Table bordered>
          <thead>
            <tr>
              <th>Variant Id</th>
              <th>SKU</th>
              <th>Key</th>
              <th>Cent Amount</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {variants.map((variant) => {
              const price = priceVariants[variant.sku];
              const formattedPrice = price ? formatCentAmount(price.value.centAmount, price.value.currencyCode) : null;

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
    </div>
  );
}

export default withParams(Variants);

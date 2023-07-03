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
              {/* <th>Price Variant</th> */}
              <th>Action</th>
            </tr>
          </thead>
         <tbody>
  {variants.map((variant) => {
    const fetchData = async () => {
          if(countryCode=="US"){
            countryCode==="USD"
          }
          if(countryCode==="DE")
          {
            countryCode="EUR"
          }
            
              for (const variant of variants) {
                try {
                  const response = await axios.get(
                    `http://localhost:8080/getPriceForProduct?sku=${variant.sku}&currencyCode=${countryCode}`
                  );
                  setPriceVariants(response.data);
                } catch (error) {
                  console.error(`Error fetching price for variant ${variant.sku}:`, error);
               
                }
              }
              
            };
            fetchData();
            {priceVariants.map((pricevariant) => {
              const price = pricevariant.prices.length > 0 ? pricevariant.prices[0] : null;
              const centAmount = price ? price.value.centAmount : null;
              const currencyCode = price ? price.value.currencyCode : null;
              const formattedPrice = centAmount
                ? formatCentAmount(centAmount, currencyCode)
                : null;
           
   
    return (
      <tr key={pricevariant.id}>
        <td>{pricevariant.id}</td>
        <td>{pricevariant.sku}</td>
        <td>{pricevariant.key}</td>
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
  });
}
  })}
</tbody>

        </Table>
      </Form>
    </div>
  );
}

export default withParams(Variants);

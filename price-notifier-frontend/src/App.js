
import './App.css';
import Login from './components/login';
import AddProduct from './components/AddProduct'
import Registration from './components/customerSignUp';
import CustomerResetPassword from './components/CustomerRestPassword';
import CustomerChangePassword from './components/CustomerChangePassword';
import EditProfile from './components/CustomerUpdateInfo';
import { BrowserRouter,Route,Routes, } from "react-router-dom"
import DefaultPage from './components/DefaultHomePage';
import CreateShoppingList from './components/ShoppingList';
import ShowShoppingList from './components/ShowShoppingList';
import AddProductToShoppingLists from './components/AddProductToShoppingList';
import NavBar from './components/NavBar';
import StoreSelection from './components/StoreSelection';


import ProductList from './components/productlist';
import Variants from './components/variants';
function App() {

  return (
    <div>
    <BrowserRouter>
    <Routes>
    <Route path='/login' element={<Login/>} />
    <Route path='/registerCustomer' element={<Registration/>} />
    <Route path='/resetPassword' element={<CustomerResetPassword/>} />
    <Route path='/changePassword' element={<CustomerChangePassword />} />
    <Route path='/updateCustomerInfo' element={<EditProfile/>} />
    <Route path='/createShoppingList/:sku/:productName' element={<CreateShoppingList/>} />
    <Route path='/showShoppingList' element={<ShowShoppingList/>} />
    <Route path='/addProductToShoppingLists/:sku/:productName' element={<AddProductToShoppingLists/>} />
    <Route path='/storeSelection' element={<StoreSelection/>} />
    <Route path='/' element={<DefaultPage/>}></Route>
     <Route path='/addProducts/:id' element={<AddProduct/>}/> 
     <Route path='/listProducts/:countryCode' element={<ProductList/>}/>
     <Route path='/navbar' element={<NavBar/>}/>
 
     <Route path='/product/:id/:productName/:countryCode' element={<Variants/>}/>
    </Routes>
     </BrowserRouter>
     </div>
  );

  }
export default App;

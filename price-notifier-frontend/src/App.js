
import './App.css';
import Login from './components/login';
import AddProduct from './components/AddProduct'
import Registration from './components/customerSignUp';
import CustomerResetPassword from './components/CustomerRestPassword';
import CustomerChangePassword from './components/CustomerChangePassword';
import { BrowserRouter,Route,Routes, } from "react-router-dom"
import DefaultPage from './components/DefaultHomePage';
function App() {

  return (
    <div>
    <BrowserRouter>
    <Routes>
    <Route path='/login' element={<Login/>} />
    <Route path='/registerCustomer' element={<Registration/>} />
    <Route path='/resetPassword' element={<CustomerResetPassword/>} />
    <Route path='/changePassword' element={<CustomerChangePassword />} />
    <Route path='/' element={<DefaultPage/>}></Route>
     <Route path='/addProducts/:id' element={<AddProduct/>}/> 
    </Routes>
     </BrowserRouter>
     </div>
  );

  }
export default App;

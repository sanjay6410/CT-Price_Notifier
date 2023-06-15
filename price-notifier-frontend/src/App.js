
import './App.css';
import Login from './components/login';
import AddProduct from './components/AddProduct'
import { BrowserRouter,Route,Routes, } from "react-router-dom"
function App() {

  return (
    <div>
    <BrowserRouter>
    <Routes>
    <Route path='/Login' element={<Login/>} />

     <Route path='/addProducts/:id' element={<AddProduct/>}/> 
    </Routes>
     </BrowserRouter>
     </div>
  );

  }
export default App;

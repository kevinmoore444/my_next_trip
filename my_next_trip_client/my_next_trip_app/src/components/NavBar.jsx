import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import {useContext } from 'react';
import UserAuth from '../context/UserAuth';
import 'bootstrap/dist/css/bootstrap.min.css'; // Ensure Bootstrap CSS is included
import Logo from "../assets/my_next_trip_logo.png";
import "../styles/nav.css";

    


    const NavigationBar = () => {
        const auth = useContext(UserAuth);
        const navigate = useNavigate();

        const handleLogout = async () => {
            auth.logout()
            navigate('/')
        }


        return (
        <nav className="navbar navbar-expand-lg navbar-dark navbar-custom">
            <div className="container-fluid">
            <Link className="navbar-brand" to="/"> <img  src={Logo} alt="logo" style={{ width: '100px', height: 'auto' }} /></Link>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarNav">
                <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                <li className="nav-item">
                    <Link className="nav-link" to="/browse-countries">Browse</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/top20">Top 20</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/mybucketlist">My Bucket List</Link>
                </li>
                </ul>
                <ul className="navbar-nav">
                {/* Conditionally render Admin: Add-Content button */}
                {auth.user && auth.user.hasRole('ADMIN') && (
                            <li className="nav-item">
                                <Link className="btn nav-link" to={"/add"}>Admin: Add Content</Link>
                            </li>
                )}
                {/* If User logged in display log out button, and vice versa */}
                {auth.user ? (
                        <li className="nav-item">
                            <button className="btn nav-link" onClick={handleLogout}>Log Out</button>
                        </li>
                    ) : (
                        <li className="nav-item">
                            <Link className="nav-link" to="/login">Log In</Link>
                        </li>
                    )}
                </ul>
            </div>
            </div>
        </nav>
        );
    };
    
    export default NavigationBar;



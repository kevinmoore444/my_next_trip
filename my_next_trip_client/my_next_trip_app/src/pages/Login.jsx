import NavBar from "../components/NavBar";
import Footer from "../components/Footer";
import { useState, useContext } from 'react';
import {useNavigate, Link, useLocation } from 'react-router-dom';
import Error from "../components/Error";
import UserAuth from '../context/UserAuth';
import '../styles/login.css'

const AuthForms = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errors, setErrors] = useState([]);
    const navigate = useNavigate();
    const location = useLocation();
    const auth = useContext(UserAuth);

    // Check for registration success message
    //If Location state exists and registration success was true...will conditionally render success message
    const registrationSuccess = location.state?.registrationSuccess || false;



    const handleSubmit = async (event) => {
        event.preventDefault();

        const response = await fetch("http://localhost:8080/authenticate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                username,
                password,
            }),
        });

        if (response.status === 200) {
            const { jwt_token } = await response.json();
            console.log(jwt_token);
            auth.login(jwt_token);
            navigate("/browse-countries");
        } else if (response.status === 403) {
            setErrors(["Login failed."]);
        } else {
            setErrors(["Unknown error."]);
        }
    };

    return (
        <>
            <NavBar/>
            <div className="container my-5">
                <div className="row justify-content-center">
                    <div className="col-md-6 container-style">
                        {/* Login Form */}
                        <div className="card mb-4">
                            <div className="card-header">Login</div>
                            <div className="card-body">
                                {registrationSuccess && (
                                    <div className="alert alert-success" role="alert">
                                        Registration successful! Please log in.
                                    </div>
                                )}
                                {errors.map((error, i) => (
                                    <Error key={i} msg={error} />
                                ))}
                                <form onSubmit={handleSubmit}>
                                    <div className="mb-3">
                                        <label htmlFor="username" className="form-label">Username</label>
                                        <input type="text" className="form-control" id="username" value={username} onChange={(event) => setUsername(event.target.value)} />
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="password" className="form-label">Password</label>
                                        <input type="password" className="form-control" id="password" value={password} onChange={(event) => setPassword(event.target.value)} />
                                    </div>
                                    <button type="submit" className="btn btn-login">Login</button>
                                </form>
                                {/* Link to Registration Page */}
                                <div className="mt-3">
                                    <p>Don't have an account? <Link to="/register" className="link-register">Register here</Link>.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Footer/>
        </>
    );
};

export default AuthForms;
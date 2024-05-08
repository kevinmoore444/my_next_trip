import NavBar from "../components/NavBar";
import Footer from "../components/Footer";
import { useState } from 'react';
import {useNavigate } from 'react-router-dom';
import Error from "../components/Error";
import '../styles/login.css'

    const AuthForms = () => {
        const [username, setUsername] = useState('');
        const [password, setPassword] = useState('');
        const [confirm, setConfirm] = useState('');
        const [errors, setErrors] = useState([]);
        const [passwordsMatch, setPasswordsMatch] = useState(true);
        const [isPasswordValid, setIsPasswordValid] = useState(true);
        const navigate = useNavigate();

        // RegEx for password validation
        const passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,}$/;

        const handleSubmit = async (event) => {
            event.preventDefault();

            // Clear existing errors
            setErrors([]);

            // Validate the password pattern
            if (!passwordPattern.test(password)) {
                setIsPasswordValid(false);
                setErrors(["Password must contain at least one letter, one number, and one special character."]);
            return;
            } else {
            setIsPasswordValid(true);
            }

            // Check if passwords match
            if (password !== confirm) {
                setPasswordsMatch(false);
                setErrors(["Passwords do not match."]);
            return;
            } else {
                setPasswordsMatch(true);
            }
            //Call server to create account
            const response = await fetch("http://localhost:8080/create_account", {
                    method: "POST",
                    headers: {
                    "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                    username,
                    password,
                    }),
                });
                if (response.status === 201) {
                    navigate("/login", { state: { registrationSuccess: true }});
                } else if (response.status === 400) {
                    setErrors(["Registration failed."]);
                } else {
                    setErrors(["Unknown error."]);
                }
            };
            //Test password for following RegEx Pattern
            const handlePasswordChange = (event) => {
                const value = event.target.value;
                setPassword(value);
                setIsPasswordValid(passwordPattern.test(value));
            };

            //Test confirm to match password
            const handleConfirmChange = (event) => {
                const value = event.target.value;
                setConfirm(value);
                setPasswordsMatch(value === password);
                };


        return (
        <>
            <NavBar/>
            <div className="container my-5">
                <div className="row justify-content-center">
                <div className="col-md-6 container-style">
                    {/* Registration Form */}
                    <div className="card" style={{ borderColor: '#FFBF47' }}>
                    <div className="card-header" style={{ backgroundColor: '#04ADEE', color: 'white' }}>Register</div>
                    <div className="card-body">
                        {errors.map((error, i) => (
                            <Error key={i} msg={error} />
                        ))}
                        <form onSubmit={handleSubmit}>
                        <div className="mb-3">
                            <label htmlFor="username" className="form-label">Username</label>
                            <input type="text" className="form-control" id="username" value={username} onChange={(event) => setUsername(event.target.value)}/>
                        </div>
                        <div className="mb-3">
                                        <label htmlFor="password" className="form-label">Password</label>
                                        <input
                                            type="password"
                                            className={`form-control ${!isPasswordValid ? 'is-invalid' : ''}`}
                                            id="password"
                                            value={password}
                                            onChange={handlePasswordChange}
                                        />
                                        <small className="text-muted">Password must contain at least one letter, one number, and one special character.</small>
                                        {/* User feedback to ensure pw is valid */}
                                        {!isPasswordValid && (
                                            <small className="text-danger"> Password does not meet criteria</small>
                                        )}
                                    </div>
                        <div className="mb-3">
                            <label htmlFor="confirm" className="form-label">Confirm Password</label>
                                <input
                                type="password"
                                className={`form-control ${!passwordsMatch ? 'is-invalid' : ''}`}
                                id="confirm"
                                value={confirm}
                                onChange={handleConfirmChange}
                                />
                                {/* User feedback to ensure confirm matches the password  */}
                                {!passwordsMatch && (
                                <small className="text-danger">Confirm must match the password</small>
                                )}
                        </div>
                        <button type="submit" className="btn" style={{ backgroundColor: '#FFBF47', color: 'white' }}>Register</button>
                        </form>
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
import NavBar from "../components/NavBar";
import Footer from "../components/Footer";

    const AuthForms = () => {

        const containerStyle = {
            backgroundColor: '#f0f0f0',
            padding: '20px',
            borderRadius: '5px',
            boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)'
            };
        


        return (
        <>
            <NavBar/>
            <div className="container my-5">
                <div className="row justify-content-center">
                <div className="col-md-6" style={containerStyle}>
                    {/* Login Form */}
                    <div className="card mb-4" style={{ borderColor: '#FFBF47' }}>
                    <div className="card-header" style={{ backgroundColor: '#04ADEE', color: 'white' }}>Login</div>
                    <div className="card-body">
                        <form>
                        <div className="mb-3">
                            <label htmlFor="loginEmail" className="form-label">Email address</label>
                            <input type="email" className="form-control" id="loginEmail" />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="loginPassword" className="form-label">Password</label>
                            <input type="password" className="form-control" id="loginPassword" />
                        </div>
                        <button type="submit" className="btn" style={{ backgroundColor: '#FFBF47', color: 'white' }}>Login</button>
                        </form>
                    </div>
                    </div>
        
                    {/* Registration Form */}
                    <div className="card" style={{ borderColor: '#FFBF47' }}>
                    <div className="card-header" style={{ backgroundColor: '#04ADEE', color: 'white' }}>Register</div>
                    <div className="card-body">
                        <form>
                        <div className="mb-3">
                            <label htmlFor="registerEmail" className="form-label">Email address</label>
                            <input type="email" className="form-control" id="registerEmail" />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="registerPassword" className="form-label">Password</label>
                            <input type="password" className="form-control" id="registerPassword" />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="confirmPassword" className="form-label">Confirm Password</label>
                            <input type="password" className="form-control" id="confirmPassword" />
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
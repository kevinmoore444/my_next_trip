    import { useState, useEffect } from 'react';
    import NavBar from "../components/NavBar";
    import Footer from '../components/Footer';

    const AddPlaceForm = () => {
    const [formType, setFormType] = useState('attraction'); // Can be 'attraction', 'city', or 'country'
    
    // States for form fields
    const [countries, setCountries] = useState([]);
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [imageUrl, setImageUrl] = useState('');
    const [country, setCountry] = useState('');
    const [costOfLiving, setCostOfLiving] = useState('');
    const [region, setRegion] = useState('');

        useEffect(() => {
            const getCountries = async () => {
            const response = await fetch("http://localhost:8080/api/country", {
                method: "GET",
                headers: {
                Accept: "application/json",
                },
            });
        
            if (response.ok) {
                const data = await response.json();
                setCountries(data);
            }
            };
            getCountries();
        }, []);




    return (
        <>
            <NavBar/>
            <div className="container my-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                <div className="btn-group mb-3">
                    <button className="btn btn-info" onClick={() => setFormType('attraction')}>Add Attraction</button>
                    <button className="btn btn-info" onClick={() => setFormType('city')}>Add City</button>
                    <button className="btn btn-info" onClick={() => setFormType('country')}>Add Country</button>
                </div>
                <div className="card" style={{ borderColor: '#FFBF47' }}>
                    <div className="card-header" style={{ backgroundColor: '#04ADEE', color: 'white' }}>
                    {formType === 'attraction' ? 'Add Attraction' :
                    formType === 'city' ? 'Add City' : 'Add Country'}
                    </div>
                    <div className="card-body">
                    <form>
                        <div className="mb-3">
                        <label htmlFor="name" className="form-label">Name</label>
                        <input type="text" className="form-control" id="name" value={name} onChange={e => setName(e.target.value)} />
                        </div>
                        <div className="mb-3">
                        <label htmlFor="description" className="form-label">Description</label>
                        <textarea className="form-control" id="description" value={description} onChange={e => setDescription(e.target.value)}></textarea>
                        </div>
                        <div className="mb-3">
                        <label htmlFor="imageUrl" className="form-label">Image URL</label>
                        <input type="text" className="form-control" id="imageUrl" value={imageUrl} onChange={e => setImageUrl(e.target.value)} />
                        </div>
                        {formType !== 'country' && (
                        <div className="mb-3">
                            <label htmlFor="country" className="form-label">Country</label>
                            <select className="form-control" id="country" value={country} onChange={e => setCountry(e.target.value)}>
                            {countries.map((c, index) => (
                                <option key={index} value={c.name}>{c.name}</option>
                            ))}
                            </select>
                        </div>
                        )}
                        {formType === 'country' && (
                        <>
                            <div className="mb-3">
                            <label htmlFor="costOfLiving" className="form-label">Cost of Living</label>
                            <select className="form-control" id="costOfLiving" value={costOfLiving} onChange={e => setCostOfLiving(e.target.value)}>
                                <option value="low">Low</option>
                                <option value="moderate">Moderate</option>
                                <option value="high">High</option>
                            </select>
                            </div>
                            <div className="mb-3">
                            <label htmlFor="region" className="form-label">Region</label>
                            <select className="form-control" id="region" value={region} onChange={e => setRegion(e.target.value)}>
                                <option value="Africa">Africa</option>
                                <option value="Asia">Asia</option>
                                <option value="Europe">Europe</option>
                                <option value="North America">North America</option>
                                <option value="Central America">Central America</option>
                                <option value="South America">South America</option>
                                <option value="Oceania">Oceania</option>
                                <option value="Middle East">Middle East</option>
                            </select>
                            </div>
                        </>
                        )}
                        <button type="submit" className="btn" style={{ backgroundColor: '#FFBF47', color: 'white' }}>
                        Submit
                        </button>
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

    export default AddPlaceForm;
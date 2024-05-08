    import { useState, useEffect, useContext } from 'react';
    import { useNavigate } from 'react-router-dom';
    import NavBar from "../components/NavBar";
    import Footer from '../components/Footer';
    import UserAuth from '../context/UserAuth';


    const AddPlaceForm = () => {
    const [formType, setFormType] = useState('attraction'); // Can be 'attraction', 'city', or 'country'
    
    // States for form fields
    const [countries, setCountries] = useState([]);
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [image, setImage] = useState('');
    const [countryId, setCountryId] = useState(6);
    const [costOfLivingOptions, setCostOfLivingOptions] = useState([]);
    const [costOfLiving, setCostOfLiving] = useState({ id: 1, name: "Low" })
    const [regionOptions, setRegionOptions] = useState('');
    const [region, setRegion] = useState({ id: 1, name: "Africa" });
    const navigate = useNavigate();
    const auth = useContext(UserAuth);

        //Use Effect to render the list of countries, cost of living options, and region options in the dropdown menu
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

            const getCostOfLivingOptions = async () => {
                const response = await fetch("http://localhost:8080/api/costofliving", {
                    method: "GET",
                    headers: {
                        Accept: "application/json",
                    },
                });
    
                if (response.ok) {
                    const data = await response.json();
                    setCostOfLivingOptions(data);
                }
            };

            const getRegionOptions = async () => {
                const response = await fetch("http://localhost:8080/api/region", {
                    method: "GET",
                    headers: {
                        Accept: "application/json",
                    },
                });
    
                if (response.ok) {
                    const data = await response.json();
                    setRegionOptions(data);
                }
            };
    
            getCountries();
            getCostOfLivingOptions();
            getRegionOptions();
        }, []);

        // Handle Submit Function
        function handleSubmit(event) {
            event.preventDefault();
            //Define empty url and payload objects to be filled in dynamically
            let url = '';
            let payload = {};
            //Add definition to the payload and url based upon form type
            if (formType === 'attraction') {
                url = "http://localhost:8080/api/attraction/user";
                payload = {
                    name,
                    description,
                    image,
                    countryId
                };
            } else if (formType === 'city') {
                url = "http://localhost:8080/api/city/user";
                payload = {
                    name,
                    description,
                    image,
                    countryId
                };
            } else if (formType === 'country') {
                url = "http://localhost:8080/api/country/user";
                payload = {
                    name,
                    description,
                    image,
                    costOfLiving,
                    region
                };
            }
            //Submit the post request, passing in the url and payload objects which were just built. 
            fetch(url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    Authorization: `Bearer ${auth.user.token}`,
                },
                body: JSON.stringify(payload),
            })
            .then(res => {
                if (res.ok) {
                    navigate(`/add`);
                    setName('')
                    setDescription('')
                    setImage('')
                    setCountryId(6)
                } else if (res.status === 400) {
                    console.log(res);
                } else {
                  //If the response code is anything else, reject promise and throw code execution to .catch()
                    return Promise.reject(
                    new Error(`Unexpected status code: ${res.status}`)
                    );
                }
                })
                .catch(console.error); 
        }
    

    //Dynamically render 1 of 3 different forms based upon selection
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
                    <form onSubmit={handleSubmit}>
                        <div className="mb-3">
                        <label htmlFor="name" className="form-label">Name</label>
                        <input type="text" className="form-control" id="name" value={name} onChange={e => setName(e.target.value)} />
                        </div>
                        <div className="mb-3">
                        <label htmlFor="description" className="form-label">Description</label>
                        <textarea className="form-control" id="description" value={description} onChange={e => setDescription(e.target.value)}></textarea>
                        </div>
                        <div className="mb-3">
                        <label htmlFor="image" className="form-label">Image URL</label>
                        <input type="text" className="form-control" id="image" value={image} onChange={e => setImage(e.target.value)} />
                        </div>
                        {formType !== 'country' && (
                        <div className="mb-3">
                        <label htmlFor="country" className="form-label">Country</label>
                            <select className="form-control" id="country" value={countryId} onChange={e => setCountryId(e.target.value)}>
                                {countries.map((c, index) => (
                                    <option key={index} value={c.id}>{c.name}</option>
                                ))}
                            </select>
                        </div>
                        )}
                        {formType === 'country' && (
                        <>
                            <div className="mb-3">
                                <label htmlFor="costOfLiving" className="form-label">Cost of Living</label>
                                    <select 
                                        className="form-control" 
                                        id="costOfLiving" 
                                        value={costOfLiving.id} 
                                        onChange={e => {
                                            // Find the selected option based on the selected ID
                                            const selectedOption = costOfLivingOptions.find(col => col.id === parseInt(e.target.value));
                                            // Update the state with the entire object
                                            setCostOfLiving(selectedOption);
                                        }}>
                                        {/* Map Out all options as options on a select menu - displays the same, but stores the id as a value */}
                                        {costOfLivingOptions.map((col, index) => (
                                            <option key={index} value={col.id}>{col.name}</option>
                                        ))}
                                    </select>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="region" className="form-label">Region</label>
                                    <select 
                                        className="form-control" 
                                        id="region" 
                                        value={region.id} 
                                        onChange={e => {
                                            // Find the selected option based on the selected ID
                                            const selectedOption = regionOptions.find(col => col.id === parseInt(e.target.value));
                                            // Update the state with the entire object
                                            setRegion(selectedOption);
                                        }}>
                                        {/* Map Out all options as options on a select menu - displays the same, but stores the id as a value */}
                                        {regionOptions.map((col, index) => (
                                            <option key={index} value={col.id}>{col.name}</option>
                                        ))}
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
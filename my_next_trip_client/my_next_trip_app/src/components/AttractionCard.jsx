import { useState, useContext, useEffect } from 'react';
import UserAuth from '../context/UserAuth';
import '../styles/attractioncard.css';

const AttractionCard = ({ attraction, toggleRefresh }) => {
    const [inList, setInList] = useState(false);
    const auth = useContext(UserAuth);
    const appUserId = auth.user?.app_user_id;
    const token = auth.user?.token

    //Check if city is already in userList or not
    useEffect(() => {

        // Only proceed if appUserId is available (logged in).
        if (!appUserId || !token) return;

        const checkIfInList = async () => {
            const response = await fetch(`http://localhost:8080/api/attraction/user-list/${attraction.id}/${appUserId}`, {
                    method: 'GET',
                    headers: {
                        Accept: 'application/json',
                        Authorization: `Bearer ${token}`
                    }
                });
                
                if (response.ok) {
                    const isInList = await response.json();
                    // Assume API returns a boolean in "isInList" field
                    setInList(isInList);
                } 
        };
        checkIfInList();
    }, [attraction.id, appUserId, token]);

    function handleToggle(event) {
        event.preventDefault();
        if (inList) {
            // Remove the city from the list
            const success =  removeAttractionFromList(attraction.id);
            if (success) setInList(false);
        } else {
            // Add the city to the list
            const success = addAttractionToList(attraction.id);
            if (success) setInList(true);
        }
    }

    function removeAttractionFromList(attractionId){
        return fetch(`http://localhost:8080/api/attraction/user-list/${attractionId}/${appUserId}`, {
            method: 'DELETE',
            headers: {
                Accept: "application/json",
                Authorization: `Bearer ${auth.user.token}`
            }
        })
        .then(res => {
            if (res.ok) {
                toggleRefresh();
                return true;
            } else {
                return false;
            }
        })
        .catch(console.error);
    }


    function addAttractionToList(attractionId) {
        //Return keyword will return the result of the fetch (true or false) to the handle toggle function.
        return fetch(`http://localhost:8080/api/attraction/user-list/${attractionId}/${appUserId}`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json",
                Authorization: `Bearer ${auth.user.token}`,
            },
        })
        .then(res => {
            if (res.ok) {
                return true;
            } else if (res.status === 400) {
                return false;
            } else {
              //If the response code is anything else, reject promise and throw code execution to .catch()
                return Promise.reject(
                new Error(`Unexpected status code: ${res.status}`)
                );
            }
            })
        .catch(console.error); 
        }




    return (
            <div className="card attraction-card mb-3" style={{ borderColor: '#FFBF47', cursor: 'pointer' }}>
                <img src={attraction.image} className="card-img-top" alt={attraction.name} style={{ maxHeight: '200px', objectFit: 'cover' }} />
                <div className="card-body" style={{ color: 'white' }}>
                    <h5 className="card-title">{attraction.name}</h5>
                    <p className="card-text">{attraction.description}</p>
                {/* Toggle Button */}
                {appUserId && (
                    <button
                        className={`toggle-button ${inList ? 'remove' : 'add'}`}
                        onClick={(event) => handleToggle(event)}
                    >
                        {inList ? 'Remove from List' : 'Add to List'}
                    </button>
                )}
                </div>
            </div>
    );
};

export default AttractionCard;
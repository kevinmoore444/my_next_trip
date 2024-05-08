import { Link } from 'react-router-dom';
import '../styles/attractioncard.css';

const AttractionCard = ({ attraction }) => {
    

    return (
            <div className="card attraction-card mb-3" style={{ borderColor: '#FFBF47', cursor: 'pointer' }}>
                <img src={attraction.image} className="card-img-top" alt={attraction.name} style={{ maxHeight: '200px', objectFit: 'cover' }} />
                <div className="card-body" style={{ color: 'white' }}>
                    <h5 className="card-title">{attraction.name}</h5>
                    <p className="card-text">{attraction.description}</p>
                </div>
            </div>
    );
};

export default AttractionCard;
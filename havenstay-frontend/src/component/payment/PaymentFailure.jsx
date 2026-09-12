import React from "react";
import { useNavigate, useParams } from "react-router-dom";

const PaymentFailure = () => {
    const navigate = useNavigate();
    const { bookingReference } = useParams();

    return (
        <div className="payment-result">
            <h2>Payment Failed</h2>
            <p>Your payment could not be completed.</p>
            <p>Booking Reference: <strong>{bookingReference}</strong></p>

            <button onClick={() => navigate(`/payment/${bookingReference}`)}>
                Try Payment Again
            </button>

            <button onClick={() => navigate("/home")}>
                Go to Home
            </button>
        </div>
    );
};

export default PaymentFailure;
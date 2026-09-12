import React from "react";
import { useNavigate, useParams } from "react-router-dom";

const PaymentSuccess = () => {
    const navigate = useNavigate();
    const { bookingReference } = useParams();

    return (
        <div className="payment-result">
            <h2>Payment Successful!</h2>
            <p>Your payment has been successfully completed.</p>
            <p>Booking Reference: <strong>{bookingReference}</strong></p>

            <button onClick={() => navigate("/profile")}>
                View My Bookings
            </button>

            <button onClick={() => navigate("/home")}>
                Go to Home
            </button>
        </div>
    );
};

export default PaymentSuccess;
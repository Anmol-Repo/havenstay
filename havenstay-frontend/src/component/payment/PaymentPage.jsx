import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useRazorpay } from "react-razorpay";
import ApiService from "../../service/ApiService";

const PaymentPage = () => {
    const { bookingReference } = useParams();
    const navigate = useNavigate();

    const { error: razorpayError, isLoading, Razorpay } = useRazorpay();

    const [error, setError] = useState("");
    const [isProcessing, setIsProcessing] = useState(false);

    useEffect(() => {
        if (razorpayError) {
            setError(razorpayError.message || "Unable to load Razorpay");
        }
    }, [razorpayError]);

    const handlePayment = async () => {
        setError("");
        setIsProcessing(true);

        try {
            // Ask backend to create the Razorpay order.
            const orderId = await ApiService.proceedForPayment({
                bookingReference: bookingReference
            });

            const options = {
                key: process.env.REACT_APP_RAZORPAY_KEY_ID,
                amount: 0, // Razorpay order already contains the server-side amount.
                currency: "INR",
                name: "HavenStay",
                description: `Payment for booking ${bookingReference}`,
                order_id: orderId,

                handler: async (response) => {
                    try {
                        const paymentData = {
                            bookingReference: bookingReference,
                            razorpayPaymentId: response.razorpay_payment_id,
                            razorpayOrderId: response.razorpay_order_id,
                            razorpaySignature: response.razorpay_signature
                        };

                        const verified =
                            await ApiService.verifyPayment(paymentData);

                        if (verified === true) {
                            navigate(
                                `/payment-success/${bookingReference}`
                            );
                        } else {
                            navigate(
                                `/payment-failure/${bookingReference}`
                            );
                        }
                    } catch (error) {
                        setIsProcessing(false);
                        setError(
                            error.response?.data?.message || error.message
                        );
                    }
                },

                modal: {
                    ondismiss: () => {
                        setIsProcessing(false);
                    }
                },

                theme: {
                    color: "#007F86"
                }
            };

            const razorpayInstance = new Razorpay(options);
            razorpayInstance.open();

        } catch (error) {
            setIsProcessing(false);
            setError(
                error.response?.data?.message || error.message
            );
        }
    };

    return (
        <div className="payment-form">
            <h2>Complete Your Payment</h2>

            <p>
                Booking Reference: <strong>{bookingReference}</strong>
            </p>

            {error && (
                <p className="error-message">
                    {error}
                </p>
            )}

            <button
                className="payment-button"
                onClick={handlePayment}
                disabled={isLoading || isProcessing}
            >
                {isProcessing ? "Processing..." : "Pay Now"}
            </button>
        </div>
    );
};

export default PaymentPage;
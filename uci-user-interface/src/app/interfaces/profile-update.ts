export interface ProfileUpdate {
    "full_name": string,
    "organization": string,
    "email": string,
    "phone_number": string,
    "address": {
        "city": string,
        "state": string,
        "country": string,
        "primary_address": string,
        "postal_code": string
    } | null,
    "rating": string,
    "delivery_count": string,
    "profile_image": "",
    "subscription_type": string,
    "period": string,
}

export interface ProfileResponse{
    "_id": string,
    "full_name": string,
    "organization": string,
    "email": string,
    "phone_number": string,
    "address": {
        "city": string | null,
        "state": string | null,
        "country": string | null,
        "primary_address": string | null,
        "postal_code": string | null
    } | null,
    "rating": number,
    "delivery_count": number,
    "profile_image": "",
    "subscription": {
        "_id": string | null,
        "subscription_type": string | null,
        "period": string | null,
        "start_date": string | null,
        "end_date": string | null
    } | null
}
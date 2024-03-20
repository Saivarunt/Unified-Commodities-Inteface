export interface LoginResponse {
    "user": {
        "_id": string,
        "username": string,
        "password": string,
        "authorities": [
            {
                "_id": string,
                "authority": string,
                "permissions": [
                    {
                        "_id": string,
                        "permission": string
                    }
                ]
            }
        ],
        "permissions": [
            {
                "_id": string,
                "permission": string
            }
        ],
        "profile": {
            "_id": string,
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
            "subscription": {
                "_id": string,
                "subscription_type": string,
                "period": string,
                "start_date": string,
                "end_date": string
            } | null
        },
        "enabled": boolean,
        "accountNonLocked": boolean,
        "credentialsNonExpired": boolean,
        "accountNonExpired": boolean
    },
    "jwt": string
}

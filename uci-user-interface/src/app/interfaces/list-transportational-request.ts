import { ProfileResponse } from "./profile-response"

export interface ListTransportationalRequest {
    _id: string,
    product: {
        "_id": string,
        "product_name": string,
        "owner": ProfileResponse,
        "description": string,
        "price": number,
        "quantity": number,
        "product_image": string,
        "transportation_type": string
    },
    user: ProfileResponse,
    quantity: number,
    status: string,
    posted_at: string

}

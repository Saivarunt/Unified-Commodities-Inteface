import { ProfileResponse } from "./profile-response"

export interface ProductListing {
    "_id": string,
    "product_name": string,
    "owner": ProfileResponse,
    "description": string,
    "price": number,
    "quantity": number,
    "product_image": any,
    "transportation_type": string
}

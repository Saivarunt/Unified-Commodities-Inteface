import { UserInfo } from "./user-info";

export interface Products {
    "_id": string,
    "product_name": string,
    "owner": UserInfo,
    "description": string,
    "price": number,
    "quantity": number,
    "product_image": any,
    "transportation_type": string
}
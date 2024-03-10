import { Products } from "./products"
import { UserInfo } from "./user-info"

export interface Requests {
    _id: string,
    product: Products,
    user: UserInfo,
    quantity: number,
    type: string,
    status: string
}
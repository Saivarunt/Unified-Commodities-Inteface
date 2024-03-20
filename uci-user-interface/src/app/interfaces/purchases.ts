import { Products } from "./products"
import { UserInfo } from "./user-info"

export interface Purchases {
    _id: string,
    product: Products,
    buyer: UserInfo
    quantity: number,
    price: number,
    transportation_type: string
}

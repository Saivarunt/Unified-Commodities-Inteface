import { Requests } from "./requests";
import { UserInfo } from "./user-info";

export interface Proposals {
    _id: string,
    request: Requests,
    transporter: UserInfo,
    status: string
}
